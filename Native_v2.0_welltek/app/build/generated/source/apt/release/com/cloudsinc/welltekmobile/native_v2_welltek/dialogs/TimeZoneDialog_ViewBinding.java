// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TimeZoneDialog_ViewBinding implements Unbinder {
  private TimeZoneDialog target;

  private View view2131296417;

  @UiThread
  public TimeZoneDialog_ViewBinding(final TimeZoneDialog target, View source) {
    this.target = target;

    View view;
    target.txt_msg = Utils.findRequiredViewAsType(source, R.id.txt_msg, "field 'txt_msg'", TextView.class);
    target.rel_main = Utils.findRequiredViewAsType(source, R.id.rel_main, "field 'rel_main'", RelativeLayout.class);
    view = source.findViewById(R.id.btn_ok);
    if (view != null) {
      view2131296417 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.btn_cancel();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    TimeZoneDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_msg = null;
    target.rel_main = null;

    if (view2131296417 != null) {
      view2131296417.setOnClickListener(null);
      view2131296417 = null;
    }
  }
}
