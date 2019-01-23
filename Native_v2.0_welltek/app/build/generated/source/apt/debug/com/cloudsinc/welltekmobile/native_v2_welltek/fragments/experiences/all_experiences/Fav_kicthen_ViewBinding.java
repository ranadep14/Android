// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Fav_kicthen_ViewBinding implements Unbinder {
  private Fav_kicthen target;

  private View view2131296681;

  private View view2131296680;

  private View view2131296686;

  private View view2131296803;

  private View view2131296807;

  private View view2131296816;

  @UiThread
  public Fav_kicthen_ViewBinding(final Fav_kicthen target, View source) {
    this.target = target;

    View view;
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_cook, "field 'img_cook' and method 'img_cook'");
    target.img_cook = Utils.castView(view, R.id.img_cook, "field 'img_cook'", ImageView.class);
    view2131296681 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_cook();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_comfort, "field 'img_comfort' and method 'img_comfort'");
    target.img_comfort = Utils.castView(view, R.id.img_comfort, "field 'img_comfort'", ImageView.class);
    view2131296680 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_comfort();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_dinner, "field 'img_dinner' and method 'img_dinner'");
    target.img_dinner = Utils.castView(view, R.id.img_dinner, "field 'img_dinner'", ImageView.class);
    view2131296686 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_dinner();
      }
    });
    target.txt_cook_active_reset = Utils.findRequiredViewAsType(source, R.id.txt_cook_active_reset, "field 'txt_cook_active_reset'", TextView.class);
    target.txt_dinner_active_reset = Utils.findRequiredViewAsType(source, R.id.txt_dinner_active_reset, "field 'txt_dinner_active_reset'", TextView.class);
    target.txt_comfort_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_comfort_active_deactive, "field 'txt_comfort_active_deactive'", TextView.class);
    target.img_room_type = Utils.findRequiredViewAsType(source, R.id.img_room_type, "field 'img_room_type'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.lin_comfort, "method 'onClick'");
    view2131296803 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_cook, "method 'onClick'");
    view2131296807 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_dinner_party, "method 'onClick'");
    view2131296816 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Fav_kicthen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_room_title = null;
    target.img_cook = null;
    target.img_comfort = null;
    target.img_dinner = null;
    target.txt_cook_active_reset = null;
    target.txt_dinner_active_reset = null;
    target.txt_comfort_active_deactive = null;
    target.img_room_type = null;

    view2131296681.setOnClickListener(null);
    view2131296681 = null;
    view2131296680.setOnClickListener(null);
    view2131296680 = null;
    view2131296686.setOnClickListener(null);
    view2131296686 = null;
    view2131296803.setOnClickListener(null);
    view2131296803 = null;
    view2131296807.setOnClickListener(null);
    view2131296807 = null;
    view2131296816.setOnClickListener(null);
    view2131296816 = null;
  }
}
