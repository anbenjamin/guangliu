package details;

/**
 * Created by BenjaminAn on 2018/10/4.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import com.example.benjaminan.test2.DetailsActivity;
import com.example.benjaminan.test2.R;

public class DetailInfoAdapter extends RecyclerView.Adapter<DetailInfoAdapter.ViewHolder> {
    private Context mContext;

    private List<DetailInfo> mDetailList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView detailWeek;
        TextView detailDate;
        ImageView detailIcon;
        TextView statistic1;
        TextView statistic2;

        View detailView;

        public ViewHolder(View view) {
            super(view);
            detailView = view;
            detailIcon = (ImageView) view.findViewById(R.id.detail_icon);
            detailWeek = (TextView) view.findViewById(R.id.detail_week);
            detailDate = (TextView) view.findViewById(R.id.detail_date);
            statistic1 = (TextView) view.findViewById(R.id.statistic1);
            statistic2 = (TextView) view.findViewById(R.id.statistic2);
        }
    }

    public DetailInfoAdapter(List<DetailInfo> detailList) {
        mDetailList = detailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.details_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.detailView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //int position = holder.getAdapterPosition();
                Intent intent=new Intent(mContext, DetailsActivity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailInfo detail = mDetailList.get(position);
        holder.detailWeek.setText(getWeekFromCalendar(detail.getCalendar()));
        holder.detailDate.setText(getDateFromCalendar(detail.getCalendar()));
        //Glide.with(mContext).load(app.getIcon()).into(holder.appIcon);
        holder.detailIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_null));
        holder.statistic1.setText("statistic1");
        holder.statistic2.setText("statistic2");
    }

    @Override
    public int getItemCount() {return mDetailList.size();}

    private String getWeekFromCalendar(Calendar calendar){
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)  w = 0;
        return weekDays[w];
    }

    private String getDateFromCalendar(Calendar calendar){
        String yearStr = calendar.get(Calendar.YEAR)+"";//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        String monthStr = month < 10 ? "0" + month : month + "";
        int day = calendar.get(Calendar.DATE);//获取日
        String dayStr = day < 10 ? "0" + day : day + "";
        return yearStr + "年" + monthStr + "月" + dayStr + "日";
    }
}
