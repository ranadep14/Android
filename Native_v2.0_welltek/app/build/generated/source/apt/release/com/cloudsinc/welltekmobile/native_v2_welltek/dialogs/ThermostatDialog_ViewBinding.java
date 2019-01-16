// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ThermostatDialog_ViewBinding implements Unbinder {
  private ThermostatDialog target;

  private View view2131296679;

  private View view2131296406;

  @UiThread
  public ThermostatDialog_ViewBinding(final ThermostatDialog target, View source) {
    this.target = target;

    View view;
    target.txt_thermostat_title = Utils.findRequiredViewAsType(source, R.id.txt_thermostat_title, "field 'txt_thermostat_title'", TextView.class);
    target.txt_thermostat_desc = Utils.findRequiredViewAsType(source, R.id.txt_thermostat_desc, "field 'txt_thermostat_desc'", TextView.class);
    target.txt_sub_desc = Utils.findRequiredViewAsType(source, R.id.txt_sub_desc, "field 'txt_sub_desc'", TextView.class);
    target.lin_main = Utils.findRequiredViewAsType(source, R.id.lin_main, "field 'lin_main'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.img_close, "method 'img_close'");
    view2131296679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_close();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm, "method 'btn_confirm'");
    view2131296406 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_confirm();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ThermostatDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_thermostat_title = null;
    target.txt_thermostat_desc = null;
    target.txt_sub_desc = null;
    target.lin_main = null;

    view2131296679.setOnClickListener(null);
    view2131296679 = null;
    view2131296406.setOnClickListener(null);
    view2131296406 = null;
  }
}
