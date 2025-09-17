package com.example.mobiledoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.ivSplashLogo);
        TextView title = findViewById(R.id.tvSplashTitle);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // 각각 애니메이션 적용
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        title.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        progressBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));

        // 1.5초 뒤에 MainActivity 실행
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1500);
    }
}
