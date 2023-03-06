package com.example.doghello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_page extends AppCompatActivity {
    private ImageButton register_imageButton;
    private EditText email_edittext,password_edittext;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        initview();

        register_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_edittext.getText().toString();
                String password = password_edittext.getText().toString();
                if(TextUtils.isEmpty(email)){
                    email_edittext.setError(getString(R.string.plz_input_email));
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_edittext.setError(getString(R.string.plz_input_password));
                    return;
                }
                email_edittext.setError("");
                password_edittext.setError("");
                //creatUser(email,password);

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(register_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {//登入的判斷式
                                if(task.isSuccessful()){
                                    String message = task.isComplete() ? "註冊成功":"註冊失敗";
                                    new AlertDialog.Builder(register_page.this)
                                            .setMessage(message)
                                            .setPositiveButton("OK",null)
                                            .show();
                                    Toast.makeText(register_page.this, "註冊成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setClass(register_page.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(register_page.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void initview(){
        mAuth = FirebaseAuth.getInstance();
        register_imageButton = findViewById(R.id.register_imageButton);
        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
    }

    private void creatUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message = task.isComplete() ? "註冊成功":"註冊失敗";
                        new AlertDialog.Builder(register_page.this)
                                .setMessage(message)
                                .setPositiveButton("OK",null)
                                .show();
                    }
                }
        );
    }
}