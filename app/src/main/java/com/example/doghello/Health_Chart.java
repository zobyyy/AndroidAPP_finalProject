package com.example.doghello;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Health_Chart extends AppCompatActivity {

    private LineChart lineChart;
    private ArrayList<String> date;
    private ArrayList<Integer> weight;
    private MyDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_chart);
        initView();

        getdata();

        List<Entry> entries = new ArrayList<>();
        for (int i = 0;i<weight.size();i++){
            //float wei = Float.parseFloat(weight)
            entries.add(new Entry(i,weight.get(i)));
        }

        //線的風格
        LineDataSet lineDataSet = new LineDataSet(entries,"體重數據圖");
        lineDataSet.setColor(Color.parseColor("#E0B090"));
        lineDataSet.setLineWidth(3.0f);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleColor(Color.parseColor("#F2D9A8"));
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//平滑曲線
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(lineDataSet);
        lineData.setDrawValues(true);
        lineData.setValueTextSize(12f);
        lineChart.setData(lineData);

        //缩放
        lineChart.setScaleEnabled(true);
        lineChart.getXAxis().setDrawGridLines(false);  //繪製背景裡的X線
        lineChart.getAxisLeft().setDrawGridLines(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        //獲取x軸
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(date.size());
        /*xAxis.setGranularity(5f);*/
        /*xAxis.setValueFormatter(new IndexAxisValueFormatter(date));*/
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return super.getFormattedValue(value, axis);
            }
        });
        xAxis.setTextSize(10f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(60f);

        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setTextSize(15f);
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setAxisMaximum(100);
        leftYAxis.setEnabled(true);
        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);
    }

    private void initView() {
        lineChart = findViewById(R.id.line_chart);
    }

    public void getdata(){ //
        /*myDB.Take_DateAndWeight_Form_HealthTable();
        Log.d("Check","");*/
        Bundle bundle = getIntent().getExtras();
        date = bundle.getStringArrayList("et_date");
        weight = bundle.getIntegerArrayList("et_wei");
        //Log.d("dataStringChart", date.toString());
        //Log.d("dataStringChart", weight.toString());
    }
}