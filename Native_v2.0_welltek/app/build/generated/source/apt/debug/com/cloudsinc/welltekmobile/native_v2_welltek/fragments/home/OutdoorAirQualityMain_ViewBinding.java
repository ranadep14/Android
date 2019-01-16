// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutdoorAirQualityMain_ViewBinding implements Unbinder {
  private OutdoorAirQualityMain target;

  private View view2131296679;

  @UiThread
  public OutdoorAirQualityMain_ViewBinding(final OutdoorAirQualityMain target, View source) {
    this.target = target;

    View view;
    target.co2_status = Utils.findRequiredViewAsType(source, R.id.co2_status, "field 'co2_status'", TextView.class);
    target.no2_status = Utils.findRequiredViewAsType(source, R.id.no2_status, "field 'no2_status'", TextView.class);
    target.o3_status = Utils.findRequiredViewAsType(source, R.id.o3_status, "field 'o3_status'", TextView.class);
    target.pm2_status = Utils.findRequiredViewAsType(source, R.id.pm2_status, "field 'pm2_status'", TextView.class);
    target.pm10_status = Utils.findRequiredViewAsType(source, R.id.pm10_status, "field 'pm10_status'", TextView.class);
    target.so2_status = Utils.findRequiredViewAsType(source, R.id.so2_status, "field 'so2_status'", TextView.class);
    target.tampilWeb = Utils.findRequiredViewAsType(source, R.id.leaflet, "field 'tampilWeb'", WebView.class);
    view = Utils.findRequiredView(source, R.id.img_close, "field 'img_close' and method 'close_nav'");
    target.img_close = Utils.castView(view, R.id.img_close, "field 'img_close'", ImageView.class);
    view2131296679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close_nav();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    OutdoorAirQualityMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.co2_status = null;
    target.no2_status = null;
    target.o3_status = null;
    target.pm2_status = null;
    target.pm10_status = null;
    target.so2_status = null;
    target.tampilWeb = null;
    target.img_close = null;

    view2131296679.setOnClickListener(null);
    view2131296679 = null;
  }
}
