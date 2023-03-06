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
import androidx.recyclerview.widget.RecyclerView;

//import com.chauthai.swipereveallayout.SwipeRevealLayout;
//import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.HealthViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<String> id,healthdate,weight,weight_unit;
    public ArrayList<HashMap<String,String>> mdata;
    private MyDBHelper myDB;
    //private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    HealthAdapter(Activity activity, Context context,ArrayList id,
                  ArrayList healthdate, ArrayList weight,ArrayList weight_unit){
            this.activity = activity;
            this.context = context;
            this.id = id;
            this.healthdate = healthdate;
            this.weight = weight;
            this.weight_unit = weight_unit;
        }
    @NonNull
    @Override
    public HealthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.health_item,
                        parent,false);
        return new HealthViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull HealthViewHolder holder, int position) { //綁定資料輸出
        //viewBinderHelper.setOpenOnlyOne(true);
        Intent intent = new Intent(context, Health_Chart.class);
        holder.tvID.setText(String.valueOf(id.get(position)));
        holder.tvDate.setText(String.valueOf(healthdate.get(position)));
        holder.tvWeight.setText(String.valueOf(weight.get(position)));
        holder.tvunit.setText(String.valueOf(weight_unit.get(position)));
        /*holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.delete_rowdata_FromHealthTable(tvID);
            }
        });*/
    }
    @Override
    public int getItemCount() {return healthdate.size();}

    class HealthViewHolder extends RecyclerView.ViewHolder {//ViewHolder:所有物件的ID綁定
        private View parent;
        private TextView tvDate,tvWeight,tvunit,tvID;
        private Button btDelete;

        HealthViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            tvID = itemView.findViewById(R.id.tv_id_item);
            tvDate = itemView.findViewById(R.id.tv_date_item);
            tvWeight = itemView.findViewById(R.id.tv_weight);
            btDelete = itemView.findViewById(R.id.btn_delete);
            tvunit = itemView.findViewById(R.id.tv_unit);
        }
    }
}
