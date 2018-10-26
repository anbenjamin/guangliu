package com.example.benjaminan.test2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminan.test2.R;
import com.example.benjaminan.test2.Schedule.*;

public class showDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView detail_S_title,detail_S_content,detail_is_Important,detail_is_Urgent;
    private Button detail_date_Start,detail_date_Finish,detail_time_Start,detail_time_Finish,detail_update,detail_delete;
    private SQLiteHelper db=new SQLiteHelper(this,"schedule",null,1);;
    int detail_sid,isimportant,isurgent;
    String stitle,scontent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    public void init(){
        Bundle bundle=this.getIntent().getExtras();
        detail_sid=bundle.getInt("Sid");
        int Position=bundle.getInt("Po");

        detail_S_title=(TextView)findViewById(R.id.s_title);
        detail_S_content=(TextView)findViewById(R.id.s_content);
        detail_is_Important=(TextView)findViewById(R.id.s_isImportant);
        detail_is_Urgent=(TextView)findViewById(R.id.s_isUrgent);
        detail_date_Start=(Button)findViewById(R.id.Start_date);
        detail_time_Start=(Button)findViewById(R.id.Start_time);
        detail_time_Finish=(Button)findViewById(R.id.finish_time);
        detail_date_Finish=(Button)findViewById(R.id.finish_date);
        detail_update=(Button)findViewById(R.id.update);
        detail_update.setOnClickListener(this);
        detail_delete=(Button)findViewById(R.id.delete);
        detail_delete.setOnClickListener(this);
        Schedule schedule=db.lookscheduleByid(detail_sid);
        stitle=schedule.getSchedule_title();
        scontent=schedule.getSchedule_content();
        isimportant=schedule.getIsImportant();
        isurgent=schedule.getIsUrgent();
//        Toast.makeText(this,"数据"+detail_sid+"sd"+Position+"js"+isurgent,Toast.LENGTH_SHORT).show();
        if (isimportant==1) {
            detail_is_Important.setText("重要");
        }else{
            detail_is_Important.setText("不重要");
        }
        if (isurgent==1){
            detail_is_Urgent.setText("紧急");
        }else{
            detail_is_Urgent.setText("不紧急");
        }
        detail_date_Start.setText(schedule.getYear_start()+"年"+schedule.getMonth_start()+"月"+schedule.getDay_start()+"日");
        detail_date_Finish.setText(schedule.getYear_finish()+"年"+schedule.getMonth_finish()+"月"+schedule.getDay_finish()+"日");
        detail_time_Start.setText(schedule.getHour_start()+"时"+schedule.getMinute_start()+"分");
        detail_time_Finish.setText(schedule.getHour_finish()+"时"+schedule.getMinute_finish()+"分");
        detail_S_title.setText(schedule.getSchedule_title());
        detail_S_content.setText(schedule.getSchedule_content());
    }
    public void onClick(View view){
        int id=view.getId();
        switch (id){
            case R.id.update:
                Intent intent=new Intent(this,updateScheduleActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("Sid",detail_sid);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.delete:
                db.deleteschedule(detail_sid);
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                showDetailActivity.this.finish();
//                Intent intent1=new Intent(show_detail.this,lookSchedule.class);
//                startActivity(intent1);
                break;
        }
    }
}
