package com.example.doghello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome_Page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ImageView welcome_imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        welcome_imageview = findViewById(R.id.welcome_imageview);

        mAuth = FirebaseAuth.getInstance();
        //須更新，然後要把Mainfest的主頁面換回來
        FirebaseUser user = mAuth.getCurrentUser();
        try{
            //welcome_imageview.setVisibility(View.VISIBLE);
            //Log.d("畫面顯示","");
            Thread.sleep(5000);
            if(user != null){
                Intent intent = new Intent();
                intent.setClass(Welcome_Page.this,HomePage.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent();
                intent.setClass(Welcome_Page.this,MainActivity.class);
                startActivity(intent);
                Log.d("onAuthStateChanged","未登入");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}