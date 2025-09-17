package com.example.mobiledoctor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // 돋보기 상태 적용
        applyZoomState();
    }

    // 돋보기 상태 적용
    protected void applyZoomState() {
        boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
        if (isZoomEnabled) {
            setTextViewSize(34);  // 확대된 텍스트 크기
        } else {
            setTextViewSize(20);  // 기본 텍스트 크기
        }
    }

    // 텍스트 크기 적용
    private void setTextViewSize(float size) {
        // 로그 추가: 텍스트 크기 설정 전
        Log.d("ZoomState", "Setting text size to: " + size);

        // 이 메소드를 사용하여 액티비티 내 모든 TextView에 대해 크기 적용
        for (TextView textView : getAllTextViews()) {
            Log.d("ZoomState", "Setting size for TextView: " + textView.getText());
            textView.setTextSize(size);
        }
    }

    // 이 메소드는 모든 TextView를 가져오는 메소드입니다.
    private List<TextView> getAllTextViews() {
        List<TextView> textViews = new ArrayList<>();
        // 액티비티에서 사용하는 모든 TextView를 찾아서 리스트에 추가합니다.
        // 예시로 루트 뷰에서 모든 TextView를 순회하는 방식
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        findAllTextViews(rootView, textViews);
        return textViews;
    }

    // 루트 뷰에서 모든 TextView를 재귀적으로 찾아서 리스트에 추가
    private void findAllTextViews(View view, List<TextView> textViews) {
        if (view instanceof TextView) {
            textViews.add((TextView) view);
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                findAllTextViews(group.getChildAt(i), textViews);
            }
        }
    }
}
