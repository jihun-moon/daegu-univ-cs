package com.example.calcu_lator;

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
    private EditText number1, number2;
    private TextView result;

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
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        result   = findViewById(R.id.result);
    }

    public void onClickAdd(View view) {
        calculate('+');
    }

    public void onClickSubtract(View view) {
        calculate('-');
    }

    public void onClickMultiply(View view) {
        calculate('*');
    }

    public void onClickDivide(View view) {
        calculate('/');
    }

    private void calculate(char operator) {
        String num1Str = number1.getText().toString().trim();
        String num2Str = number2.getText().toString().trim();
        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            result.setText("숫자를 모두 입력하세요.");
            return;
        }
        double n1, n2;
        try {
            n1 = Double.parseDouble(num1Str);
            n2 = Double.parseDouble(num2Str);
        } catch (NumberFormatException e) {
            result.setText("유효한 숫자를 입력하세요.");
            return;
        }
        double res;
        switch (operator) {
            case '+': res = n1 + n2; break;
            case '-': res = n1 - n2; break;
            case '*': res = n1 * n2; break;
            case '/':
                if (n2 == 0) {
                    result.setText("0으로 나눌 수 없습니다.");
                    return;
                }
                res = n1 / n2;
                break;
            default:
                result.setText("알 수 없는 연산자: " + operator);
                return;
        }
        result.setText("결과: " + res);
    }
}
