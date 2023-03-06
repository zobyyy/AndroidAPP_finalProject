package com.example.doghello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.graphics.Color;


public class HomePage extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth mAuth;
    //private String func[] = {"基本資料","附近醫院","小倉庫","體重圖表","設定"};
    private int imageId[] = {R.drawable.information, R.drawable.hospital,R.drawable.ware,R.drawable.weight,R.drawable.web,R.drawable.set};
    private static final int Func_Login = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        GridView main_grid = findViewById(R.id.main_page_view);

        MyAdapter adapter = new MyAdapter(HomePage.this);

        /*ArrayAdapter gAdapter = new ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1,func);*/
        main_grid.setAdapter(adapter);
        main_grid.setOnItemClickListener(this);

        main_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: //跳轉至基本資料頁面
                        startActivity(
                                new Intent(HomePage.this,Basic_Data_Page.class));
                        break;
                    case 1: //跳轉至地圖頁面
                        /*startActivity(
                                new Intent(HomePage.this,Maps_Page.class));*/
                        Toast.makeText(HomePage.this, "功能開發中", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: //跳轉至小倉庫頁面
                        startActivity(
                                new Intent(HomePage.this,Ware_House.class));
                        break;
                    case 3: //跳轉至體重圖表頁面
                        startActivity(
                                new Intent(HomePage.this,Health.class));
                        break;
                    case 4:
                        Uri uri = Uri.parse("https://reurl.cc/DZOyzd");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case 5://跳轉至設定頁面
                        startActivity(
                                new Intent(HomePage.this,Set_page.class));
                        break;
                }
            }
        });




    }

    public class MyAdapter extends BaseAdapter{

        private Context mContext;

        public MyAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return imageId.length;
            //return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View View, ViewGroup parent) {
            ImageView iv = new ImageView(mContext);
            iv.setImageResource(imageId[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new GridView.LayoutParams(420,420));
            return iv;

            //return null;
        }
    }



   @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0: //跳轉至基本資料頁面
                startActivity(
                        new Intent(HomePage.this,Basic_Data_Page.class));
                break;
            case 1: //跳轉至地圖頁面
                /*startActivity(
                        new Intent(HomePage.this,Maps_Page.class));*/
                Toast.makeText(HomePage.this, "功能開發中", Toast.LENGTH_SHORT).show();
                break;
            case 2: //跳轉至小倉庫頁面
                startActivity(
                        new Intent(HomePage.this,Ware_House.class));
                break;
            case 3: //跳轉至體重圖表頁面
                startActivity(
                        new Intent(HomePage.this,Health.class));
                break;
            case 4: //跳轉至設定頁面
                startActivity(
                        new Intent(HomePage.this,Set_page.class));
                break;
        }
    }
}