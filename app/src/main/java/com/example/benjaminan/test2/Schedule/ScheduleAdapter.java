package com.example.benjaminan.test2.Schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.benjaminan.test2.R;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter{
    private int resourceId;
    private List<Schedule> list;
    private LayoutInflater inflater;
    public ScheduleAdapter(List<Schedule> list, Context context) {
    this.list = list;
    this.inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
    public void setList(List<Schedule> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.schedule_list, null);
        Schedule schedule=list.get(position);
        CheckBox important=(CheckBox) convertView.findViewById(R.id.is_Important);
        CheckBox urgent=(CheckBox) convertView.findViewById(R.id.is_urgent);
        important.setEnabled(false);
        urgent.setEnabled(false);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        title.setText(String.valueOf(schedule.getSchedule_title()));
        int Important=schedule.getIsImportant();
        int Urgent=schedule.getIsUrgent();
        if (Important==1){
            important.setChecked(true);
        }else important.setChecked(false);
        if (Urgent==1){
            urgent.setChecked(true);
        }else urgent.setChecked(false);
        return convertView;
    }
}