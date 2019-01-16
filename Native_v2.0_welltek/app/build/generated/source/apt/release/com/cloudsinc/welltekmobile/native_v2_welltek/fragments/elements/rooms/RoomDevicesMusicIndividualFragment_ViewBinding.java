// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class RoomDevicesMusicIndividualFragment_ViewBinding implements Unbinder {
  private RoomDevicesMusicIndividualFragment target;

  private View view2131296670;

  private View view2131296407;

  private View view2131296428;

  private View view2131296408;

  private View view2131296429;

  @UiThread
  public RoomDevicesMusicIndividualFragment_ViewBinding(final RoomDevicesMusicIndividualFragment target,
      View source) {
    this.target = target;

    View view;
    target.rec_indivi_music = Utils.findRequiredViewAsType(source, R.id.rec_indivi_music, "field 'rec_indivi_music'", RecyclerView.class);
    target.lin_ind_music = Utils.findRequiredViewAsType(source, R.id.lin_ind_music, "field 'lin_ind_music'", LinearLayout.class);
    target.txt_no_devices = Utils.findRequiredViewAsType(source, R.id.txt_no_devices, "field 'txt_no_devices'", TextView.class);
    target.txt_playlist = Utils.findRequiredViewAsType(source, R.id.txt_playlist, "field 'txt_playlist'", TextView.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
    target.lin_dawn_running = Utils.findRequiredViewAsType(source, R.id.lin_dawn_running, "field 'lin_dawn_running'", LinearLayout.class);
    target.lin_sleep_running = Utils.findRequiredViewAsType(source, R.id.lin_sleep_running, "field 'lin_sleep_running'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_dawn_cancle, "method 'btn_dawn_cancle'");
    view2131296407 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_dawn_cancle();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_sleep_cancle, "method 'btn_sleep_cancle'");
    view2131296428 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_sleep_cancle();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_dawn_confirm, "method 'btn_dawn_confirm'");
    view2131296408 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_dawn_confirm();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_sleep_confirm, "method 'btn_sleep_confirm'");
    view2131296429 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_sleep_confirm();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomDevicesMusicIndividualFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rec_indivi_music = null;
    target.lin_ind_music = null;
    target.txt_no_devices = null;
    target.txt_playlist = null;
    target.txt_fragment_title = null;
    target.rel_loading = null;
    target.img_back = null;
    target.lin_dawn_running = null;
    target.lin_sleep_running = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
    view2131296407.setOnClickListener(null);
    view2131296407 = null;
    view2131296428.setOnClickListener(null);
    view2131296428 = null;
    view2131296408.setOnClickListener(null);
    view2131296408 = null;
    view2131296429.setOnClickListener(null);
    view2131296429 = null;
  }
}
