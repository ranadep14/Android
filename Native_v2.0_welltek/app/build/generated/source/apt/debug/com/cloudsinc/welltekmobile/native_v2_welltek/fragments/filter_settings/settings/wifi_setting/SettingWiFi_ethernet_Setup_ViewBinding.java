// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingWiFi_ethernet_Setup_ViewBinding implements Unbinder {
  private SettingWiFi_ethernet_Setup target;

  private View view2131297506;

  private View view2131296670;

  @UiThread
  public SettingWiFi_ethernet_Setup_ViewBinding(final SettingWiFi_ethernet_Setup target,
      View source) {
    this.target = target;

    View view;
    target.lin_prog = Utils.findRequiredViewAsType(source, R.id.lin_prog, "field 'lin_prog'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_save, "field 'txt_save' and method 'txt_save'");
    target.txt_save = Utils.castView(view, R.id.txt_save, "field 'txt_save'", TextView.class);
    view2131297506 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_save();
      }
    });
    target.edt_ip_address = Utils.findRequiredViewAsType(source, R.id.edt_ip_address, "field 'edt_ip_address'", EditText.class);
    target.spn_ip_type = Utils.findRequiredViewAsType(source, R.id.spn_ip_type, "field 'spn_ip_type'", Spinner.class);
    target.edt_gate_way = Utils.findRequiredViewAsType(source, R.id.edt_gate_way, "field 'edt_gate_way'", EditText.class);
    target.edt_subnet = Utils.findRequiredViewAsType(source, R.id.edt_subnet, "field 'edt_subnet'", EditText.class);
    target.edt_dns = Utils.findRequiredViewAsType(source, R.id.edt_dns, "field 'edt_dns'", EditText.class);
    target.lin_main = Utils.findRequiredViewAsType(source, R.id.lin_main, "field 'lin_main'", LinearLayout.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.lin_mode_visiblity = Utils.findRequiredViewAsType(source, R.id.lin_mode_visiblity, "field 'lin_mode_visiblity'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingWiFi_ethernet_Setup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lin_prog = null;
    target.txt_save = null;
    target.edt_ip_address = null;
    target.spn_ip_type = null;
    target.edt_gate_way = null;
    target.edt_subnet = null;
    target.edt_dns = null;
    target.lin_main = null;
    target.txt_fragment_title = null;
    target.lin_mode_visiblity = null;

    view2131297506.setOnClickListener(null);
    view2131297506 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
