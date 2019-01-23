// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingTimeDate_ViewBinding implements Unbinder {
  private SettingTimeDate target;

  private View view2131296313;

  private View view2131296513;

  private View view2131296670;

  @UiThread
  public SettingTimeDate_ViewBinding(final SettingTimeDate target, View source) {
    this.target = target;

    View view;
    target.txt_timeZone = Utils.findRequiredViewAsType(source, R.id.txt_timeZone, "field 'txt_timeZone'", TextView.class);
    target.swt_24hr = Utils.findRequiredViewAsType(source, R.id.swt_24hr, "field 'swt_24hr'", Switch.class);
    target.swt_auto = Utils.findRequiredViewAsType(source, R.id.swt_auto, "field 'swt_auto'", Switch.class);
    view = Utils.findRequiredView(source, R.id.Timepicker, "field 'showTime' and method 'pickTime'");
    target.showTime = Utils.castView(view, R.id.Timepicker, "field 'showTime'", EditText.class);
    view2131296313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pickTime();
      }
    });
    view = Utils.findRequiredView(source, R.id.datepicker, "field 'showDate' and method 'pickDate'");
    target.showDate = Utils.castView(view, R.id.datepicker, "field 'showDate'", EditText.class);
    view2131296513 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pickDate();
      }
    });
    target.TimeDate = Utils.findRequiredViewAsType(source, R.id.lyt_changeTimeDate, "field 'TimeDate'", LinearLayout.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
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
    SettingTimeDate target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_timeZone = null;
    target.swt_24hr = null;
    target.swt_auto = null;
    target.showTime = null;
    target.showDate = null;
    target.TimeDate = null;
    target.txt_fragment_title = null;

    view2131296313.setOnClickListener(null);
    view2131296313 = null;
    view2131296513.setOnClickListener(null);
    view2131296513 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
