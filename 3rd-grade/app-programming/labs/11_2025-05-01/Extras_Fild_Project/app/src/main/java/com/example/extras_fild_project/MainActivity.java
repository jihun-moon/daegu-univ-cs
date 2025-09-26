package com.example.extras_fild_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText j_emailEditText, j_passwordEditText;
    private TextView j_status;
    private Button j_login;
    private Button j_btn_main;

    ActivityResultLauncher<Intent> launcher;


    String tag = "LoginState";

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

        j_emailEditText = findViewById(R.id.id);
        j_passwordEditText = findViewById(R.id.password);
        j_status = findViewById(R.id.textView3);
        j_login = findViewById(R.id.j_login);

        j_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // 아이디와 비밀번호를 String 변수에 대입
                String email = j_emailEditText.getText().toString();
                String password = j_passwordEditText.getText().toString();

                Log.d(tag, "입력 아이디: " + email);
                Log.d(tag, "입력 비밀번호: " + password);

                //Login 화면으로 전환하면서 데이터 전달
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("ID", email);
                intent.putExtra("Password", password);
                launcher.launch(intent);
            }
        });

        // launcher
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result-> {
                if(result.getResultCode() == Activity.RESULT_OK){
                     Intent data = result.getData();
                     j_status.setText(data.getStringExtra("status"));
                }

                // 상태 텍스트가 "로그인 성공"인지 확인
                j_btn_main = findViewById(R.id.btn_main);
                if("로그인 성공".equals(j_status.getText().toString())){
                    j_btn_main.setVisibility(View.VISIBLE); // 버튼 보이게 설정
                }
                else
                {
                    j_btn_main.setVisibility(View.GONE); // 그 외는 숨김 (선택사항)
                }

        });
    }

    public void onClicked_main(View view) {
        Intent intent = new Intent(MainActivity.this, IntentActivity.class);
        startActivity(intent);
    }
}