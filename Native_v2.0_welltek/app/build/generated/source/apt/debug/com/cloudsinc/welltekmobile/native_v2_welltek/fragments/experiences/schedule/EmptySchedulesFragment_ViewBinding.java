// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EmptySchedulesFragment_ViewBinding implements Unbinder {
  private EmptySchedulesFragment target;

  private View view2131296396;

  private View view2131296394;

  private View view2131296638;

  private View view2131296670;

  @UiThread
  public EmptySchedulesFragment_ViewBinding(final EmptySchedulesFragment target, View source) {
    this.target = target;

    View view;
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.txt_help1 = Utils.findRequiredViewAsType(source, R.id.txt_help1, "field 'txt_help1'", TextView.class);
    target.txt_help2 = Utils.findRequiredViewAsType(source, R.id.txt_help2, "field 'txt_help2'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_add_wake, "field 'btn_add_wake' and method 'btn_add_wake'");
    target.btn_add_wake = Utils.castView(view, R.id.btn_add_wake, "field 'btn_add_wake'", Button.class);
    view2131296396 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_add_wake();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_add_sleep, "field 'btn_add_sleep' and method 'btn_add_sleep'");
    target.btn_add_sleep = Utils.castView(view, R.id.btn_add_sleep, "field 'btn_add_sleep'", Button.class);
    view2131296394 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_add_sleep();
      }
    });
    target.lyt_dawn_added = Utils.findRequiredViewAsType(source, R.id.lyt_dawn_added, "field 'lyt_dawn_added'", LinearLayout.class);
    target.lst_sim = Utils.findRequiredViewAsType(source, R.id.lst_sim, "field 'lst_sim'", ListView.class);
    target.lst_sleep = Utils.findRequiredViewAsType(source, R.id.lst_sleep, "field 'lst_sleep'", ListView.class);
    view = Utils.findRequiredView(source, R.id.ic_back, "field 'ic_back' and method 'ic_back'");
    target.ic_back = Utils.castView(view, R.id.ic_back, "field 'ic_back'", ImageView.class);
    view2131296638 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.ic_back();
      }
    });
    target.sleep_circle = Utils.findRequiredViewAsType(source, R.id.sleep_circle, "field 'sleep_circle'", ImageView.class);
    target.wake_circle = Utils.findRequiredViewAsType(source, R.id.wake_circle, "field 'wake_circle'", ImageView.class);
    target.txt_save = Utils.findOptionalViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    target.txt_fragment_title = Utils.findOptionalViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = source.findViewById(R.id.img_back);
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
    if (view != null) {
      view2131296670 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_back();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    EmptySchedulesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_room_title = null;
    target.txt_help1 = null;
    target.txt_help2 = null;
    target.btn_add_wake = null;
    target.btn_add_sleep = null;
    target.lyt_dawn_added = null;
    target.lst_sim = null;
    target.lst_sleep = null;
    target.ic_back = null;
    target.sleep_circle = null;
    target.wake_circle = null;
    target.txt_save = null;
    target.txt_fragment_title = null;
    target.img_back = null;

    view2131296396.setOnClickListener(null);
    view2131296396 = null;
    view2131296394.setOnClickListener(null);
    view2131296394 = null;
    view2131296638.setOnClickListener(null);
    view2131296638 = null;
    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
  }
}
