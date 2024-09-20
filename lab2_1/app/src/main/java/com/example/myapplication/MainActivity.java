package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etMin, etMax;
    private Button btnGenerate;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMin = findViewById(R.id.editTextMin);
        etMax = findViewById(R.id.editTextMax);
        btnGenerate = findViewById(R.id.buttonGenerate);
        tvResult = findViewById(R.id.textViewResult);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minText = etMin.getText().toString();
                String maxText = etMax.getText().toString();

                if (minText.isEmpty() || maxText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both min and max values", Toast.LENGTH_SHORT).show();
                    return;
                }

                int min = Integer.parseInt(minText);
                int max = Integer.parseInt(maxText);

                if (min >= max) {
                    Toast.makeText(MainActivity.this, "Max must be greater than Min", Toast.LENGTH_SHORT).show();
                    return;
                }

                Random random = new Random();
                int randomNumber = random.nextInt((max - min) + 1) + min;
                tvResult.setText("Result: " + randomNumber);
            }
        });
    }
}
