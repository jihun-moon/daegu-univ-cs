package com.example.image_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView; // XML_ID : imageView3
    private int scaleTyeIndex = 0;


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

        imageView = findViewById(R.id.imageView3);

    }

    public void changeScaleType(View view) {
        ImageView.ScaleType[] scaleTypes = {
                ImageView.ScaleType.CENTER,
                ImageView.ScaleType.CENTER_CROP,
                ImageView.ScaleType.CENTER_INSIDE,
                ImageView.ScaleType.FIT_CENTER,
                ImageView.ScaleType.FIT_XY
        };

        imageView.setScaleType(scaleTypes[scaleTyeIndex]);
        scaleTyeIndex = (scaleTyeIndex + 1) % scaleTypes.length;

    }

    public void changeRotation(View view) {
        imageView.setRotation(imageView.getRotation()+45);
    }

    public void changeAlpha(View view) {

        float alpha = imageView.getAlpha();


        // alpha = (alpha == 1.0f) ? 0.5f : 1.0f;

        if (alpha == 1.0f){
            alpha = 0.5f;
        }
        else alpha = 1.0f;


        imageView.setAlpha(alpha);

    }
}