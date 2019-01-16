// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileSettingFragment_ViewBinding implements Unbinder {
  private ProfileSettingFragment target;

  private View view2131296564;

  private View view2131296670;

  private View view2131297509;

  private View view2131297473;

  @UiThread
  public ProfileSettingFragment_ViewBinding(final ProfileSettingFragment target, View source) {
    this.target = target;

    View view;
    target.txt_username = Utils.findRequiredViewAsType(source, R.id.txt_username, "field 'txt_username'", TextView.class);
    target.txt_email = Utils.findRequiredViewAsType(source, R.id.txt_email, "field 'txt_email'", TextView.class);
    target.txt_password = Utils.findRequiredViewAsType(source, R.id.txt_password, "field 'txt_password'", TextView.class);
    target.txt_userprofilename = Utils.findRequiredViewAsType(source, R.id.txt_user_name, "field 'txt_userprofilename'", TextView.class);
    target.validenmail = Utils.findRequiredViewAsType(source, R.id.validenmail, "field 'validenmail'", TextView.class);
    target.validname = Utils.findRequiredViewAsType(source, R.id.validname, "field 'validname'", TextView.class);
    target.edt_email = Utils.findRequiredViewAsType(source, R.id.edt_email, "field 'edt_email'", EditText.class);
    target.profile_image = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profile_image'", CircleImageView.class);
    view = Utils.findRequiredView(source, R.id.edt_profile, "field 'edt_profile' and method 'edt_profile'");
    target.edt_profile = Utils.castView(view, R.id.edt_profile, "field 'edt_profile'", ImageView.class);
    view2131296564 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.edt_profile();
      }
    });
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.edt_first_name = Utils.findRequiredViewAsType(source, R.id.edt_first_name, "field 'edt_first_name'", EditText.class);
    view = Utils.findRequiredView(source, R.id.img_back, "field 'img_back' and method 'img_back'");
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_send_invite, "field 'txt_send_invite' and method 'txt_send_invite'");
    target.txt_send_invite = Utils.castView(view, R.id.txt_send_invite, "field 'txt_send_invite'", TextView.class);
    view2131297509 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_send_invite();
      }
    });
    target.lin_user_profile = Utils.findRequiredViewAsType(source, R.id.lin_user_profile, "field 'lin_user_profile'", LinearLayout.class);
    target.lin_no_user_profile = Utils.findRequiredViewAsType(source, R.id.lin_no_user_profile, "field 'lin_no_user_profile'", RelativeLayout.class);
    target.txt_recovery_email = Utils.findRequiredViewAsType(source, R.id.txt_recovery_email, "field 'txt_recovery_email'", TextView.class);
    view = Utils.findRequiredView(source, R.id.txt_pawd_change, "method 'txt_pawd_change'");
    view2131297473 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_pawd_change();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileSettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_username = null;
    target.txt_email = null;
    target.txt_password = null;
    target.txt_userprofilename = null;
    target.validenmail = null;
    target.validname = null;
    target.edt_email = null;
    target.profile_image = null;
    target.edt_profile = null;
    target.rel_loading = null;
    target.txt_fragment_title = null;
    target.edt_first_name = null;
    target.img_back = null;
    target.txt_send_invite = null;
    target.lin_user_profile = null;
    target.lin_no_user_profile = null;
    target.txt_recovery_email = null;

    view2131296564.setOnClickListener(null);
    view2131296564 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
    view2131297509.setOnClickListener(null);
    view2131297509 = null;
    view2131297473.setOnClickListener(null);
    view2131297473 = null;
  }
}
