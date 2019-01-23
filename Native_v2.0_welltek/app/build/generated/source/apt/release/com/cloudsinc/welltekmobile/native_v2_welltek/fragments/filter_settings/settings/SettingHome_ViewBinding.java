// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingHome_ViewBinding implements Unbinder {
  private SettingHome target;

  private View view2131296670;

  private View view2131296889;

  private View view2131296838;

  private View view2131296877;

  @UiThread
  public SettingHome_ViewBinding(final SettingHome target, View source) {
    this.target = target;

    View view;
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_back, "field 'img_back' and method 'img_back'");
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
    target.switch_pin_popup = Utils.findRequiredViewAsType(source, R.id.switch_pin_popup, "field 'switch_pin_popup'", Switch.class);
    target.txt_pin_status = Utils.findRequiredViewAsType(source, R.id.txt_pin_status, "field 'txt_pin_status'", TextView.class);
    target.rel_pin_input = Utils.findRequiredViewAsType(source, R.id.rel_pin_input, "field 'rel_pin_input'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.lin_wifi_network, "method 'lin_wifi_network'");
    view2131296889 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_wifi_network();
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_location, "method 'lin_location'");
    view2131296838 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_location();
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_time_date, "method 'lin_time_date'");
    view2131296877 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_time_date();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingHome target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_fragment_title = null;
    target.img_back = null;
    target.switch_pin_popup = null;
    target.txt_pin_status = null;
    target.rel_pin_input = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
    view2131296889.setOnClickListener(null);
    view2131296889 = null;
    view2131296838.setOnClickListener(null);
    view2131296838 = null;
    view2131296877.setOnClickListener(null);
    view2131296877 = null;
  }
}
