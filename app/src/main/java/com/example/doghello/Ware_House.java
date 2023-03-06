package com.example.doghello;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class Ware_House extends AppCompatActivity {
    private MyDBHelper myDB;
    private ArrayList<String> id,kind,kindname,date;
    private MyAdapter myAdapter;
    private ImageButton back_imagebutton;
    private ImageView ware_nadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware__house);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //RecyclerView的顯示方法
        RecyclerView recyclerView = findViewById(R.id.list_recycleview_warehouse);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,DividerItemDecoration.VERTICAL)); //為RecyclerView每個item畫底線
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDB = new MyDBHelper(Ware_House.this);
        id = new ArrayList<>();
        kind = new ArrayList<>();
        kindname = new ArrayList<>();
        date = new ArrayList<>();
        ware_nadata = findViewById(R.id.ware_nadata);

        storeDataInArrays();

        myAdapter = new MyAdapter(Ware_House.this,
                this, id, kind, kindname, date);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                Ware_House.this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Ware_House.this,Ware_plus.class));
            }
        });

        /*back_imagebutton = findViewById(R.id.back_imagebutton);
        back_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(Ware_House.this,HomePage.class));
            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    private void storeDataInArrays(){ //資料列顯示
        Cursor cursor = myDB.readAllDataFromWareTable();
        if(cursor.getCount() == 0){
            ware_nadata.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                kind.add(cursor.getString(1));
                kindname.add(cursor.getString(2));
                date.add(cursor.getString(3));
            }
            ware_nadata.setVisibility(View.GONE);
        }
        cursor.close();
        myDB.readAllDataFromWareTable().close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}