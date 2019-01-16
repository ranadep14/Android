package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.activities.PinInputActivity;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PassowrdEncryptDecrypt;
import com.poovam.pinedittextfield.CirclePinField;
import com.poovam.pinedittextfield.PinField;
import com.poovam.pinedittextfield.SquarePinField;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.hideSystemUI;
public class PinInputDialog extends DialogFragment {

    PrefManager spm;
    String str_final_input="";
    String TAG=this.getClass().getSimpleName();
    View rootView;
    Context mcontext;
    @BindView(R.id.circlePinField)
    CirclePinField circlePinField;
    @BindView(R.id.linePinField)
    SquarePinField linePinField;
    @BindView(R.id.txt_valid_text)
    TextView txt_valid_text;
    @BindView(R.id.txt_pin_title)
    TextView txt_pin_title;
    @BindView(R.id.btn_save)
    Button btn_save;
    private String call_from="";
    private boolean action_flag=false;

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
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.custom_dialog_input_pin, container,
                false);
        mcontext=rootView.getContext();
        ButterKnife.bind(this,rootView);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        if(getArguments()!=null) {
            call_from = getArguments().containsKey("call_from") ? getArguments().getString("call_from") : "";
            action_flag = getArguments().containsKey("action_flag") ? getArguments().getBoolean("action_flag") : false;
        }
        if(action_flag)
        {
            linePinField.setVisibility(View.VISIBLE);
            circlePinField.setVisibility(View.GONE);
            btn_save.setVisibility(View.VISIBLE);
            txt_pin_title.setText(mcontext.getResources().getString(R.string.new_pin_enter_screen_title));
        }
        final RelativeLayout root = new RelativeLayout(getActivity());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        spm=new PrefManager(mcontext);
        circlePinField.setOnTextCompleteListener(new PinField.OnTextCompleteListener() {
            @Override
            public boolean onTextComplete (@NotNull String enteredText) {
                try {
                    str_final_input = enteredText;
                    Logs.info(TAG, "----------------" + PassowrdEncryptDecrypt.encrypt(str_final_input) + "------------" + spm.getValue("pin_value"));
                    if (str_final_input.length() == 6 && PassowrdEncryptDecrypt.encrypt(str_final_input).equals(spm.getValue("pin_value"))) {

                        if(call_from.equals("reset_pin"))spm.removeKey("pin_value");
                        if (App.getMain_activity_subcriber() != null) {
                            App.setSubcription(Observable.just("hide_status_bar").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getMain_activity_subcriber().getObserver()));
                        }
                        if (App.getCurrentSubcriber() != null) {
                            App.setSubcription(Observable.just("reset_pin").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getCurrentSubcriber().getObserver()));
                        }
                        dismiss();
                    } else {
                        final Animation animShake = AnimationUtils.loadAnimation(mcontext, R.anim.shake_animation);
                        circlePinField.setText("");
                        circlePinField.startAnimation(animShake);
                        txt_valid_text.setVisibility(View.VISIBLE);
                    }


                }
                catch (Exception ex)
                {
                    Logs.error(TAG,"-----"+ex.getMessage());
                }
                return true; // Return true to keep the keyboard open else return false to close the keyboard
            }
        });

        linePinField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                str_final_input=s.toString();


            }} );
        return rootView;
    }


    @OnClick(R.id.btn_save)
    public void btn_save()
    {
        try {
            if(str_final_input.length()==6) {
                spm.setValue("pin_value", PassowrdEncryptDecrypt.encrypt(str_final_input));
                dismiss();
                if (App.getMain_activity_subcriber() != null) {
                    App.setSubcription(Observable.just("hide_status_bar").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getMain_activity_subcriber().getObserver()));
                }
                if (App.getCurrentSubcriber() != null) {
                    App.setSubcription(Observable.just("reset_pin").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(App.getCurrentSubcriber().getObserver()));
                }
            }
            else
            {
                str_final_input="";
                CustomDialogShow.dispDialogWithOK(mcontext,"Enter 6 digits pin");
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"-------"+ex.getMessage());
        }

    }


}
