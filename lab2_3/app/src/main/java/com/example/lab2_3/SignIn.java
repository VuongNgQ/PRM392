package com.example.lab2_3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignIn extends AppCompatActivity {

    private EditText us;
    private EditText ps;
    private TextView createOne;
    private Button btnSignin;

    private final String REQUIRE = "REQUIRE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        us = findViewById(R.id.us);
        ps = findViewById(R.id.ps);
        createOne = findViewById(R.id.createOne);
        btnSignin = findViewById(R.id.btnSignin);

        createOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFormIn();
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signinlayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private boolean checkInput() {
        if (TextUtils.isEmpty((us.getText().toString()))){
            us.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty((ps.getText().toString()))){
            ps.setError(REQUIRE);
            return false;
        }
        return true;
    }

    private void signIn(){
        if(!checkInput()){
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void signUpFormIn(){
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnSignin:
//                signIn();
//                break;
//            case R.id.createOne:
//                signUpForm();
//                break;
//        }
//    }
}