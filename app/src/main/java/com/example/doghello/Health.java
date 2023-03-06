package com.example.doghello;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doghello.databinding.ActivityHealthBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Health extends AppCompatActivity {
    private MyDBHelper myDB;
    private AppBarConfiguration appBarConfiguration;
    private ActivityHealthBinding binding;
    private ArrayList<String> id,healthdate,petweight,weight_unit;
    private ArrayList<Integer> weight ;
    private HealthAdapter Adapter;
    private String et_wei,et_date;
    private ImageView health_nodata;
    ArrayList<HashMap<String,String>> mdata = new ArrayList<HashMap<String,String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHealthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.list_recycleview_health);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDB = new MyDBHelper(Health.this);
        id = new ArrayList<>();
        healthdate = new ArrayList<>();
        petweight = new ArrayList<>();
        weight_unit = new ArrayList<>();
        health_nodata = findViewById(R.id.health_nodata);

        storeDataInArrays();

        Adapter = new HealthAdapter(Health.this,
                this, id,healthdate, petweight,weight_unit);
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                Health.this));

        FloatingActionButton fab = findViewById(R.id.fabtohealth);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                Health.this,Health_plus.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){

            et_date = data.getStringExtra("et_date").toString();
            et_wei = data.getStringExtra("et_wei").toString();
            if (et_wei != ""){
                Integer wei = Integer.parseInt(et_wei); //numberformatexception s== null
                weight.add(wei);
            }
            recreate();
            Log.d("Done",et_date+""+et_wei);
    }}

    private void storeDataInArrays(){ //資料列顯示
        Cursor cursor = myDB.readAllDataFromHealthTable();
        if(cursor.getCount() == 0){
            health_nodata.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                healthdate.add(cursor.getString(1));
                petweight.add(cursor.getString(2));
                weight_unit.add(cursor.getString(3));
            }
            health_nodata.setVisibility(View.GONE);
        }
        Log.d("Take",healthdate + "" + petweight + " " + weight_unit);
        cursor.close();
        myDB.readAllDataFromHealthTable().close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.health_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.chart){
            /*Intent intent = new Intent();
            intent.setClass(Health.this,Health_Chart.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("et_date",healthdate);
            bundle.putStringArrayList("et_wei",petweight);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;*/
            Toast.makeText(Health.this, "功能開發中", Toast.LENGTH_SHORT).show();
        }
        return  super.onOptionsItemSelected(item);
    }




}