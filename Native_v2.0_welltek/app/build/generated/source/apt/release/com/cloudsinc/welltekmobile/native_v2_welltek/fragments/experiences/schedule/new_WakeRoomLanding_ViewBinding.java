// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class new_WakeRoomLanding_ViewBinding implements Unbinder {
  private new_WakeRoomLanding target;

  @UiThread
  public new_WakeRoomLanding_ViewBinding(new_WakeRoomLanding target, View source) {
    this.target = target;

    target.notext = Utils.findRequiredViewAsType(source, R.id.notext, "field 'notext'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    new_WakeRoomLanding target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.notext = null;
    target.rel_loading = null;
  }
}
