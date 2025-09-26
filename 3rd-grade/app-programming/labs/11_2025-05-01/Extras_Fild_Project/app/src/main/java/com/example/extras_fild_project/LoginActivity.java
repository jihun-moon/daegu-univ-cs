package com.example.extras_fild_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private TextView j_displayIDTextView, j_displayPWTextView, j_statusTextView;
    String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        j_displayIDTextView = findViewById(R.id.TextViewid);
        j_displayPWTextView = findViewById(R.id.TextViewpw);
        j_statusTextView = findViewById(R.id.textView5);

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("ID");
            password = intent.getStringExtra("Password");

            j_displayIDTextView.setText(id);
            j_displayPWTextView.setText(password);
        }
    }

    public void check(View v){
        Intent intent = new Intent();
        if(isUserValid(id, password)){
            intent.putExtra("status", "로그인 성공");
        }
        else{
            intent.putExtra("status", "로그인 실패");
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean isUserValid(String username, String password){
        return username.equals("abcd") && password.equals("1234");
    }

}