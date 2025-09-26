package com.example.event_test;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView j_ImageView;


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

        j_ImageView = findViewById(R.id.imageView);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);

        // 람다식 사용
        btn1.setOnClickListener(view->changeColor_method(Color.RED));
        btn2.setOnClickListener(view->changeColor_method(Color.BLUE));

        /*

        btn1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                changeColor_method(Color.RED);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                changeColor_method(Color.BLUE);
            }
        });

         */

    }

    private void changeColor_method(int color){
        j_ImageView.setBackgroundColor(color);
    }
}