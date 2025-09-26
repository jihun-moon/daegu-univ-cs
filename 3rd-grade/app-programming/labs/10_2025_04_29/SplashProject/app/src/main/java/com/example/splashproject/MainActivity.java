package com.example.splashproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText pwEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idEditText = (EditText) findViewById(R.id.id);
        pwEditText = (EditText) findViewById(R.id.password);
        resultTextView = (TextView) findViewById(R.id.result);

    }

    // 회원가입 버튼 클릭 시 호출
    public void onClick_Signup(View view) {
        displayCredentials();
    }

    // 로그인 버튼 클릭 시 호출
    public void onClick_Login(View view) {
        displayCredentials();
    }

    // 입력된 아이디와 비밀번호를 읽어 하단 TextView에 출력
    private void displayCredentials() {
        String idStr = idEditText.getText().toString();
        String pwStr = pwEditText.getText().toString();
        String displayText = "아이디: " + idStr + "\n비밀번호: " + pwStr;
        resultTextView.setText(displayText);
    }
}