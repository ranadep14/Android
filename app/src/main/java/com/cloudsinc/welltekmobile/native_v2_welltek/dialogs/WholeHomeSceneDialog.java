package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.JsonObjectCreater;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.SwitchTrackChange;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;

import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static android.view.View.VISIBLE;
/**
 * This class containing functionality related to displaying dialog for scene selection for whole home.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class WholeHomeSceneDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener{
    private Observable<String> mobservable;
    private Observer<String> myObserver;
     @Nullable @BindView(R.id.img_relax)ImageView img_relax;
    @Nullable @BindView(R.id.img_cook)ImageView img_cook;
    @Nullable @BindView(R.id.img_circadian)ImageView img_circadian;
    @Nullable @BindView(R.id.img_energize)ImageView img_energize;
    @Nullable @BindView(R.id.img_whole_light_add_fav)ImageView img_whole_light_add_fav;
    @Nullable @BindView(R.id.lin_scene_selection)LinearLayout lin_scene_selection;
    @Nullable @BindView(R.id.rel_main)RelativeLayout rel_main;
    @BindView(R.id.whole_on_off)Switch whole_on_off;
    @BindView(R.id.circular_whole_home_on_off)ProgressBar circular_whole_home_on_off;
    String scene_id="";
    String device_id="";
    Context mcontext;
    boolean fav_flag=false,add_fav_whole_light=false;
    String TAG=WholeHomeSceneDialog.this.getClass().getName();
    String applied_scene_id="-1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_whole_home_scenes_dialog, container,
                false);
        mcontext=rootView.getContext();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this,rootView);
        UserInteractionSleep.siboSleepChecking(rootView.getContext());
        setDefaultValues();
        setDefaultWholeHomeFavoriteState();
        setSubcriber();
        rel_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });
        return rootView;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(!App.isOrientationFlag()) setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }
    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        if(!App.isOrientationFlag()) {
            Dialog dialog = getDialog();
            if (dialog != null)
            {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(null);
            }
        }
        else
        {
            Dialog dialog = getDialog();
            if (dialog != null)
            {
                int width = (Build.MODEL.equals("QUAD-CORE R40 sc3826r") && Build.MANUFACTURER.equals("Allwinner"))||(Build.MODEL.equals("Q8919") && Build.MANUFACTURER.equals("SIBO"))?700:1000;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(null);
            }
        }
        // ... other stuff you want to do in your onStart() method
    }
    @Optional @OnClick(R.id.img_close)
    public void img_close()
    {
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        this.dismiss();
    }
    @Optional @OnClick(R.id.btn_cancel)
    public void btn_cancel()
    {
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        this.dismiss();
    }
    private void triggerScence(String tri)
    {
//        swt_light_on_off.setChecked(true);
        if(!applied_scene_id.equals(scene_id)) {
            try {
                JSONObject dataJson = new JSONObject();
                dataJson.put("scene", tri);
                dataJson.put("home", "all");
                JSONObject obj = new JSONObject();
                obj.put("data", dataJson);
                obj.put("type", "trigger_scene");
                Logs.info(TAG + "_scene_light", "" + obj);
                App.getSocket().emit("action", obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
                if(string.contains("Whole Home")) {
                    setDefaultWholeHomeFavoriteState();
                    setDefaultValues();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setDialog_subcriber(s);
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId()==R.id.whole_on_off) {
            circular_whole_home_on_off.setVisibility(VISIBLE);
            whole_on_off.setVisibility(View.GONE);
            try {
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("home_power", new JSONObject().put("type", "Lighting").put("state", isChecked)));
            } catch (Exception ex) {
                Logs.error(TAG, "----------" + ex.getMessage());
            }
            whole_on_off.setChecked(!whole_on_off.isChecked());
        }
    }
    @Optional @OnClick(R.id.btn_apply)
    public void btn_apply()
    {
       triggerScence(scene_id);
        App.setCurrentSubcriber(App.getCurrentSubcriber());
        dismiss();
    }
    @Optional
   @OnClick({R.id.img_circadian, R.id.img_relax, R.id.img_energize,R.id.img_cook})
    public void onClick(View view) {
        setDefault();
        switch (view.getId())
        {
            case R.id.img_circadian:
                scene_id="1";
                setSelection(img_circadian);
                break;
            case R.id.img_energize:
                scene_id="3";
                setSelection(img_energize);
                break;
            case R.id.img_relax:
                scene_id="2";
                setSelection(img_relax);
                break;
            case R.id.img_cook:
                scene_id="7";
                setSelection(img_cook);
                break;
        }
    }
    public void setSceneSelection(String scene_id)
    {
        setDefault();
        switch (scene_id)
        {
            case "1":
                setSelection(img_circadian);
                break;
            case "3":
                setSelection(img_energize);
                break;
            case "2":
                setSelection(img_relax);
                break;
            case "7":
                setSelection(img_cook);
                break;
        }
    }
    public void setSelection(ImageView img_selection) {
        img_selection.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_selection.setBackground(this.getResources().getDrawable(R.drawable.blue_circle));
    }
    public void setDefault()
    {
        img_circadian.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_energize.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_relax.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_cook.setColorFilter(this.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        img_circadian.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
        img_energize.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
        img_relax.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
        img_cook.setBackground(mcontext.getResources().getDrawable(R.drawable.blue_border_circle));
    }
    public void setDefaultValues()
    {
        boolean state=true;
        try {
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("CML_TITLE").equals("Whole Home"))
                {
                    Logs.info(TAG+"_HomeZoneObject",""+jsonObject);
                    // Light value set
                    if(jsonObject.has("CML_SCENE_ID")) {
                        applied_scene_id=jsonObject.getString("CML_SCENE_ID");
                        scene_id=jsonObject.getString("CML_SCENE_ID");
                        setSceneSelection(jsonObject.getString("CML_SCENE_ID"));
                    }
                    // Light value set
                    if (jsonObject.has("CML_LIGHT_POWER")) {
                        whole_on_off.setOnCheckedChangeListener(null);
                       // state=jsonObject.getBoolean("CML_LIGHT_POWER");
                        whole_on_off.setChecked(jsonObject.getBoolean("CML_LIGHT_POWER"));
                        whole_on_off.setOnCheckedChangeListener(this);
                        circular_whole_home_on_off.setVisibility(View.GONE);
                        whole_on_off.setVisibility(View.VISIBLE);
                        SwitchTrackChange.changeTrackColor(mcontext, whole_on_off);
                    }
                    // Blind value set
                }
            }
            if(!state)
            {
                lin_scene_selection.setAlpha(0.5f);
                disableAllViews(lin_scene_selection,state);
            }
            else
            {
                lin_scene_selection.setAlpha(1f);
                disableAllViews(lin_scene_selection,state);
            }
        }
        catch (Exception ex)
        {
            Logs.info(TAG+"_SetAQIError",""+ex.getMessage());
        }
    }
    @OnClick(R.id.img_whole_light_add_fav)
    public void   img_whole_light_add_fav()
    {
        if(add_fav_whole_light)
        {
            FavoritesOperations.deleteFav(""+FavoritesOperations.getWholeHomeFavId("allLight"));
            img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            FavoritesOperations.addFavWholeHome("light#"+System.currentTimeMillis(),"allLight");
            add_fav_whole_light=true;
            img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
    }
    public void disableAllViews(View v,boolean f){
        v.setEnabled(f);
        if(v instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup)v).getChildCount();  i++) {
                View view = ((ViewGroup)v).getChildAt(i);
                disableAllViews(view,f);
            }
        }
    }
    public void setDefaultWholeHomeFavoriteState()
    {
        try {
            add_fav_whole_light=false;
            img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
            JSONArray jsonArray = App.getFavData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("type")) {
                    if (jsonObject.getString("type").equals("allLight")) {
                        add_fav_whole_light=true;
                        img_whole_light_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                    }
                }
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getMessage());}
    }
}
