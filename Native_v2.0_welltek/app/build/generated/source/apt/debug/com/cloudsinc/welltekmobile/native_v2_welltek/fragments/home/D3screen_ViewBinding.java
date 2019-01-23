// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class D3screen_ViewBinding implements Unbinder {
  private D3screen target;

  private View view2131296679;

  private View view2131296501;

  @UiThread
  public D3screen_ViewBinding(final D3screen target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_close, "field 'img_close' and method 'close_nav'");
    target.img_close = Utils.castView(view, R.id.img_close, "field 'img_close'", ImageView.class);
    view2131296679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close_nav();
      }
    });
    target.txt_time = Utils.findRequiredViewAsType(source, R.id.txt_time, "field 'txt_time'", TextView.class);
    target.zone_name = Utils.findRequiredViewAsType(source, R.id.zone_name, "field 'zone_name'", TextView.class);
    target.lin_activity_main = Utils.findRequiredViewAsType(source, R.id.lin_activity_main, "field 'lin_activity_main'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.current_d3, "method 'current_d3'");
    view2131296501 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.current_d3();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    D3screen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_close = null;
    target.txt_time = null;
    target.zone_name = null;
    target.lin_activity_main = null;

    view2131296679.setOnClickListener(null);
    view2131296679 = null;
    view2131296501.setOnClickListener(null);
    view2131296501 = null;
  }
}
