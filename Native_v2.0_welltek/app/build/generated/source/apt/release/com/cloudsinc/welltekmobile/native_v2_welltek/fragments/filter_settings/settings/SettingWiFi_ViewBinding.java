// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingWiFi_ViewBinding implements Unbinder {
  private SettingWiFi target;

  private View view2131296890;

  private View view2131296819;

  private View view2131296814;

  private View view2131296420;

  private View view2131296670;

  @UiThread
  public SettingWiFi_ViewBinding(final SettingWiFi target, View source) {
    this.target = target;

    View view;
    target.rel_cloud_status_red = Utils.findRequiredViewAsType(source, R.id.rel_cloud_status_red, "field 'rel_cloud_status_red'", RelativeLayout.class);
    target.rel_cloud_status_green = Utils.findRequiredViewAsType(source, R.id.rel_cloud_status_green, "field 'rel_cloud_status_green'", RelativeLayout.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.rel_int_status_red = Utils.findRequiredViewAsType(source, R.id.rel_int_status_red, "field 'rel_int_status_red'", RelativeLayout.class);
    target.rel_int_status_green = Utils.findRequiredViewAsType(source, R.id.rel_int_status_green, "field 'rel_int_status_green'", RelativeLayout.class);
    target.txt_wireless_setup = Utils.findRequiredViewAsType(source, R.id.txt_wireless_setup, "field 'txt_wireless_setup'", TextView.class);
    target.txt_ethernet_setup = Utils.findRequiredViewAsType(source, R.id.txt_ethernet_setup, "field 'txt_ethernet_setup'", TextView.class);
    target.img_wireless_enter = Utils.findRequiredViewAsType(source, R.id.img_wireless_enter, "field 'img_wireless_enter'", ImageView.class);
    target.img_ethernet_enter = Utils.findRequiredViewAsType(source, R.id.img_ethernet_enter, "field 'img_ethernet_enter'", ImageView.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lin_wireless_setup, "field 'lin_wireless_setup' and method 'lin_wireless_setup'");
    target.lin_wireless_setup = Utils.castView(view, R.id.lin_wireless_setup, "field 'lin_wireless_setup'", LinearLayout.class);
    view2131296890 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_wireless_setup();
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_eth_setup, "field 'lin_eth_setup' and method 'lin_eth_setup'");
    target.lin_eth_setup = Utils.castView(view, R.id.lin_eth_setup, "field 'lin_eth_setup'", LinearLayout.class);
    view2131296819 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_eth_setup();
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_email_setup, "method 'lin_email_setup'");
    view2131296814 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_email_setup();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_reset, "method 'onReset'");
    view2131296420 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onReset();
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
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingWiFi target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rel_cloud_status_red = null;
    target.rel_cloud_status_green = null;
    target.txt_fragment_title = null;
    target.rel_int_status_red = null;
    target.rel_int_status_green = null;
    target.txt_wireless_setup = null;
    target.txt_ethernet_setup = null;
    target.img_wireless_enter = null;
    target.img_ethernet_enter = null;
    target.txt_save = null;
    target.lin_wireless_setup = null;
    target.lin_eth_setup = null;

    view2131296890.setOnClickListener(null);
    view2131296890 = null;
    view2131296819.setOnClickListener(null);
    view2131296819 = null;
    view2131296814.setOnClickListener(null);
    view2131296814 = null;
    view2131296420.setOnClickListener(null);
    view2131296420 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
