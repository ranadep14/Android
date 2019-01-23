// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DevicesByType_ViewBinding implements Unbinder {
  private DevicesByType target;

  private View view2131296670;

  @UiThread
  public DevicesByType_ViewBinding(final DevicesByType target, View source) {
    this.target = target;

    View view;
    target.recycler_view = Utils.findRequiredViewAsType(source, R.id.recycler_view, "field 'recycler_view'", RecyclerView.class);
    target.group_recycler_view = Utils.findRequiredViewAsType(source, R.id.group_recycler_view, "field 'group_recycler_view'", RecyclerView.class);
    target.txt_no_devices = Utils.findRequiredViewAsType(source, R.id.txt_no_devices, "field 'txt_no_devices'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.txt_devices_type = Utils.findRequiredViewAsType(source, R.id.txt_devices_type, "field 'txt_devices_type'", TextView.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
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
    DevicesByType target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycler_view = null;
    target.group_recycler_view = null;
    target.txt_no_devices = null;
    target.rel_loading = null;
    target.txt_devices_type = null;
    target.txt_fragment_title = null;
    target.img_back = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
