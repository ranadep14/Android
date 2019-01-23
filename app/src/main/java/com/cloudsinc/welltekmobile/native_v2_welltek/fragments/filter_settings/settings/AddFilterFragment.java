package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddFilterFragment extends Fragment {
    public static AddFilterFragment newInstance() {
        return new AddFilterFragment();
    }

    private View view;
    @BindView(R.id.swt_autotime) Switch swt_autotime;
private Context mcontext;
    @BindView(R.id.txt_clock)TextView txt_clock;
    @BindView(R.id.txt_calender) TextView txt_calender;
    @BindView(R.id.txt_fragment_title) TextView txt_fragment_title;
    @BindView(R.id.lin_time) LinearLayout lin_time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_filter, container, false);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");


        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        ButterKnife.bind(this,view);
        mcontext=view.getContext();
        FontUtility.setAppFont(mContainer, mFont);

        txt_fragment_title.setText("Add Filter");
        swt_autotime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    lin_time.setVisibility(View.GONE);
                }
                else
                {
                    lin_time.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
    @OnClick(R.id.txt_clock)
    public void txt_clock()
    {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog td = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        try {
                            String dtStart = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);

                            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                            Date date = null;
                            try {
                                date = fmt.parse(dtStart);
                            } catch (ParseException e) {

                                e.printStackTrace();
                            }

                            SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                            String formattedTime=fmtOut.format(date);

                            txt_clock.setText(formattedTime);
                        } catch (Exception ex) {
                            Log.e("", ex.getMessage());
                        }
                    }
                },
                mHour, mMinute,
                false
        );
        td.show();

    }
    @OnClick(R.id.txt_calender)
    public void txt_calender()
    {
        final Calendar c = Calendar.getInstance();
       int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txt_calender.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        ReplaceFragment.replaceFragment(AddFilterFragment.this,R.id.frm_filter_main_container, SettingAddDevices.newInstance());
    }

}
