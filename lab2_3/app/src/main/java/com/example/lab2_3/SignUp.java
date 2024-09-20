package com.example.lab2_3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {
    //k nen reference tại đây luôn vì khi chuyển activity nó bị null liền và crash cmnl mà hãy vào set view rồi set
    private EditText uscf;
    private EditText pwcf;
    private EditText cfpwcf;
    private TextView haveAcc;
    private Button btnSignUp;
    private final String REQUIRE = "REQUIRE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        uscf = findViewById(R.id.username);
        pwcf = findViewById(R.id.password);
        cfpwcf = findViewById(R.id.confirm);
        haveAcc = findViewById(R.id.haveAcc);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpForm();
            }
        });

        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signuplayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty((uscf.getText().toString()))){
            uscf.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty((pwcf.getText().toString()))){
            uscf.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty((cfpwcf.getText().toString()))){
            uscf.setError(REQUIRE);
            return false;
        }
        if(!TextUtils.equals(pwcf.getText().toString(),cfpwcf.getText().toString())){
            Toast.makeText(this, "Password doenst match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signIn(){
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
        finish();
    }

    private void signUpForm(){
       if(checkInput()){
           Intent intent = new Intent(this,MainActivity.class);
           startActivity(intent);
           finish();
       }else {
           return;
       }
    }
}