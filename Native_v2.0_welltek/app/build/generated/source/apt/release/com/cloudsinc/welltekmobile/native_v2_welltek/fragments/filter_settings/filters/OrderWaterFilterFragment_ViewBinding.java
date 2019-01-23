// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.filters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderWaterFilterFragment_ViewBinding implements Unbinder {
  private OrderWaterFilterFragment target;

  @UiThread
  public OrderWaterFilterFragment_ViewBinding(OrderWaterFilterFragment target, View source) {
    this.target = target;

    target.web_order_filter = Utils.findRequiredViewAsType(source, R.id.web_order_filter, "field 'web_order_filter'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderWaterFilterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.web_order_filter = null;
  }
}
