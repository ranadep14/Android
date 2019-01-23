package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import java.util.ArrayList;
/**
 * This class containing functionality related to displaying zone list and select zone
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class CustomChoosZoneListAdapter extends BaseAdapter {
    private Context activity;
    private String zone_id="";
    private ArrayList<Zone> chooseRoomArrayList, time, roomtype, days;
    private static LayoutInflater inflater=null;
    public CustomChoosZoneListAdapter(Context a, ArrayList<Zone> chooseRoomArrayList) {
        activity = a;
        this.chooseRoomArrayList=chooseRoomArrayList;
        if(App.getTemp_bundle().containsKey("zone_id"))
        {
            zone_id=App.getTemp_bundle().getString("zone_id");
        }
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return chooseRoomArrayList.size();
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
            vi = inflater.inflate(R.layout.choose_list_item, null);
       final TextView txt_id = vi.findViewById(R.id.txt_id); // title
        final TextView   txt_title = vi.findViewById(R.id.txt_title); // artist name
        final LinearLayout lin_room = vi.findViewById(R.id.lin_room); // artist name
        final ImageView img_selection= vi.findViewById(R.id.img_selection);
        final Typeface mFont = Typeface.createFromAsset(vi.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) vi.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        Zone choose=chooseRoomArrayList.get(position);
        if(choose.getZone_id().equals(zone_id))
        {
            img_selection.setBackground(activity.getResources().getDrawable(R.drawable.ic_radio_button_check));
        }
        else
        {
            img_selection.setBackground(activity.getResources().getDrawable(R.drawable.ic_radio_button_uncheck));
        }
//            SimulaterModel simulaterModel=data.get(position);
        txt_id.setText(""+choose.getZone_id());
        txt_title.setText(""+choose.getZone_title());
        return vi;
    }
}