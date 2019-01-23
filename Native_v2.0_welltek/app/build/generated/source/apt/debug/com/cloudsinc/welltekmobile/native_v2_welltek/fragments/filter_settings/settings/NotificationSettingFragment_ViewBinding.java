// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationSettingFragment_ViewBinding implements Unbinder {
  private NotificationSettingFragment target;

  private View view2131296670;

  @UiThread
  public NotificationSettingFragment_ViewBinding(final NotificationSettingFragment target,
      View source) {
    this.target = target;

    View view;
    target.txt_no_data = Utils.findRequiredViewAsType(source, R.id.txt_no_data, "field 'txt_no_data'", TextView.class);
    target.lin_air_filter = Utils.findRequiredViewAsType(source, R.id.lin_air_filter, "field 'lin_air_filter'", LinearLayout.class);
    target.lin_water_filter = Utils.findRequiredViewAsType(source, R.id.lin_water_filter, "field 'lin_water_filter'", LinearLayout.class);
    target.swt_water_allow = Utils.findRequiredViewAsType(source, R.id.swt_water_allow, "field 'swt_water_allow'", Switch.class);
    target.swt_air_allow = Utils.findRequiredViewAsType(source, R.id.swt_air_allow, "field 'swt_air_allow'", Switch.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.img_back, "field 'img_back' and method 'img_back'");
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
    NotificationSettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_no_data = null;
    target.lin_air_filter = null;
    target.lin_water_filter = null;
    target.swt_water_allow = null;
    target.swt_air_allow = null;
    target.txt_fragment_title = null;
    target.rel_loading = null;
    target.img_back = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
