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

public class Fav_bedroom_ViewBinding implements Unbinder {
  private Fav_bedroom target;

  private View view2131296740;

  private View view2131296680;

  private View view2131296719;

  private View view2131296721;

  private View view2131296697;

  private View view2131296862;

  private View view2131296888;

  private View view2131296859;

  private View view2131296836;

  private View view2131296803;

  @UiThread
  public Fav_bedroom_ViewBinding(final Fav_bedroom target, View source) {
    this.target = target;

    View view;
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.txt_watch_tv_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_watch_tv_active_deactive, "field 'txt_watch_tv_active_deactive'", TextView.class);
    target.txt_prepare_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_prepare_active_deactive, "field 'txt_prepare_active_deactive'", TextView.class);
    target.txt_read_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_read_active_deactive, "field 'txt_read_active_deactive'", TextView.class);
    target.txt_leave_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_leave_active_deactive, "field 'txt_leave_active_deactive'", TextView.class);
    target.txt_comfort_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_comfort_active_deactive, "field 'txt_comfort_active_deactive'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_watch_movie, "field 'img_watch_movie' and method 'img_watch_movie'");
    target.img_watch_movie = Utils.castView(view, R.id.img_watch_movie, "field 'img_watch_movie'", ImageView.class);
    view2131296740 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_watch_movie();
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
    view = Utils.findRequiredView(source, R.id.img_prepare, "field 'img_prepare' and method 'img_prepare'");
    target.img_prepare = Utils.castView(view, R.id.img_prepare, "field 'img_prepare'", ImageView.class);
    view2131296719 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_prepare();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_read, "field 'img_read' and method 'img_read'");
    target.img_read = Utils.castView(view, R.id.img_read, "field 'img_read'", ImageView.class);
    view2131296721 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_read();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_leave_home, "field 'img_leave_home' and method 'img_leave_home'");
    target.img_leave_home = Utils.castView(view, R.id.img_leave_home, "field 'img_leave_home'", ImageView.class);
    view2131296697 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_leave_home();
      }
    });
    target.img_room_type = Utils.findRequiredViewAsType(source, R.id.img_room_type, "field 'img_room_type'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.lin_read, "method 'onClick'");
    view2131296862 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_watch_tv, "method 'onClick'");
    view2131296888 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_prepare, "method 'onClick'");
    view2131296859 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_leave_home, "method 'onClick'");
    view2131296836 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_comfort, "method 'onClick'");
    view2131296803 = view;
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
    Fav_bedroom target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_room_title = null;
    target.txt_watch_tv_active_deactive = null;
    target.txt_prepare_active_deactive = null;
    target.txt_read_active_deactive = null;
    target.txt_leave_active_deactive = null;
    target.txt_comfort_active_deactive = null;
    target.img_watch_movie = null;
    target.img_comfort = null;
    target.img_prepare = null;
    target.img_read = null;
    target.img_leave_home = null;
    target.img_room_type = null;

    view2131296740.setOnClickListener(null);
    view2131296740 = null;
    view2131296680.setOnClickListener(null);
    view2131296680 = null;
    view2131296719.setOnClickListener(null);
    view2131296719 = null;
    view2131296721.setOnClickListener(null);
    view2131296721 = null;
    view2131296697.setOnClickListener(null);
    view2131296697 = null;
    view2131296862.setOnClickListener(null);
    view2131296862 = null;
    view2131296888.setOnClickListener(null);
    view2131296888 = null;
    view2131296859.setOnClickListener(null);
    view2131296859 = null;
    view2131296836.setOnClickListener(null);
    view2131296836 = null;
    view2131296803.setOnClickListener(null);
    view2131296803 = null;
  }
}
