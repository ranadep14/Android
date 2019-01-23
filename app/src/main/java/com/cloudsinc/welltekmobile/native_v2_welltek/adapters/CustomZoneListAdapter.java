package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Zone;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.util.ArrayList;
/**
 * This class containing functionality related to displaying zone list and select those for further use.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class CustomZoneListAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<Zone> ssidArrayList;
    private static LayoutInflater inflater=null;
    private Fragment fragment;
    private String TAG=CustomZoneListAdapter.this.getClass().getName();
    private TextView txtsimid,txttime,txtdays,txtroomtype;
    private Switch simstatus;
    public CustomZoneListAdapter(Context a, ArrayList<Zone> mssidArrayList) {
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
            vi = inflater.inflate(R.layout.custom_zone_spinner_list_item, null);
        final Typeface mFont = Typeface.createFromAsset(vi.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) vi.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        final TextView txt_title = vi.findViewById(R.id.txt_title); // title
        final TextView txt_id = vi.findViewById(R.id.txt_id); // title
        txt_id.setText(ssidArrayList.get(position).getZone_id());
        txt_title.setText(ssidArrayList.get(position).getZone_title());
        return vi;
    }
}