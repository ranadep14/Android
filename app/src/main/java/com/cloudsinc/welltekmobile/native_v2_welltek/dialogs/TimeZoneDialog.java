package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
/**
 * This class containing functionality related to displaying dialog for mismatch time zone
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class TimeZoneDialog extends DialogFragment  {
    String dialog_type="";
    String TAG=this.getClass().getSimpleName();
    @BindView(R.id.txt_msg) TextView txt_msg;
    @BindView(R.id.rel_main) RelativeLayout rel_main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_timezone, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, rootView);
        dialog_type=getArguments().getString("dialg_type");
        Logs.info(TAG,"dialog_type......"+dialog_type);
        if(dialog_type.equals("time")) {
            txt_msg.setText(""+getActivity().getResources().getString(R.string.time_zone));
        }
        else
        {
            txt_msg.setText(""+getActivity().getResources().getString(R.string.darwing_installtion_msg));
        }
        rel_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(rootView.getContext());
                return false;
            }
        });
        return rootView;
    }


    @Optional
    @OnClick(R.id.btn_ok)
    public void btn_cancel() {
        if(dialog_type.equals("time")) {
            getActivity().finishAffinity();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            getActivity().startActivity(intent);
            getActivity().finish();
            System.exit(0);
        }
        else
        {
            getActivity().finishAffinity();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            getActivity().startActivity(intent);
            getActivity().finish();
            System.exit(0);
        }
    }
}
