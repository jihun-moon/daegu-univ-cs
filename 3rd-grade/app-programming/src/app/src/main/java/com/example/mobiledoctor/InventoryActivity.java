package com.example.mobiledoctor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 활동(Activity) 클래스: 약국 재고 현황을 보여주고 검색 기능을 제공합니다.
 */
public class InventoryActivity extends BaseActivity {
    private static final String TAG = "InventoryActivity";

    // Intent Extra 키
    public static final String EXTRA_PLACE_NAME    = "place_name";
    public static final String EXTRA_PLACE_ADDRESS = "place_address";
    public static final String EXTRA_PLACE_PHONE   = "place_phone";

    // 예시 재고 데이터 (불변 리스트로 관리)
    private static final List<String> SAMPLE_DATA = Collections.unmodifiableList(Arrays.asList(
            "아세트아미노펜 500mg — 재고 12개",
            "이부프로펜 200mg — 재고 5개",
            "니트로글리세린설하정 0.3mg — 재고 8개",
            "아목시실린캡슐 250mg — 재고 8개",
            "로페라미드정 — 재고 20개",
            "심바스타틴정 10mg — 재고 3개",
            "메트포르민정 500mg — 재고 15개",
            "비타민C 1000mg — 재고 30개",
            "알부테롤흡입제 — 재고 2개",
            "오메프라졸정 20mg — 재고 7개",
            "로라타딘정 — 재고 0개 (품절)",
            "폴리덴탈겔 — 재고 4개",
            "나프록센정 250mg — 재고 6개",
            "필로카르핀점안액 — 재고 1개",
            "시메티딘정 200mg — 재고 9개",
            "프레드니솔론정 5mg — 재고 5개",
            "클로르헥시딘가글액 — 재고 11개",
            "페니토인정 100mg — 재고 2개",
            "세프트리악손주사액 1g — 재고 7개",
            "아스피린 325mg — 재고 18개",
            "클로피도그렐정 75mg — 재고 10개",
            "라모트리진정 100mg — 재고 4개",
            "레보플록사신캡슐 500mg — 재고 3개",
            "메토클로프라미드정 10mg — 재고 9개",
            "옥시토신주사액 5IU — 재고 1개",
            "디곡신정 0.25mg — 재고 6개",
            "리스페리돈정 2mg — 재고 5개",
            "발프로산나트륨정 200mg — 재고 8개",
            "시타글립틴정 50mg — 재고 12개",
            "피록시캄정 20mg — 재고 7개",
            "아세클로페낙겔 — 재고 15개",
            "세트리악손주사액 250mg — 재고 0개 (품절)",
            "메티마졸정 10mg — 재고 2개",
            "레보티록신정 50µg — 재고 13개",
            "벤즈트로핀정 1mg — 재고 3개",
            "카르베디롤정 12.5mg — 재고 11개",
            "로수바스타틴정 5mg — 재고 9개",
            "도네페질정 5mg — 재고 4개",
            "갈란타민정 8mg — 재고 6개",
            "프라조신정 1mg — 재고 5개",
            "수크랄페이트정 1g — 재고 14개",
            "니코틴패치 21mg — 재고 10개"
    ));

    private InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // 돋보기 상태 적용
        applyZoomState();  // BaseActivity에서 구현된 메소드 호출

        // 1) 인텐트로 전달된 장소 정보 받기
        displayPlaceInfo();

        // 2) 툴바 설정
        setupToolbar();

        // 3) RecyclerView + Adapter 설정
        setupRecyclerView();
    }

    /**
     * 인텐트로 전달된 장소 정보를 화면에 표시합니다.
     */
    private void displayPlaceInfo() {
        Intent intent = getIntent();
        String name    = intent.getStringExtra(EXTRA_PLACE_NAME);
        String address = intent.getStringExtra(EXTRA_PLACE_ADDRESS);
        String phone   = intent.getStringExtra(EXTRA_PLACE_PHONE);

        TextView tvName    = findViewById(R.id.place_name);
        TextView tvAddress = findViewById(R.id.place_address);
        TextView tvPhone   = findViewById(R.id.place_phone);

        tvName.setText(name != null ? name : "");
        tvAddress.setText(address != null ? address : "");
        tvPhone.setText(phone != null ? phone : "");

        // 돋보기 상태 적용 (텍스트 크기 변경)
        applyZoomState(tvName, tvAddress, tvPhone);
    }

    /**
     * 액션바(toolbar)를 초기화합니다.
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_inventory);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * RecyclerView와 InventoryAdapter를 초기화합니다.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_inventory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Inside onCreate method in InventoryActivity
        adapter = new InventoryAdapter(this, SAMPLE_DATA);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("약품명으로 검색");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 다른 액티비티에서 InventoryActivity를 시작할 때 사용합니다.
     * @param context 호출할 Context
     * @param placeName 장소 이름
     * @param address 장소 주소
     * @param phone 전화번호
     */
    public static void launch(Context context,
                              String placeName,
                              String address,
                              String phone) {
        Intent intent = new Intent(context, InventoryActivity.class);
        intent.putExtra(EXTRA_PLACE_NAME, placeName);
        intent.putExtra(EXTRA_PLACE_ADDRESS, address);
        intent.putExtra(EXTRA_PLACE_PHONE, phone);
        context.startActivity(intent);
    }

    // 돋보기 상태를 화면의 텍스트 뷰에 적용하는 메소드
    private void applyZoomState(TextView... textViews) {
        // SharedPreferences에서 돋보기 상태 가져오기
        boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
        float zoomSize = isZoomEnabled ? 30f : 16f;

        // 각 TextView의 크기를 돋보기 상태에 맞게 설정
        for (TextView textView : textViews) {
            textView.setTextSize(zoomSize);
        }
    }
}