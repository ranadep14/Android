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
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Notifier;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import java.util.ArrayList;
public class CustomNotifierAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<Notifier> ssidArrayList;
    private static LayoutInflater inflater=null;
    Fragment fragment;
    String TAG=CustomNotifierAdapter.this.getClass().getName();
    TextView txtsimid,txttime,txtdays,txtroomtype;
    Switch simstatus;
    public CustomNotifierAdapter(Context a, ArrayList<Notifier> mssidArrayList) {
        activity = a;
        this.ssidArrayList=mssidArrayList;
        Logs.info(TAG+"ssiarr", "" + mssidArrayList);
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
            vi = inflater.inflate(R.layout.custom_spinner_list_item, null);
        final Typeface mFont = Typeface.createFromAsset(vi.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) vi.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        final TextView txt_title = vi.findViewById(R.id.txt_title); // title
        final TextView txt_id = vi.findViewById(R.id.txt_id); // title
        txt_title.setText(ssidArrayList.get(position).getName());
        return vi;
    }
}