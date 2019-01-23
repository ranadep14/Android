// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131297448;

  private View view2131296427;

  private View view2131296908;

  private View view2131297416;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.edt_email = Utils.findRequiredViewAsType(source, R.id.edt_email, "field 'edt_email'", EditText.class);
    target.mProgressView = Utils.findRequiredView(source, R.id.login_progress, "field 'mProgressView'");
    target.rel_invalid_user_pass = Utils.findRequiredViewAsType(source, R.id.rel_invalid_user_pass, "field 'rel_invalid_user_pass'", RelativeLayout.class);
    target.edt_password = Utils.findRequiredViewAsType(source, R.id.edt_password, "field 'edt_password'", EditText.class);
    target.img_login_logo = Utils.findRequiredViewAsType(source, R.id.img_login_logo, "field 'img_login_logo'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.txt_new_account, "field 'txt_new_account'");
    target.txt_new_account = Utils.castView(view, R.id.txt_new_account, "field 'txt_new_account'", TextView.class);
    view2131297448 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_signin(p0);
      }
    });
    target.valid_password = Utils.findRequiredViewAsType(source, R.id.valid_password, "field 'valid_password'", TextView.class);
    target.valid_user_name = Utils.findRequiredViewAsType(source, R.id.valid_user_name, "field 'valid_user_name'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_signin, "field 'btn_signin'");
    target.btn_signin = Utils.castView(view, R.id.btn_signin, "field 'btn_signin'", Button.class);
    view2131296427 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_signin(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_lay, "method 'closeKeyboard'");
    view2131296908 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.closeKeyboard();
      }
    });
    view = source.findViewById(R.id.txt_forget_pass);
    if (view != null) {
      view2131297416 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.btn_signin(p0);
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edt_email = null;
    target.mProgressView = null;
    target.rel_invalid_user_pass = null;
    target.edt_password = null;
    target.img_login_logo = null;
    target.txt_new_account = null;
    target.valid_password = null;
    target.valid_user_name = null;
    target.btn_signin = null;

    view2131297448.setOnClickListener(null);
    view2131297448 = null;
    view2131296427.setOnClickListener(null);
    view2131296427 = null;
    view2131296908.setOnClickListener(null);
    view2131296908 = null;
    if (view2131297416 != null) {
      view2131297416.setOnClickListener(null);
      view2131297416 = null;
    }
  }
}
