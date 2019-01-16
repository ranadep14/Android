// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.device_settings;

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

public class DeviceSettingSelectRoom_ViewBinding implements Unbinder {
  private DeviceSettingSelectRoom target;

  private View view2131296670;

  @UiThread
  public DeviceSettingSelectRoom_ViewBinding(final DeviceSettingSelectRoom target, View source) {
    this.target = target;

    View view;
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
    DeviceSettingSelectRoom target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_fragment_title = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
