// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BoerHubConnection_ViewBinding implements Unbinder {
  private BoerHubConnection target;

  private View view2131296411;

  @UiThread
  public BoerHubConnection_ViewBinding(BoerHubConnection target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BoerHubConnection_ViewBinding(final BoerHubConnection target, View source) {
    this.target = target;

    View view;
    target.spn_hub_msg = Utils.findRequiredViewAsType(source, R.id.spn_hub_msg, "field 'spn_hub_msg'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.btn_hub_connect, "method 'btn_hub_connect'");
    view2131296411 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_hub_connect();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    BoerHubConnection target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spn_hub_msg = null;

    view2131296411.setOnClickListener(null);
    view2131296411 = null;
  }
}
