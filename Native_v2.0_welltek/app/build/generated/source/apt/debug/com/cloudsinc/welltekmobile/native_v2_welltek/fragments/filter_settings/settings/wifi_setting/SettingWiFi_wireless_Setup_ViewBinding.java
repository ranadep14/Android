// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingWiFi_wireless_Setup_ViewBinding implements Unbinder {
  private SettingWiFi_wireless_Setup target;

  private View view2131296722;

  private View view2131297506;

  private View view2131296670;

  private View view2131297491;

  private View view2131297513;

  @UiThread
  public SettingWiFi_wireless_Setup_ViewBinding(final SettingWiFi_wireless_Setup target,
      View source) {
    this.target = target;

    View view;
    target.edt_passwrd = Utils.findRequiredViewAsType(source, R.id.edt_passwrd, "field 'edt_passwrd'", EditText.class);
    target.spn_ssid = Utils.findRequiredViewAsType(source, R.id.spn_ssid, "field 'spn_ssid'", Spinner.class);
    target.spn_ip_type = Utils.findRequiredViewAsType(source, R.id.spn_ip_type, "field 'spn_ip_type'", Spinner.class);
    target.validepass = Utils.findRequiredViewAsType(source, R.id.validepass, "field 'validepass'", TextView.class);
    target.lin_ssid_password = Utils.findRequiredViewAsType(source, R.id.lin_ssid_password, "field 'lin_ssid_password'", LinearLayout.class);
    target.lin_prog = Utils.findRequiredViewAsType(source, R.id.lin_prog, "field 'lin_prog'", LinearLayout.class);
    target.lin_ip_visiblity = Utils.findRequiredViewAsType(source, R.id.lin_ip_visiblity, "field 'lin_ip_visiblity'", LinearLayout.class);
    target.lin_main = Utils.findRequiredViewAsType(source, R.id.lin_main, "field 'lin_main'", LinearLayout.class);
    target.lin_wifi_config = Utils.findRequiredViewAsType(source, R.id.lin_wifi_config, "field 'lin_wifi_config'", LinearLayout.class);
    target.edt_ip_address = Utils.findRequiredViewAsType(source, R.id.edt_ip_address, "field 'edt_ip_address'", EditText.class);
    target.edt_gate_way = Utils.findRequiredViewAsType(source, R.id.edt_gate_way, "field 'edt_gate_way'", EditText.class);
    target.edt_subnet = Utils.findRequiredViewAsType(source, R.id.edt_subnet, "field 'edt_subnet'", EditText.class);
    target.edt_dns = Utils.findRequiredViewAsType(source, R.id.edt_dns, "field 'edt_dns'", EditText.class);
    view = Utils.findRequiredView(source, R.id.img_refresh, "field 'img_refresh' and method 'img_refresh'");
    target.img_refresh = Utils.castView(view, R.id.img_refresh, "field 'img_refresh'", ImageView.class);
    view2131296722 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_refresh();
      }
    });
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.txt_save, "field 'txt_save' and method 'txt_connect'");
    target.txt_save = Utils.castView(view, R.id.txt_save, "field 'txt_save'", TextView.class);
    view2131297506 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_connect();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_reconfig, "method 'txt_wifi_connect'");
    view2131297491 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_wifi_connect();
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_show_pass, "method 'txt_show_pass'");
    view2131297513 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_show_pass();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingWiFi_wireless_Setup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edt_passwrd = null;
    target.spn_ssid = null;
    target.spn_ip_type = null;
    target.validepass = null;
    target.lin_ssid_password = null;
    target.lin_prog = null;
    target.lin_ip_visiblity = null;
    target.lin_main = null;
    target.lin_wifi_config = null;
    target.edt_ip_address = null;
    target.edt_gate_way = null;
    target.edt_subnet = null;
    target.edt_dns = null;
    target.img_refresh = null;
    target.txt_fragment_title = null;
    target.txt_save = null;

    view2131296722.setOnClickListener(null);
    view2131296722 = null;
    view2131297506.setOnClickListener(null);
    view2131297506 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
    view2131297491.setOnClickListener(null);
    view2131297491 = null;
    view2131297513.setOnClickListener(null);
    view2131297513 = null;
  }
}
