package com.example.calcul_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView TextView1, TextView2; // 각각 버튼 입력값은 TextView1에 결과는 TextView2에 보이게 하기.

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

        TextView1 = findViewById(R.id.XML_textView1);
        TextView2 = findViewById(R.id.XML_textView2);


    }

    private void appendText(View view) {
        Button button = (Button) view;
        String current = TextView1.getText().toString();
        String newText = current + button.getText().toString();
        TextView1.setText(newText);
    }


    public void onClick7(View view) {
        appendText(view);
    }

    public void onClick8(View view) {
        appendText(view);
    }

    public void onClick9(View view) {
        appendText(view);
    }

    public void onClickD(View view) {
        appendText(view);
    }

    public void onClick4(View view) {
        appendText(view);
    }

    public void onClick5(View view) {
        appendText(view);
    }

    public void onClick6(View view) {
        appendText(view);
    }

    public void onClickM(View view) {
        appendText(view);
    }

    public void onClick1(View view) {
        appendText(view);
    }

    public void onClick2(View view) {
        appendText(view);
    }

    public void onClick3(View view) {
        appendText(view);
    }

    public void onClickS(View view) {
        appendText(view);
    }

    public void onClick0(View view) {
        appendText(view);
    }

    public void onClickAC(View view) {
        String currentText = TextView1.getText().toString();
        if (currentText.length() > 0 ){
            //마지막 글자 제거
            currentText = currentText.substring(0, currentText.length() - 1);
            TextView1.setText(currentText);
        }
    }

    public void onClickE(View view) {
        String input = TextView1.getText().toString();
        String operator = "";

        // 입력된 수식에서 연산자 탐색 (우선 +, -, *, / 순)
        if (input.contains("+")) {
            operator = "+";
        } else if (input.contains("-")) {
            operator = "-";
        } else if (input.contains("*")) {
            operator = "*";
        } else if (input.contains("/")) {
            operator = "/";
        }

        if (!operator.isEmpty()) {
            // 연산자를 기준으로 두 피연산자 분리
            String parts[] = input.split("\\" + operator);
            if (parts.length == 2) {
                try {
                    double num1 = Double.parseDouble(parts[0]);
                    double num2 = Double.parseDouble(parts[1]);
                    double result = 0;

                    switch(operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 == 0) {
                                TextView2.setText("0으로 나눌 수 없습니다");
                                return;
                            }
                            result = num1 / num2;
                            break;
                    }
                    TextView2.setText("= " + result);
                } catch (NumberFormatException e) {
                    TextView2.setText("수식 오류");
                }
            } else {
                TextView2.setText("수식 오류");
            }
        } else {
            TextView2.setText("수식 오류");
        }
    }


    public void onClickA(View view) {
        appendText(view);
    }
}