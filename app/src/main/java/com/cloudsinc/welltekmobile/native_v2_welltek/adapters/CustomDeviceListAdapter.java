package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Device;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import java.util.ArrayList;
/**
 * This class containing functionality related to displaying devices list for edit those
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class CustomDeviceListAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<Device> deviceArrayList;
    private String TAG=CustomDeviceListAdapter.this.getClass().getName();
    private static LayoutInflater inflater=null;
    public CustomDeviceListAdapter(Context a, ArrayList<Device> deviceArrayList) {
        activity = a;
        this.deviceArrayList=deviceArrayList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return deviceArrayList.size();
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
            vi = inflater.inflate(R.layout.device_setting_list_item, null);
        final TextView txt_title = vi.findViewById(R.id.txt_devices_title); // title
        final TextView txt_id = vi.findViewById(R.id.txt_devices_id); // title
        final TextView   txt_type = vi.findViewById(R.id.txt_devices_type); // artist name
        final TextView   txt_assign_room = vi.findViewById(R.id.txt_assind_room); // artist name
        final ImageView   img_device_type = vi.findViewById(R.id.img_device_type);
        Device device=deviceArrayList.get(position);
        txt_title.setText(""+device.getDevice_title());
        txt_id.setText(""+device.getDevice_id());
        txt_type.setText(""+device.getDevice_type());
        txt_assign_room.setText(""+device.getAssign_room());
            switch (device.getDevice_type())
        {
            case "Water":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case "Lighting":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_bulb_white));
                break;
            case "Air":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_hvac_air));
                break;
            case "Music":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_audio_white));
                break;
            case "HVAC":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_cook_white));
                break;
            case "Blinds":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_blinds_white));
                break;
            case "Sensor":
                img_device_type.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_sensor));
                break;
        }
        return vi;
    }
}