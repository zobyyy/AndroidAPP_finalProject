package com.example.doghello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

//import com.chauthai.swipereveallayout.SwipeRevealLayout;
//import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

public class Basic_Data_Adapter extends RecyclerView.Adapter<Basic_Data_Adapter.BasicDataViewHolder>{
    private Context context;
    private Activity activity;
    private ArrayList<String> id,petname,age,petkind,gender,ligation,chip;
   // private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    Basic_Data_Adapter(Activity activity, Context context, ArrayList id,ArrayList petname, ArrayList age, ArrayList petkind,
              ArrayList gender,ArrayList ligation,ArrayList chip){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.petname = petname;
        this.age = age;
        this.petkind = petkind;
        this.gender = gender;
        this.ligation = ligation;
        this.chip = chip;
    }

    @NonNull
    @Override
    public BasicDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.basic_data_item,
                        parent,false);
        return new BasicDataViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)//API作用為更改APP的版本號
    @Override
    public void onBindViewHolder(@NonNull BasicDataViewHolder holder, int position) { //綁定資料輸出
       // viewBinderHelper.setOpenOnlyOne(true);
        //viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));
        holder.tvid.setText(String.valueOf(id.get(holder.getAdapterPosition())));
        holder.tvPetName.setText(String.valueOf(petname.get(holder.getAdapterPosition())));
        holder.tvAge.setText(String.valueOf(age.get(holder.getAdapterPosition())));
        holder.tvPetKind.setText(String.valueOf(petkind.get(holder.getAdapterPosition())));
        holder.tvGender.setText(String.valueOf(gender.get(holder.getAdapterPosition())));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Basic_Data_Update.class);
                intent.putExtra("_id", String.valueOf(id.get(holder.getAdapterPosition())));
                intent.putExtra("PetName", String.valueOf(petname.get(holder.getAdapterPosition())));
                intent.putExtra("Age", String.valueOf(age.get(holder.getAdapterPosition())));
                intent.putExtra("PetKind", String.valueOf(petkind.get(holder.getAdapterPosition())));
                intent.putExtra("Gender", String.valueOf(gender.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {return petname.size();} //需更改

    class BasicDataViewHolder extends RecyclerView.ViewHolder {//ViewHolder:所有物件的ID綁定
        private View parent;
        private TextView tvid,tvPetName,tvAge,tvPetKind,tvGender;
        //private Button btDelete,btChange;
        //private SwipeRevealLayout swipeRevealLayout;
        BasicDataViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            tvid = itemView.findViewById(R.id.Personal_id);
            tvPetName = itemView.findViewById(R.id.Pet_name_textview);
            tvAge = itemView.findViewById(R.id.Age_input_textview);
            tvPetKind = itemView.findViewById(R.id.Pet_Kind_input_textview);
            tvGender = itemView.findViewById(R.id.Gender_input_textview);
            //swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
        }
    }
}
