// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements;

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

public class ElementsMainFragment_ViewBinding implements Unbinder {
  private ElementsMainFragment target;

  private View view2131297139;

  private View view2131297154;

  private View view2131297163;

  @UiThread
  public ElementsMainFragment_ViewBinding(final ElementsMainFragment target, View source) {
    this.target = target;

    View view;
    target.view_fav_select = Utils.findRequiredView(source, R.id.view_fav_select, "field 'view_fav_select'");
    target.view_rooms_select = Utils.findRequiredView(source, R.id.view_rooms_select, "field 'view_rooms_select'");
    target.view_type_select = Utils.findRequiredView(source, R.id.view_type_select, "field 'view_type_select'");
    view = Utils.findRequiredView(source, R.id.rel_fav, "field 'rel_fav'");
    target.rel_fav = Utils.castView(view, R.id.rel_fav, "field 'rel_fav'", RelativeLayout.class);
    view2131297139 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_rooms, "field 'rel_rooms'");
    target.rel_rooms = Utils.castView(view, R.id.rel_rooms, "field 'rel_rooms'", RelativeLayout.class);
    view2131297154 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_types, "field 'rel_types'");
    target.rel_types = Utils.castView(view, R.id.rel_types, "field 'rel_types'", RelativeLayout.class);
    view2131297163 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.txt_type_heading = Utils.findRequiredViewAsType(source, R.id.txt_type_heading, "field 'txt_type_heading'", TextView.class);
    target.txt_fav_heading = Utils.findRequiredViewAsType(source, R.id.txt_fav_heading, "field 'txt_fav_heading'", TextView.class);
    target.txt_room_heading = Utils.findRequiredViewAsType(source, R.id.txt_room_heading, "field 'txt_room_heading'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ElementsMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.view_fav_select = null;
    target.view_rooms_select = null;
    target.view_type_select = null;
    target.rel_fav = null;
    target.rel_rooms = null;
    target.rel_types = null;
    target.txt_type_heading = null;
    target.txt_fav_heading = null;
    target.txt_room_heading = null;

    view2131297139.setOnClickListener(null);
    view2131297139 = null;
    view2131297154.setOnClickListener(null);
    view2131297154 = null;
    view2131297163.setOnClickListener(null);
    view2131297163 = null;
  }
}
