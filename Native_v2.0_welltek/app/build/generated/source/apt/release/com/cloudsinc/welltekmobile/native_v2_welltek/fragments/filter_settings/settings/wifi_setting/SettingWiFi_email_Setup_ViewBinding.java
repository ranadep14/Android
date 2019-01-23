// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.wifi_setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingWiFi_email_Setup_ViewBinding implements Unbinder {
  private SettingWiFi_email_Setup target;

  private View view2131297499;

  private View view2131296670;

  @UiThread
  public SettingWiFi_email_Setup_ViewBinding(final SettingWiFi_email_Setup target, View source) {
    this.target = target;

    View view;
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.edt_email = Utils.findRequiredViewAsType(source, R.id.edt_email, "field 'edt_email'", EditText.class);
    view = Utils.findRequiredView(source, R.id.txt_save, "field 'txt_save' and method 'txt_save_email'");
    target.txt_save = Utils.castView(view, R.id.txt_save, "field 'txt_save'", TextView.class);
    view2131297499 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_save_email();
      }
    });
    target.validenmail = Utils.findRequiredViewAsType(source, R.id.validenmail, "field 'validenmail'", TextView.class);
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
    SettingWiFi_email_Setup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_fragment_title = null;
    target.edt_email = null;
    target.txt_save = null;
    target.validenmail = null;

    view2131297499.setOnClickListener(null);
    view2131297499 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
