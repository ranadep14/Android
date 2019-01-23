// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceSettingFragment_ViewBinding implements Unbinder {
  private DeviceSettingFragment target;

  private View view2131296670;

  @UiThread
  public DeviceSettingFragment_ViewBinding(final DeviceSettingFragment target, View source) {
    this.target = target;

    View view;
    target.lst_devices_setting = Utils.findRequiredViewAsType(source, R.id.lst_devices_setting, "field 'lst_devices_setting'", ListView.class);
    target.txt_no_device = Utils.findRequiredViewAsType(source, R.id.txt_no_device, "field 'txt_no_device'", TextView.class);
    target.img_add_devices = Utils.findRequiredViewAsType(source, R.id.img_add_devices, "field 'img_add_devices'", ImageView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
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
    target.rel_no_data = Utils.findRequiredViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceSettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lst_devices_setting = null;
    target.txt_no_device = null;
    target.img_add_devices = null;
    target.rel_loading = null;
    target.txt_fragment_title = null;
    target.img_back = null;
    target.rel_no_data = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
