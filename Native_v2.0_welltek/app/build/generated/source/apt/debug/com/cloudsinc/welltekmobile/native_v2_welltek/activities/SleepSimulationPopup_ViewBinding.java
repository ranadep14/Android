// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SleepSimulationPopup_ViewBinding implements Unbinder {
  private SleepSimulationPopup target;

  private View view2131296417;

  private View view2131296402;

  private View view2131296416;

  private View view2131296400;

  @UiThread
  public SleepSimulationPopup_ViewBinding(SleepSimulationPopup target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SleepSimulationPopup_ViewBinding(final SleepSimulationPopup target, View source) {
    this.target = target;

    View view;
    target.txt_sleep_timer = Utils.findRequiredViewAsType(source, R.id.txt_sleep_timer, "field 'txt_sleep_timer'", TextView.class);
    target.lyt_no_data = Utils.findRequiredViewAsType(source, R.id.lyt_no_data, "field 'lyt_no_data'", LinearLayout.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progress_bar'", ProgressBar.class);
    target.lyt_data = Utils.findRequiredViewAsType(source, R.id.lyt_data, "field 'lyt_data'", LinearLayout.class);
    target.txt_room_name = Utils.findRequiredViewAsType(source, R.id.txt_room_name, "field 'txt_room_name'", TextView.class);
    target.no_text = Utils.findRequiredViewAsType(source, R.id.no_text, "field 'no_text'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_ok, "field 'btn_ok' and method 'btn_ok'");
    target.btn_ok = Utils.castView(view, R.id.btn_ok, "field 'btn_ok'", Button.class);
    view2131296417 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_ok();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'btn_cancel' and method 'btn_cancel'");
    target.btn_cancel = Utils.castView(view, R.id.btn_cancel, "field 'btn_cancel'", Button.class);
    view2131296402 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_cancel();
      }
    });
    target.img_gif_loading = Utils.findRequiredViewAsType(source, R.id.img_gif_loading, "field 'img_gif_loading'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_nodata_ok, "method 'btn_nodata_ok'");
    view2131296416 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_nodata_ok();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'btn_back'");
    view2131296400 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SleepSimulationPopup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_sleep_timer = null;
    target.lyt_no_data = null;
    target.progress_bar = null;
    target.lyt_data = null;
    target.txt_room_name = null;
    target.no_text = null;
    target.btn_ok = null;
    target.btn_cancel = null;
    target.img_gif_loading = null;

    view2131296417.setOnClickListener(null);
    view2131296417 = null;
    view2131296402.setOnClickListener(null);
    view2131296402 = null;
    view2131296416.setOnClickListener(null);
    view2131296416 = null;
    view2131296400.setOnClickListener(null);
    view2131296400 = null;
  }
}
