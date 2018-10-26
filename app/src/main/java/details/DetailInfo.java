package details;

/**
 * Created by BenjaminAn on 2018/10/4.
 */

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.example.benjaminan.test2.DetailsActivity;

import java.util.Calendar;
import java.util.List;

public class DetailInfo {
    private Calendar calendar;
    private List<AppInfo> AppList;
    private  Drawable icon;

    public DetailInfo(){};
    public DetailInfo(Calendar calendar, List<AppInfo> appList){
        this.calendar = calendar;
        this.AppList = appList;
    }
    public DetailInfo(Calendar calendar){this.calendar = calendar;}
    public void setCalendar(Calendar calendar){this.calendar = calendar;}
    public void setAppList(List<AppInfo> appList){this.AppList = appList;}
    public void setIcon(Drawable icon){this.icon = icon;}

    public Calendar getCalendar(){return calendar;}
    public List<AppInfo> getAppList(){return AppList;}
    public Drawable getIcon(){return icon;}

}
