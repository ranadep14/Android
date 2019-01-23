// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.lang.IllegalStateException;
import java.lang.Override;
import pl.droidsonroids.gif.GifImageView;

public class HomeHeathByZoneFragment_ViewBinding implements Unbinder {
  private HomeHeathByZoneFragment target;

  private View view2131296724;

  private View view2131296698;

  private View view2131296694;

  private View view2131297045;

  private View view2131296753;

  private View view2131296415;

  private View view2131296404;

  @UiThread
  public HomeHeathByZoneFragment_ViewBinding(final HomeHeathByZoneFragment target, View source) {
    this.target = target;

    View view;
    target.txt_air_quality = Utils.findOptionalViewAsType(source, R.id.txt_air_quality, "field 'txt_air_quality'", TextView.class);
    target.txt_quality = Utils.findOptionalViewAsType(source, R.id.txt_indoor_quality, "field 'txt_quality'", TextView.class);
    target.outdoor_descp = Utils.findOptionalViewAsType(source, R.id.outdoor_descp, "field 'outdoor_descp'", TextView.class);
    target.txt_humidity = Utils.findOptionalViewAsType(source, R.id.txt_humidity_weather, "field 'txt_humidity'", TextView.class);
    target.txt_cur_weather = Utils.findOptionalViewAsType(source, R.id.txt_cur_weather, "field 'txt_cur_weather'", TextView.class);
    target.txt_temp_weather = Utils.findOptionalViewAsType(source, R.id.txt_temp_weather, "field 'txt_temp_weather'", TextView.class);
    target.txt_current_action = Utils.findOptionalViewAsType(source, R.id.txt_current_action, "field 'txt_current_action'", TextView.class);
    target.txt_current_wheather = Utils.findRequiredViewAsType(source, R.id.txt_current_wheather, "field 'txt_current_wheather'", TextView.class);
    target.txt_out_air_quality = Utils.findRequiredViewAsType(source, R.id.txt_out_air_quality, "field 'txt_out_air_quality'", TextView.class);
    target.txt_comfortrange = Utils.findOptionalViewAsType(source, R.id.txt_comfortrange, "field 'txt_comfortrange'", TextView.class);
    target.txt_temp = Utils.findOptionalViewAsType(source, R.id.txt_temp, "field 'txt_temp'", TextView.class);
    target.txt_degree_sign = Utils.findOptionalViewAsType(source, R.id.txt_degree_sign, "field 'txt_degree_sign'", TextView.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.web_view_gif, "field 'webView'", GifImageView.class);
    target.donut_progress = Utils.findOptionalViewAsType(source, R.id.donut_progress, "field 'donut_progress'", DonutProgress.class);
    target.rel_disable = Utils.findOptionalViewAsType(source, R.id.rel_disable, "field 'rel_disable'", RelativeLayout.class);
    view = source.findViewById(R.id.img_right);
    target.img_right = Utils.castView(view, R.id.img_right, "field 'img_right'", ImageView.class);
    if (view != null) {
      view2131296724 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_increase();
        }
      });
    }
    view = source.findViewById(R.id.img_left);
    target.img_left = Utils.castView(view, R.id.img_left, "field 'img_left'", ImageView.class);
    if (view != null) {
      view2131296698 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_decrease();
        }
      });
    }
    target.txt_comfort_title = Utils.findOptionalViewAsType(source, R.id.txt_comfort_title, "field 'txt_comfort_title'", TextView.class);
    target.txt_no_action = Utils.findRequiredViewAsType(source, R.id.txt_no_action, "field 'txt_no_action'", TextView.class);
    target.txt_no_indoor = Utils.findRequiredViewAsType(source, R.id.txt_no_indoor, "field 'txt_no_indoor'", TextView.class);
    target.img_meter_indicator = Utils.findOptionalViewAsType(source, R.id.img_meter_indicator, "field 'img_meter_indicator'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.img_hvac_power, "field 'img_hvac_power'");
    target.img_hvac_power = Utils.castView(view, R.id.img_hvac_power, "field 'img_hvac_power'", ImageView.class);
    view2131296694 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_hvac_power();
      }
    });
    target.img_thermostat_point = Utils.findOptionalViewAsType(source, R.id.img_thermostat_point, "field 'img_thermostat_point'", ImageView.class);
    target.img_thermostat_desire_temp = Utils.findOptionalViewAsType(source, R.id.img_thermostat_desire_temp, "field 'img_thermostat_desire_temp'", ImageView.class);
    view = source.findViewById(R.id.outdoor_aqi_graph);
    target.outdoor_aqi_graph = Utils.castView(view, R.id.outdoor_aqi_graph, "field 'outdoor_aqi_graph'", DonutProgress.class);
    if (view != null) {
      view2131297045 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.outdoor_aqi_graph();
        }
      });
    }
    view = source.findViewById(R.id.indoor_aqi_graph);
    target.indoor_aqi_graph = Utils.castView(view, R.id.indoor_aqi_graph, "field 'indoor_aqi_graph'", DonutProgress.class);
    if (view != null) {
      view2131296753 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.indoor_aqi_graph();
        }
      });
    }
    target.lines = Utils.findRequiredViewAsType(source, R.id.lines, "field 'lines'", LinearLayout.class);
    target.current_line = Utils.findRequiredView(source, R.id.current_line, "field 'current_line'");
    target.outdoor_line = Utils.findRequiredView(source, R.id.outdoor_line, "field 'outdoor_line'");
    target.img_sun_circle = Utils.findRequiredViewAsType(source, R.id.img_sun_circle, "field 'img_sun_circle'", ImageView.class);
    target.spn_zone = Utils.findOptionalViewAsType(source, R.id.spn_zone, "field 'spn_zone'", Spinner.class);
    target.img_mode = Utils.findOptionalViewAsType(source, R.id.img_mode, "field 'img_mode'", ImageView.class);
    target.txt_zone_name = Utils.findOptionalViewAsType(source, R.id.txt_zone_name, "field 'txt_zone_name'", TextView.class);
    target.gif_title = Utils.findRequiredViewAsType(source, R.id.gif_title, "field 'gif_title'", TextView.class);
    target.rel_thermostat = Utils.findOptionalViewAsType(source, R.id.rel_thermostat, "field 'rel_thermostat'", RelativeLayout.class);
    target.rel_thermostat_control_panel = Utils.findOptionalViewAsType(source, R.id.rel_thermostat_control_panel, "field 'rel_thermostat_control_panel'", RelativeLayout.class);
    target.simpleChronometer = Utils.findOptionalViewAsType(source, R.id.simpleChronometer, "field 'simpleChronometer'", Chronometer.class);
    target.img_main_home = Utils.findOptionalViewAsType(source, R.id.activity_main_home, "field 'img_main_home'", ImageView.class);
    target.rel_lyt = Utils.findRequiredViewAsType(source, R.id.rel_lyt, "field 'rel_lyt'", RelativeLayout.class);
    target.rel_no_data = Utils.findOptionalViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
    target.img_btn_pull_down_panel = Utils.findOptionalViewAsType(source, R.id.img_btn_pull_down_panel, "field 'img_btn_pull_down_panel'", ImageView.class);
    target.img_btn_open_nav_drawer = Utils.findOptionalViewAsType(source, R.id.img_btn_open_nav_drawer, "field 'img_btn_open_nav_drawer'", ImageView.class);
    target.txt_time = Utils.findOptionalViewAsType(source, R.id.txt_time, "field 'txt_time'", TextView.class);
    target.view1 = Utils.findRequiredViewAsType(source, R.id.view1, "field 'view1'", GifImageView.class);
    target.view2 = Utils.findRequiredViewAsType(source, R.id.view2, "field 'view2'", GifImageView.class);
    target.view3 = Utils.findRequiredViewAsType(source, R.id.view3, "field 'view3'", GifImageView.class);
    target.view4i = Utils.findRequiredViewAsType(source, R.id.view4, "field 'view4i'", GifImageView.class);
    target.txt_no_hvac = Utils.findRequiredViewAsType(source, R.id.txt_no_hvac, "field 'txt_no_hvac'", TextView.class);
    target.current_action_prog_bar = Utils.findRequiredViewAsType(source, R.id.current_action_prog_bar, "field 'current_action_prog_bar'", ProgressBar.class);
    target.current_weather_prog_bar = Utils.findOptionalViewAsType(source, R.id.current_weather_prog_bar, "field 'current_weather_prog_bar'", ProgressBar.class);
    target.outdoor_prog_bar = Utils.findOptionalViewAsType(source, R.id.outdoor_prog_bar, "field 'outdoor_prog_bar'", ProgressBar.class);
    target.indoor_prog_bar = Utils.findOptionalViewAsType(source, R.id.indoor_prog_bar, "field 'indoor_prog_bar'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.btn_next, "method 'btn_next'");
    view2131296415 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_next();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_change_backround, "method 'btn_change_backround'");
    view2131296404 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_change_backround();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeHeathByZoneFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_air_quality = null;
    target.txt_quality = null;
    target.outdoor_descp = null;
    target.txt_humidity = null;
    target.txt_cur_weather = null;
    target.txt_temp_weather = null;
    target.txt_current_action = null;
    target.txt_current_wheather = null;
    target.txt_out_air_quality = null;
    target.txt_comfortrange = null;
    target.txt_temp = null;
    target.txt_degree_sign = null;
    target.webView = null;
    target.donut_progress = null;
    target.rel_disable = null;
    target.img_right = null;
    target.img_left = null;
    target.txt_comfort_title = null;
    target.txt_no_action = null;
    target.txt_no_indoor = null;
    target.img_meter_indicator = null;
    target.img_hvac_power = null;
    target.img_thermostat_point = null;
    target.img_thermostat_desire_temp = null;
    target.outdoor_aqi_graph = null;
    target.indoor_aqi_graph = null;
    target.lines = null;
    target.current_line = null;
    target.outdoor_line = null;
    target.img_sun_circle = null;
    target.spn_zone = null;
    target.img_mode = null;
    target.txt_zone_name = null;
    target.gif_title = null;
    target.rel_thermostat = null;
    target.rel_thermostat_control_panel = null;
    target.simpleChronometer = null;
    target.img_main_home = null;
    target.rel_lyt = null;
    target.rel_no_data = null;
    target.img_btn_pull_down_panel = null;
    target.img_btn_open_nav_drawer = null;
    target.txt_time = null;
    target.view1 = null;
    target.view2 = null;
    target.view3 = null;
    target.view4i = null;
    target.txt_no_hvac = null;
    target.current_action_prog_bar = null;
    target.current_weather_prog_bar = null;
    target.outdoor_prog_bar = null;
    target.indoor_prog_bar = null;

    if (view2131296724 != null) {
      view2131296724.setOnClickListener(null);
      view2131296724 = null;
    }
    if (view2131296698 != null) {
      view2131296698.setOnClickListener(null);
      view2131296698 = null;
    }
    view2131296694.setOnClickListener(null);
    view2131296694 = null;
    if (view2131297045 != null) {
      view2131297045.setOnClickListener(null);
      view2131297045 = null;
    }
    if (view2131296753 != null) {
      view2131296753.setOnClickListener(null);
      view2131296753 = null;
    }
    view2131296415.setOnClickListener(null);
    view2131296415 = null;
    view2131296404.setOnClickListener(null);
    view2131296404 = null;
  }
}
