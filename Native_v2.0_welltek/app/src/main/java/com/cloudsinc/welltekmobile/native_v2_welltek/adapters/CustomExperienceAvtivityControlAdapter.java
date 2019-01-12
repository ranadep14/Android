package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities.ExperienceActivityControl;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developers on 20/12/17.
 */

public class CustomExperienceAvtivityControlAdapter extends BaseAdapter {

    private Context activity;
    private List<HashMap<String,String>> aList;

    public static String [] id;
    public static String [] fav_act_deact;
    public static String [] rooms;
    public static String [] is_rooms_slected;
    private static LayoutInflater inflater=null;
    Fragment mfragment;


    public CustomExperienceAvtivityControlAdapter(Context a, List<HashMap<String, String>> aList, ExperienceActivityControl mfragment) {
        activity = a;
        this.aList=aList;
        this.mfragment=mfragment;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        id = new String[this.aList.size()];
        fav_act_deact = new String[this.aList.size()];
        rooms = new String[this.aList.size()];
        is_rooms_slected = new String[this.aList.size()];


        for(int i=0;i<this.aList.size();i++){
            HashMap<String,String> temp=aList.get(i);
            id[i] = temp.get("id");
            fav_act_deact[i] = temp.get("favorites");
            rooms[i] = temp.get("rooms");
            is_rooms_slected[i] = temp.get("active_rooms");
        }

    }

    public int getCount() {
        return aList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        if(convertView==null)
            vi = inflater.inflate(R.layout.activities_control_room_list_items, null);

        final ImageView fav = vi.findViewById(R.id.img_favorate_room);
        final TextView  room_name = vi.findViewById(R.id.experinces_activities_room_name);
        final CheckBox room_select= vi.findViewById(R.id.check_experinces_activities_room);
        fav.setTag(position);
        room_name.setTag(position);
        room_select.setTag(position);

        if (fav_act_deact[position].contentEquals("0")) {
            fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.heart_outline_pink));

        } else {
            fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_heart_fill));
        }

        if (is_rooms_slected[position].contentEquals("0")) {
            room_select.setChecked(false);
        } else {
            room_select.setChecked(true);
        }

        room_name.setText(rooms[position]);



        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                if (fav_act_deact[pos]=="0") {
                    fav_act_deact[pos]="1";
                    notifyDataSetChanged();

                } else {
                    fav_act_deact[pos]="0";
                    notifyDataSetChanged();

                }

            }
        });

        room_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                if (is_rooms_slected[pos]=="0") {
                    is_rooms_slected[pos]="1";
                    notifyDataSetChanged();

                } else {
                    is_rooms_slected[pos]="0";
                    notifyDataSetChanged();

                }

            }
        });
        room_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer) view.getTag();
                if (is_rooms_slected[pos]=="0") {
                    is_rooms_slected[pos]="1";
                    notifyDataSetChanged();

                } else {
                    is_rooms_slected[pos]="0";
                    notifyDataSetChanged();

                }

            }
        });

        return vi;

    }





}
