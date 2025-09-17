package com.example.mobiledoctor;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Activity to display Google Map, show nearby hospitals and pharmacies,
 * and handle marker interactions with custom info windows.
 */
public class MapActivity extends BaseActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.InfoWindowAdapter,
        GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MapActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int SEARCH_RADIUS_METERS = 2000;
    private static final String METADATA_API_KEY = "com.example.mobiledoctor.PLACES_API_KEY";

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private PlacesClient placesClient;
    private String placesApiKey;

    private LatLng currentLocation;    // ← 추가

    private LocationCallback locationCallback;


    // Map marker to its type and details
    private final Map<Marker, String> markerTypes = new HashMap<>();
    private final Map<Marker, PlaceDetails> detailsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 돋보기 상태 적용
        applyZoomState();  // BaseActivity에서 구현된 메소드 호출

        initializeToolbar();
        loadPlacesApiKey();
        initializePlacesSdk();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermissionsOrInit();
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_map);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadPlacesApiKey() {
        try {
            ApplicationInfo ai = getPackageManager()
                    .getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            placesApiKey = ai.metaData.getString(METADATA_API_KEY);
        } catch (Exception e) {
            Toast.makeText(this, "API_KEY 읽기 실패", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializePlacesSdk() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), placesApiKey);
        }
        placesClient = Places.createClient(this);
    }

    private void requestLocationPermissionsOrInit() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            initMapFragment();
        }
    }

    private void initMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.length == 2
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            initMapFragment();
        } else {
            Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdates() {
        // 1) 권한 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 권한 없으면 요청하거나 그냥 반환
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                    LOCATION_PERMISSION_REQUEST_CODE
            );
            return;
        }

        // 2) 위치 요청 설정
        LocationRequest req = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)        // 5초마다
                .setFastestInterval(2000);// 최소 2초마다

        // 3) 콜백 정의 (위치만 저장)
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult result) {
                if (result == null) return;
                Location loc = result.getLastLocation();
                currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                Log.d(TAG, "🔄 위치 업데이트: "
                        + currentLocation.latitude + ", "
                        + currentLocation.longitude);
            }
        };

        // 4) 업데이트 요청
        fusedLocationClient.requestLocationUpdates(
                req,
                locationCallback,
                Looper.getMainLooper()
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        enableMyLocation();
        startLocationUpdates();   // ← 추가!
        map.setInfoWindowAdapter(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);

        // Pad map below toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_map);
        toolbar.post(() -> googleMap.setPadding(0, toolbar.getHeight(), 0, 0));
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                new com.google.android.gms.tasks.CancellationTokenSource().getToken()
        ).addOnSuccessListener(loc -> {
            if (loc == null) {
                Toast.makeText(this,
                        "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1) currentLocation 에 저장
            currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());

            // 2) 지도 카메라 이동 및 주변 검색
            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLocation, 15f)
            );
            searchNearbyPlaces(currentLocation, "hospital");
            searchNearbyPlaces(currentLocation, "pharmacy");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 위에서 만든 menu_map.xml 을 inflate
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }


    private void searchNearbyPlaces(LatLng location, String type) {
        String url = String.format(Locale.getDefault(),
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                        + "?location=%.6f,%.6f"
                        + "&radius=%d"
                        + "&type=%s"
                        + "&language=ko"
                        + "&key=%s",
                location.latitude, location.longitude,
                SEARCH_RADIUS_METERS, type, placesApiKey);
        Log.d(TAG, "searchNearby URL: " + url);

        new OkHttpClient().newCall(new Request.Builder().url(url).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "Nearby search failed", e);
                    }

                    @Override
                    public void onResponse(Call call, Response resp) throws IOException {
                        if (!resp.isSuccessful()) return;
                        try {
                            JSONObject root = new JSONObject(resp.body().string());
                            if (!"OK".equals(root.optString("status"))) return;
                            JSONArray arr = root.optJSONArray("results");
                            runOnUiThread(() -> addMarkers(arr, type));
                        } catch (Exception ex) {
                            Log.e(TAG, "Parsing nearby search error", ex);
                        }
                    }
                });
    }

    private void addMarkers(JSONArray arr, String type) {
        if (arr == null) return;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.optJSONObject(i);
            if (o == null) continue;

            // 위치
            LatLng pos = new LatLng(
                    o.optJSONObject("geometry")
                            .optJSONObject("location")
                            .optDouble("lat"),
                    o.optJSONObject("geometry")
                            .optJSONObject("location")
                            .optDouble("lng")
            );

            // opening_hours 정보 파싱
            JSONObject oh = o.optJSONObject("opening_hours");

            String status;
            @DrawableRes int iconRes;

            if (oh == null) {
                // 정보 자체가 없으면 “영업시간 정보 없음” + ‘?’ 아이콘
                status  = "영업시간 정보 없음";
                iconRes = type.equals("hospital")
                        ? R.drawable.ic_hospital_unknown
                        : R.drawable.ic_pharmacy_unknown;
            }
            else if (oh.optBoolean("open_now", false)) {
                // open_now=true → 영업 중
                status  = "영업 중";
                iconRes = type.equals("hospital")
                        ? R.drawable.ic_hospital_open
                        : R.drawable.ic_pharmacy_open;
            }
            else {
                // open_now=false → 영업 종료
                status  = "영업 종료";
                iconRes = type.equals("hospital")
                        ? R.drawable.ic_hospital_closed
                        : R.drawable.ic_pharmacy_closed;
            }

            // 마커 생성
            MarkerOptions opt = new MarkerOptions()
                    .position(pos)
                    .title(o.optString("name", "이름 없음"))
                    .snippet(status)
                    .icon(bitmapDescriptorFromVector(iconRes, 48, 48));

            Marker m = googleMap.addMarker(opt);
            if (m != null) {
                markerTypes.put(m, type);
                m.setTag(o.optString("place_id"));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        fetchPlaceDetails((String) marker.getTag(), marker);
        return false;
    }

    private void fetchPlaceDetails(String placeId, Marker marker) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&key=%s",
                placeId, placesApiKey);
        Log.d(TAG, "fetchPlaceDetails URL: " + url);

        new OkHttpClient().newCall(new Request.Builder().url(url).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "Place details failed", e);
                    }

                    @Override
                    public void onResponse(Call call, Response resp) throws IOException {
                        if (!resp.isSuccessful()) return;
                        try {
                            JSONObject result = new JSONObject(resp.body().string())
                                    .getJSONObject("result");
                            String addr = result.optString("formatted_address", "");
                            String phone = result.optString("formatted_phone_number", "N/A");
                            JSONObject oh = result.optJSONObject("current_opening_hours");
                            String hours = "영업시간 정보 없음";
                            if (oh != null) {
                                boolean now = oh.optBoolean("open_now", false);
                                hours = now ? "영업 중" : "영업 종료";
                                if (oh.has("weekday_text")) {
                                    JSONArray wd = oh.getJSONArray("weekday_text");
                                    StringBuilder sb = new StringBuilder();
                                    for (int j = 0; j < wd.length(); j++)
                                        sb.append(wd.getString(j)).append("\n");
                                    hours = sb.toString();
                                }
                            }
                            String cleanAddr = addr.contains("<")
                                    ? Html.fromHtml(addr).toString() : addr;
                            detailsMap.put(marker, new PlaceDetails(cleanAddr, phone, hours));
                            runOnUiThread(marker::showInfoWindow);
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing details", e);
                        }
                    }
                });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if ("pharmacy".equals(markerTypes.get(marker))) {
            PlaceDetails d = detailsMap.get(marker);
            InventoryActivity.launch(this, marker.getTitle(),
                    d.address, d.phone);
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        // getInfoWindow 을 쓰고 있으므로 여기서는 null 을 리턴
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // 아예 이 뷰 전체를 InfoWindow 로 쓰겠다!
        View v = getLayoutInflater().inflate(R.layout.info_window, null);
        TextView t1 = v.findViewById(R.id.title);
        TextView t2 = v.findViewById(R.id.snippet);
        TextView tAddr = v.findViewById(R.id.address);
        TextView tPhone = v.findViewById(R.id.phone);
        TextView tHours = v.findViewById(R.id.opening_hours);
        Button btnStock = v.findViewById(R.id.btn_inventory);

        t1.setText(marker.getTitle());

        String status = marker.getSnippet();
        t2.setText(status);
        int colorRes = "영업 중".equals(status)
                ? android.R.color.holo_green_dark
                : android.R.color.holo_red_dark;
        t2.setTextColor(ContextCompat.getColor(MapActivity.this, colorRes));

        PlaceDetails d = detailsMap.get(marker);
        if (d != null) {
            tAddr.setText("주소: " + d.address);
            tPhone.setText("전화: " + d.phone);
            tHours.setText("영업 시간:\n" + d.hours);
        }

        boolean isPharm = "pharmacy".equals(markerTypes.get(marker));
        btnStock.setVisibility(isPharm ? View.VISIBLE : View.GONE);
        btnStock.setOnClickListener(x -> onInfoWindowClick(marker));

        // Apply zoom state (font size)
        float textSize = getTextSizeBasedOnZoomState();
        t1.setTextSize(textSize);
        t2.setTextSize(textSize);
        tAddr.setTextSize(textSize);
        tPhone.setTextSize(textSize);
        tHours.setTextSize(textSize);

        return v;
    }

    private float getTextSizeBasedOnZoomState() {
        boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
        return isZoomEnabled ? 30f : 16f; // Zoomed-in size vs. default size
    }

    private BitmapDescriptor bitmapDescriptorFromVector(@DrawableRes int vectorResId,
                                                        int wDp, int hDp) {
        Drawable d = ContextCompat.getDrawable(this, vectorResId);
        int w = dpToPx(wDp), h = dpToPx(hDp);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        d.setBounds(0, 0, w, h);
        d.draw(c);
        return BitmapDescriptorFactory.fromBitmap(bmp);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_refresh) {
            if (currentLocation != null) {
                // ① 기존에 지도에 올라가 있던 마커 전부 지우기
                googleMap.clear();

                // ② 마지막으로 받은 currentLocation 기준으로 다시 주변 검색
                searchNearbyPlaces(currentLocation, "hospital");
                searchNearbyPlaces(currentLocation, "pharmacy");

                Toast.makeText(this, "주변 정보가 갱신되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                enableMyLocation();  // 아직 위치를 못 받았다면 위치 권한/조회부터 다시
                Toast.makeText(this, "위치를 다시 가져옵니다…", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // Holder for place details
    private static class PlaceDetails {
        final String address, phone, hours;

        PlaceDetails(String a, String p, String h) {
            address = a;
            phone = p;
            hours = h;
        }
    }
}