package com.example.mobiledoctor;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullscreenImageActivity extends AppCompatActivity {
    public static final String EXTRA_RES_ID = "extra_res_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 검은 배경 없는 상태바/툴바 없는 전체화면
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(iv);

        // 리소스 ID 로드
        int resId = getIntent().getIntExtra(EXTRA_RES_ID, 0);
        if (resId != 0) iv.setImageResource(resId);

        // 클릭 시 종료
        iv.setOnClickListener(v -> finish());
    }
}

