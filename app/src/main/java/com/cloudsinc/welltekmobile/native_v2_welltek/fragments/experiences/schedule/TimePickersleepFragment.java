package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import com.philliphsu.numberpadtimepicker.NumberPadTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by developers on 21/11/17.
 */

public class TimePickersleepFragment extends DialogFragment implements NumberPadTimePicker.OkButtonCallbacks {

    Button button;
    ImageButton cancel;
    private String str_action="";
    NumberPadTimePicker timePicker;
    String message;
    Bundle bundle;
    Context mcontext;
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(null);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_holder, container, false);
        timePicker= v.findViewById(R.id.tp);
        button= v.findViewById(R.id.ok);
        cancel=v.findViewById(R.id.cancel);
        timePicker.setOkButtonCallbacks(this);
        mcontext = v.getContext();
       /* Bundle bundle = getActivity().getIntent().getExtras();
         message = bundle.getString("message");
*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.confirmTimeSelection();  // Calls your onOkButtonClick() callback
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = getDialog();
                dialog.dismiss();
            }
        });


        str_action = App.getTemp_bundle().getString("actionupdate");
        //  //Toast.makeText(getActivity(), "str_Action"+str_action, Toast.LENGTH_SHORT).show();


        return v;
    }

    @Override
    public void onOkButtonEnabled(boolean enabled) {
        button.setEnabled(enabled);

    }
    @Override
    public void onOkButtonClick(NumberPadTimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        //     //Toast.makeText(getActivity(),String.valueOf(hourOfDay)+" "+String.valueOf(minute),Toast.LENGTH_SHORT).show();
        //Add_custmise_wake callingActivity = new  Add_custmise_wake() ;

        // callingActivity.onUserSelectTime(hourOfDay,minute );
        Dialog dialog = getDialog();
        Bundle bundle= App.getTemp_bundle();
        bundle.putInt("hours",hourOfDay);
        bundle.putInt("minutes",minute);
      //  bundle.putString("Callfromsleep","edit");
        bundle.putString("sleepCallfrom","edit");
        App.setTemp_bundle(bundle);
       // if (DateFormat.is24HourFormat(mcontext)){
        if(new PrefManager(mcontext).getValue("time_fomat").equals("1")) {


            String _24HourTime=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            bundle.putString("time",_24HourTime);
            App.setTemp_bundle(bundle);

        }else{
            String _24HourTime=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = null;
            try {
                _24HourDt = _24HourSDF.parse(_24HourTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bundle.putString("time",_12HourSDF.format(_24HourDt));
            App.setTemp_bundle(bundle);

        }





        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(this,R.id.frm_expriencesmain_container, Add_custmise_sleep.newInstance());
        else  ReplaceFragment.replaceFragment(this,R.id.frm_main_container, Add_custmise_sleep.newInstance());

        dialog.dismiss();

    }



}