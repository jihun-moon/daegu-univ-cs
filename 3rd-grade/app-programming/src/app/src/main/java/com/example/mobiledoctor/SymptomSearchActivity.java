package com.example.mobiledoctor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SymptomSearchActivity extends BaseActivity {

    // UI
    private AutoCompleteTextView spCategory, spSub;
    private TextView tvMedicineCount, tvHospitalCount, tvPharmacyCount;
    private RecyclerView rvHospitals, rvPharmacies, rvMedResults;

    // Location
    private LatLng currentLocation;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private String placesApiKey;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int SEARCH_RADIUS = 1000; // 1km
    private static final String TAG = "SymptomSearchActivity";

    // Data
    private Map<String, List<String>> categoryMap = new HashMap<>();
    private Map<String, List<Medicine>> medicineData = new HashMap<>();

    // 장소+좌표 보관용
    private static class PlaceItem {
        final String name;
        final double lat, lng;
        PlaceItem(String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_search);

        // 돋보기 상태 적용
        applyZoomState();  // BaseActivity에서 구현된 메소드 호출

        // 1) UI 초기화
        spCategory      = findViewById(R.id.spinner_category);
        spSub           = findViewById(R.id.spinner_sub);
        tvMedicineCount = findViewById(R.id.medicine_count);
        tvHospitalCount = findViewById(R.id.nearby_hospitals_count);
        tvPharmacyCount = findViewById(R.id.nearby_pharmacies_count);

        rvHospitals   = findViewById(R.id.rv_nearby_hospitals);
        rvPharmacies  = findViewById(R.id.rv_nearby_pharmacies);
        rvMedResults  = findViewById(R.id.rv_med_results);

        rvHospitals.setLayoutManager(new LinearLayoutManager(this));
        rvPharmacies.setLayoutManager(new LinearLayoutManager(this));
        rvMedResults.setLayoutManager(new LinearLayoutManager(this));

        // 2) Toolbar 설정
        MaterialToolbar toolbar = findViewById(R.id.toolbar_symptom_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 3) 데이터 초기화
        initCategoryMap();
        initMedicineData();

        // 4) Spinner & Adapter 세팅
        setupMedicineSpinners();

        // 5) 위치 권한 및 API 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        placesApiKey        = getString(R.string.PLACES_API_KEY);
        requestLocationPermission();
    }

    // — Spinner 세팅
    private void setupMedicineSpinners() {
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<>(categoryMap.keySet())
        ) {
            @NonNull @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                boolean zoom = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                        .getBoolean("isZoomEnabled", false);
                tv.setTextSize(zoom ? 30f : 16f);
                return tv;
            }

            @NonNull @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                boolean zoom = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                        .getBoolean("isZoomEnabled", false);
                tv.setTextSize(zoom ? 30f : 16f);
                return tv;
            }
        };

        catAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spCategory.setAdapter(catAdapter);
        spCategory.setThreshold(1);
        spCategory.setOnClickListener(v -> spCategory.showDropDown());

        spCategory.setOnItemClickListener((parent, view, pos, id) -> {
            String key = catAdapter.getItem(pos);
            List<String> subs = categoryMap.getOrDefault(key, Collections.emptyList());

            ArrayAdapter<String> subAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_dropdown_item_1line, subs
            ) {
                @NonNull @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    TextView tv = (TextView) super.getView(position, convertView, parent);
                    boolean zoom = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                            .getBoolean("isZoomEnabled", false);
                    tv.setTextSize(zoom ? 30f : 16f);
                    return tv;
                }
                @NonNull @Override
                public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                    TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
                    boolean zoom = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                            .getBoolean("isZoomEnabled", false);
                    tv.setTextSize(zoom ? 30f : 16f);
                    return tv;
                }
            };
            subAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spSub.setAdapter(subAdapter);
            spSub.setThreshold(1);
            spSub.setOnClickListener(v -> spSub.showDropDown());
            spSub.setText("", false);
        });

        spSub.setOnItemClickListener((parent, view, pos, id) -> {
            String catKey = spCategory.getText().toString();
            List<Medicine> meds = medicineData.getOrDefault(catKey, Collections.emptyList());
            tvMedicineCount.setText("계열별 약 검색 수: " + meds.size());
            rvMedResults.setAdapter(new MedicineAdapter(meds, this));
        });
    }

    // — 주변 장소 검색
    @SuppressLint("MissingPermission")
    private void fetchNearbyPlaces(String type) {
        if (currentLocation == null) return;
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + currentLocation.latitude + "," + currentLocation.longitude
                + "&radius=" + SEARCH_RADIUS
                + "&type=" + type
                + "&key=" + placesApiKey;

        new OkHttpClient().newCall(new Request.Builder().url(url).build())
                .enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {
                        Log.e(TAG, type + " 검색 실패", e);
                    }
                    @Override public void onResponse(Call call, Response resp) throws IOException {
                        if (!resp.isSuccessful()) return;
                        try {
                            JSONObject root = new JSONObject(resp.body().string());
                            JSONArray arr = root.optJSONArray("results");
                            List<PlaceItem> items = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject o = arr.getJSONObject(i);
                                String name = o.optString("name");
                                double lat = o.getJSONObject("geometry")
                                        .getJSONObject("location")
                                        .optDouble("lat");
                                double lng = o.getJSONObject("geometry")
                                        .getJSONObject("location")
                                        .optDouble("lng");
                                items.add(new PlaceItem(name, lat, lng));
                            }
                            runOnUiThread(() -> {
                                if (type.equals("hospital")) {
                                    tvHospitalCount.setText("병원 수: " + items.size());
                                    // Context를 명시적으로 SymptomSearchActivity.this로 전달
                                    rvHospitals.setAdapter(new PlaceAdapter(items, SymptomSearchActivity.this));  // Context 전달
                                } else {
                                    tvPharmacyCount.setText("약국 수: " + items.size());
                                    rvPharmacies.setAdapter(new PlaceAdapter(items, SymptomSearchActivity.this));  // Context 전달
                                }
                            });
                        } catch (JSONException ex) {
                            Log.e(TAG, "파싱 에러", ex);
                        }
                    }
                });
    }

    // — 권한 요청 & 위치 업데이트
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(loc -> {
                        if (loc != null) {
                            currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                            fetchNearbyPlaces("hospital");
                            fetchNearbyPlaces("pharmacy");
                        }
                    });
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);

        locationCallback = new LocationCallback() {
            @Override public void onLocationResult(LocationResult result) {
                if (result == null) return;
                currentLocation = new LatLng(
                        result.getLastLocation().getLatitude(),
                        result.getLastLocation().getLongitude()

                );
                Log.d(TAG, "🔄 위치 업데이트: "
                        + currentLocation.latitude + ", "
                        + currentLocation.longitude);
            }
        };
        fusedLocationClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
        );
    }

    @Override
    public void onRequestPermissionsResult(
            int code, @NonNull String[] perms, @NonNull int[] res) {
        super.onRequestPermissionsResult(code, perms, res);
        if (code == LOCATION_PERMISSION_REQUEST_CODE
                && res.length>0
                && res[0]==PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_symptom_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish(); return true;
        }
        if (item.getItemId()==R.id.action_refresh && currentLocation!=null) {
            fetchNearbyPlaces("hospital");
            fetchNearbyPlaces("pharmacy");
            Toast.makeText(this,
                    "주변 정보가 갱신되었습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationCallback!=null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    // — 데이터 초기화
    private void initCategoryMap() {
        categoryMap.put("두통 계열", Arrays.asList("두통","편두통","머리아픔","두통약"));
        categoryMap.put("피부 계열", Arrays.asList("알레르기","피부염","발진","여드름","가려움"));
        categoryMap.put("소화 계열", Arrays.asList("소화불량","속쓰림","위염","식도염"));
        categoryMap.put("감기 계열", Arrays.asList("감기","콧물","목아픔","기침","인후염"));
        categoryMap.put("염증 계열", Arrays.asList("염증","통증","근육통","관절염"));
        categoryMap.put("심혈관 계열", Arrays.asList("고혈압","혈전","심장병"));
        categoryMap.put("호흡기 계열", Arrays.asList("천식","기침","호흡곤란","폐렴"));
        categoryMap.put("정신건강 계열", Arrays.asList("우울증","불안","불면증"));
        categoryMap.put("당뇨 계열", Arrays.asList("당뇨","혈당","인슐린"));
        categoryMap.put("기타", Collections.singletonList("기타"));
    }

    private void initMedicineData() {
        // 두통 계열 (4개)
        medicineData.put("두통 계열", Arrays.asList(
                new Medicine("타이레놀",     "두통·발열 완화",    "500mg 4~6h", "₩3,000", R.drawable.img_tylenol),
                new Medicine("게보린",       "두통 완화",         "400mg 4h",   "₩3,500", R.drawable.img_gevorin),
                new Medicine("이부프로펜",   "통증·염증 완화",    "200mg 3회",  "₩2,500", R.drawable.img_ibuprofen)
        ));

        // 감기 계열 (5개)
        medicineData.put("감기 계열", Arrays.asList(
                new Medicine("판피린",       "감기 증상 완화",      "2정 3회",    "₩2,500", R.drawable.img_panpyrin),
                new Medicine("콜대원",       "기침·가래 완화",      "1포 3회",    "₩4,000", R.drawable.img_coldawon),
                new Medicine("테라플루",     "감기 복합 증상 완화", "1포 4회",    "₩5,500", R.drawable.img_theraflu),
                new Medicine("판콜",         "콧물·재채기 완화",    "2정 3회",    "₩3,200", R.drawable.img_pancol),
                new Medicine("타이레놀 콜드","감기 통증·해열",      "500mg 4회",  "₩4,200", R.drawable.img_tylenolcold)
        ));

        // 소화 계열 (5개)
        medicineData.put("소화 계열", Arrays.asList(
                new Medicine("겔포스",       "위산 중화",           "1포 식전·식후", "₩1,500", R.drawable.img_gelfos),
                new Medicine("베아제",       "소화불량 개선",       "2정 식전",      "₩1,800", R.drawable.img_beazyme),
                new Medicine("우르사",       "간 보호",             "1정 3회",       "₩3,200", R.drawable.img_ursa),
                new Medicine("가스터정",     "위염·속쓰림 완화",    "1정 2회",       "₩2,200", R.drawable.img_gastertab),
                new Medicine("모사프리드",   "위장 운동 촉진",      "5mg 3회",       "₩2,600", R.drawable.img_mosapride)
        ));

        // 피부 계열 (4개)
        medicineData.put("피부 계열", Arrays.asList(
                new Medicine("센텔라 크림", "가려움 완화",     "적당량 도포",   "₩5,000", R.drawable.img_centella_cream),
                new Medicine("벤젠크림",     "발진 억제",       "1일 2회",       "₩4,500", R.drawable.img_benzocaine_cream),
                new Medicine("히루닥",       "상처 치료",       "적용부위 도포", "₩6,000", R.drawable.img_hirudac),
                new Medicine("스테로이드 연고","염증 완화",     "1일 1회",       "₩3,800", R.drawable.img_steroid_ointment)
        ));

        // 염증 계열 (4개)
        medicineData.put("염증 계열", Arrays.asList(
                new Medicine("나프록센",   "염증·통증 완화",    "220mg 2회", "₩4,000", R.drawable.img_naproxen),
                new Medicine("피록시캄",   "염증 완화",         "20mg 1회",  "₩3,500", R.drawable.img_piroxicam),
                new Medicine("프레드니솔론","중등도 염증 억제", "5mg 1회",   "₩2,000", R.drawable.img_prednisolone),
                new Medicine("아스피린",   "통증·염증 완화",    "325mg 4~6h","₩3,000", R.drawable.img_aspirin)
        ));

        // 심혈관 계열 (4개)
        medicineData.put("심혈관 계열", Arrays.asList(
                new Medicine("로수바스타틴","콜레스테롤 저하", "10mg 1회",   "₩8,000", R.drawable.img_rosuvastatin),
                new Medicine("카르베디롤",  "혈압 강하",       "12.5mg 2회", "₩5,500", R.drawable.img_carvedilol),
                new Medicine("디곡신",      "심박 조절",       "0.25mg 1회", "₩6,000", R.drawable.img_digoxin),
                new Medicine("클로피도그렐","혈전 예방",       "75mg 1회",   "₩7,000", R.drawable.img_clopidogrel)
        ));

        // 호흡기 계열 (5개)
        medicineData.put("호흡기 계열", Arrays.asList(
                new Medicine("알부테롤흡입제","천식 증상 완화",   "1~2회 흡입",  "₩4,500", R.drawable.img_albuterol_inhaler),
                new Medicine("몬테루카스트",  "기관지 확장",       "10mg 1회",    "₩3,000", R.drawable.img_montelukast),
                new Medicine("암브록솔",      "가래 배출 도움",     "30mg 3회",    "₩2,800", R.drawable.img_ambroxol),
                new Medicine("타이로민",      "콧물 완화",         "1포 3회",     "₩4,000", R.drawable.img_tyromine),
                new Medicine("스테로이드 흡입제","천식 염증 억제","1일 2회 흡입","₩7,500", R.drawable.img_inhaled_corticosteroid)
        ));

        // 정신건강 계열 (4개)
        medicineData.put("정신건강 계열", Arrays.asList(
                new Medicine("시탈로프람", "우울증 치료",     "20mg 1회",  "₩5,500", R.drawable.img_citalopram),
                new Medicine("알프라졸람", "불안 완화",        "0.5mg 2회", "₩6,000", R.drawable.img_alprazolam),
                new Medicine("졸피뎀",     "수면 유도",        "10mg 취침 전","₩4,200", R.drawable.img_zolpidem),
                new Medicine("부스피론",   "경도 불안 완화",   "5mg 2회",   "₩3,800", R.drawable.img_buspirone)
        ));

        // 당뇨 계열 (5개)
        medicineData.put("당뇨 계열", Arrays.asList(
                new Medicine("메트포르민",     "혈당 강하",       "500mg 2회",     "₩4,500", R.drawable.img_metformin),
                new Medicine("인슐린 글라진","혈당 조절",       "1회 자가주사",  "₩15,000", R.drawable.img_insulin_glargine),
                new Medicine("시타글립틴",   "혈당 조절",       "50mg 1회",      "₩6,000", R.drawable.img_sitagliptin),
                new Medicine("글리벤클라미드","인슐린 분비 촉진","5mg 1회",       "₩4,200", R.drawable.img_glibenclamide),
                new Medicine("에토글리플로진","혈당 배출 촉진", "10mg 1회",      "₩7,500", R.drawable.img_ertugliflozin)
        ));

        // 기타 (3개)
        medicineData.put("기타", Arrays.asList(
                new Medicine("비타민C",    "면역력 증진",    "500mg 1회",  "₩1,000", R.drawable.img_vitamin_c),
                new Medicine("폴리덴탈겔","치은염 완화",    "3~4회 도포", "₩1,500", R.drawable.img_polydental_gel),
                new Medicine("니코틴패치","금연 보조",      "21mg 1패치", "₩5,000", R.drawable.img_nicotine_patch)
        ));
    }

    private static class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.VH> {
        private final List<PlaceItem> items;
        private final SharedPreferences sharedPreferences;

        // 생성자에서 SharedPreferences 초기화
        PlaceAdapter(List<PlaceItem> items, Context context) {
            this.items = items;
            this.sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup p, int vt) {
            View v = LayoutInflater.from(p.getContext()).inflate(android.R.layout.simple_list_item_2, p, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int pos) {
            PlaceItem it = items.get(pos);
            vh.t1.setText(it.name);

            // 거리 계산
            float[] dist = new float[1];
            Location.distanceBetween(
                    ((SymptomSearchActivity) vh.t1.getContext()).currentLocation.latitude,
                    ((SymptomSearchActivity) vh.t1.getContext()).currentLocation.longitude,
                    it.lat, it.lng, dist
            );
            vh.t2.setText(Math.round(dist[0]) + "m");

            // 돋보기 상태 적용
            applyZoomState(vh);  // 돋보기 상태 적용
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class VH extends RecyclerView.ViewHolder {
            final TextView t1, t2;

            VH(View v) {
                super(v);
                t1 = v.findViewById(android.R.id.text1);
                t2 = v.findViewById(android.R.id.text2);
            }
        }

        // 돋보기 상태 적용 (TextView 크기 변경)
        private void applyZoomState(VH vh) {
            // SharedPreferences에서 돋보기 상태 가져오기
            boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
            float zoomSize = isZoomEnabled ? 30f : 16f;

            // 각 TextView의 크기를 돋보기 상태에 맞게 설정
            vh.t1.setTextSize(zoomSize); // Place 이름
            vh.t2.setTextSize(zoomSize); // 거리 텍스트
        }
    }


    private static class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.VH> {
        private final List<Medicine> meds;
        private final SharedPreferences sharedPreferences;

        // 생성자에서 SharedPreferences 초기화
        MedicineAdapter(List<Medicine> meds, Context context) {
            this.meds = meds;
            this.sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup p, int vt) {
            View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_medicine, p, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int pos) {
            Medicine m = meds.get(pos);
            vh.name.setText(m.getName());
            vh.efficacy.setText("효능: " + m.getEfficacy());
            vh.usage.setText("복용법: " + m.getUsage());
            vh.price.setText("가격: " + m.getPrice());

            // 📌 이미지 바인딩 추가
            vh.image.setImageResource(m.getImageResId());

            // ★ 여기에 클릭 리스너 추가
            vh.image.setOnClickListener(v -> {
                Context ctx = v.getContext();
                Intent i = new Intent(ctx, FullscreenImageActivity.class);
                i.putExtra(FullscreenImageActivity.EXTRA_RES_ID, m.getImageResId());
                ctx.startActivity(i);
            });

            // 돋보기 상태 적용
            applyZoomState(vh);  // 돋보기 상태 적용
        }

        @Override
        public int getItemCount() {
            return meds.size();
        }

        static class VH extends RecyclerView.ViewHolder {
            final TextView name, efficacy, usage, price;
            final ImageView image;   // ← 추가

            VH(View v) {
                super(v);
                name = v.findViewById(R.id.tvMedName);
                efficacy = v.findViewById(R.id.tvMedEfficacy);
                usage = v.findViewById(R.id.tvMedUsage);
                price = v.findViewById(R.id.tvMedPrice);
                image    = v.findViewById(R.id.ivMedImage);  // ← 초기화
            }
        }

        // 돋보기 상태 적용 (TextView 크기 변경)
        private void applyZoomState(VH vh) {
            // SharedPreferences에서 돋보기 상태 가져오기
            boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
            float zoomSize = isZoomEnabled ? 30f : 16f;

            // 각 TextView의 크기를 돋보기 상태에 맞게 설정
            vh.name.setTextSize(zoomSize); // 약 이름
            vh.efficacy.setTextSize(zoomSize); // 효능
            vh.usage.setTextSize(zoomSize); // 복용법
            vh.price.setTextSize(zoomSize); // 가격
        }
    }
}
