package details;

/**
 * Created by BenjaminAn on 2018/10/3.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import com.example.benjaminan.test2.R;


/**
 * Created by BenjaminAn on 2018/9/23.
 */

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {
    private List<AppInfo> mAppList;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appTime;

        View appView;

        public ViewHolder(View view) {
            super(view);
            appView = view;
            appIcon = (ImageView) view.findViewById(R.id.app_icon);
            appName = (TextView) view.findViewById(R.id.app_name);
            appTime = (TextView) view.findViewById(R.id.app_time);
        }
    }

    public AppInfoAdapter(List<AppInfo> appList) {
        mAppList = appList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.app_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.appView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                AppInfo temp = mAppList.get(position);
                Toast.makeText(mContext,"you click the 《" + temp.getAppName() + "》", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInfo app = mAppList.get(position);
        holder.appName.setText(app.getAppName());
        holder.appTime.setText(getTimeString(app.getTime()));
        //Glide.with(mContext).load(app.getIcon()).into(holder.appIcon);
        holder.appIcon.setImageDrawable(app.getIcon());
        holder.appIcon.setImageDrawable(app.getIcon());
    }

    @Override
    public int getItemCount() {return mAppList.size();}

    private static String getTimeString(long time){
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02dh%02dm%02ds", hours, minutes, seconds) : String.format("%02dm%02ds", minutes, seconds);
    }
}
