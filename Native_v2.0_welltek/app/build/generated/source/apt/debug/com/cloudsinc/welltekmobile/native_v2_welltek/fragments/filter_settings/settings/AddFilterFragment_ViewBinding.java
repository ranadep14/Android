// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddFilterFragment_ViewBinding implements Unbinder {
  private AddFilterFragment target;

  private View view2131297363;

  private View view2131297359;

  private View view2131296670;

  @UiThread
  public AddFilterFragment_ViewBinding(final AddFilterFragment target, View source) {
    this.target = target;

    View view;
    target.swt_autotime = Utils.findRequiredViewAsType(source, R.id.swt_autotime, "field 'swt_autotime'", Switch.class);
    view = Utils.findRequiredView(source, R.id.txt_clock, "field 'txt_clock' and method 'txt_clock'");
    target.txt_clock = Utils.castView(view, R.id.txt_clock, "field 'txt_clock'", TextView.class);
    view2131297363 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_clock();
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_calender, "field 'txt_calender' and method 'txt_calender'");
    target.txt_calender = Utils.castView(view, R.id.txt_calender, "field 'txt_calender'", TextView.class);
    view2131297359 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_calender();
      }
    });
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.lin_time = Utils.findRequiredViewAsType(source, R.id.lin_time, "field 'lin_time'", LinearLayout.class);
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
    AddFilterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swt_autotime = null;
    target.txt_clock = null;
    target.txt_calender = null;
    target.txt_fragment_title = null;
    target.lin_time = null;

    view2131297363.setOnClickListener(null);
    view2131297363 = null;
    view2131297359.setOnClickListener(null);
    view2131297359 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
