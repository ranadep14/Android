// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUp_ViewBinding implements Unbinder {
  private SignUp target;

  private View view2131296425;

  private View view2131296414;

  @UiThread
  public SignUp_ViewBinding(SignUp target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUp_ViewBinding(final SignUp target, View source) {
    this.target = target;

    View view;
    target.vuserid = Utils.findRequiredViewAsType(source, R.id.validenmail, "field 'vuserid'", TextView.class);
    target.vpwd = Utils.findRequiredViewAsType(source, R.id.validpassword, "field 'vpwd'", TextView.class);
    target.repwd = Utils.findRequiredViewAsType(source, R.id.validRepeatpwd, "field 'repwd'", TextView.class);
    target.validfirtsname = Utils.findRequiredViewAsType(source, R.id.validfirtsname, "field 'validfirtsname'", TextView.class);
    target.validlastname = Utils.findRequiredViewAsType(source, R.id.validlastname, "field 'validlastname'", TextView.class);
    target.validserialid = Utils.findRequiredViewAsType(source, R.id.validserialid, "field 'validserialid'", TextView.class);
    target.userid = Utils.findRequiredViewAsType(source, R.id.edt_email, "field 'userid'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.edt_password, "field 'password'", EditText.class);
    target.re_pwd = Utils.findRequiredViewAsType(source, R.id.edt_Repeat_password, "field 're_pwd'", EditText.class);
    target.edt_last_name = Utils.findRequiredViewAsType(source, R.id.edt_last_name, "field 'edt_last_name'", EditText.class);
    target.edt_first_name = Utils.findRequiredViewAsType(source, R.id.edt_first_name, "field 'edt_first_name'", EditText.class);
    target.edt_serial_id = Utils.findRequiredViewAsType(source, R.id.edt_serial_id, "field 'edt_serial_id'", EditText.class);
    target.edt_recovery_email = Utils.findRequiredViewAsType(source, R.id.edt_recovery_email, "field 'edt_recovery_email'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_signUp, "method 'btnSignUp'");
    view2131296425 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnSignUp();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login, "method 'btnLogin'");
    view2131296414 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btnLogin();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUp target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.vuserid = null;
    target.vpwd = null;
    target.repwd = null;
    target.validfirtsname = null;
    target.validlastname = null;
    target.validserialid = null;
    target.userid = null;
    target.password = null;
    target.re_pwd = null;
    target.edt_last_name = null;
    target.edt_first_name = null;
    target.edt_serial_id = null;
    target.edt_recovery_email = null;

    view2131296425.setOnClickListener(null);
    view2131296425 = null;
    view2131296414.setOnClickListener(null);
    view2131296414 = null;
  }
}
