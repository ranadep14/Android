// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgetPasswordActivity_ViewBinding implements Unbinder {
  private ForgetPasswordActivity target;

  @UiThread
  public ForgetPasswordActivity_ViewBinding(ForgetPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgetPasswordActivity_ViewBinding(ForgetPasswordActivity target, View source) {
    this.target = target;

    target.valid_recovery_email = Utils.findRequiredViewAsType(source, R.id.valid_recovery_email, "field 'valid_recovery_email'", TextView.class);
    target.valid_user_name = Utils.findRequiredViewAsType(source, R.id.valid_user_name, "field 'valid_user_name'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgetPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.valid_recovery_email = null;
    target.valid_user_name = null;
  }
}
