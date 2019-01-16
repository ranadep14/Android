// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

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

public class ChangePassword_ViewBinding implements Unbinder {
  private ChangePassword target;

  private View view2131296670;

  @UiThread
  public ChangePassword_ViewBinding(final ChangePassword target, View source) {
    this.target = target;

    View view;
    target.edt_crm_pass = Utils.findRequiredViewAsType(source, R.id.edt_crm_pass, "field 'edt_crm_pass'", EditText.class);
    target.vpwd = Utils.findRequiredViewAsType(source, R.id.validpassword, "field 'vpwd'", TextView.class);
    target.repwd = Utils.findRequiredViewAsType(source, R.id.validRepeatpwd, "field 'repwd'", TextView.class);
    target.validoldpassword = Utils.findRequiredViewAsType(source, R.id.validoldpassword, "field 'validoldpassword'", TextView.class);
    target.edt_old_pass = Utils.findRequiredViewAsType(source, R.id.edt_old_pass, "field 'edt_old_pass'", EditText.class);
    target.edt_new_pass = Utils.findRequiredViewAsType(source, R.id.edt_new_pass, "field 'edt_new_pass'", EditText.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangePassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edt_crm_pass = null;
    target.vpwd = null;
    target.repwd = null;
    target.validoldpassword = null;
    target.edt_old_pass = null;
    target.edt_new_pass = null;
    target.txt_fragment_title = null;
    target.txt_save = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
