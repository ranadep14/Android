// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HubConnection_ViewBinding implements Unbinder {
  private HubConnection target;

  private View view2131296411;

  private View view2131296414;

  @UiThread
  public HubConnection_ViewBinding(HubConnection target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HubConnection_ViewBinding(final HubConnection target, View source) {
    this.target = target;

    View view;
    target.spn_hub_msg = Utils.findRequiredViewAsType(source, R.id.spn_hub_msg, "field 'spn_hub_msg'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.btn_hub_connect, "field 'btn_hub_connect' and method 'btn_hub_connect'");
    target.btn_hub_connect = Utils.castView(view, R.id.btn_hub_connect, "field 'btn_hub_connect'", Button.class);
    view2131296411 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_hub_connect();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login, "method 'btn_login'");
    view2131296414 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_login();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HubConnection target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spn_hub_msg = null;
    target.btn_hub_connect = null;

    view2131296411.setOnClickListener(null);
    view2131296411 = null;
    view2131296414.setOnClickListener(null);
    view2131296414 = null;
  }
}
