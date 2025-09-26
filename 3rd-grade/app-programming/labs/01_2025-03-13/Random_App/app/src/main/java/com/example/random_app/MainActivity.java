package com.example.random_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRandomNumber;

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

        textViewRandomNumber = findViewById(R.id.textViewRandomNumber);

        /*

        R.id.~을 사용하는 이유는 Android의 리소스(Resource) 관리 방식 때문입니다.

        R.id.textViewRandomNumber는 XML 레이아웃 파일에서 정의한 View 요소의 ID를 참조하는 코드입니다.

        -  R : Android가 자동 생성하는 리소스 클래스. 이 클래스는 res 폴더에 있는 레이아웃, 문자열, 이미지 등 모든 리소스의 ID를 포함하고 있습니다.
        -  R.id : res/layout/ 파일에서 정의한 뷰(View)들의 ID
        -  res/layout/파일.xml에서 <TextView>에 부여한 android:id="@+id/textViewRandomNumber"를 참조하는 것

        */

    }

    public void generateRandomNumber(View view) {
        Random random = new Random();
        int randomNumber = random.nextInt(100);

        textViewRandomNumber.setText("난수: " + randomNumber);
    }
}