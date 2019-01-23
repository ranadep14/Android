package com.cloudsinc.welltekmobile.native_v2_welltek.activities;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
/**
 * This class containing functionality to display alert.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class MyAlertDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide activity title
        setContentView(R.layout.activity_my_alert_dialog);
        final Dialog intd = new Dialog(MyAlertDialog.this, R.style.DialogTheme);
        intd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        intd.setContentView(R.layout.dialog_timezone);
        intd.setCanceledOnTouchOutside(false);
        TextView txt_msg = intd.findViewById(R.id.txt_msg);
        txt_msg.setText(""+getIntent().getStringExtra("msg"));
        Button btnretry = intd.findViewById(R.id.btn_ok);
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.setNetwork_flag(true);
                MyAlertDialog.this.finish();
                final Intent mainIntent = new Intent(MyAlertDialog.this, ConnectionLostActivity.class);
                MyAlertDialog.this.startActivity(mainIntent);
            }
        });
        intd.show();
    }
}
