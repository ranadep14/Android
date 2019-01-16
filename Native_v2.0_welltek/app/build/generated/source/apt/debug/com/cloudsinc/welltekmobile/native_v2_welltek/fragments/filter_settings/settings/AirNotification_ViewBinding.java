// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AirNotification_ViewBinding implements Unbinder {
  private AirNotification target;

  private View view2131296670;

  @UiThread
  public AirNotification_ViewBinding(final AirNotification target, View source) {
    this.target = target;

    View view;
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    target.spn_notifer = Utils.findRequiredViewAsType(source, R.id.spn_notifer, "field 'spn_notifer'", Spinner.class);
    target.edt_replace_air = Utils.findRequiredViewAsType(source, R.id.edt_replace_air, "field 'edt_replace_air'", TextView.class);
    target.txt_zone = Utils.findRequiredViewAsType(source, R.id.txt_zone, "field 'txt_zone'", TextView.class);
    target.lin_prog = Utils.findRequiredViewAsType(source, R.id.lin_prog, "field 'lin_prog'", LinearLayout.class);
    target.lin_main = Utils.findRequiredViewAsType(source, R.id.lin_main, "field 'lin_main'", LinearLayout.class);
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
    AirNotification target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_save = null;
    target.spn_notifer = null;
    target.edt_replace_air = null;
    target.txt_zone = null;
    target.lin_prog = null;
    target.lin_main = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
