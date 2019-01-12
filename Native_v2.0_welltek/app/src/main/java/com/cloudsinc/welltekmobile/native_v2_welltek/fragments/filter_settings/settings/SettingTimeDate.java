package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * This class containing functionality related to setting date and time
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SettingTimeDate extends Fragment {
    public static SettingTimeDate newInstance() {
        return new SettingTimeDate();
    }

    private View view;
    private TextView txt_save;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    //  private int year, month, day;
    java.sql.Time timeValue;
    SimpleDateFormat format;
    Calendar c;
    int year, month, day;
    static int hour, min;
    String strDatetime="";
    String rdate="",rtime="";
    Context mcontext;
    @BindView(R.id.txt_timeZone)TextView txt_timeZone;
    @BindView(R.id.swt_24hr)Switch swt_24hr;
    @BindView(R.id.swt_auto)Switch swt_auto;
    @BindView(R.id.Timepicker)EditText showTime;
    @BindView(R.id.datepicker)EditText showDate;
    @BindView(R.id.lyt_changeTimeDate)LinearLayout TimeDate;
    String TAG=SettingTimeDate.this.getClass().getName();
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
     AlertDialog dialog;
    private boolean hoursFlag=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_time_date, container, false);
        mcontext=view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();

        FontUtility.setAppFont(mContainer, mFont);

        ButterKnife.bind(SettingTimeDate.this,view);

        txt_fragment_title.setText("Time & Date");

         Logs.info(TAG+"_TimeZone",""+TimeZone.getDefault());
        dialog = new AlertDialog.Builder(mcontext)
                .setView(R.layout.custom_dialog_single)
                .create();
        swt_24hr.setChecked(new PrefManager(mcontext).getValue("time_fomat").equals("1"));
        SwitchTrackChange.changeTrackColor(mcontext,swt_24hr);
        hoursFlag=new PrefManager(mcontext).getValue("time_fomat").equals("1");
        // dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        // showTime(year, month+1, day);
        setSubcriber();
        if (App.getSocket()!=null) {
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
            App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_date_time",new JSONObject()));
        }
         Logs.info(TAG+"_SetTimeDateFlag",""+App.isSetDateTime());




      /*  TextView txt_tab_title= (TextView) getActivity().findViewById(R.id.txt_tab_title);
        /*txt_tab_title.setText("Time & Date Setting");*//*
        txt_save=(TextView) getActivity().findViewById(R.id.txt_save);
        txt_save.setVisibility(View.GONE);
        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.setSetDateTime(true);
                try {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("CML_DATETIME", convertRequestDate());
                    JSONObject obj = new JSONObject();
                    obj.put("data",dataObj);
                    obj.put("type", "set_date_time");
                    Log.d("SimulationReqUpdated",""+obj);
                    App.getSocket().emit("action", obj);
                }
                catch (Exception ex){ Logs.info(TAG+"_Error",""+ex.getMessage());}

                // BackFunctionality.goBack(SettingTimeDate.this,R.id.frame_layout,MySettings.newInstance());
            }
        });
*/


       // getTime();

      /*  TextView txt_back=(TextView)getActivity().findViewById(R.id.txt_cancle);
        txt_back.setText("Cancel");
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment.replaceFragmentWithRotation(SettingTimeDate.this, SettingMainFragment.newInstance());
            }
        });*/

       /* if(!App.isOrientationFlag()) {
            txt_save.setVisibility(View.VISIBLE);
            txt_back.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.btn_setting).setVisibility(View.GONE);
            getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.GONE);
        }*/
       /* getActivity().findViewById(R.id.btn_setting).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.txt_save).setVisibility(View.GONE);
        getActivity().findViewById(R.id.txt_cancle).setVisibility(View.GONE);*/
        swt_auto.setChecked(true);
        TimeDate.setVisibility(View.GONE);
        swt_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    //getTime();
                    TimeDate.setVisibility(View.GONE);
                 //   txt_save.setVisibility(View.GONE);
                }
                else
                {
                    convertResponseDate(strDatetime);
                    TimeDate.setVisibility(View.VISIBLE);
                  //  txt_save.setVisibility(View.VISIBLE);

                }

            }
        });

        swt_24hr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    new PrefManager(mcontext).setValue("time_fomat","1");

                    showTime.setText(convert24(showTime.getText().toString()));
                    hoursFlag=true;
                }
                else
                {
                    new PrefManager(mcontext).setValue("time_fomat","2");
                    showTime.setText(convert12(showTime.getText().toString()));
                    hoursFlag=false;

                }

                SwitchTrackChange.changeTrackColor(mcontext,swt_24hr);

            }
        });


       // getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.GONE);
        return view;
    }



    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
                        sub.onCompleted();
                    }
                }
        );



        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String string) {
                getTimeInfo();
                getDateTime();

                 Logs.info(TAG+"_TimeFlag",""+App.isSetDateTime()+"----"+dialog.isShowing());

                if(App.isSetDateTime())
                {
                    App.setSetDateTime(false);
                    if(!dialog.isShowing()) {


                        View vi = dialog.getWindow().getDecorView();
                        vi.setBackgroundResource(android.R.color.transparent);
                        dialog.show();
                        TextView msgt = dialog.findViewById(R.id.title);
                        assert msgt != null;
                        msgt.setText("Saved!");

                        TextView msg = dialog.findViewById(R.id.msg);
                        assert msg != null;
                        msg.setText("Date  and time saved successfully.");

                        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
                        assert diagButtonOK != null;
                        diagButtonOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                            }
                        });
                    }

                }



            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }


    private void getTimeInfo()
    {


        try {
            JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            txt_timeZone.setText(""+jsonObject.getString("CML_TIMEZONE"));

        }
        catch (Exception ex){
             Logs.info(TAG+"_TimeError",ex.getMessage());}
    }


    @OnClick(R.id.Timepicker)
    public void pickTime()
    {
        TimePickerDialog td = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        try {

                            if(!hoursFlag) {

                                showTime.setText(convert12ForTimePicker(hourOfDay+":"+minute+":00"));
                               /* StringBuilder sb = new StringBuilder();
                                if (hourOfDay > 12) {
                                    sb.append(hourOfDay - 12).append(":").append(minute).append(" PM");
                                } else {
                                    sb.append(hourOfDay).append(":").append(minute).append(" AM");
                                }*/
                              //  showTime.setText(String.valueOf(sb));
                            }
                            else
                            {
                                showTime.setText(hourOfDay+":"+minute+":00");
                               // showTime.setText(""+hourOfDay+":"+minute);
                            }





                        } catch (Exception ex) {
                            showTime.setText(ex.getMessage());
                        }
                    }
                },
                hour, min,false
        );
        td.show();
    }


    @OnClick(R.id.datepicker)
    public void pickDate() {


        DatePickerDialog dd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String dateInString = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            Date date = formatter.parse(dateInString);

                            formatter = new SimpleDateFormat("dd-MM-yyyy");
                            showDate.setText(formatter.format(date));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                }, year, month, day);
        dd.show();
    }

    private void getTime()
    {
        String str_time;


        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        str_time=df2.format(Calendar.getInstance().getTime());
        convertResponseDate(str_time);

    }

    public String convert24(String time)
    {

        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss a");
            Date date = parseFormat.parse(time);

            return displayFormat.format(date);
        }
        catch (Exception ex){ Logs.info(TAG+"_TimeError",ex.getMessage());}

        return "";
    }
    public String convert12(String time)
    {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);

            return displayFormat.format(date);
        }
        catch (Exception ex){ Logs.info(TAG+"_TimeError",ex.getMessage());}

        return "";

    }

    public String convert12ForTimePicker(String time)
    {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);

            return displayFormat.format(date);
        }
        catch (Exception ex){ Logs.info(TAG+"_TimeError",ex.getMessage());}

        return "";

    }

    public void convertResponseDate(String mdate)
    {

        try {


            SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat displayTimeFormat;
            if(hoursFlag) {
                displayTimeFormat = new SimpleDateFormat("HH:mm:ss");

            }
            else {
                 displayTimeFormat = new SimpleDateFormat("hh:mm:ss a");
            }
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            displayDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = parseFormat.parse(mdate);
             Logs.info(TAG+"_HubDateTime",""+date.getTime());

            showDate.setText(displayDateFormat.format(date));
            showTime.setText(displayTimeFormat.format(date));
        }
        catch (Exception ex){ Logs.info(TAG+"_TimeError",ex.getMessage());}

    }

    public String convertRequestDate()
    {
        try {
            String strDate = showDate.getText().toString();
            String strTime="";
            if (!hoursFlag) {
                strTime = convert24(showTime.getText().toString());
            }
            else {
                strTime = showTime.getText().toString();
            }




            SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");


            Date date = parseFormat.parse(strDate + "" + strTime);
             Logs.info(TAG+"_requesthubDateTime",displayDateFormat.format(date));
            return displayDateFormat.format(date);
        }
        catch (Exception ex){ Logs.info(TAG+"_TimeErro",ex.getMessage());}
        return "";

    }


    public void getDateTime()
    {
        try {
            strDatetime=App.getDateTimeJson().getJSONObject("data").getJSONArray("data").getJSONObject(0).getString("dateTime");
            convertResponseDate(strDatetime);
            // showDate.setText(convert24(convertResponseDate(strDatetime)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.img_back)
    public void img_back()
    {

         if(App.isOrientationFlag())ReplaceFragment.replaceFragment(SettingTimeDate.this,R.id.frm_filter_main_container,SettingHome.newInstance());
          else ReplaceFragment.replaceFragment(SettingTimeDate.this,R.id.frm_main_container,SettingHome.newInstance());
    }



}