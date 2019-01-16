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

public class DawnSimulationPopup_ViewBinding implements Unbinder {
  private DawnSimulationPopup target;

  private View view2131296392;

  private View view2131296391;

  private View view2131296390;

  private View view2131296417;

  private View view2131296400;

  @UiThread
  public DawnSimulationPopup_ViewBinding(DawnSimulationPopup target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DawnSimulationPopup_ViewBinding(final DawnSimulationPopup target, View source) {
    this.target = target;

    View view;
    target.txt_dawn_timer = Utils.findRequiredViewAsType(source, R.id.txt_dawn_timer, "field 'txt_dawn_timer'", TextView.class);
    target.img_gif_loading = Utils.findRequiredViewAsType(source, R.id.img_gif_loading, "field 'img_gif_loading'", RelativeLayout.class);
    target.lyt_no_data = Utils.findRequiredViewAsType(source, R.id.lyt_no_data, "field 'lyt_no_data'", LinearLayout.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progress_bar'", ProgressBar.class);
    target.lyt_data = Utils.findRequiredViewAsType(source, R.id.lyt_data, "field 'lyt_data'", LinearLayout.class);
    target.no_text = Utils.findRequiredViewAsType(source, R.id.no_text, "field 'no_text'", TextView.class);
    target.txt_room_name = Utils.findRequiredViewAsType(source, R.id.txt_room_name, "field 'txt_room_name'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_Snooze, "field 'btn_Snooze' and method 'btn_Snooze'");
    target.btn_Snooze = Utils.castView(view, R.id.btn_Snooze, "field 'btn_Snooze'", Button.class);
    view2131296392 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_Snooze();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_Deactivate, "field 'btn_Deactivate' and method 'btn_Deactivate'");
    target.btn_Deactivate = Utils.castView(view, R.id.btn_Deactivate, "field 'btn_Deactivate'", Button.class);
    view2131296391 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_Deactivate();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_Awake, "field 'btn_Awake' and method 'btn_Awake'");
    target.btn_Awake = Utils.castView(view, R.id.btn_Awake, "field 'btn_Awake'", Button.class);
    view2131296390 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_Awake();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ok, "method 'btn_ok'");
    view2131296417 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_ok();
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
    DawnSimulationPopup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_dawn_timer = null;
    target.img_gif_loading = null;
    target.lyt_no_data = null;
    target.progress_bar = null;
    target.lyt_data = null;
    target.no_text = null;
    target.txt_room_name = null;
    target.btn_Snooze = null;
    target.btn_Deactivate = null;
    target.btn_Awake = null;

    view2131296392.setOnClickListener(null);
    view2131296392 = null;
    view2131296391.setOnClickListener(null);
    view2131296391 = null;
    view2131296390.setOnClickListener(null);
    view2131296390 = null;
    view2131296417.setOnClickListener(null);
    view2131296417 = null;
    view2131296400.setOnClickListener(null);
    view2131296400 = null;
  }
}
