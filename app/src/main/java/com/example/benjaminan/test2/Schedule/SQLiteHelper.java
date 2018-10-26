package com.example.benjaminan.test2.Schedule;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库创建、更新
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    /**
     * @param context
     *            上下文
     * @param name
     *            数据库名称
     * @param factory
     *            游标工厂
     * @param version
     *            数据库版本
     */
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("SqliteHelper", "数据库创建");
        String sql = "create table schedule(sid integer Primary Key autoincrement," +
                "schedule_title varchar(20)," +
                " hour_start integer," +
                "schedule_content varchar(200)," +
                "minute_start integer," +
                "hour_finish integer," +
                "minute_finish integer," +
                "year_start integer," +
                "month_start integer," +
                "day_start integer," +
                "year_finish integer," +
                "month_finish integer," +
                "day_finish integer," +
                "isImportant integer," +
                "isUrgent integer)";
        db.execSQL(sql);
    }

    // 数据库更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SqliteHelper", "数据库更新");
    }

    /**
     * 添加Person到数据库
     *
     * @param schedule
     *            Schedule
     */
    public void addSchedule(Schedule schedule) {
        Log.e("SqliteHelper", "插入");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        db.execSQL(
                "insert into schedule(schedule_title,schedule_content,hour_start,minute_start,hour_finish,minute_finish,year_start,month_start,day_start,year_finish,month_finish,day_finish,isImportant,isUrgent) values("
                        + String.format("'%s'", schedule.getSchedule_title())+ ","
                        + String.format("'%s'", schedule.getSchedule_content())+ ","
                        + schedule.getHour_start() + ","
                        + schedule.getMinute_start() + ","
                        + schedule.getHour_finish() + ","
                        + schedule.getMinute_finish() + ","
                        + schedule.getYear_start() + ","
                        + schedule.getMonth_start() + ","
                        + schedule.getDay_start() + ","
                        + schedule.getYear_finish() + ","
                        + schedule.getMonth_finish() + ","
                        + schedule.getDay_finish() + ","
                        + schedule.getIsImportant()+ ","
                        + schedule.getIsUrgent()
                        +");"
        ); // 插入数据库

        db.close(); // 关闭数据库连接
    }

    /**
     * 更新Schedule
     *
     * @param schedule
     *            Schedule
     */
    public void updateSchedule(Schedule schedule) {
        Log.e("SqliteHelper", "更新");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
//		String sql = "update person set name="
//				+ String.format("'%s'", person.getName()) + ",age="
//				+ person.getAge() + " where _id=" + person.get_id();

        String sql = "update schedule set schedule_title="
                + String.format("'%s'", schedule.getSchedule_title())
                + ",schedule_content=" + String.format("'%s'", schedule.getSchedule_content())
                + ",hour_start=" + schedule.getHour_start()
                + ",hour_finish=" + schedule.getHour_finish()
                + ",minute_start=" + schedule.getMinute_start()
                + ",minute_finish=" + schedule.getMinute_finish()
                + ",year_start=" + schedule.getYear_start()
                + ",month_start=" + schedule.getMonth_start()
                + ",day_start=" + schedule.getDay_start()
                + ",year_finish=" + schedule.getYear_finish()
                + ",month_finish=" + schedule.getMonth_finish()
                + ",day_finish=" + schedule.getDay_finish()
                + ",isImportant=" + schedule.getIsImportant()
                + ",isUrgent=" + schedule.getIsUrgent()
                + " where sid=" + schedule.getSid();

        Log.e("updatePerson", sql);
        db.execSQL(sql); // 更新数据库
        db.close(); // 关闭数据库连接
    }

    /**
     * 删除Person
     *
     * @param sid
     *            Person的id
     */
    public void deleteschedule(int sid) {
        Log.e("SqliteHelper", "删除");
        SQLiteDatabase db = getWritableDatabase(); // 以读写的形式打开数据库
        String sql = "sid = ?";
        String wheres[] = { String.valueOf(sid) };
        db.delete("schedule", sql, wheres); // 数据库删除
        db.close(); // 关闭数据库
    }

    /**
     * 根据当天日期查询Schedule
     *
     * @return 所有Schedule集合
     */
    public ArrayList<Schedule> queryScheduleNow(int year,int month,int day_month) {
        ArrayList<Schedule> list = new ArrayList<Schedule>();
        SQLiteDatabase db = getReadableDatabase(); // 以只读的方式打开数据库
        String sql = "select * from schedule where year_start=? and month_start=? and day_start=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(year),String.valueOf(month),String.valueOf(day_month)});
        while (cursor.moveToNext()) {
            int sid = cursor.getInt(cursor.getColumnIndex("sid"));
            String schedule_title = cursor.getString(cursor.getColumnIndex("schedule_title"));
            String schedule_content = cursor.getString(cursor.getColumnIndex("schedule_content"));
            int isImportant = cursor.getInt(cursor.getColumnIndex("isImportant"));
            int isUrgent = cursor.getInt(cursor.getColumnIndex("isUrgent"));
            int hour_start = cursor.getInt(cursor.getColumnIndex("hour_start"));
            int minute_start = cursor.getInt(cursor.getColumnIndex("minute_start"));
            int minute_finish = cursor.getInt(cursor.getColumnIndex("minute_finish"));
            int hour_finish = cursor.getInt(cursor.getColumnIndex("hour_finish"));
            int year_start = cursor.getInt(cursor.getColumnIndex("year_start"));
            int month_start = cursor.getInt(cursor.getColumnIndex("month_start"));
            int day_start = cursor.getInt(cursor.getColumnIndex("day_start"));
            int year_finish = cursor.getInt(cursor.getColumnIndex("year_finish"));
            int month_finish = cursor.getInt(cursor.getColumnIndex("month_finish"));
            int day_finish = cursor.getInt(cursor.getColumnIndex("day_finish"));
            Schedule schedule = new Schedule();
            schedule.setSid(sid);
            schedule.setSchedule_title(schedule_title);
            schedule.setSchedule_content(schedule_content);
            schedule.setIsImportant(isImportant);
            schedule.setIsUrgent(isUrgent);
            schedule.setHour_start(hour_start);
            schedule.setMinute_start(minute_start);
            schedule.setMinute_finish(minute_finish);
            schedule.setHour_finish(hour_finish);
            schedule.setYear_start(year_start);
            schedule.setMonth_start(month_start);
            schedule.setDay_start(day_start);
            schedule.setYear_finish(year_finish);
            schedule.setMonth_finish(month_finish);
            schedule.setDay_finish(day_finish);
            list.add(schedule); // 添加到数组
        }
        cursor.close(); // 关闭游标
        db.close(); // 关闭数据库
        return list;
    }
    public ArrayList<Schedule> queryScheduleBydat(int syear,int smonth,int sday_month,int fyear,int fmonth,int fday_month) {
        ArrayList<Schedule> list = new ArrayList<Schedule>();
        SQLiteDatabase db = getReadableDatabase(); // 以只读的方式打开数据库
        String[] columns = { "sid", "schedule_title", "schedule_content", "hour_start","minute_start","hour_finish","minute_finish",
                "year_start","month_start","day_start","year_finish","month_finish","day_finish","isImportant","isUrgent" };
        String selection = "year_start=? and month_start=? and day_start=? and year_finish=? and month_finish=? and day_finish=?";
        String[] selectionArgs = { String.valueOf(syear),String.valueOf(smonth),String.valueOf(sday_month),String.valueOf(fyear),String.valueOf(fmonth),String.valueOf(fday_month) };
        Cursor cursor = db.query("schedule", columns, selection, selectionArgs,
                null, null, null);
        while (cursor.moveToNext()) {
            int sid = cursor.getInt(cursor.getColumnIndex("sid"));
            String schedule_title = cursor.getString(cursor.getColumnIndex("schedule_title"));
            String schedule_content = cursor.getString(cursor.getColumnIndex("schedule_content"));
            int isImportant = cursor.getInt(cursor.getColumnIndex("isImportant"));
            int isUrgent = cursor.getInt(cursor.getColumnIndex("isUrgent"));
            int hour_start = cursor.getInt(cursor.getColumnIndex("hour_start"));
            int minute_start = cursor.getInt(cursor.getColumnIndex("minute_start"));
            int minute_finish = cursor.getInt(cursor.getColumnIndex("minute_finish"));
            int hour_finish = cursor.getInt(cursor.getColumnIndex("hour_finish"));
            int year_start = cursor.getInt(cursor.getColumnIndex("year_start"));
            int month_start = cursor.getInt(cursor.getColumnIndex("month_start"));
            int day_start = cursor.getInt(cursor.getColumnIndex("day_start"));
            int year_finish = cursor.getInt(cursor.getColumnIndex("year_finish"));
            int month_finish = cursor.getInt(cursor.getColumnIndex("month_finish"));
            int day_finish = cursor.getInt(cursor.getColumnIndex("day_finish"));
            Schedule schedule = new Schedule();
            schedule.setSid(sid);
            schedule.setSchedule_title(schedule_title);
            schedule.setSchedule_content(schedule_content);
            schedule.setIsImportant(isImportant);
            schedule.setIsUrgent(isUrgent);
            schedule.setHour_start(hour_start);
            schedule.setMinute_start(minute_start);
            schedule.setMinute_finish(minute_finish);
            schedule.setHour_finish(hour_finish);
            schedule.setYear_start(year_start);
            schedule.setMonth_start(month_start);
            schedule.setDay_start(day_start);
            schedule.setYear_finish(year_finish);
            schedule.setMonth_finish(month_finish);
            schedule.setDay_finish(day_finish);
            list.add(schedule); // 添加到数组
        }
        cursor.close(); // 关闭游标
        db.close(); // 关闭数据库
        return list;
    }

    /**
     * 根据id查询Person
     *
     * @param Sid
     *            id
     * @return Schedule
     */
    public List<Schedule> queryScheduleById(int Sid) {
        List<Schedule> list = new ArrayList<Schedule>();
        SQLiteDatabase db = getReadableDatabase(); // 以只读的方式打开数据库
        String sql = "select * from schedule where Sid=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(Sid)});
        if (cursor.moveToNext()) {
            int sid = cursor.getInt(cursor.getColumnIndex("sid"));
            String schedule_title = cursor.getString(cursor.getColumnIndex("schedule_title"));
            String schedule_content = cursor.getString(cursor.getColumnIndex("schedule_content"));
            int isImportant = cursor.getInt(cursor.getColumnIndex("isImportant"));
            int isUrgent = cursor.getInt(cursor.getColumnIndex("isUrgent"));
            int hour_start = cursor.getInt(cursor.getColumnIndex("hour_start"));
            int minute_start = cursor.getInt(cursor.getColumnIndex("minute_start"));
            int minute_finish = cursor.getInt(cursor.getColumnIndex("minute_finish"));
            int hour_finish = cursor.getInt(cursor.getColumnIndex("hour_finish"));
            int year_start = cursor.getInt(cursor.getColumnIndex("year_start"));
            int month_start = cursor.getInt(cursor.getColumnIndex("month_start"));
            int day_start = cursor.getInt(cursor.getColumnIndex("day_start"));
            int year_finish = cursor.getInt(cursor.getColumnIndex("year_finish"));
            int month_finish = cursor.getInt(cursor.getColumnIndex("month_finish"));
            int day_finish = cursor.getInt(cursor.getColumnIndex("day_finish"));
            Schedule schedule = new Schedule();
            schedule.setSid(sid);
            schedule.setSchedule_title(schedule_title);
            schedule.setSchedule_content(schedule_content);
            schedule.setIsImportant(isImportant);
            schedule.setIsUrgent(isUrgent);
            schedule.setHour_start(hour_start);
            schedule.setMinute_start(minute_start);
            schedule.setMinute_finish(minute_finish);
            schedule.setHour_finish(hour_finish);
            schedule.setYear_start(year_start);
            schedule.setMonth_start(month_start);
            schedule.setDay_start(day_start);
            schedule.setYear_finish(year_finish);
            schedule.setMonth_finish(month_finish);
            schedule.setDay_finish(day_finish);
            list.add(schedule); // 添加到数组
        }
        cursor.close(); // 关闭游标
        db.close(); // 关闭数据库
        return list;
    }
    public Schedule lookscheduleByid(int Sid){
        Schedule schedule=null;
        SQLiteDatabase db = getReadableDatabase(); // 以只读方式打开数据库
        String[] columns = { "sid", "schedule_title", "schedule_content", "hour_start","minute_start","hour_finish","minute_finish",
                "year_start","month_start","day_start","year_finish","month_finish","day_finish","isImportant","isUrgent" };
        String selection = "sid=?";
        String[] selectionArgs = { String.valueOf(Sid) };
        Cursor cursor = db.query("schedule", columns, selection, selectionArgs,
                null, null, null);
        if (cursor.moveToNext()) {
            schedule = new Schedule();
            schedule.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
            schedule.setSchedule_title(cursor.getString(cursor.getColumnIndex("schedule_title")));
            schedule.setSchedule_content(cursor.getString(cursor.getColumnIndex("schedule_content")));
            schedule.setIsImportant(cursor.getInt(cursor.getColumnIndex("isImportant")));
            schedule.setIsUrgent(cursor.getInt(cursor.getColumnIndex("isUrgent")));
            schedule.setHour_start(cursor.getInt(cursor.getColumnIndex("hour_start")));
            schedule.setMinute_start(cursor.getInt(cursor.getColumnIndex("minute_start")));
            schedule.setHour_finish(cursor.getInt(cursor.getColumnIndex("hour_finish")));
            schedule.setMinute_finish(cursor.getInt(cursor.getColumnIndex("minute_finish")));
            schedule.setYear_start(cursor.getInt(cursor.getColumnIndex("year_start")));
            schedule.setMonth_start(cursor.getInt(cursor.getColumnIndex("month_start")));
            schedule.setDay_start(cursor.getInt(cursor.getColumnIndex("day_start")));
            schedule.setYear_finish(cursor.getInt(cursor.getColumnIndex("year_finish")));
            schedule.setMonth_finish(cursor.getInt(cursor.getColumnIndex("month_finish")));
            schedule.setDay_finish(cursor.getInt(cursor.getColumnIndex("day_finish")));
        }
        return schedule;
    }
}
