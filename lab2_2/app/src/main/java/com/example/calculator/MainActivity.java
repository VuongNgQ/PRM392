package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtNumber1, edtNumber2;
    Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        edtNumber1 = findViewById(R.id.edtNumber1);
        edtNumber2 = findViewById(R.id.edtNumber2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        tvResult = findViewById(R.id.tvResult);

        // Xử lý sự kiện cho nút Cộng
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double num1 = Double.parseDouble(edtNumber1.getText().toString());
                double num2 = Double.parseDouble(edtNumber2.getText().toString());
                double result = num1 + num2;
                tvResult.setText("Kết quả: " + result);
            }
        });

        // Xử lý sự kiện cho nút Trừ
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double num1 = Double.parseDouble(edtNumber1.getText().toString());
                double num2 = Double.parseDouble(edtNumber2.getText().toString());
                double result = num1 - num2;
                tvResult.setText("Kết quả: " + result);
            }
        });

        // Xử lý sự kiện cho nút Nhân
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double num1 = Double.parseDouble(edtNumber1.getText().toString());
                double num2 = Double.parseDouble(edtNumber2.getText().toString());
                double result = num1 * num2;
                tvResult.setText("Kết quả: " + result);
            }
        });

        // Xử lý sự kiện cho nút Chia
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double num1 = Double.parseDouble(edtNumber1.getText().toString());
                double num2 = Double.parseDouble(edtNumber2.getText().toString());
                if (num2 != 0) {
                    double result = num1 / num2;
                    tvResult.setText("Kết quả: " + result);
                } else {
                    tvResult.setText("Không thể chia cho 0");
                }
            }
        });
    }
}
