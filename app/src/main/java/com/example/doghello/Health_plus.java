package com.example.doghello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Health_plus extends AppCompatActivity {
    TextView tv_date;
    EditText et_wei;
    Button btn_creyes,btn_date; //新增鈕
    Spinner spinner_weight_unit;
    Integer  resultcode = 1;
    private Boolean FirstTime = true;
    private int mYear, mMonth, mDay;
    private MyDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_plus);

        myDB = new MyDBHelper(this);

        tv_date = findViewById(R.id.tv_date);
        et_wei = findViewById(R.id.et_wei);
        btn_creyes = findViewById(R.id.btn_creyes);
        btn_date = findViewById(R.id.btn_date);
        spinner_weight_unit = findViewById(R.id.sp_weight_unit);

        tv_date.setInputType(InputType.TYPE_NULL);  //不跳鍵盤
        et_wei.setInputType(InputType.TYPE_CLASS_NUMBER);  //跳數字框

        //跳轉日曆輸入
       btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth =c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(Health_plus.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                },mYear,mMonth,mDay).show();
            }
        });

        ArrayAdapter weight_unit = ArrayAdapter.createFromResource(this,
                R.array.weight_unit, android.R.layout.simple_dropdown_item_1line);
        spinner_weight_unit.setAdapter(weight_unit);
        spinner_weight_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (FirstTime){FirstTime = false;}
                else{ Toast.makeText(view.getContext(), parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show(); }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btn_creyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                health_add();
                /*getIntent().putExtra("et_date",tv_date.getText().toString());
                getIntent().putExtra("et_wei",et_wei.getText().toString());

                setResult(resultcode,getIntent());
                Log.d("Resultcode",resultcode+"");
                startActivity(
                        new Intent(Health_plus.this,Health.class)
                );
                setResult(resultcode,getIntent());*/
                finish();
            }
        });
    }
    private void health_add(){ //將資料讀取並轉型存放進資料庫，之後跳轉回顯示頁

        String healthdate = tv_date.getText().toString();
        String weight = et_wei.getText().toString();
        String weightunit = spinner_weight_unit.getSelectedItem().toString();

        ContentValues values = new ContentValues();

        values.put("HealthDate",healthdate);
        values.put("Weight",weight);
        values.put("WeightUnit",weightunit);

        long id = myDB.getWritableDatabase().insert("health",
                null,values);
        Log.d("ADD",id+"");


        myDB.getWritableDatabase().close();
        startActivity(
                new Intent(Health_plus.this,Health.class)
        );
    }
}