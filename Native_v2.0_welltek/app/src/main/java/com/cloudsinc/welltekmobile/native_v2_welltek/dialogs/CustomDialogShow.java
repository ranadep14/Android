package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.UserInteractionSleep;
/**
 * This class containing functionality related to displaying dialog with given string.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class CustomDialogShow {
    private static Dialog progress=null;
    private static Dialog cir_progress=null;
    private static Dialog intd=null;
    private static Typeface typeface;
    private static Exception e;
    private static  ProgressDialog pd;
    private TextView txt;
    public static Dialog d;
    public static void dispDialog(final Context mcontext, final Class mclass, String msgstr)
    {
        e=new Exception(msgstr);
        final Dialog d=new Dialog(mcontext);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.general_dialog);
           d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.setCanceledOnTouchOutside(false);
            TextView txtmsgstr= d.findViewById(R.id.txt_msg);
        LinearLayout lin_main= d.findViewById(R.id.lin_main);
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });
        txtmsgstr.setText(""+msgstr);
        txtmsgstr.setTypeface(setFontForDialog(mcontext));
            Button btnretry= d.findViewById(R.id.btn_retry);
        btnretry.setTypeface(setFontForDialog(mcontext));
            btnretry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        d.dismiss();
                         Logs.info("CustomDialog_Dismiss","--------------");
                        if(mcontext instanceof AppCompatActivity)((AppCompatActivity)mcontext).finish();
                        Intent i = new Intent(mcontext, mclass);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(i);
                    }catch (Exception ex)
                    {
                         Logs.error("CustomDialog_Error",ex.getMessage());
                    }
                }
            });
            Button btncancle= d.findViewById(R.id.btncancle);
        btncancle.setTypeface(setFontForDialog(mcontext));
        btncancle.setVisibility(View.GONE);
       btncancle.setText("OK");
            btncancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.dismiss();
                }
            });
            d.show();
    }
    public static void dispDialogWithOK(final Context mcontext, String msgstr)
    {
        e=new Exception(msgstr);
        d=new Dialog(mcontext);
        if(d.isShowing())d.dismiss();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.general_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCanceledOnTouchOutside(false);
        LinearLayout lin_main= d.findViewById(R.id.lin_main);
        lin_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UserInteractionSleep.userInteract(mcontext);
                return false;
            }
        });
        TextView txtmsgstr= d.findViewById(R.id.txt_msg);
        txtmsgstr.setTypeface(setFontForDialog(mcontext));
        txtmsgstr.setText(""+msgstr);
        Button btnretry= d.findViewById(R.id.btn_retry);
        btnretry.setTypeface(setFontForDialog(mcontext));
        Button btncancle= d.findViewById(R.id.btncancle);
        btncancle.setVisibility(View.GONE);
        btnretry.setText("OK");
        btncancle.setTypeface(setFontForDialog(mcontext));
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(d!=null) d.dismiss();
                d=null;
            }
        });
        d.show();
    }
    public static void hideDiaiogNoInternet()
    {
        if(intd!=null) {
            intd.dismiss();
        }
    }
    public static void dispDialogNoInternet(final Context mcontext,final Class classname)
    {
        intd = new Dialog(mcontext, R.style.DialogTheme);
        intd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        intd.setContentView(R.layout.dialog_internet_connection);
        intd.setCanceledOnTouchOutside(false);
        Button btnretry = intd.findViewById(R.id.btn_retry);
        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intd.dismiss();
                Intent i = new Intent(mcontext.getApplicationContext(), classname);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(i);
            }
        });
        intd.show();
    }
    public static void startProgressDialog(final Context mcontext)
    {
        progress=new Dialog(mcontext,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.dialog_circuler_progress);
        progress.show();
    }
    public static void circulerProgressDialog(final Context mcontext)
    {
        progress=new Dialog(mcontext,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.dialog_circuler_progress);
        progress.show();
    }
    public static void circulerProgressDialog(final Context mcontext,String str_msg)
    {
        progress=new Dialog(mcontext,R.style.ProgressDialogTheme);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.dialog_circuler_progress);
        ((TextView)progress.findViewById(R.id.txt_circle_prog_msg)).setText(str_msg);
        progress.show();
    }
    public static void stopProgressDialog()
    {
       if(progress.isShowing()) progress.dismiss();
    }
    public static void stopOkDialog()
    {
      if(d!=null)
      {
         // Activity activity = d.getOwnerActivity();
          if(d.isShowing())
          {
              d.dismiss();
          }
      }
    }
    private static Typeface setFontForDialog(Context mcontext)
    {
         typeface = Typeface.createFromAsset(mcontext.getAssets(),
                 "fonts/gotham-light-591d8629985e3.otf");
        return typeface;
    }
    public static void stopTransferentProgress()
    {
        pd.dismiss();
    }
}