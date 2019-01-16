package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
/**
 * The BaseActivity is activity which use for extend as base class for other activites
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected static final String TAG = BaseActivity.class.getName();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static boolean isAppWentToBg = false;
    public static boolean isWindowFocused = false;
    public static boolean isMenuOpened = false;
    public static boolean isBackPressed = false;
    @Override
    protected void onStart() {
        Log.d(TAG, "onStart isAppWentToBg " + isAppWentToBg);
        applicationWillEnterForeground();
        super.onStart();
    }
    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            App.setApplication_state(true);
            isAppWentToBg = false;

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ");
        applicationdidenterbackground();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void applicationdidenterbackground() {
        if (!isWindowFocused) {
            isAppWentToBg = true;
            App.setApplication_state(false);
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }
    public void onBackPressed() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.custom_dialog)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        LinearLayout lin_main= dialog.findViewById(R.id.lin_main);
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(BaseActivity.this);
                return false;
            }
        });
        TextView msgt= dialog.findViewById(R.id.title);
        msgt.setText("EXIT");
        TextView msg= dialog.findViewById(R.id.msg);
        msg.setText("       Do  you  want  to  exit?      ");
        Button diagButtonCancel = dialog.findViewById(R.id.customDialogCancel);
        diagButtonCancel.setText("NO");
        diagButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
        diagButtonOK.setText("YES");
        diagButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finishAffinity();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
    }
}