// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DawnRunningDialog_ViewBinding implements Unbinder {
  private DawnRunningDialog target;

  private View view2131296679;

  private View view2131296406;

  @UiThread
  public DawnRunningDialog_ViewBinding(final DawnRunningDialog target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_close, "method 'img_close'");
    view2131296679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_close();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm, "method 'btn_confirm'");
    view2131296406 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_confirm();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131296679.setOnClickListener(null);
    view2131296679 = null;
    view2131296406.setOnClickListener(null);
    view2131296406 = null;
  }
}
