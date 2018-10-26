package com.example.benjaminan.test2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.benjaminan.test2.R;
import com.example.benjaminan.test2.Schedule.*;

import java.util.Calendar;

public class updateScheduleActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText update_schedule_title;
    private EditText update_schedule_content;
    private Button update_btn_reset,update_btn_submit,update_start_date,update_start_time,update_finish_date,update_finish_time;
    private CheckBox update_box_isImportant,update_box_isUrgent;
    int isimportant,isurgent,houre_start,minute_start,houre_finish,minute_finish,year_start,month_start,day_start,year_finish,month_finish,day_finish;
    String s_title,s_content;
    int sid;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private SQLiteHelper db=new SQLiteHelper(this,"schedule",null,1);;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    public void init(){
        Bundle bundle=this.getIntent().getExtras();
        sid=bundle.getInt("Sid");
        Schedule schedule=db.lookscheduleByid(sid);
        update_schedule_title=findViewById(R.id.update_title);
        update_schedule_content=findViewById(R.id.update_content);
        calendar = Calendar.getInstance();
        update_btn_reset=findViewById(R.id.update_reset);
        update_btn_reset.setOnClickListener(this);
        update_start_date=findViewById(R.id.update_Start_date);
        update_start_date.setOnClickListener(this);
        update_start_time=findViewById(R.id.update_Start_time);
        update_start_time.setOnClickListener(this);
        update_finish_date=findViewById(R.id.update_finish_date);
        update_finish_date.setOnClickListener(this);
        update_finish_time=findViewById(R.id.update_finish_time);
        update_finish_time.setOnClickListener(this);
        update_box_isImportant=(CheckBox)findViewById(R.id.update_checkBox_isImportant);
        update_box_isUrgent=(CheckBox)findViewById(R.id.update_checkBox_isUrgent);
        update_btn_submit=findViewById(R.id.update_submit);
        update_btn_submit.setOnClickListener(this);
        //初始化数据，显示之前的数据
        isimportant=schedule.getIsImportant();
        isurgent=schedule.getIsUrgent();
        s_title=schedule.getSchedule_title();
        s_content=schedule.getSchedule_content();
        houre_start=schedule.getHour_start();
        minute_start=schedule.getMinute_start();
        houre_finish=schedule.getHour_finish();
        minute_finish=schedule.getMinute_finish();
        year_start=schedule.getYear_start();
        year_finish=schedule.getYear_finish();
        month_start=schedule.getMonth_start();
        month_finish=schedule.getMonth_finish();
        day_start=schedule.getDay_start();
        day_finish=schedule.getDay_finish();
        update_schedule_title.setText(schedule.getSchedule_title());
        update_schedule_content.setText(schedule.getSchedule_content());
        update_start_date.setText(schedule.getYear_start()+"年"+schedule.getMonth_start()+"月"+schedule.getDay_start()+"日");
        update_finish_date.setText(schedule.getYear_finish()+"年"+schedule.getMonth_finish()+"月"+schedule.getDay_finish()+"日");
        update_start_time.setText(schedule.getHour_start()+"时"+schedule.getMinute_start()+"分");
        update_finish_time.setText(schedule.getHour_finish()+"时"+schedule.getMinute_finish()+"分");
        if (isimportant==1){
            update_box_isImportant.setChecked(true);
        }
        if (isurgent==1){
            update_box_isUrgent.setChecked(true);
        }
        //获取新的数据
        update_box_isImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    isimportant=1;
                    update_box_isImportant.setText("重要");
                }else{
                    isimportant=0;
                    update_box_isImportant.setText("不重要");
                }
            }
        });
        update_box_isUrgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    isurgent=1;
                    update_box_isUrgent.setText("紧急");
                }else{
                    isurgent=0;
                    update_box_isUrgent.setText("不紧急");
                }
            }
        });
    }
    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id) {
            case R.id.update_submit:
                getEditString();
                update();
                break;
            case R.id.update_reset:
                cancel(); // 取消修改
                break;
            case R.id.update_Start_date:
                getDateStart();
                break;
            case R.id.update_Start_time:
                getTimeStart();
                break;
            case R.id.update_finish_date:
                getDateFinish();
                break;
            case R.id.update_finish_time:
                getTimeFinish();
                break;
        }
    }
    public void update(){
        Schedule schedule=new Schedule();
        schedule.setSid(sid);
        schedule.setSchedule_title(s_title);
        schedule.setSchedule_content(s_content);
        schedule.setYear_start(year_start);
        schedule.setYear_finish(year_finish);
        schedule.setMonth_start(month_start);
        schedule.setMonth_finish(month_finish);
        schedule.setDay_start(day_start);
        schedule.setDay_finish(day_finish);
        schedule.setHour_start(houre_start);
        schedule.setHour_finish(houre_finish);
        schedule.setMinute_start(minute_start);
        schedule.setMinute_finish(minute_finish);
        schedule.setIsImportant(isimportant);
        schedule.setIsUrgent(isurgent);
        db.updateSchedule(schedule);
        Toast.makeText(updateScheduleActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(updateScheduleActivity.this,showDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("Sid",sid);
        intent1.putExtras(bundle);
        startActivity(intent1);
        updateScheduleActivity.this.finish();
    }
    public void cancel(){
        Intent intent=new Intent(updateScheduleActivity.this,lookScheduleActivity.class);
        startActivity(intent);
        updateScheduleActivity.this.finish();
    }
    private void getEditString(){
        s_title=update_schedule_title.getText().toString().trim();
        s_content=update_schedule_content.getText().toString().trim();
    }
    public void getDateStart(){
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
                update_start_date.setText(year_start+"年"+month_start+"月"+day_start+"日");
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    public void getTimeStart(){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d("测试", Integer.toString(hourOfDay));
                Log.d("测试", Integer.toString(minute));
                houre_start=Integer.parseInt(Integer.toString(hourOfDay));
                minute_start=Integer.parseInt(Integer.toString(minute));
                update_start_time.setText(houre_start+"时"+minute_start+"分");
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void getDateFinish(){
        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1所以我们要加1
                String time = String.valueOf(year) + "　" + String.valueOf(monthOfYear + 1) + "  " + Integer.toString(dayOfMonth);
                Log.d("测试", time);
                year_finish=Integer.parseInt(String.valueOf(year));
                month_finish=Integer.parseInt(String.valueOf(monthOfYear+1));
                day_finish=Integer.parseInt(Integer.toString(dayOfMonth));
                update_finish_date.setText(year_finish+"年"+month_finish+"月"+day_finish+"日");
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void getTimeFinish(){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d("测试", Integer.toString(hourOfDay));
                Log.d("测试", Integer.toString(minute));
                houre_finish=Integer.parseInt(Integer.toString(hourOfDay));
                minute_finish=Integer.parseInt(Integer.toString(minute));
                update_finish_time.setText(houre_finish+"时"+minute_finish+"分");
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
