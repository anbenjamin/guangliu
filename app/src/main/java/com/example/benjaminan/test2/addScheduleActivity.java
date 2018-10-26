package com.example.benjaminan.test2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.benjaminan.test2.Schedule.*;
import com.example.benjaminan.test2.R;
import com.example.benjaminan.test2.Schedule.SQLiteHelper;

import java.util.Calendar;

public class addScheduleActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * 插入请求代码 大于0
     */
    private static final int INSERT_REQUESTCODE = 1;
    private EditText schedule_title;
    private EditText schedule_content;
    private Button reset,submit,start_date,start_time,finish_date,finish_time;
    private CheckBox box_isImportant,box_isUrgent;
    int isimportant,isurgent,houre_start,minute_start,houre_finish,minute_finish,year_start,month_start,day_start,year_finish,month_finish,day_finish;
    String s_title,s_content;
    int flag1=0,flag2=0,flag3=0,flag4=0;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private SQLiteHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        box_isImportant=(CheckBox)findViewById(R.id.checkBox_isImportant);
        box_isUrgent=findViewById(R.id.checkBox_isUrgent);
        box_isImportant.setText("不重要");
        box_isUrgent.setText("不紧急");
        box_isImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    isimportant=1;
                    box_isImportant.setText("重要");
                }else{
                    isimportant=0;
                    box_isImportant.setText("不重要");
                }
            }
        });
        box_isUrgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    isurgent=1;
                    box_isUrgent.setText("紧急");
                }else{
                    isurgent=0;
                    box_isUrgent.setText("不紧急");
                }
            }
        });
    }
    public void init(){
        db=new SQLiteHelper(this,"schedule",null,1);
        schedule_title=findViewById(R.id.schedule_title);
        schedule_content=findViewById(R.id.schedule_content);
        calendar = Calendar.getInstance();
        submit=findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        reset=findViewById(R.id.btn_reset);
        reset.setOnClickListener(this);
        start_date=findViewById(R.id.Start_date);
        start_date.setOnClickListener(this);
        start_time=findViewById(R.id.Start_time);
        start_time.setOnClickListener(this);
        finish_date=findViewById(R.id.finish_date);
        finish_date.setOnClickListener(this);
        finish_time=findViewById(R.id.finish_time);
        finish_time.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id) {
            case R.id.btn_submit:
                getEditString();
                if (TextUtils.isEmpty(s_title)) {
                    Toast.makeText(addScheduleActivity.this, "请输入日程标题", Toast.LENGTH_SHORT).show();
                    break;
                } else if (TextUtils.isEmpty(s_content)) {
                    Toast.makeText(addScheduleActivity.this, "请输入日程内容", Toast.LENGTH_SHORT).show();
                    break;
                } else if (flag1 == 0) {
                    Toast.makeText(addScheduleActivity.this, "请选择起始日期", Toast.LENGTH_SHORT).show();
                    break;
                } else if (flag2 == 0) {
                    Toast.makeText(addScheduleActivity.this, "请选择起始时间", Toast.LENGTH_SHORT).show();
                    break;
                } else if (flag3 == 0) {
                    Toast.makeText(addScheduleActivity.this, "请选择结束日期", Toast.LENGTH_SHORT).show();
                    break;
                } else if (flag4 == 0) {
                    Toast.makeText(addScheduleActivity.this, "请选择结束时间", Toast.LENGTH_SHORT).show();
                    break;
                } else if (year_start > year_finish) {
                    Toast.makeText(addScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                    break;
                } else if (year_finish == year_start) {
                    if (month_start > month_finish) {
                        Toast.makeText(addScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (month_finish == month_start) {
                        if (day_finish < day_start) {
                            Toast.makeText(addScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                            break;
                        } else if (day_start == day_finish) {
                            if (houre_finish < houre_start) {
                                Toast.makeText(addScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                                break;
                            } else if (houre_finish == houre_start) {
                                if (minute_finish < minute_start) {
                                    Toast.makeText(addScheduleActivity.this, "开始时间不可以晚于结束时间", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                    }
                }
                save();
                break;
            case R.id.btn_reset:
                cancel(); // 取消插入
                break;
            case R.id.Start_date:
                getDateStart();
                flag1=1;
                break;
            case R.id.Start_time:
                getTimeStart();
                flag2=1;
                break;
            case R.id.finish_date:
                getDateFinish();
                flag3=1;
                break;
            case R.id.finish_time:
                getTimeFinish();
                flag4=1;
                break;
        }
    }
    public void save(){
        Schedule schedule=new Schedule();
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
        Intent intent = new Intent();
        intent.putExtra("schedule", schedule);
        setResult(INSERT_REQUESTCODE, intent);
        db.addSchedule(schedule);
        Toast.makeText(addScheduleActivity.this, "插入成功！", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
        intent2.putExtra("data","refresh");
        LocalBroadcastManager.getInstance(addScheduleActivity.this).sendBroadcast(intent2);
        sendBroadcast(intent2);
        addScheduleActivity.this.finish();
    }
    public void cancel(){
        setResult(INSERT_REQUESTCODE);
        addScheduleActivity.this.finish();
    }
    private void getEditString(){
        s_title=schedule_title.getText().toString().trim();
        s_content=schedule_content.getText().toString().trim();
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
                start_date.setText(year_start+"年"+month_start+"月"+day_start+"日");
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
                start_time.setText(houre_start+"时"+minute_start+"分");
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
                finish_date.setText(year_finish+"年"+month_finish+"月"+day_finish+"日");
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
                finish_time.setText(houre_finish+"时"+minute_finish+"分");
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
