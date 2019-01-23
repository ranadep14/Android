// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomsMainFragmentPortrait_ViewBinding implements Unbinder {
  private RoomsMainFragmentPortrait target;

  @UiThread
  public RoomsMainFragmentPortrait_ViewBinding(RoomsMainFragmentPortrait target, View source) {
    this.target = target;

    target.rel_no_data = Utils.findOptionalViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
    target.txt_no_rooms_available = Utils.findOptionalViewAsType(source, R.id.txt_no_rooms_available, "field 'txt_no_rooms_available'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.elements_room_list = Utils.findOptionalViewAsType(source, R.id.elements_room_list, "field 'elements_room_list'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomsMainFragmentPortrait target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rel_no_data = null;
    target.txt_no_rooms_available = null;
    target.rel_loading = null;
    target.elements_room_list = null;
  }
}
