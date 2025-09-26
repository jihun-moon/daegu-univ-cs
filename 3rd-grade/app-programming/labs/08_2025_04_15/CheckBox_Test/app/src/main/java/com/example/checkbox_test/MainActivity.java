package com.example.checkbox_test;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView j_imageView1, j_imageView2;

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

        j_imageView1 = (ImageView) findViewById(R.id.imageView1);
        j_imageView2 = (ImageView) findViewById(R.id.imageView2);

    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox)view).isChecked();

        if (view.getId() == R.id.checkBox1){
            if (checked){
                j_imageView1.setImageResource(R.drawable.kitkat);
            }
            else {
                j_imageView1.setImageResource(0);
            }

        } else if (view.getId() == R.id.checkBox2) {
            if (checked) {
                j_imageView2.setImageResource(R.drawable.nougat);
            } else {
                j_imageView2.setImageResource(0);
            }
        }
    }
}