package com.example.doghello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
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

public class Ware_plus extends AppCompatActivity {
    private MyDBHelper myDB;
    private Spinner kindspinner;
    private EditText nameedittext;
    private TextView datetextview;
    private Button datebutton,plusbutton,nextbutton;
    private int nYear,nMonth,nDay;
    Boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_plus);
        findViews();
        myDB = new MyDBHelper(this);
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this,R.array.kind_name, android.R.layout.simple_spinner_item
        );
        nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindspinner.setAdapter(nAdapter);
        kindspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                if (firstTime){firstTime = false;}
                else{
                    Toast.makeText(view.getContext(),
                            parent.getSelectedItem().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        datebutton.setOnClickListener(new View.OnClickListener() { //日期選單
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                nYear = c.get(Calendar.YEAR);
                nMonth = c.get(Calendar.MONTH);
                nDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Ware_plus.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateform(year,month,day);
                        datetextview.setText(format);
                    }
                },nYear,nMonth,nDay).show();
            }
        });

        plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ware_add_and_changetolastpage();
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ware_add_and_next_data();
            }
        });
    };

    private String setDateform(int year,int month,int day){
        return String.valueOf(year)+"-"
                +String.valueOf(month + 1) + "-"
                +String.valueOf(day);
    }
    private void findViews(){
        kindspinner = findViewById(R.id.kindSP);
        nameedittext = findViewById(R.id.nameET);
        datetextview = findViewById(R.id.dateTv);
        datebutton = findViewById(R.id.dateBT);
        plusbutton = findViewById(R.id.plusBT);
        nextbutton = findViewById(R.id.next_data_button);
    }
    private void delViews(){
        kindspinner = findViewById(R.id.kindSP);
        nameedittext.setText("");
        datetextview.setText("");
    }
    private void ware_add_and_next_data(){ //將資料讀取並轉型存放進資料庫，清除元件資料供下一筆輸入
        String kind_edittext = kindspinner.getSelectedItem().toString();
        String name_edittext = nameedittext.getText().toString();
        String date_edittext = datetextview.getText().toString();

        ContentValues values = new ContentValues();
        values.put("kind",kind_edittext);
        values.put("kindname",name_edittext);
        values.put("date",date_edittext);
        long id = myDB.getWritableDatabase().insert("ware",
                null,values);
        Log.d("ADD",id+"");
        delViews();
    }

    private void ware_add_and_changetolastpage(){ //將資料讀取並轉型存放進資料庫，之後跳轉回顯示頁
        String kind_edittext = kindspinner.getSelectedItem().toString();
        String name_edittext = nameedittext.getText().toString();
        String date_edittext = datetextview.getText().toString();

        ContentValues values = new ContentValues();
        values.put("kind",kind_edittext);
        values.put("kindname",name_edittext);
        values.put("date",date_edittext);
        long id = myDB.getWritableDatabase().insert("ware",
                null,values);
        Log.d("ADD",id+"");
        startActivity(
                new Intent(Ware_plus.this,Ware_House.class)
        );
        myDB.getWritableDatabase().close();
    }
}
//該專案尚未連結至Firestore