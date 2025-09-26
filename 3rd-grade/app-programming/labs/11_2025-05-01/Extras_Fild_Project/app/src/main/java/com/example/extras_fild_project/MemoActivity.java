package com.example.extras_fild_project;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MemoActivity extends AppCompatActivity {

    EditText j_edit;
    Button j_btn_read, j_btn_write, j_btn_exit;
    String FILENAME = "test.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.memo_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        j_edit = findViewById(R.id.editTextText);
        j_btn_read = findViewById(R.id.btn_read);
        j_btn_write = findViewById(R.id.btn_write);
        j_btn_exit = findViewById(R.id.btn_exit);

        j_btn_write.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try{
                    // 파일 쓰기
                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(j_edit.getText().toString().getBytes());
                    fos.close();
                }
                catch (IOException e) {

                }
            }
        });

        j_btn_read.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try{
                    // 파일 읽기
                    FileInputStream fis = openFileInput(FILENAME);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    j_edit.setText(new String(buffer));
                    fis.close();
                }
                catch (IOException e) {

                }
            }
        });

        j_btn_exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // 닫기
                finish();
            }
        });

    }
}