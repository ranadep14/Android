// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingDevicesScenesList_ViewBinding implements Unbinder {
  private SettingDevicesScenesList target;

  private View view2131296670;

  @UiThread
  public SettingDevicesScenesList_ViewBinding(final SettingDevicesScenesList target, View source) {
    this.target = target;

    View view;
    target.rd_circadian = Utils.findRequiredViewAsType(source, R.id.rd_circadian, "field 'rd_circadian'", CheckBox.class);
    target.rd_energise = Utils.findRequiredViewAsType(source, R.id.rd_energise, "field 'rd_energise'", CheckBox.class);
    target.rd_relax = Utils.findRequiredViewAsType(source, R.id.rd_relax, "field 'rd_relax'", CheckBox.class);
    target.rd_sleep = Utils.findRequiredViewAsType(source, R.id.rd_sleep, "field 'rd_sleep'", CheckBox.class);
    target.rd_dawn = Utils.findRequiredViewAsType(source, R.id.rd_dawn, "field 'rd_dawn'", CheckBox.class);
    target.lin_circadian = Utils.findRequiredViewAsType(source, R.id.lin_circadian, "field 'lin_circadian'", LinearLayout.class);
    target.lin_energize = Utils.findRequiredViewAsType(source, R.id.lin_energize, "field 'lin_energize'", LinearLayout.class);
    target.lin_sleep = Utils.findRequiredViewAsType(source, R.id.lin_sleep, "field 'lin_sleep'", LinearLayout.class);
    target.lin_relax = Utils.findRequiredViewAsType(source, R.id.lin_relax, "field 'lin_relax'", LinearLayout.class);
    target.lin_dawn = Utils.findRequiredViewAsType(source, R.id.lin_dawn, "field 'lin_dawn'", LinearLayout.class);
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    target.rd_cooking = Utils.findRequiredViewAsType(source, R.id.rd_cooking, "field 'rd_cooking'", CheckBox.class);
    target.lin_cooking = Utils.findRequiredViewAsType(source, R.id.lin_cooking, "field 'lin_cooking'", LinearLayout.class);
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
    SettingDevicesScenesList target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rd_circadian = null;
    target.rd_energise = null;
    target.rd_relax = null;
    target.rd_sleep = null;
    target.rd_dawn = null;
    target.lin_circadian = null;
    target.lin_energize = null;
    target.lin_sleep = null;
    target.lin_relax = null;
    target.lin_dawn = null;
    target.txt_room_title = null;
    target.txt_save = null;
    target.rd_cooking = null;
    target.lin_cooking = null;
    target.txt_fragment_title = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
