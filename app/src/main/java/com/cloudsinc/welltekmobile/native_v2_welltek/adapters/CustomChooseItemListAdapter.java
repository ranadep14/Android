package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 30/3/17.
 */
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
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Room;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import java.util.ArrayList;
public class CustomChooseItemListAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<Room> chooseRoomArrayList, time, roomtype, days;
    private static LayoutInflater inflater=null;
    String room_id="";
    String TAG=CustomChooseItemListAdapter.this.getClass().getName();
   /* TextView txtsimid,txttime,txtdays,txtroomtype;
    Switch simstatus;*/
    public CustomChooseItemListAdapter(Context a, ArrayList<Room> chooseRoomArrayList) {
        activity = a;
        this.chooseRoomArrayList=chooseRoomArrayList;
        if(App.getTemp_bundle().containsKey("room_id"))
        {
            room_id=App.getTemp_bundle().getString("room_id");
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
        final LinearLayout lin_room= vi.findViewById(R.id.lin_room);
        final ImageView img_selection= vi.findViewById(R.id.img_selection);
        final Typeface mFont = Typeface.createFromAsset(vi.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) vi.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        Room choose=chooseRoomArrayList.get(position);
        if(choose.getRoom_id().equals(room_id))
        {
            img_selection.setBackground(activity.getResources().getDrawable(R.drawable.ic_radio_button_check));
        }
        else
        {
            img_selection.setBackground(activity.getResources().getDrawable(R.drawable.ic_radio_button_uncheck));
        }
//            SimulaterModel simulaterModel=data.get(position);
        txt_id.setText(""+choose.getRoom_id());
        txt_title.setText(""+choose.getRoom_title());
        return vi;
    }
}