package com.example.doghello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText email_edittext,password_edittext;
    private ImageButton imageButton,main_imageButton2;
    private FirebaseAuth mAuth;
    private String userUID;
    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_edittext.getText().toString();
                String password = password_edittext.getText().toString();
                if(TextUtils.isEmpty(email)){
                    email_edittext.setError(getString(R.string.plz_input_email));
                    //email_edittext.setError("");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_edittext.setError(getString(R.string.plz_input_password));
                    //password_edittext.setError("");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {//登入的判斷式
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        main_imageButton2.setOnClickListener(new View.OnClickListener() { //切換至註冊頁面
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this,register_page.class)
                );
            }
        });
    }

    private void initview(){
        mAuth = FirebaseAuth.getInstance();
        email_edittext = findViewById(R.id.email_enter_edittext);
        password_edittext = findViewById(R.id.password_enter_edittext);
        imageButton = findViewById(R.id.imageButton);
        main_imageButton2 = findViewById(R.id.main_imageButton2);
    }

}