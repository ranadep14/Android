package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This class containing functionality related to choose any scene for specific device.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingDevicesScenesList extends Fragment implements View.OnClickListener {
    public static SettingDevicesScenesList newInstance() {
        return new SettingDevicesScenesList();
    }

    View view;

    @BindView( R.id.rd_circadian)CheckBox rd_circadian;
    @BindView( R.id.rd_energise)CheckBox rd_energise;
    @BindView( R.id.rd_relax)CheckBox rd_relax;
    @BindView( R.id.rd_sleep)CheckBox rd_sleep;
    @BindView( R.id.rd_dawn)CheckBox rd_dawn;
    @BindView( R.id.lin_circadian)LinearLayout lin_circadian;
    @BindView( R.id.lin_energize)LinearLayout lin_energize;
    @BindView( R.id.lin_sleep)LinearLayout lin_sleep;
    @BindView( R.id.lin_relax)LinearLayout lin_relax;
    @BindView( R.id.lin_dawn)LinearLayout lin_dawn;
    @BindView(R.id.txt_room_title)TextView txt_room_title;
    @BindView(R.id.txt_save)TextView txt_save;
    @BindView( R.id.rd_cooking)CheckBox rd_cooking;
    @BindView( R.id.lin_cooking)LinearLayout lin_cooking;
    @BindView( R.id.txt_fragment_title)TextView txt_fragment_title;
    String TAG=SettingDevicesScenesList.this.getClass().getName();
    Context mcontext;
    TextView txt_cancle;
    ArrayList<Integer> arr_scence_list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_devices_scenes_list, container, false);
        mcontext=view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);



        ButterKnife.bind(this,view);
        txt_fragment_title.setText("Device Settings");

        Bundle temp_bundle= App.getTemp_bundle();

        if(temp_bundle.containsKey("room_title")) txt_room_title.setText(""+temp_bundle.getString("room_title"));

        if (temp_bundle!=null) {
             Logs.info(TAG+"_FBBBBBBBBBBBBBBBBBBBB",""+temp_bundle);
             Logs.info(TAG+"_FScenced",""+temp_bundle.getIntegerArrayList("Scence"));
            if( temp_bundle.getIntegerArrayList("Scence").size()>0) {
                setAllVisiblity();
                for (int i = 0; i < temp_bundle.getIntegerArrayList("Scence").size(); i++) {
                    switch (temp_bundle.getIntegerArrayList("Scence").get(i)) {
                        case 1:
                            lin_circadian.setVisibility(View.VISIBLE);
                           // rd_circadian.setChecked(true);
                            arr_scence_list.add(1);
                            break;
                        case 3:
                           // rd_energise.setChecked(true);
                            arr_scence_list.add(3);
                            break;
                        case 2:
                           // rd_relax.setChecked(true);
                            arr_scence_list.add(2);
                            break;
                        case 5:
                          //  rd_dawn.setChecked(true);
                            arr_scence_list.add(5);
                            break;
                        case 6:
                           // rd_sleep.setChecked(true);
                            arr_scence_list.add(6);
                            break;
                        case 7:
                           // rd_cooking.setChecked(true);
                            arr_scence_list.add(7);
                            break;

                    }
                }
            }

        }
        if(temp_bundle.containsKey("room_type"))
        {
            setAllVisiblity();
            fetchSceneId(temp_bundle.getString("room_type"));

        }
        rd_circadian.setOnClickListener(this);
        rd_energise.setOnClickListener(this);
        rd_relax.setOnClickListener(this);
        rd_sleep.setOnClickListener(this);
        rd_dawn.setOnClickListener(this);
        rd_cooking.setOnClickListener(this);




       /* TextView txt_tab_title= (TextView) getActivity().findViewById(R.id.txt_tab_title);
        txt_tab_title.setText("Select Scenes");*/

        txt_save.setVisibility(View.GONE);
        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= App.getTemp_bundle();
                bundle.putIntegerArrayList("Scence",arr_scence_list);
                bundle.putBoolean("select_flag",true);
                App.setTemp_bundle(bundle);
                if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesScenesList.this,R.id.frm_filter_main_container, SettingDevicesLights.newInstance());
                else  ReplaceFragment.replaceFragment(SettingDevicesScenesList.this,R.id.frm_main_container, SettingDevicesLights.newInstance());

            }
        });
      /*  txt_cancle=(TextView) getActivity().findViewById(R.id.txt_cancle);
        txt_cancle.setVisibility(View.GONE);
        txt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReplaceFragment.replaceFragmentWithRotation(SettingDevicesScenesList.this,  SettingDevicesLights.newInstance());
            }
        });*/

        return view;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rd_circadian:
                if(rd_circadian.isChecked()) arr_scence_list.add(1);
                else arr_scence_list.remove(arr_scence_list.indexOf(1));

                break;
            case R.id.rd_energise:
                if(rd_energise.isChecked()) arr_scence_list.add(3);
                else arr_scence_list.remove(arr_scence_list.indexOf(3));
                break;
            case R.id.rd_relax:
                if(rd_relax.isChecked()) arr_scence_list.add(2);
                else arr_scence_list.remove(arr_scence_list.indexOf(2));
                break;


            case R.id.rd_dawn:

                if(rd_dawn.isChecked()) arr_scence_list.add(5);
                else arr_scence_list.remove(arr_scence_list.indexOf(5));
                break;
            case R.id.rd_sleep:
                if(rd_sleep.isChecked()) arr_scence_list.add(6);
                else arr_scence_list.remove(arr_scence_list.indexOf(6));
                break;
            case R.id.rd_cooking:
                if(rd_cooking.isChecked()) arr_scence_list.add(7);
                else arr_scence_list.remove(arr_scence_list.indexOf(7));
                break;

        }
        if(arr_scence_list.size()>0)
        {
            txt_save.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_save.setVisibility(View.GONE);
        }
    }


   /* public void setScences(String str_room_id) {



            try {
                JSONArray jsonArray = App.getRoomData().getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("CML_ID").equals(str_room_id)) {
                        for(int j=0;j<jsonObject.getJSONArray("scenes").length();j++)
                        {
                            setVisiblity(jsonObject.getJSONArray("scenes").getJSONObject(j).getString(j));
                        }

                    }
                }


            } catch (Exception ex) {
                 Logs.info(TAG+"_FSimulationError", ex.getMessage());
            }

    }*/

    public void fetchSceneId(String room_type)
    {
        String jsonarr = null;
        try {
            InputStream is = mcontext.getAssets().open("scenes_ids.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonarr = new String(buffer, "UTF-8");
             Logs.info(TAG+"_FScenesjson",jsonarr);

            JSONArray jsonArray=new JSONArray(jsonarr);


             Logs.info(TAG+"_Frrrrrrrrrrrrrrrr",room_type+"mmmmmmmmmm"+ App.getDevice().getDevice_type());
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                JSONArray jsonArray1=jsonObject.getJSONArray("roomType");
                JSONArray jsonArray2=jsonObject.getJSONArray("type");

                for (int j = 0; j < jsonArray2.length(); j++) {

                    for(int k=0;k<jsonArray1.length();k++) {
                        if (jsonArray1.getString(k).equals(room_type) && jsonArray2.getString(j).equals(App.getDevice().getDevice_type())) {
                            setVisiblity(jsonObject.getString("Id"));
                        }
                    }

                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    public void setVisiblity(String scene_id)
    {

         Logs.info(TAG+"_Feeeeeeeeee",scene_id);
        switch (scene_id)
        {
            case "1":
                lin_circadian.setVisibility(View.VISIBLE);

                break;
            case "2":
                lin_relax.setVisibility(View.VISIBLE);

                break;
            case "3":
                lin_energize.setVisibility(View.VISIBLE);

                break;
            case "5":
                lin_dawn.setVisibility(View.VISIBLE);
                break;
            case "6":
                lin_sleep.setVisibility(View.VISIBLE);
                break;
            case "7":
                lin_cooking.setVisibility(View.VISIBLE);
                break;

        }
    }

    public void setAllVisiblity()
    {
        lin_circadian.setVisibility(View.GONE);
        lin_relax.setVisibility(View.GONE);
        lin_energize.setVisibility(View.GONE);
        lin_dawn.setVisibility(View.GONE);
        lin_sleep.setVisibility(View.GONE);
        lin_cooking.setVisibility(View.GONE);

    }


    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(SettingDevicesScenesList.this,R.id.frm_filter_main_container, SettingDevicesLights.newInstance());
        else ReplaceFragment.replaceFragment(SettingDevicesScenesList.this,R.id.frm_main_container, SettingDevicesLights.newInstance());

    }


}