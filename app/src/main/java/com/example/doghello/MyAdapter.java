package com.example.doghello;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

//import com.chauthai.swipereveallayout.SwipeRevealLayout;
//import com.chauthai.swipereveallayout.ViewBinderHelper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Context context;
    private Activity activity;
    private ArrayList<String> id,kind,kindname,date;
    private ListView listView;
    private String _id,_kind,_kindname,_date;
    //private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private MyDBHelper myDB;


    MyAdapter(Activity activity, Context context, ArrayList id,
              ArrayList kind, ArrayList kindname,
              ArrayList date){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.kind = kind;
        this.kindname = kindname;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item,parent,
                        false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)//API作用為更改APP的版本號
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) { //綁定資料輸出
        /*viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));*/

        holder.tvId.setText(String.valueOf(id.get(holder.getAdapterPosition())));
        holder.tvKind.setText(String.valueOf(kind.get(holder.getAdapterPosition())));
        holder.tvKindName.setText(String.valueOf(kindname.get(holder.getAdapterPosition())));
        holder.tvDate.setText(String.valueOf(date.get(holder.getAdapterPosition())));
        holder.btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.swipeRevealLayout.close(true);
                Intent intent = new Intent(context, ware_update.class);
                intent.putExtra("_id", String.valueOf(id.get(holder.getAdapterPosition())));
                intent.putExtra("kind", String.valueOf(kind.get(holder.getAdapterPosition())));
                intent.putExtra("kindname", String.valueOf(kindname.get(holder.getAdapterPosition())));
                intent.putExtra("date", String.valueOf(date.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return kind.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {//ViewHolder:所有物件的ID綁定
        private View parent;
        private TextView tvKind,tvDate,tvKindName,tvId;
        //private SwipeRevealLayout swipeRevealLayout;
        private Button btChange;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            //swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            tvId = itemView.findViewById(R.id._idTV);
            tvKind = itemView.findViewById(R.id._kindtv);
            tvDate = itemView.findViewById(R.id._dateTV);
            tvKindName = itemView.findViewById(R.id._kindnameTV);
            //swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            btChange= itemView.findViewById(R.id.button_change);
            //listView = (ListView)findViewById(R.id.list_recycleview_warehouse);
        }
    }

}
