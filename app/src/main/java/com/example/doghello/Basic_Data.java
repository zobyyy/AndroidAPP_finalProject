
//此頁面為資料輸入頁
package com.example.doghello;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Basic_Data extends AppCompatActivity {
    private MyDBHelper myDB;
    private EditText name_edittext,petkind_edittext;
    private Spinner age_spinner,gender_spinner;
    private Button complete_button,photo_update_button;
    private Switch ligation_switch,chip_switch;
    private ImageView photo_imageview;
    private Activity activity;
    private Boolean FirstTime = true;
    private SwitchCompat Switch = null;
    StorageReference storageReference,pic_storage;
    int request = 1;
    Uri uri;
    String data_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic__data);
        initView();
        myDB = new MyDBHelper(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        //camera_permission();
        //照片上傳
        photo_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

               startActivityForResult(intent,1);

            }
        });

        //年齡列表，目前只到20歲
        ArrayList AgeList = new ArrayList<Integer>();
        for (int i = 0;i<20;i++){ AgeList.add(i); }

        ArrayAdapter Age = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,AgeList);
        age_spinner.setAdapter(Age);
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
        /*ArrayAdapter PetKind = ArrayAdapter.createFromResource(this,
                R.array.Pet_kind, android.R.layout.simple_dropdown_item_1line);
        petkind_spinner.setAdapter(PetKind);
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
        ArrayAdapter Gender = ArrayAdapter.createFromResource(this,
                R.array.Pet_gender, android.R.layout.simple_dropdown_item_1line);
        gender_spinner.setAdapter(Gender);
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

        //確認按鈕
        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //photo_upload_to_firebase();

                add();
                /*if(request == 2){

                    );
                }*/

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        int PICk_CONTACT_REQUEST=1;
        if(requestCode==PICk_CONTACT_REQUEST){
            uri = data.getData();
            photo_imageview.setImageURI(uri);

            ContentResolver contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            data_list=mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        StringBuffer word = new StringBuffer();
        switch (permissions.length) {
            case 1:
                if (permissions[0].equals(Manifest.permission.CAMERA)) word.append("相機權限");
                else word.append("儲存權限");
                if (grantResults[0] == 0) word.append("已取得");
                else word.append("未取得");
                word.append("\n");
                if (permissions[0].equals(Manifest.permission.CAMERA)) word.append("儲存權限");
                else word.append("相機權限");
                word.append("已取得");

                break;
            case 2:
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equals(Manifest.permission.CAMERA)) word.append("相機權限");
                    else word.append("儲存權限");
                    if (grantResults[i] == 0) word.append("已取得");
                    else word.append("未取得");
                    if (i < permissions.length - 1) word.append("\n");
                }
                break;
        }
        //tvRes.setText(word.toString());
    }

    //定義元件
    private void initView(){
        name_edittext = findViewById(R.id.name_edittext);
        age_spinner = findViewById(R.id.age_spinner);
        petkind_edittext = findViewById(R.id.pet_kind_edittext);
        gender_spinner = findViewById(R.id.gender_spinner);
        complete_button = findViewById(R.id.complete_button);
        ligation_switch = findViewById(R.id.ligation_switch);
        chip_switch = findViewById(R.id.chip_switch);
        photo_update_button = findViewById(R.id.photo_button);
        photo_imageview = findViewById(R.id.photo_imageview);
    }

    //將資料轉型並存進資料庫
    private void add(){
        String petname = name_edittext.getText().toString();
        String age = age_spinner.getSelectedItem().toString();
        String petkind = petkind_edittext.getText().toString();
        String gender = gender_spinner.getSelectedItem().toString();
        String ligation = ligation_switch.getText().toString();
        String chip = chip_switch.getText().toString();

        ContentValues values = new ContentValues();
        values.put("PetName",petname);
        values.put("Age",age);
        values.put("PetKind",petkind);
        values.put("Gender",gender);
        values.put("Ligation",ligation);
        values.put("Chip",chip);

        long id = myDB.getWritableDatabase().insert("personal",
                null,values);
        Log.d("ADD",id+"");
        startActivity(
                new Intent(Basic_Data.this,Basic_Data_Page.class));

    }

    //相機、相簿權限
    private void camera_permission(){
        boolean cameraHasGone = checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        boolean externalHasGone= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions;
            if (!cameraHasGone && !externalHasGone) {//如果兩個權限都未取得
                permissions = new String[2];
                permissions[0] = Manifest.permission.CAMERA;
                permissions[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            } else if (!cameraHasGone) {//如果只有相機權限未取得
                permissions = new String[1];
                permissions[0] = Manifest.permission.CAMERA;
            } else if (!externalHasGone) {//如果只有存取權限未取得
                permissions = new String[1];
                permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            } else {
                Log.d("Permission","相機權限已取得\n儲存權限已取得");
                return;
            }
            requestPermissions(permissions, 100);
        }
    }

    //將圖片上傳至Firebase
    private void photo_upload_to_firebase(){
        pic_storage=storageReference.child("name:"+data_list);
        pic_storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("Upload","Complete");
                //request = 2;
                add();
                startActivity(
                        new Intent(Basic_Data.this,Basic_Data_Page.class));
            }
        });
    }

}