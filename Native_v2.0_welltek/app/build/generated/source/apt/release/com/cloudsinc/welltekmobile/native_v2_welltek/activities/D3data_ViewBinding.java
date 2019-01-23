// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class D3data_ViewBinding implements Unbinder {
  private D3data target;

  private View view2131296484;

  @UiThread
  public D3data_ViewBinding(final D3data target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.close_nav, "method 'close_nav'");
    view2131296484 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close_nav();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131296484.setOnClickListener(null);
    view2131296484 = null;
  }
}
