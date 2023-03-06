package com.example.doghello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class Basic_Data_Update extends AppCompatActivity {
    private MyDBHelper myDB;
    private EditText name_edittext,petkind_edittext;
    private Spinner age_spinner,gender_spinner;
    private Button update_button,delete_button;
    private Switch ligation_switch,chip_switch;
    private String _id,PetName,Age,PetKind,Gender,Ligation,Chip;
    Boolean FirstTime = true;
    SwitchCompat Switch = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic__data__update);
        initView();
        //myDB = new MyDBHelper(this);

        getAndSetIntentDatetoBasicData();

        //年齡可選範圍，目前只到20歲
        ArrayList AgeList = new ArrayList<Integer>();
        for (int i = 0;i<20;i++){ AgeList.add(i); }

        ArrayAdapter _Age = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,AgeList);
        age_spinner.setAdapter(_Age);
        age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (FirstTime){ FirstTime = false; }
                else{ Toast.makeText(view.getContext(),parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show(); }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //寵物品種，需要再精簡
        /*ArrayAdapter _PetKind = ArrayAdapter.createFromResource(this,
                R.array.Pet_kind, android.R.layout.simple_dropdown_item_1line);
        petkind_spinner.setAdapter(_PetKind);
        petkind_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (FirstTime){FirstTime = false;}
                else{ Toast.makeText(view.getContext(),parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show(); }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });*/

        //性別選項，僅公母
        ArrayAdapter _Gender = ArrayAdapter.createFromResource(this,
                R.array.Pet_gender, android.R.layout.simple_dropdown_item_1line);
        gender_spinner.setAdapter(_Gender);
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (FirstTime){FirstTime = false;}
                else{ Toast.makeText(view.getContext(), parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show(); }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //結紮Switch，動作後會更改顯示字，並保存顯示字
        //ligation_switch.setChecked(false);
        ligation_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ligation_switch.setText("已結紮");
                }else{
                    ligation_switch.setText("未結紮");
                }
            }
        });

        //晶片Switch，動作後會更改顯示字，並保存顯示字
        //chip_switch.setChecked(false);
        chip_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    chip_switch.setText("已植入晶片");
                }else{
                    chip_switch.setText("未植入晶片");
                }
            }
        });

        //更新按鈕
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper myDB = new MyDBHelper(Basic_Data_Update.this);
                PetName = name_edittext.getText().toString();
                Age = age_spinner.getSelectedItem().toString();
                PetKind = petkind_edittext.getText().toString();
                Gender = gender_spinner.getSelectedItem().toString();
                Ligation = ligation_switch.getText().toString();
                Chip = chip_switch.getText().toString();

                /*ContentValues values = new ContentValues();
                values.put("PetName",PetName);
                values.put("Age",Age);
                values.put("PetKind",PetKind);
                values.put("Gender",Gender);
                values.put("Ligation",Ligation);
                values.put("Chip",Chip);*/
                myDB.UpdateDataToPersonalTable(_id, PetName, Age, PetKind, Gender, Ligation, Chip);
                startActivity(
                        new Intent(Basic_Data_Update.this,Basic_Data_Page.class)
                );
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper myDB = new MyDBHelper(Basic_Data_Update.this);
                myDB.delete_rowdata_FromPersonalTable(_id);
                startActivity(
                        new Intent(Basic_Data_Update.this,Basic_Data_Page.class)
                );
            }
        });
    }

    private void initView(){
        name_edittext = findViewById(R.id.name_edittext2);
        age_spinner = findViewById(R.id.age_spinner2);
        petkind_edittext = findViewById(R.id.pet_kind_edittext2);
        gender_spinner = findViewById(R.id.gender_spinner2);
        update_button = findViewById(R.id.update_button2);
        delete_button = findViewById(R.id.delete_button2);
        ligation_switch = findViewById(R.id.ligation_switch2);
        chip_switch = findViewById(R.id.chip_switch2);
    }

    private void getAndSetIntentDatetoBasicData(){ //在跳轉進該頁面時，取得相關資料並顯示
        if(getIntent().hasExtra("_id") && getIntent().hasExtra("PetName") &&
                getIntent().hasExtra("Age") && getIntent().hasExtra("PetKind")&&
                getIntent().hasExtra("Gender")/*&& getIntent().hasExtra("Ligation")&&
                getIntent().hasExtra("Chip")*/){
            //Getting Data from Intent
            _id = getIntent().getStringExtra("_id");
            PetName = getIntent().getStringExtra("PetName");
            Age = getIntent().getStringExtra("Age");
            PetKind = getIntent().getStringExtra("PetKind");
            Gender = getIntent().getStringExtra("Gender");
            //Ligation = getIntent().getStringExtra("Ligation");
            //Chip = getIntent().getStringExtra("Chip");

            //Setting Intent Data
            name_edittext.setText(PetName);
            petkind_edittext.setText(PetKind);
            //if(PetKind = )
            //age_spinner.setText(date);
            /*if(_Ligation == "已結紮"){
                ligation_switch.setChecked(true);
            }*/
            Log.d("take", "");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}