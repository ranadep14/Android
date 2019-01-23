// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ConnectionLostActivity_ViewBinding implements Unbinder {
  private ConnectionLostActivity target;

  private View view2131296421;

  private View view2131297437;

  @UiThread
  public ConnectionLostActivity_ViewBinding(ConnectionLostActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ConnectionLostActivity_ViewBinding(final ConnectionLostActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_retry, "field 'btn_retry' and method 'btn_retry'");
    target.btn_retry = Utils.castView(view, R.id.btn_retry, "field 'btn_retry'", Button.class);
    view2131296421 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_retry();
      }
    });
    target.txt_msg = Utils.findRequiredViewAsType(source, R.id.txt_msg, "field 'txt_msg'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_logout, "field 'txt_logout' and method 'txt_logout'");
    target.txt_logout = Utils.castView(view, R.id.txt_logout, "field 'txt_logout'", TextView.class);
    view2131297437 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_logout();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ConnectionLostActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btn_retry = null;
    target.txt_msg = null;
    target.rel_loading = null;
    target.txt_logout = null;

    view2131296421.setOnClickListener(null);
    view2131296421 = null;
    view2131297437.setOnClickListener(null);
    view2131297437 = null;
  }
}
