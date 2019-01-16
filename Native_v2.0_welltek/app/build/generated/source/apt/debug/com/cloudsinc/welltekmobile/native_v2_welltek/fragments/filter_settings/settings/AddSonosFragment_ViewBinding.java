// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddSonosFragment_ViewBinding implements Unbinder {
  private AddSonosFragment target;

  private View view2131296960;

  private View view2131296670;

  @UiThread
  public AddSonosFragment_ViewBinding(final AddSonosFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lyt_sonosadd, "field 'sonosAdd' and method 'AddRooms'");
    target.sonosAdd = Utils.castView(view, R.id.lyt_sonosadd, "field 'sonosAdd'", LinearLayout.class);
    view2131296960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.AddRooms();
      }
    });
    target.txt_room = Utils.findRequiredViewAsType(source, R.id.txt_room, "field 'txt_room'", TextView.class);
    target.txt_sonos_device_name = Utils.findRequiredViewAsType(source, R.id.txt_sonos_device_name, "field 'txt_sonos_device_name'", EditText.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
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
    AddSonosFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sonosAdd = null;
    target.txt_room = null;
    target.txt_sonos_device_name = null;
    target.txt_save = null;
    target.txt_fragment_title = null;

    view2131296960.setOnClickListener(null);
    view2131296960 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
