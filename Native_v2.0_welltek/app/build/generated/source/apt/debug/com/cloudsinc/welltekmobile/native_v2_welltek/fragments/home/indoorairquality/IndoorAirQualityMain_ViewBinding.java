// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IndoorAirQualityMain_ViewBinding implements Unbinder {
  private IndoorAirQualityMain target;

  private View view2131296484;

  private View view2131296626;

  private View view2131297078;

  private View view2131297082;

  private View view2131296263;

  private View view2131297341;

  private View view2131296485;

  @UiThread
  public IndoorAirQualityMain_ViewBinding(final IndoorAirQualityMain target, View source) {
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
    view = Utils.findRequiredView(source, R.id.histoty_d3, "field 'button1' and method 'histoty_d3'");
    target.button1 = Utils.castView(view, R.id.histoty_d3, "field 'button1'", Button.class);
    view2131296626 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.histoty_d3();
      }
    });
    target.txt_quality = Utils.findRequiredViewAsType(source, R.id.txt_aqi_status, "field 'txt_quality'", TextView.class);
    target.co2_text = Utils.findRequiredViewAsType(source, R.id.co2score, "field 'co2_text'", TextView.class);
    target.pm2_5text = Utils.findRequiredViewAsType(source, R.id.pm2_5score, "field 'pm2_5text'", TextView.class);
    target.pm10_text = Utils.findRequiredViewAsType(source, R.id.pm10_score, "field 'pm10_text'", TextView.class);
    target.tvoc_score = Utils.findRequiredViewAsType(source, R.id.tvoc_score, "field 'tvoc_score'", TextView.class);
    target.tvoc_text_descp = Utils.findRequiredViewAsType(source, R.id.tvoc_text_descp, "field 'tvoc_text_descp'", TextView.class);
    target.simpleChronometer = Utils.findRequiredViewAsType(source, R.id.simpleChronometer, "field 'simpleChronometer'", Chronometer.class);
    target.score_no_data = Utils.findRequiredViewAsType(source, R.id.score_no_data, "field 'score_no_data'", TextView.class);
    target.mid = Utils.findRequiredViewAsType(source, R.id.mid, "field 'mid'", LinearLayout.class);
    target.aqi_graph = Utils.findRequiredViewAsType(source, R.id.aqi_graph, "field 'aqi_graph'", DonutProgress.class);
    view = Utils.findRequiredView(source, R.id.pm10prog, "field 'pm10prog' and method 'pm10prog'");
    target.pm10prog = Utils.castView(view, R.id.pm10prog, "field 'pm10prog'", DonutProgress.class);
    view2131297078 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pm10prog();
      }
    });
    view = Utils.findRequiredView(source, R.id.pm2prog, "field 'pm2prog' and method 'pm2prog'");
    target.pm2prog = Utils.castView(view, R.id.pm2prog, "field 'pm2prog'", DonutProgress.class);
    view2131297082 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pm2prog();
      }
    });
    view = Utils.findRequiredView(source, R.id.COprog, "field 'COprog' and method 'COprog'");
    target.COprog = Utils.castView(view, R.id.COprog, "field 'COprog'", DonutProgress.class);
    view2131296263 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.COprog();
      }
    });
    view = Utils.findRequiredView(source, R.id.tvocprog, "field 'tvocprog' and method 'tvocprog'");
    target.tvocprog = Utils.castView(view, R.id.tvocprog, "field 'tvocprog'", DonutProgress.class);
    view2131297341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tvocprog();
      }
    });
    target.txt_time = Utils.findRequiredViewAsType(source, R.id.txt_time, "field 'txt_time'", TextView.class);
    target.lin_activity_main = Utils.findRequiredViewAsType(source, R.id.lin_activity_main, "field 'lin_activity_main'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.close_nav_btn, "method 'close_nav_btn'");
    view2131296485 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close_nav_btn();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    IndoorAirQualityMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.close_nav = null;
    target.button1 = null;
    target.txt_quality = null;
    target.co2_text = null;
    target.pm2_5text = null;
    target.pm10_text = null;
    target.tvoc_score = null;
    target.tvoc_text_descp = null;
    target.simpleChronometer = null;
    target.score_no_data = null;
    target.mid = null;
    target.aqi_graph = null;
    target.pm10prog = null;
    target.pm2prog = null;
    target.COprog = null;
    target.tvocprog = null;
    target.txt_time = null;
    target.lin_activity_main = null;

    view2131296484.setOnClickListener(null);
    view2131296484 = null;
    view2131296626.setOnClickListener(null);
    view2131296626 = null;
    view2131297078.setOnClickListener(null);
    view2131297078 = null;
    view2131297082.setOnClickListener(null);
    view2131297082 = null;
    view2131296263.setOnClickListener(null);
    view2131296263 = null;
    view2131297341.setOnClickListener(null);
    view2131297341 = null;
    view2131296485.setOnClickListener(null);
    view2131296485 = null;
  }
}
