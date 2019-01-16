// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomsMainFragment_ViewBinding implements Unbinder {
  private RoomsMainFragment target;

  private View view2131297150;

  @UiThread
  public RoomsMainFragment_ViewBinding(final RoomsMainFragment target, View source) {
    this.target = target;

    View view;
    target.lin_room_light = Utils.findRequiredViewAsType(source, R.id.lin_room_lights, "field 'lin_room_light'", LinearLayout.class);
    target.lin_room_blinds = Utils.findRequiredViewAsType(source, R.id.lin_room_blinds, "field 'lin_room_blinds'", LinearLayout.class);
    target.lin_room_audio = Utils.findRequiredViewAsType(source, R.id.lin_room_audio, "field 'lin_room_audio'", LinearLayout.class);
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.txt_room_type = Utils.findRequiredViewAsType(source, R.id.txt_room_type, "field 'txt_room_type'", TextView.class);
    target.txt_room_id = Utils.findRequiredViewAsType(source, R.id.txt_id, "field 'txt_room_id'", TextView.class);
    target.rel_no_data = Utils.findRequiredViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
    target.txt_no_rooms_available = Utils.findRequiredViewAsType(source, R.id.txt_no_rooms_available, "field 'txt_no_rooms_available'", TextView.class);
    target.lin_single_room = Utils.findRequiredViewAsType(source, R.id.lin_single_room, "field 'lin_single_room'", LinearLayout.class);
    target.grid_room = Utils.findRequiredViewAsType(source, R.id.grid_room, "field 'grid_room'", GridView.class);
    target.img_single_room_back = Utils.findRequiredViewAsType(source, R.id.img_single_room_back, "field 'img_single_room_back'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.rel_single_room, "method 'rel_single_room'");
    view2131297150 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.rel_single_room(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomsMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lin_room_light = null;
    target.lin_room_blinds = null;
    target.lin_room_audio = null;
    target.txt_room_title = null;
    target.txt_room_type = null;
    target.txt_room_id = null;
    target.rel_no_data = null;
    target.txt_no_rooms_available = null;
    target.lin_single_room = null;
    target.grid_room = null;
    target.img_single_room_back = null;

    view2131297150.setOnClickListener(null);
    view2131297150 = null;
  }
}
