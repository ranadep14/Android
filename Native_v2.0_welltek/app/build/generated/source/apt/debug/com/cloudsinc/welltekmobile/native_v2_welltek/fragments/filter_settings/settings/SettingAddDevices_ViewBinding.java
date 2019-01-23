// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

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

public class SettingAddDevices_ViewBinding implements Unbinder {
  private SettingAddDevices target;

  private View view2131296960;

  private View view2131296959;

  private View view2131296670;

  @UiThread
  public SettingAddDevices_ViewBinding(final SettingAddDevices target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lyt_sonosadd, "field 'sonosAdd' and method 'AddSonos'");
    target.sonosAdd = Utils.castView(view, R.id.lyt_sonosadd, "field 'sonosAdd'", LinearLayout.class);
    view2131296960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.AddSonos();
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_smartfilter, "field 'filter' and method 'AddFilter'");
    target.filter = Utils.castView(view, R.id.lyt_smartfilter, "field 'filter'", LinearLayout.class);
    view2131296959 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.AddFilter();
      }
    });
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
    SettingAddDevices target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sonosAdd = null;
    target.filter = null;
    target.txt_fragment_title = null;

    view2131296960.setOnClickListener(null);
    view2131296960 = null;
    view2131296959.setOnClickListener(null);
    view2131296959 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
