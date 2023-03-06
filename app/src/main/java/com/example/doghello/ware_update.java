package com.example.doghello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

public class ware_update extends AppCompatActivity {
    private Spinner kindspinner;
    private EditText nameedittext;
    private TextView datetextview;
    private Button datebutton,updatebutton,deletebutton;
    private String id,kind,kindname,date;
    private int nYear,nMonth,nDay;
    Boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_update);
        initview();

        getAndSetIntentDate();

        datebutton.setOnClickListener(new View.OnClickListener() { //日期選單
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                nYear = c.get(Calendar.YEAR);
                nMonth = c.get(Calendar.MONTH);
                nDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(ware_update.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateform(year,month,day);
                        datetextview.setText(format);
                    }
                },nYear,nMonth,nDay).show();
            }
        });

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

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDBHelper myDB = new MyDBHelper(ware_update.this);
                kind = kindspinner.getSelectedItem().toString();
                kindname = nameedittext.getText().toString();
                date = datetextview.getText().toString();
                myDB.UpdateData_ToWareTable(id, kind, kindname, date);
                startActivity(
                        new Intent(ware_update.this,Ware_House.class)
                );
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() { //直接刪除這筆資料，之後可以設計對話框確認
            @Override
            public void onClick(View v) {
                MyDBHelper myDB = new MyDBHelper(ware_update.this);
                myDB.delete_rowdata_FromWareTable(id);
                startActivity(
                        new Intent(ware_update.this,Ware_House.class)
                );
            }
        });

    }

    private String setDateform(int year,int month,int day){
        return String.valueOf(year)+"-"
                +String.valueOf(month + 1) + "-"
                +String.valueOf(day);
    }
    private void initview(){
        kindspinner = findViewById(R.id.kindSP2);
        nameedittext = findViewById(R.id.nameET2);
        datetextview = findViewById(R.id.dateTv2);
        datebutton = findViewById(R.id.dateBT2);
        updatebutton = findViewById(R.id.updateBT2);
        deletebutton = findViewById(R.id.deleteBT2);
    }
    private void getAndSetIntentDate(){ //在跳轉進該頁面時，取得相關資料並顯示
        if(getIntent().hasExtra("_id") && getIntent().hasExtra("kind") &&
                getIntent().hasExtra("kindname") && getIntent().hasExtra("date")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("_id");
            kind = getIntent().getStringExtra("kind");
            kindname = getIntent().getStringExtra("kindname");
            date = getIntent().getStringExtra("date");

            //Setting Intent Data
            nameedittext.setText(kindname);
            datetextview.setText(date);
            Log.d("take", kind+" "+kindname+" "+date);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}