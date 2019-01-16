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

public class SettingLocation_ViewBinding implements Unbinder {
  private SettingLocation target;

  private View view2131296670;

  @UiThread
  public SettingLocation_ViewBinding(final SettingLocation target, View source) {
    this.target = target;

    View view;
    target.txtcountry = Utils.findRequiredViewAsType(source, R.id.txtcountry, "field 'txtcountry'", TextView.class);
    target.txtstate = Utils.findRequiredViewAsType(source, R.id.txtstate, "field 'txtstate'", TextView.class);
    target.txtcity = Utils.findRequiredViewAsType(source, R.id.txtcity, "field 'txtcity'", TextView.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
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
    SettingLocation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtcountry = null;
    target.txtstate = null;
    target.txtcity = null;
    target.txt_save = null;
    target.txt_fragment_title = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
