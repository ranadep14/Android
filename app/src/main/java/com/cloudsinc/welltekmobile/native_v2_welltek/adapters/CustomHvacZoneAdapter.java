package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality.IndoorAirQualityMain;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.util.ArrayList;
public class CustomHvacZoneAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<Zone> ssidArrayList;
    private static LayoutInflater inflater=null;
    Fragment fragment;
    String scene_id="";
    String color="";
    int color_score;
    TextView txtsimid,txttime,txtdays,txtroomtype;
    Switch simstatus;
    public CustomHvacZoneAdapter(Context a, ArrayList<Zone> mssidArrayList) {
        activity = a;
        this.ssidArrayList=mssidArrayList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return ssidArrayList.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.custom_spinner_list_item_sensor, null);
        final TextView txt_title = vi.findViewById(R.id.txt_title); // title
        final ImageView bullet = vi.findViewById(R.id.bullet); // title
      //  final TextView txt_id = (TextView)vi.findViewById(R.id.txt_id); // title
       // txt_id.setText(ssidArrayList.get(position).getMac());
        if(ssidArrayList.size()>0) {
            txt_title.setText(ssidArrayList.get(position).getZone_title());
        }
        color_score=ssidArrayList.get(position).getZone_score();
        Logs.info("color_score",""+color_score);
        color= IndoorAirQualityMain.room_color;
        if(color_score >= 0 && color_score <= 39){
            bullet.setBackground(activity.getResources().getDrawable(R.drawable.bullet_red));
        }
        else if (color_score >= 40 && color_score <= 79){
            bullet.setBackground(activity.getResources().getDrawable(R.drawable.bullet_yellow));
        }
        else if(color_score >= 80 && color_score <= 100){
            bullet.setBackground(activity.getResources().getDrawable(R.drawable.bullet_moon));
        }
        return vi;
    }
}