package com.example.extras_fild_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeDateActivity extends AppCompatActivity {

    private Button dateButton, timeButton;
    private EditText dateEditText, timeEditText;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timedate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.timedate), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 뷰 초기화
        dateButton     = findViewById(R.id.btn_date);
        timeButton     = findViewById(R.id.btn_time);
        dateEditText   = findViewById(R.id.editTextView_date);
        timeEditText   = findViewById(R.id.editTextView_time);

        // 현재 날짜/시간을 담은 Calendar
        calendar = Calendar.getInstance();

        // 날짜 선택 버튼 클릭 리스너
        dateButton.setOnClickListener(v -> {
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                .format(calendar.getTime());
                        dateEditText.setText("선택된 날짜: " + formattedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });


        // 시간 선택 버튼 클릭 리스너
        timeButton.setOnClickListener(v -> {
            new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String formattedTime = new SimpleDateFormat("HH:mm", Locale.getDefault())
                                .format(calendar.getTime());
                        timeEditText.setText("선택된 시간: " + formattedTime);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true  // 24시간 형식 사용
            ).show();
        });
    }
}

