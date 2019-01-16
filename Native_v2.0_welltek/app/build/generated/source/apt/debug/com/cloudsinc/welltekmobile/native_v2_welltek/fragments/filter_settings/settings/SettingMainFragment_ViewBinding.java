// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingMainFragment_ViewBinding implements Unbinder {
  private SettingMainFragment target;

  private View view2131296670;

  @UiThread
  public SettingMainFragment_ViewBinding(final SettingMainFragment target, View source) {
    this.target = target;

    View view;
    target.txt_user_name = Utils.findRequiredViewAsType(source, R.id.txt_user_name, "field 'txt_user_name'", TextView.class);
    target.profile_image = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profile_image'", CircleImageView.class);
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
    SettingMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_user_name = null;
    target.profile_image = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
