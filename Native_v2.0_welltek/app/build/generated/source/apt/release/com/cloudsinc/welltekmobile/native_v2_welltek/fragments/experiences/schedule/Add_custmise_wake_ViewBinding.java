// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Add_custmise_wake_ViewBinding implements Unbinder {
  private Add_custmise_wake target;

  private View view2131296313;

  private View view2131296423;

  private View view2131297395;

  private View view2131297399;

  private View view2131297397;

  private View view2131297401;

  private View view2131297440;

  private View view2131297532;

  private View view2131297546;

  private View view2131297527;

  private View view2131297418;

  private View view2131297497;

  private View view2131297515;

  private View view2131296684;

  private View view2131296402;

  private View view2131297499;

  private View view2131296670;

  @UiThread
  public Add_custmise_wake_ViewBinding(final Add_custmise_wake target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.Timepicker, "field 'showTime' and method 'pickTime'");
    target.showTime = Utils.castView(view, R.id.Timepicker, "field 'showTime'", TextView.class);
    view2131296313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pickTime();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btn_save' and method 'btn_save'");
    target.btn_save = Utils.castView(view, R.id.btn_save, "field 'btn_save'", Button.class);
    view2131296423 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_save();
      }
    });
    target.rel_track = Utils.findRequiredViewAsType(source, R.id.rel_track, "field 'rel_track'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_elem_audio, "field 'txt_elem_audio' and method 'imgOnclickaudio'");
    target.txt_elem_audio = Utils.castView(view, R.id.txt_elem_audio, "field 'txt_elem_audio'", ImageView.class);
    view2131297395 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imgOnclickaudio(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_elem_lights, "field 'txt_elem_lights' and method 'imgOnclickLights'");
    target.txt_elem_lights = Utils.castView(view, R.id.txt_elem_lights, "field 'txt_elem_lights'", ImageView.class);
    view2131297399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imgOnclickLights(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_elem_blinds, "field 'txt_elem_blinds' and method 'imgOnclickblinds'");
    target.txt_elem_blinds = Utils.castView(view, R.id.txt_elem_blinds, "field 'txt_elem_blinds'", ImageView.class);
    view2131297397 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imgOnclickblinds(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_elem_tracks, "field 'txt_elem_tracks' and method 'imgOnclick'");
    target.txt_elem_tracks = Utils.castView(view, R.id.txt_elem_tracks, "field 'txt_elem_tracks'", ImageView.class);
    view2131297401 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imgOnclick(p0);
      }
    });
    target.simpleChronometer = Utils.findRequiredViewAsType(source, R.id.simpleChronometer, "field 'simpleChronometer'", Chronometer.class);
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.lbl_mon = Utils.findRequiredViewAsType(source, R.id.lbl_mon, "field 'lbl_mon'", TextView.class);
    target.lbl_tue = Utils.findRequiredViewAsType(source, R.id.lbl_tue, "field 'lbl_tue'", TextView.class);
    target.lbl_wed = Utils.findRequiredViewAsType(source, R.id.lbl_wed, "field 'lbl_wed'", TextView.class);
    target.lbl_thu = Utils.findRequiredViewAsType(source, R.id.lbl_thu, "field 'lbl_thu'", TextView.class);
    target.lbl_fri = Utils.findRequiredViewAsType(source, R.id.lbl_fri, "field 'lbl_fri'", TextView.class);
    target.lbl_sat = Utils.findRequiredViewAsType(source, R.id.lbl_sat, "field 'lbl_sat'", TextView.class);
    target.lbl_sun = Utils.findRequiredViewAsType(source, R.id.lbl_sun, "field 'lbl_sun'", TextView.class);
    target.lbl_repeat = Utils.findRequiredViewAsType(source, R.id.lbl_repeat, "field 'lbl_repeat'", TextView.class);
    view = Utils.findRequiredView(source, R.id.txt_mon, "field 'txt_mon' and method 'textOnclick'");
    target.txt_mon = Utils.castView(view, R.id.txt_mon, "field 'txt_mon'", TextView.class);
    view2131297440 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_tue, "field 'txt_tue' and method 'textOnclick'");
    target.txt_tue = Utils.castView(view, R.id.txt_tue, "field 'txt_tue'", TextView.class);
    view2131297532 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_wen, "field 'txt_wen' and method 'textOnclick'");
    target.txt_wen = Utils.castView(view, R.id.txt_wen, "field 'txt_wen'", TextView.class);
    view2131297546 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_thu, "field 'txt_thu' and method 'textOnclick'");
    target.txt_thu = Utils.castView(view, R.id.txt_thu, "field 'txt_thu'", TextView.class);
    view2131297527 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_fri, "field 'txt_fri' and method 'textOnclick'");
    target.txt_fri = Utils.castView(view, R.id.txt_fri, "field 'txt_fri'", TextView.class);
    view2131297418 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_sat, "field 'txt_sat' and method 'textOnclick'");
    target.txt_sat = Utils.castView(view, R.id.txt_sat, "field 'txt_sat'", TextView.class);
    view2131297497 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_sun, "field 'txt_sun' and method 'textOnclick'");
    target.txt_sun = Utils.castView(view, R.id.txt_sun, "field 'txt_sun'", TextView.class);
    view2131297515 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_delete, "field 'img_delete' and method 'imgOnclick'");
    target.img_delete = Utils.castView(view, R.id.img_delete, "field 'img_delete'", TextView.class);
    view2131296684 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imgOnclick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'btn_cancel' and method 'btn_cancel'");
    target.btn_cancel = Utils.castView(view, R.id.btn_cancel, "field 'btn_cancel'", TextView.class);
    view2131296402 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_cancel();
      }
    });
    target.txt_no_audio = Utils.findRequiredViewAsType(source, R.id.txt_no_audio, "field 'txt_no_audio'", TextView.class);
    target.txt_no_blinds = Utils.findRequiredViewAsType(source, R.id.txt_no_blinds, "field 'txt_no_blinds'", TextView.class);
    target.lbl_tracks = Utils.findOptionalViewAsType(source, R.id.lbl_tracks, "field 'lbl_tracks'", TextView.class);
    view = source.findViewById(R.id.txt_save);
    target.txt_save = Utils.castView(view, R.id.txt_save, "field 'txt_save'", TextView.class);
    if (view != null) {
      view2131297499 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.txt_saveDelete();
        }
      });
    }
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
    Add_custmise_wake target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.showTime = null;
    target.btn_save = null;
    target.rel_track = null;
    target.txt_elem_audio = null;
    target.txt_elem_lights = null;
    target.txt_elem_blinds = null;
    target.txt_elem_tracks = null;
    target.simpleChronometer = null;
    target.txt_room_title = null;
    target.lbl_mon = null;
    target.lbl_tue = null;
    target.lbl_wed = null;
    target.lbl_thu = null;
    target.lbl_fri = null;
    target.lbl_sat = null;
    target.lbl_sun = null;
    target.lbl_repeat = null;
    target.txt_mon = null;
    target.txt_tue = null;
    target.txt_wen = null;
    target.txt_thu = null;
    target.txt_fri = null;
    target.txt_sat = null;
    target.txt_sun = null;
    target.img_delete = null;
    target.btn_cancel = null;
    target.txt_no_audio = null;
    target.txt_no_blinds = null;
    target.lbl_tracks = null;
    target.txt_save = null;
    target.txt_fragment_title = null;
    target.img_back = null;

    view2131296313.setOnClickListener(null);
    view2131296313 = null;
    view2131296423.setOnClickListener(null);
    view2131296423 = null;
    view2131297395.setOnClickListener(null);
    view2131297395 = null;
    view2131297399.setOnClickListener(null);
    view2131297399 = null;
    view2131297397.setOnClickListener(null);
    view2131297397 = null;
    view2131297401.setOnClickListener(null);
    view2131297401 = null;
    view2131297440.setOnClickListener(null);
    view2131297440 = null;
    view2131297532.setOnClickListener(null);
    view2131297532 = null;
    view2131297546.setOnClickListener(null);
    view2131297546 = null;
    view2131297527.setOnClickListener(null);
    view2131297527 = null;
    view2131297418.setOnClickListener(null);
    view2131297418 = null;
    view2131297497.setOnClickListener(null);
    view2131297497 = null;
    view2131297515.setOnClickListener(null);
    view2131297515 = null;
    view2131296684.setOnClickListener(null);
    view2131296684 = null;
    view2131296402.setOnClickListener(null);
    view2131296402 = null;
    if (view2131297499 != null) {
      view2131297499.setOnClickListener(null);
      view2131297499 = null;
    }
    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
  }
}
