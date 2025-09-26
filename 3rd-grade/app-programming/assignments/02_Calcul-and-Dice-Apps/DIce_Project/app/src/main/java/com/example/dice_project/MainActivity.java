package com.example.dice_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2;
    private Random rand;
    private String tag = "MyTag";
    int diceImages[] = {
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
    };

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


        rand = new Random();

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        imageView1.setImageResource(diceImages[0]);
        imageView2.setImageResource(diceImages[0]);

    }

    public void Roll_Dice(View view) {

        //1. 랜덤숫자 생성하기
        int dice1 = rand.nextInt(6); // 0~5까지의 랜덤숫자를 생성
        int dice2 = rand.nextInt(6); // 0~5까지의 랜덤숫자를 생성

        Log.d(tag, "주사위숫자1: "+ dice1); //Tag, String
        Log.d(tag, "주사위숫자2: "+ dice2); //Tag, String



        //2. 이미지 뷰 출력하기
        imageView1.setImageResource(diceImages[dice1]);
        imageView2.setImageResource(diceImages[dice2]);

        /*

        switch (dice1) {
            case 0:
                imageView1.setImageResource(R.drawable.dice_1);
                break;
            case 1:
                imageView1.setImageResource(R.drawable.dice_2);
                break;
            case 2:
                imageView1.setImageResource(R.drawable.dice_3);
                break;
            case 3:
                imageView1.setImageResource(R.drawable.dice_4);
                break;
            case 4:
                imageView1.setImageResource(R.drawable.dice_5);
                break;
            case 5:
                imageView1.setImageResource(R.drawable.dice_6);
                break;

        }

        switch (dice2) {
            case 0:
                imageView2.setImageResource(R.drawable.dice_1);
                break;
            case 1:
                imageView2.setImageResource(R.drawable.dice_2);
                break;
            case 2:
                imageView2.setImageResource(R.drawable.dice_3);
                break;
            case 3:
                imageView2.setImageResource(R.drawable.dice_4);
                break;
            case 4:
                imageView2.setImageResource(R.drawable.dice_5);
                break;
            case 5:
                imageView2.setImageResource(R.drawable.dice_6);
                break;
        }

        */

    }
}