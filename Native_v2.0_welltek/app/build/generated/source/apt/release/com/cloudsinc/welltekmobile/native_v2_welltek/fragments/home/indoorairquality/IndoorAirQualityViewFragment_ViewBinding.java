// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IndoorAirQualityViewFragment_ViewBinding implements Unbinder {
  private IndoorAirQualityViewFragment target;

  @UiThread
  public IndoorAirQualityViewFragment_ViewBinding(IndoorAirQualityViewFragment target,
      View source) {
    this.target = target;

    target.indoor_aqi_graph = Utils.findRequiredViewAsType(source, R.id.indoor_aqi_graph, "field 'indoor_aqi_graph'", DonutProgress.class);
    target.txt_air_quality = Utils.findRequiredViewAsType(source, R.id.txt_air_quality, "field 'txt_air_quality'", TextView.class);
    target.txt_no_indoor = Utils.findRequiredViewAsType(source, R.id.txt_no_indoor, "field 'txt_no_indoor'", TextView.class);
    target.txt_quality = Utils.findOptionalViewAsType(source, R.id.txt_indoor_quality, "field 'txt_quality'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IndoorAirQualityViewFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.indoor_aqi_graph = null;
    target.txt_air_quality = null;
    target.txt_no_indoor = null;
    target.txt_quality = null;
  }
}
