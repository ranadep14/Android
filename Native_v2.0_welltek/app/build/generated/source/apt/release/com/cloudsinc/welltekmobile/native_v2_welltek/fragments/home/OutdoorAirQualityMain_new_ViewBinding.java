// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutdoorAirQualityMain_new_ViewBinding implements Unbinder {
  private OutdoorAirQualityMain_new target;

  private View view2131296484;

  @UiThread
  public OutdoorAirQualityMain_new_ViewBinding(final OutdoorAirQualityMain_new target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.close_nav, "field 'close_nav' and method 'close_nav'");
    target.close_nav = Utils.castView(view, R.id.close_nav, "field 'close_nav'", ImageView.class);
    view2131296484 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close_nav();
      }
    });
    target.lyt_popullant = Utils.findRequiredViewAsType(source, R.id.lyt_popullant, "field 'lyt_popullant'", RelativeLayout.class);
    target.click_populant = Utils.findRequiredViewAsType(source, R.id.click_populant, "field 'click_populant'", LinearLayout.class);
    target.simpleChronometer = Utils.findRequiredViewAsType(source, R.id.simpleChronometer, "field 'simpleChronometer'", Chronometer.class);
    target.txt_time = Utils.findRequiredViewAsType(source, R.id.outdoor_time, "field 'txt_time'", TextView.class);
    target.co2_status = Utils.findRequiredViewAsType(source, R.id.co2_status, "field 'co2_status'", TextView.class);
    target.no2_status = Utils.findRequiredViewAsType(source, R.id.no2_status, "field 'no2_status'", TextView.class);
    target.o3_status = Utils.findRequiredViewAsType(source, R.id.o3_status, "field 'o3_status'", TextView.class);
    target.pm2_status = Utils.findRequiredViewAsType(source, R.id.pm2_status, "field 'pm2_status'", TextView.class);
    target.pm10_status = Utils.findRequiredViewAsType(source, R.id.pm10_status, "field 'pm10_status'", TextView.class);
    target.so2_status = Utils.findRequiredViewAsType(source, R.id.so2_status, "field 'so2_status'", TextView.class);
    target.lin_activity_main = Utils.findRequiredViewAsType(source, R.id.activity_main, "field 'lin_activity_main'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OutdoorAirQualityMain_new target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.close_nav = null;
    target.lyt_popullant = null;
    target.click_populant = null;
    target.simpleChronometer = null;
    target.txt_time = null;
    target.co2_status = null;
    target.no2_status = null;
    target.o3_status = null;
    target.pm2_status = null;
    target.pm10_status = null;
    target.so2_status = null;
    target.lin_activity_main = null;

    view2131296484.setOnClickListener(null);
    view2131296484 = null;
  }
}
