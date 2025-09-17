// MainActivity.java
package com.example.mobiledoctor;

import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private TextView brightnessText, sampleText, tvHistoryComment;
    private SeekBar brightnessSeekBar;
    private MaterialButton zoomButton, btnHistory;
    private SharedPreferences sharedPreferences;
    private HospitalHistoryActivity.HospitalDbHelper dbHelper;

    private static final int REQ_HISTORY = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면 밝기 설정
        brightnessText    = findViewById(R.id.brightnessText);
        brightnessSeekBar = findViewById(R.id.seek_bar);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                !Settings.System.canWrite(this)) {
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS), 0
            );
        }
        try {
            int cur = Settings.System.getInt(
                    getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );
            int pct = (int)(cur / 255.0 * 100);
            brightnessSeekBar.setProgress(pct);
            brightnessText.setText("현재 밝기: " + pct);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar sb, int p, boolean u) {
                int val = (int)(p / 100.0 * 255);
                if (Settings.System.canWrite(MainActivity.this)) {
                    ContentResolver cr = getContentResolver();
                    Settings.System.putInt(
                            cr, Settings.System.SCREEN_BRIGHTNESS, val
                    );
                    brightnessText.setText("현재 밝기: " + p);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {}
        });

        // ImageView 설정
        ImageView imageView = findViewById(R.id.iv_bottom_image);
        // 애니메이션 설정: 아래로 100dp 만큼 이동 후 원위치로 돌아옴
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 100f);
        moveDown.setDuration(1000);  // 1초 동안 아래로 이동
        moveDown.setInterpolator(new DecelerateInterpolator());  // 자연스러운 속도 변화

        // 이동 후 원위치로 돌아오는 애니메이션 설정
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(imageView, "translationY", 100f, 0f);
        moveUp.setDuration(1000);  // 1초 동안 원위치로 돌아옴
        moveUp.setInterpolator(new DecelerateInterpolator());  // 자연스러운 속도 변화

        // 애니메이션 순차 실행 (아래로 이동 -> 원위치로 돌아오기)
        moveDown.start();
        moveDown.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                moveUp.start();  // 첫 번째 애니메이션이 끝난 후 두 번째 애니메이션 실
            }
        });

        // 증상 검색 및 병원/약국 찾기 버튼 클릭 시 액티비티 전환
        findViewById(R.id.btn_symptom)
                .setOnClickListener(v -> startActivity(new Intent(this, SymptomSearchActivity.class)));

        findViewById(R.id.btn_map)
                .setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // 돋보기 버튼 및 텍스트뷰 연결
        zoomButton = findViewById(R.id.btn_zoom);
        sampleText = findViewById(R.id.tv_sample_text);

        // 돋보기 버튼 클릭 시 텍스트 크기 조정
        zoomButton.setOnClickListener(v -> {
            toggleZoom();
            applyZoomState();  // 버튼 클릭 시 실시간 반영
        });

        // 앱 실행 시 돋보기 상태를 가져와서 텍스트 크기 조정
        applyZoomState();


        // DB 헬퍼 & 코멘트 뷰
        dbHelper = new HospitalHistoryActivity.HospitalDbHelper(this);
        tvHistoryComment = findViewById(R.id.tv_history_comment);
        tvHistoryComment.setVisibility(View.GONE);

        // “병원이용기록 조회” 버튼: 결과를 받도록 한 번만 세팅
        btnHistory = findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, HospitalHistoryActivity.class);
            startActivityForResult(intent, REQ_HISTORY);
        });
    }

    // HistoryActivity에서 돌아왔을 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_HISTORY && resultCode == RESULT_OK) {
            // 1) Intent로 받은 이름·생년월일
            String name = data.getStringExtra("patient_name");
            String dob  = data.getStringExtra("dob");

            // 2) 해당 환자만 다시 조회
            List<HospitalHistoryActivity.HospitalRecord> list =
                    dbHelper.queryByPatient(name, dob);

            // ① 2주 내 방문 횟수 계산
            int recentCount = countVisitsWithinDays(list, 14);

            // ② 기준(예: 4회) 이상일 때만 코멘트 표시
            if (recentCount >= 4) {
                String comment = String.format(Locale.getDefault(),
                        "%s님, 최근 2주간 병원 방문이 %d회 있네요.\n" +
                                "적절한 운동과 스트레스를 줄이고, 자극적인 음식은 피하세요.",
                        name, recentCount);
                tvHistoryComment.setText(comment);
                tvHistoryComment.setVisibility(View.VISIBLE);
            } else {
                // 4회 미만일 때
                String healthyComment = String.format(Locale.getDefault(),
                        "%s님, 최근 2주간 병원 방문이 %d회로 적어 전반적으로 건강 상태가 좋아 보여요!\n" +
                                "앞으로도 규칙적인 운동과 균형 잡힌 식단으로 건강을 잘 유지하세요.",
                        name, recentCount);
                tvHistoryComment.setText(healthyComment);
                tvHistoryComment.setVisibility(View.VISIBLE);
            }
        }
    }

    private int countVisitsWithinDays(List<HospitalHistoryActivity.HospitalRecord> list, int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        Date threshold = cal.getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int cnt = 0;
        for (HospitalHistoryActivity.HospitalRecord r : list) {
            try {
                Date d = fmt.parse(r.visitDate);
                if (d != null && d.after(threshold)) cnt++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return cnt;
    }

    // 돋보기 활성화/비활성화
    private void toggleZoom() {
        boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);

        // 상태 반전 후 SharedPreferences에 저장
        if (isZoomEnabled) {
            // 돋보기 비활성화
            sharedPreferences.edit().putBoolean("isZoomEnabled", false).apply();
            zoomButton.setText("돋보기 활성화");
            sampleText.setTextSize(16); // 기본 텍스트 크기
        } else {
            // 돋보기 활성화
            sharedPreferences.edit().putBoolean("isZoomEnabled", true).apply();
            zoomButton.setText("돋보기 비활성화");
            sampleText.setTextSize(30); // 확대된 텍스트 크기
        }
    }
}