
//此頁面為顯示頁
package com.example.doghello;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Basic_Data_Page extends AppCompatActivity {
    private MyDBHelper myDB;
    private Basic_Data_Adapter Adapter;
    private ArrayList<String> id,petname,age,petkind,gender,ligation,chip;
    private ImageView basic_nodata;
    StorageReference storageReference,pic_storage;
    String data_list;
    ImageView photo_image;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic__data__page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        storageReference= FirebaseStorage.getInstance().getReference();
        //RecyclerView的顯示方法
        RecyclerView recyclerView = findViewById(R.id.list_recycleview_basicdata);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDB = new MyDBHelper(Basic_Data_Page.this);
        id = new ArrayList<>();
        petname = new ArrayList<>();
        age = new ArrayList<>();
        petkind = new ArrayList<>();
        gender = new ArrayList<>();
        basic_nodata=findViewById(R.id.basic_nodata);

        storeDataInArrays();

        Adapter = new Basic_Data_Adapter(Basic_Data_Page.this,
                this, id,petname, age, petkind, gender,ligation,chip);
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Basic_Data_Page.this));

        FloatingActionButton fab = findViewById(R.id.fabtobasicdata);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Basic_Data_Page.this,Basic_Data.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            uri = data.getData();
            //photo_image.setImageURI(uri);
            //pick_photo();
            ContentResolver contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            data_list=mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
            recreate();
        }
    }

    private void storeDataInArrays(){ //資料列顯示

        Cursor cursor = myDB.readAllDataFromPersonalTable();
        if(cursor.getCount() == 0){
            basic_nodata.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                //take_photo_form_firebase();
                id.add(cursor.getString(0));
                petname.add(cursor.getString(1));
                age.add(cursor.getString(2));
                petkind.add(cursor.getString(3));
                gender.add(cursor.getString(4));
            }
            basic_nodata.setVisibility(View.GONE);
        }
        cursor.close();
        myDB.readAllDataFromPersonalTable().close();
    }

    private void take_photo_form_firebase(){

        pic_storage=storageReference.child("name:"+data_list);
        photo_image = findViewById(R.id.photo_imagebutton);
        File file;
        try{
            String prefix;
            Object suffix;
            file=File.createTempFile("images","png");
            pic_storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    photo_image.setImageURI(Uri.fromFile(file));
                    Log.d("Take:","name:"+data_list);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void pick_photo(){
        List<String> petkindforimg = petkind;
        petkindforimg.add("Cat");
        petkindforimg.add("Dog");
        petkindforimg.add("Rabbit");
        petkindforimg.add("Hamster");
        petkindforimg.add("Hedgehog");
        String petkindforimage = "";
        for(String petkind : petkindforimg){
            petkindforimage += petkind+",";
        }
        switch (petkindforimage){
            case "Cat":
                photo_image.setImageResource(R.drawable.cat);
            case "Dog":
                photo_image.setImageResource(R.drawable.dog);
            case "Rabbit":
                photo_image.setImageResource(R.drawable.rabbit);
            case "Hamster":
                photo_image.setImageResource(R.drawable.hamster);
            case "Hedgehog":
                photo_image.setImageResource(R.drawable.hedgehog);
        }
    }



}