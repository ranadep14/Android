// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.climate;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ClimateSetDesireTemperature_ViewBinding implements Unbinder {
  private ClimateSetDesireTemperature target;

  private View view2131296695;

  private View view2131296683;

  private View view2131296694;

  private View view2131296670;

  @UiThread
  public ClimateSetDesireTemperature_ViewBinding(final ClimateSetDesireTemperature target,
      View source) {
    this.target = target;

    View view;
    target.txt_purification_state = Utils.findOptionalViewAsType(source, R.id.txt_purification_state, "field 'txt_purification_state'", TextView.class);
    target.txt_humidity = Utils.findOptionalViewAsType(source, R.id.txt_humidity, "field 'txt_humidity'", TextView.class);
    target.img_meter_indicator = Utils.findOptionalViewAsType(source, R.id.img_meter_indicator, "field 'img_meter_indicator'", ImageView.class);
    target.txt_temp = Utils.findOptionalViewAsType(source, R.id.txt_temp, "field 'txt_temp'", TextView.class);
    target.txt_degree_sign = Utils.findOptionalViewAsType(source, R.id.txt_degree_sign, "field 'txt_degree_sign'", TextView.class);
    target.img_thermostat_point = Utils.findOptionalViewAsType(source, R.id.img_thermostat_point, "field 'img_thermostat_point'", ImageView.class);
    target.seek_set_desire_point = Utils.findOptionalViewAsType(source, R.id.seek_set_point, "field 'seek_set_desire_point'", SeekBar.class);
    target.txt_lower = Utils.findOptionalViewAsType(source, R.id.txt_lower, "field 'txt_lower'", TextView.class);
    target.txt_higher = Utils.findOptionalViewAsType(source, R.id.txt_higher, "field 'txt_higher'", TextView.class);
    view = source.findViewById(R.id.img_increase);
    target.img_increase = Utils.castView(view, R.id.img_increase, "field 'img_increase'", ImageView.class);
    if (view != null) {
      view2131296695 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_increase();
        }
      });
    }
    view = source.findViewById(R.id.img_decrease);
    target.img_decrease = Utils.castView(view, R.id.img_decrease, "field 'img_decrease'", ImageView.class);
    if (view != null) {
      view2131296683 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_decrease();
        }
      });
    }
    target.rel_disable = Utils.findOptionalViewAsType(source, R.id.rel_disable, "field 'rel_disable'", RelativeLayout.class);
    target.rel_thermostat_control_panel = Utils.findOptionalViewAsType(source, R.id.rel_thermostat_control_panel, "field 'rel_thermostat_control_panel'", RelativeLayout.class);
    target.img_mode = Utils.findOptionalViewAsType(source, R.id.img_mode, "field 'img_mode'", ImageView.class);
    target.rel_thermostat = Utils.findOptionalViewAsType(source, R.id.rel_thermostat, "field 'rel_thermostat'", RelativeLayout.class);
    target.img_thermostat_desire_temp = Utils.findOptionalViewAsType(source, R.id.img_thermostat_desire_temp, "field 'img_thermostat_desire_temp'", ImageView.class);
    target.lin_zone_selection = Utils.findOptionalViewAsType(source, R.id.lin_zone_selection, "field 'lin_zone_selection'", LinearLayout.class);
    target.rel_no_data = Utils.findOptionalViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
    target.txt_time = Utils.findOptionalViewAsType(source, R.id.txt_date_time, "field 'txt_time'", TextView.class);
    target.lin_comfort_band = Utils.findOptionalViewAsType(source, R.id.lin_comfort_band, "field 'lin_comfort_band'", LinearLayout.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    view = source.findViewById(R.id.img_hvac_power);
    target.img_hvac_power = Utils.castView(view, R.id.img_hvac_power, "field 'img_hvac_power'", ImageView.class);
    if (view != null) {
      view2131296694 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_hvac_power();
        }
      });
    }
    target.rel_date_zone = Utils.findOptionalViewAsType(source, R.id.rel_date_zone, "field 'rel_date_zone'", RelativeLayout.class);
    target.donut_progress = Utils.findOptionalViewAsType(source, R.id.donut_progress, "field 'donut_progress'", DonutProgress.class);
    target.spn_zone = Utils.findOptionalViewAsType(source, R.id.spn_zone, "field 'spn_zone'", Spinner.class);
    target.swt_purification_power = Utils.findOptionalViewAsType(source, R.id.swt_purification_power, "field 'swt_purification_power'", Switch.class);
    target.txt_air_purification = Utils.findRequiredViewAsType(source, R.id.txt_air_purification, "field 'txt_air_purification'", TextView.class);
    view = source.findViewById(R.id.img_back);
    if (view != null) {
      view2131296670 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_back();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    ClimateSetDesireTemperature target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_purification_state = null;
    target.txt_humidity = null;
    target.img_meter_indicator = null;
    target.txt_temp = null;
    target.txt_degree_sign = null;
    target.img_thermostat_point = null;
    target.seek_set_desire_point = null;
    target.txt_lower = null;
    target.txt_higher = null;
    target.img_increase = null;
    target.img_decrease = null;
    target.rel_disable = null;
    target.rel_thermostat_control_panel = null;
    target.img_mode = null;
    target.rel_thermostat = null;
    target.img_thermostat_desire_temp = null;
    target.lin_zone_selection = null;
    target.rel_no_data = null;
    target.txt_time = null;
    target.lin_comfort_band = null;
    target.rel_loading = null;
    target.img_hvac_power = null;
    target.rel_date_zone = null;
    target.donut_progress = null;
    target.spn_zone = null;
    target.swt_purification_power = null;
    target.txt_air_purification = null;

    if (view2131296695 != null) {
      view2131296695.setOnClickListener(null);
      view2131296695 = null;
    }
    if (view2131296683 != null) {
      view2131296683.setOnClickListener(null);
      view2131296683 = null;
    }
    if (view2131296694 != null) {
      view2131296694.setOnClickListener(null);
      view2131296694 = null;
    }
    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
  }
}
