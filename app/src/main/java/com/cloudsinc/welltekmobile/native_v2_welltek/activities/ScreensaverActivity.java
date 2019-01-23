package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

public class ScreensaverActivity extends AppCompatActivity {
    pl.droidsonroids.gif.GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_screensaver);


         gifImageView = findViewById(R.id.GifImageView);
        gifImageView.setImageResource(R.drawable.time);
    }
}
