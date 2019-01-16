package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.PassowrdEncryptDecrypt;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.poovam.pinedittextfield.LinePinField;
import com.poovam.pinedittextfield.PinField;
import com.poovam.pinedittextfield.SquarePinField;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class PinInputActivity extends AppCompatActivity {

    PrefManager spm;
    String str_final_input="";
    String TAG=this.getClass().getSimpleName();
   // OtpView edt_pin;
    @BindView(R.id.linePinField)
   SquarePinField linePinField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_input);
      //  edt_pin=(OtpView)findViewById(R.id.edt_pin);
        ButterKnife.bind(this);
        spm=new PrefManager(this);


        linePinField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                str_final_input=s.toString();


            }} );


    }

    @OnClick(R.id.btn_save)
    public void btn_save()
    {



        Logs.info(TAG,"------------------------"+str_final_input);
        try {
            if(str_final_input.length()==6) {
                spm.setValue("pin_value", PassowrdEncryptDecrypt.encrypt(str_final_input));
                PinInputActivity.this.finish();
                final Intent mainIntent = new Intent(PinInputActivity.this, MainActivity.class);
                PinInputActivity.this.startActivity(mainIntent);
            }
            else
            {
                str_final_input="";
                CustomDialogShow.dispDialogWithOK(this,"Enter 6 digits pin");
            }
        }
        catch (Exception ex)
        {
            Logs.error(TAG,"-------"+ex.getMessage());
        }
    }
}
