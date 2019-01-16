package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 8/6/17.
 * @purpose  - USed for Add SONOS devices assigned to rooms
 */
public class AddSonosFragment extends Fragment {
                            public static AddSonosFragment newInstance() {
                                return new AddSonosFragment();
                            }

                            private View view;
                            @BindView(R.id.lyt_sonosadd)LinearLayout sonosAdd;
                           // @BindView(R.id.lyt_smartfilter)LinearLayout filter;
                            @BindView(R.id.txt_room)TextView txt_room;
                        @BindView(R.id.txt_sonos_device_name) EditText txt_sonos_device_name;
                        @BindView(R.id.txt_save)TextView txt_save;
                        @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;

                            private Bundle bundle;
                        private Context mcontext;
                            @Override
                            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                                view = inflater.inflate(R.layout.fragment_add_sonos, container, false);
                                mcontext=view.getContext();
                               /* TextView txt_tab_title= (TextView) getActivity().findViewById(R.id.txt_tab_title);
                                txt_tab_title.setText("Add Sonos");*/



                                final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                                        "fonts/gotham-light-591d8629985e3.otf");
                                final ViewGroup mContainer = (ViewGroup) view.getRootView();
                                ButterKnife.bind(this,view);
                                txt_fragment_title.setText("Add SONOS");
                                txt_sonos_device_name.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                       if (s.length()>0)
                                       {
                                           txt_save.setVisibility(View.VISIBLE);
                                       }
                                        else
                                       {
                                           txt_save.setVisibility(View.GONE);
                                       }

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                FontUtility.setAppFont(mContainer, mFont);
                                bundle= App.getTemp_bundle();





                                if(bundle!=null) {

                                    if (bundle.containsKey("room_id")) {
                                        Log.e("room_id", bundle.getString("room_id"));
                                        txt_room.setText(getRoomTitle(bundle.getString("room_id")));
                                    }
                                    else
                                    {
                                        txt_room.setText("please select room to be assigned");
                                    }
                                    if(bundle.containsKey("device_name"))
                                    {
                                        txt_sonos_device_name.setText(""+bundle.getString("device_name"));
                                    }
                                }
                                return view;
                            }
                        private String getRoomTitle(String str_room_id)
                        {
                            String room_title="";
                            try {
                                JSONArray jsonArray = App.getRoomData().getJSONArray("data");
                                for (int i = 0; i <jsonArray.length();i++)
                                {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    if(jsonObject.getString("CML_ID").equals(str_room_id))
                                    {
                                        Log.e("device_id_litht","Got.....room");
                                        room_title=jsonObject.getString("CML_TITLE");

                                    }
                                }


                            }
                            catch (Exception ex){
                                Log.e("SimulationError",ex.getMessage());}
                            return room_title;
                        }
                            @OnClick(R.id.lyt_sonosadd)
                            public void AddRooms()
                            {

                                App.getTemp_bundle().putString("device_name",txt_sonos_device_name.getText().toString());
                                ReplaceFragment.replaceFragment(AddSonosFragment.this,R.id.frm_filter_main_container, SettingAddSonosRooms.newInstance());


                            }
                        @OnClick(R.id.img_back)
                        public void img_back()
                        {
                            ReplaceFragment.replaceFragment(AddSonosFragment.this,R.id.frm_filter_main_container, SettingAddDevices.newInstance());
                        }

    }
