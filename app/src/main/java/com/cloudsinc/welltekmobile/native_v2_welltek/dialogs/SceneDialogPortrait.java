package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Light;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
/**
 * This class containing functionality related to displaying dialog fragment for scene potrait screen
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class SceneDialogPortrait extends DialogFragment {
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @BindView(R.id.img_relax)ImageView img_relax;
    @BindView(R.id.img_cook)ImageView img_cook;
    @BindView(R.id.img_circadian)ImageView img_circadian;
    @BindView(R.id.img_energize)ImageView img_energize;
    @BindView(R.id.txt_cooking)TextView txt_cooking;
    @BindView(R.id.txt_circadian)TextView txt_circadian;
    @BindView(R.id.txt_energize)TextView txt_energize;
    @BindView(R.id.txt_relax)TextView txt_relax;
    @BindView(R.id.lin_cook)LinearLayout lin_cook;
    @BindView(R.id.btn_apply)Button btn_apply;
    @BindView(R.id.txt_title)TextView txt_title;
    @BindView(R.id.txt_room_name)TextView txt_room_name;
    @BindView(R.id.btn_cancel)Button btn_cancel;
    @BindView(R.id.lin_main)LinearLayout lin_main;
    String str_type="";
    String room_type="";
    String scene_id="";
    String device_id="";
    Context mcontext;
    List<String> wholeRoomLightList;
    HashMap<String, List<Light>> lightList;
    String light_name;
    String TAG=SceneDialogPortrait.this.getClass().getName();
    String room_name;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.custom_scenes_dialog, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this,rootView);
        UserInteractionSleep.siboSleepChecking(rootView.getContext());
        light_name=getArguments().getString("light_names");
        room_name=getArguments().getString("room_name");
        mcontext=rootView.getContext();
        txt_title.setText(light_name);
        txt_room_name.setText(room_name);
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });
        return rootView;
    }
    @OnClick(R.id.btn_apply)
    public void btn_apply(){
        dismiss();
    }
     @OnClick(R.id.btn_cancel)
    public void btn_cancel()
    {
        this.dismiss();
    }
    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
    private void prepareListData() {
        wholeRoomLightList = App.getLightRoomArrayList();
        lightList = App.getLightArrayList();
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+ App.getRoomData());
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
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            }
            @Override
            public void onNext(String string) {
                 Logs.info(TAG+"_WhereIm","InTypeMoreDialog"+string);
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setDialog_subcriber(s);
    }
    @Optional
    @OnClick({R.id.img_circadian, R.id.img_relax, R.id.img_energize, R.id.img_cook})
    public void onClick(View view) {
        setDefault();
        switch (view.getId())
        {
            case R.id.img_circadian:
                scene_id="1";
                setSelection(img_circadian,txt_circadian);
                break;
            case R.id.img_energize:
                scene_id="3";
                setSelection(img_energize,txt_energize);
                break;
            case R.id.img_relax:
                scene_id="2";
                setSelection(img_relax,txt_relax);
                break;
            case R.id.img_cook:
                scene_id="7";
                setSelection(img_cook,txt_cooking);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Logs.info(TAG, "------i am in onResume");
        UserInteractionSleep.siboSleepChecking(mcontext);
    }
    @Override
    public void onStop() {
        super.onStop();
        Logs.info(TAG, "------i am in onstop");
        UserInteractionSleep.stopHandler();
    }
    public void setSelection(ImageView img_selection,TextView txt_bottom_text_color) {
        img_selection.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_selection.setBackground(this.getResources().getDrawable(R.drawable.blue_circle));
        txt_bottom_text_color.setTextColor(mcontext.getResources().getColor(R.color.white));
    }
    public void setDefault()
    {
        txt_cooking.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_relax.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_energize.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        txt_circadian.setTextColor(mcontext.getResources().getColor(R.color.light_white));
        img_circadian.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_energize.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_relax.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_cook.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_circadian.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
        img_energize.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
        img_relax.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
        img_cook.setBackground(mcontext.getResources().getDrawable(R.drawable.grey_border_blue));
    }
}
