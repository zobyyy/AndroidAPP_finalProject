package com.example.doghello;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;

public class Set_page extends AppCompatActivity {
    private GridView main_grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        main_grid = findViewById(R.id.main_page_view2);
        String func[] = {"隱私條款","使用者政策","問題回報","常見問題","使用教學","登出"};
        ArrayAdapter gAdapter = new ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1,func);
        main_grid.setAdapter(gAdapter);
        main_grid.setOnItemClickListener(this::onItemClick);
    }

    //@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0: //跳轉至隱私條款頁面
                startActivity(
                        new Intent(Set_page.this,Privacy_Page.class));
                break;
            case 1: //跳轉至使用者政策頁面
                startActivity(
                        new Intent(Set_page.this,User_Terms.class));
                break;
            case 2: //跳轉至問題回報頁面
                startActivity(
                        new Intent(Set_page.this,Feedback_Page.class));
                break;
            case 3: //跳轉至常見問題頁面
                break;
            case 4: //跳轉至使用教學頁面
                break;
            case 5: //登出動作
                FirebaseAuth.getInstance().signOut();
                Log.d("Logout","");
                startActivity(
                        new Intent(Set_page.this,MainActivity.class));
                break;
        }
    }
}