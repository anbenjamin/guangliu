package com.example.benjaminan.test2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benjaminan.test2.R;
import com.example.benjaminan.test2.Schedule.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class lookScheduleActivity extends AppCompatActivity implements View.OnClickListener{
    private Button choose_Start,choose_End,search_Button;
    int year_start,month_start,day_start,year_finish,month_finish,day_finish;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog,datePickerDialog2;
    private Calendar calendar,calendar2;
    private SQLiteHelper db;
    private ScheduleAdapter listAdapter;
    private List<Schedule> scheduleList;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_schedule);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = new SQLiteHelper(this, "schedule", null, 1);
        init();
    }
    public void init(){
        calendar = Calendar.getInstance();
        calendar2=Calendar.getInstance();
        choose_Start=findViewById(R.id.choose_start);
        choose_Start.setOnClickListener(this);
        choose_End=findViewById(R.id.choose_end);
        choose_End.setOnClickListener(this);
        search_Button=findViewById(R.id.search_button);
        search_Button.setOnClickListener(this);
        listAdapter = new ScheduleAdapter(new ArrayList<Schedule>(), this);
        ListView listView=(ListView)findViewById(R.id.look_list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(lookScheduleActivity.this,showDetailActivity.class);
                Bundle bundle=new Bundle();
                Schedule schedule=scheduleList.get(position);
                int Sid=schedule.getSid();
                bundle.putInt("Sid",Sid);
                intent.putExtras(bundle);
                lookScheduleActivity.this.finish();
                startActivity(intent);
            }
        });
    }
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.choose_start:
                choosestart();
                break;
            case R.id.choose_end:
                chooseend();
                break;
            case R.id.search_button:
                if(year_start>year_finish){
                    Toast.makeText(lookScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                    break;
                }else if (year_finish==year_start) {
                    if (month_start > month_finish) {
                        Toast.makeText(lookScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                        break;
                    }else if (month_finish==month_start){
                        if (day_finish<day_start){
                            Toast.makeText(lookScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                            break;
                        }else start();
                    }else start();
                }else start();
                start();
                break;
        }
    }
    public void start(){
        scheduleList = db.queryScheduleBydat(year_start,month_start,day_start,year_finish,month_finish,day_finish); // 查询所有的Person
        Toast.makeText(this,"find",Toast.LENGTH_SHORT).show();
        listAdapter.setList(scheduleList);
        listAdapter.notifyDataSetChanged();
//        loadData(scheduleList);
    }
    //    private void loadData(List<Schedule> scheduleList) {
//        listAdapter.setList(scheduleList);
//        listAdapter.notifyDataSetChanged(); // 刷新数据
//    }
    public void choosestart(){
        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1所以我们要加1
                String time = String.valueOf(year) + "　" + String.valueOf(monthOfYear + 1) + "  " + Integer.toString(dayOfMonth);
                Log.d("测试", time);
                year_start=Integer.parseInt(String.valueOf(year));
                month_start=Integer.parseInt(String.valueOf(monthOfYear+1));
                day_start=Integer.parseInt(Integer.toString(dayOfMonth));
                choose_Start.setText(year_start+"年"+month_start+"月"+day_start+"日");
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    public void chooseend(){
        datePickerDialog2 = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1所以我们要加1
                String time = String.valueOf(year) + "　" + String.valueOf(monthOfYear + 1) + "  " + Integer.toString(dayOfMonth);
                Log.d("测试", time);
                year_finish=Integer.parseInt(String.valueOf(year));
                month_finish=Integer.parseInt(String.valueOf(monthOfYear+1));
                day_finish=Integer.parseInt(Integer.toString(dayOfMonth));
                choose_End.setText(year_finish+"年"+month_finish+"月"+day_finish+"日");
            }
        },
                calendar2.get(Calendar.YEAR),
                calendar2.get(Calendar.MONTH),
                calendar2.get(Calendar.DAY_OF_MONTH));
        datePickerDialog2.show();
        //自动弹出键盘问题解决
        datePickerDialog2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
