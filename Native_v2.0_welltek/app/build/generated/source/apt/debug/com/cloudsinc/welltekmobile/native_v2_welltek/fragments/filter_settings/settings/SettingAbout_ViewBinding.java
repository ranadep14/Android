// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingAbout_ViewBinding implements Unbinder {
  private SettingAbout target;

  private View view2131297490;

  private View view2131296670;

  @UiThread
  public SettingAbout_ViewBinding(final SettingAbout target, View source) {
    this.target = target;

    View view;
    target.txt_app_version = Utils.findRequiredViewAsType(source, R.id.txt_app_version, "field 'txt_app_version'", TextView.class);
    target.txt_firm_version = Utils.findRequiredViewAsType(source, R.id.txt_firm_version, "field 'txt_firm_version'", TextView.class);
    target.txt_darwin_id = Utils.findRequiredViewAsType(source, R.id.txt_darwin_id, "field 'txt_darwin_id'", TextView.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.txt_reboot_darwin, "field 'txt_reboot_darwin' and method 'txt_reboot_darwin'");
    target.txt_reboot_darwin = Utils.castView(view, R.id.txt_reboot_darwin, "field 'txt_reboot_darwin'", TextView.class);
    view2131297490 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_reboot_darwin();
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
    SettingAbout target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_app_version = null;
    target.txt_firm_version = null;
    target.txt_darwin_id = null;
    target.txt_save = null;
    target.txt_fragment_title = null;
    target.txt_reboot_darwin = null;

    view2131297490.setOnClickListener(null);
    view2131297490 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
