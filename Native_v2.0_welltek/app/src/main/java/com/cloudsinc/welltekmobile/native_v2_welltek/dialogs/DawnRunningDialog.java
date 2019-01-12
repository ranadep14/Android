package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * This class containing functionality related to displaying dawn running dialog on dawn trigger
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class DawnRunningDialog extends DialogFragment {
    Context mcontext;
    String room_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_dawn_running_dialog, container,
                false);
        mcontext=rootView.getContext();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        ButterKnife.bind(this,rootView);
        room_id=getArguments().getString("room_id");
        Logs.info("DawnRunningdialog_"+"room_id",room_id);
        return rootView;
    }
    @OnClick(R.id.img_close)
    public void img_close()
    {
       // this.dismiss();
    }
    @OnClick(R.id.btn_confirm)
    public void btn_confirm()
    {
            //hvacOnOff(!hvac_flag);
           // dismiss();
    }
}
