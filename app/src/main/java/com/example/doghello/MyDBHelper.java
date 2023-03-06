package com.example.doghello;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;
    public MyDBHelper(Context context){
        super(context,"DB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){ //資料表建立
        db.execSQL("CREATE TABLE main.ware"+
                "("+"_id INTEGER PRIMARY KEY NOT NULL,"+
                "kind TEXT,"+
                "kindname TEXT,"+
                "date DATATIME NOT NULL"+")"); //小倉庫資料表

        db.execSQL("CREATE TABLE main.personal"+
                "("+"_id INTEGER PRIMARY KEY NOT NULL,"+
                "PetName TEXT,"+
                "Age TEXT,"+
                "PetKind TEXT,"+
                "Gender TEXT,"+
                "Ligation TEXT,"+
                "Chip TEXT"+ ")"); //寵物資訊資料表

        db.execSQL("CREATE TABLE main.health"+
                "("+"_id INTEGER PRIMARY KEY NOT NULL,"+
                "HealthDate DATATIME NOT NULL,"+
                "Weight TEXT,"+
                "WeightUnit TEXT"+ ")"); //體重資料表
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,int newVersion){ //版本更新
        db.execSQL("DROP TABLE IF EXISTS " + "ware");
        onCreate(db);
    }

    Cursor readAllDataFromWareTable(){ //讀取所有資料(小倉庫頁面)
        String query = "SELECT * FROM " + "main.ware";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void delete_rowdata_FromWareTable(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("main.ware","_id =?",new String[] {row_id});
        Log.d("delete",result+"");
    }

    void UpdateData_ToWareTable(String row_id,String kind,String kindname,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("kind",kind);
        cv.put("kindname",kindname);
        cv.put("date",date);

        long result = db.update("main.ware",cv,"_id = ?",new String[]{row_id});
        Log.d("update",result+"");
    }
    Cursor readAllDataFromPersonalTable(){ //讀取所有資料(寵物基本資料頁面)
        String query = "SELECT * FROM " + "main.personal";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void delete_rowdata_FromPersonalTable(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("main.personal","_id =?",new String[] {row_id});
        Log.d("delete",result+"");
    }



    void UpdateDataToPersonalTable(String row_id,String petname,String age,
                                   String petkind,String gender,String ligation,String chip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("PetName",petname);
        cv.put("Age",age);
        cv.put("PetKind",petkind);
        cv.put("Gender",gender);
        cv.put("Ligation",ligation);
        cv.put("Chip",chip);

        long result = db.update("main.personal",cv,"_id = ?",new String[]{row_id});
        Log.d("update",result+"");
    }
    Cursor readAllDataFromHealthTable(){ //讀取所有資料(健康頁面)
        String query = "SELECT * FROM " + "main.health";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void UpdateData_ToHealthTable(String row_id,String healthdate,
                                  String weight,String weightunit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("HealthDate",healthdate);
        cv.put("Weight",weight);
        cv.put("WeightUnit",weightunit);

        long result = db.update("main.health",cv,"_id = ?",new String[]{row_id});
        Log.d("update",result+"");
    }

    Cursor Take_DateAndWeight_Form_HealthTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("main.health",
                new String[]{"HealthDate","Weight","WeightUnit"},null,
                null,null,null,null);
        return c;
    }
    public void delete_rowdata_FromHealthTable(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("main.health","_id =?",new String[]{row_id});
        Log.d("delete",result+"");
    }

}
