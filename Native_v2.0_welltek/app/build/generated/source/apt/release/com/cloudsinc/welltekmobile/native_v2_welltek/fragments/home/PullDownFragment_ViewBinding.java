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
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PullDownFragment_ViewBinding implements Unbinder {
  private PullDownFragment target;

  private View view2131296415;

  private View view2131296404;

  @UiThread
  public PullDownFragment_ViewBinding(final PullDownFragment target, View source) {
    this.target = target;

    View view;
    target.outdoor_descp = Utils.findRequiredViewAsType(source, R.id.outdoor_descp, "field 'outdoor_descp'", TextView.class);
    target.txt_humidity = Utils.findOptionalViewAsType(source, R.id.txt_humidity_weather, "field 'txt_humidity'", TextView.class);
    target.txt_cur_weather = Utils.findOptionalViewAsType(source, R.id.txt_cur_weather, "field 'txt_cur_weather'", TextView.class);
    target.activity_main_home = Utils.findRequiredViewAsType(source, R.id.activity_main_home, "field 'activity_main_home'", RelativeLayout.class);
    target.seek_sun_move = Utils.findRequiredViewAsType(source, R.id.seek_sun_move, "field 'seek_sun_move'", SeekBar.class);
    target.txt_temp_weather = Utils.findOptionalViewAsType(source, R.id.txt_temp_weather, "field 'txt_temp_weather'", TextView.class);
    target.txt_v1 = Utils.findRequiredViewAsType(source, R.id.txt_v1, "field 'txt_v1'", TextView.class);
    target.no_txt = Utils.findRequiredViewAsType(source, R.id.no_txt, "field 'no_txt'", TextView.class);
    target.top_lyt = Utils.findRequiredViewAsType(source, R.id.top_lyt, "field 'top_lyt'", LinearLayout.class);
    target.lyt_data = Utils.findRequiredViewAsType(source, R.id.lyt_data, "field 'lyt_data'", LinearLayout.class);
    target.simpleChronometer = Utils.findOptionalViewAsType(source, R.id.simpleChronometer, "field 'simpleChronometer'", Chronometer.class);
    target.prog = Utils.findRequiredViewAsType(source, R.id.prog, "field 'prog'", ProgressBar.class);
    target.v1 = Utils.findRequiredView(source, R.id.v1, "field 'v1'");
    target.v2 = Utils.findRequiredView(source, R.id.vv2, "field 'v2'");
    target.v3 = Utils.findRequiredView(source, R.id.vv3, "field 'v3'");
    target.img_sun_circle = Utils.findRequiredViewAsType(source, R.id.img_sun_circle, "field 'img_sun_circle'", ImageView.class);
    target.img_btn_pull_up_panel = Utils.findRequiredViewAsType(source, R.id.img_btn_pull_up_panel, "field 'img_btn_pull_up_panel'", ImageView.class);
    target.img_sun_move = Utils.findRequiredViewAsType(source, R.id.img_sun_move, "field 'img_sun_move'", ImageView.class);
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
    PullDownFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.outdoor_descp = null;
    target.txt_humidity = null;
    target.txt_cur_weather = null;
    target.activity_main_home = null;
    target.seek_sun_move = null;
    target.txt_temp_weather = null;
    target.txt_v1 = null;
    target.no_txt = null;
    target.top_lyt = null;
    target.lyt_data = null;
    target.simpleChronometer = null;
    target.prog = null;
    target.v1 = null;
    target.v2 = null;
    target.v3 = null;
    target.img_sun_circle = null;
    target.img_btn_pull_up_panel = null;
    target.img_sun_move = null;

    view2131296415.setOnClickListener(null);
    view2131296415 = null;
    view2131296404.setOnClickListener(null);
    view2131296404 = null;
  }
}
