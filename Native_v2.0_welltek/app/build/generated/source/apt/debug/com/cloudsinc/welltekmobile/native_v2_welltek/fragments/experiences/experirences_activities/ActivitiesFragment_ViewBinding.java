// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class ActivitiesFragment_ViewBinding implements Unbinder {
  private ActivitiesFragment target;

  private View view2131296680;

  private View view2131296720;

  private View view2131296687;

  private View view2131296677;

  private View view2131296723;

  private View view2131296682;

  private View view2131296801;

  private View view2131296859;

  private View view2131296809;

  private View view2131296822;

  private View view2131296863;

  private View view2131296803;

  private View view2131296877;

  private View view2131296811;

  private View view2131296818;

  @UiThread
  public ActivitiesFragment_ViewBinding(final ActivitiesFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_comfort, "field 'img_comfort' and method 'img_comfort'");
    target.img_comfort = Utils.castView(view, R.id.img_comfort, "field 'img_comfort'", ImageView.class);
    view2131296680 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_comfort();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_prepare_for_sleep, "field 'img_prepare_for_sleep' and method 'img_prepare_for_sleep'");
    target.img_prepare_for_sleep = Utils.castView(view, R.id.img_prepare_for_sleep, "field 'img_prepare_for_sleep'", ImageView.class);
    view2131296720 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_prepare_for_sleep();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_energize, "field 'img_energize' and method 'img_energize'");
    target.img_energize = Utils.castView(view, R.id.img_energize, "field 'img_energize'", ImageView.class);
    view2131296687 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_energize();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_circadian_demo, "field 'img_circadian_demo' and method 'img_circadian_demo'");
    target.img_circadian_demo = Utils.castView(view, R.id.img_circadian_demo, "field 'img_circadian_demo'", ImageView.class);
    view2131296677 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_circadian_demo();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_relax, "field 'img_relax' and method 'img_relax'");
    target.img_relax = Utils.castView(view, R.id.img_relax, "field 'img_relax'", ImageView.class);
    view2131296723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_relax();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_cooking, "field 'img_cooking' and method 'img_cooking'");
    target.img_cooking = Utils.castView(view, R.id.img_cooking, "field 'img_cooking'", ImageView.class);
    view2131296682 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_cooking();
      }
    });
    target.txt_cooking_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_cooking_active_deactive, "field 'txt_cooking_active_deactive'", TextView.class);
    target.txt_circadian_demo_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_circadian_demo_active_deactive, "field 'txt_circadian_demo_active_deactive'", TextView.class);
    target.txt_prepare_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_prepare_active_deactive, "field 'txt_prepare_active_deactive'", TextView.class);
    target.txt_egerise_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_egerise_active_deactive, "field 'txt_egerise_active_deactive'", TextView.class);
    target.txt_relax_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_relax_active_deactive, "field 'txt_relax_active_deactive'", TextView.class);
    target.txt_comfort_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_comfort_active_deactive, "field 'txt_comfort_active_deactive'", TextView.class);
    target.txt_dawn_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_dawn_demo_active_deactive, "field 'txt_dawn_active_deactive'", TextView.class);
    target.txt_display_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_display_demo_active_deactive, "field 'txt_display_active_deactive'", TextView.class);
    target.txt_sleep_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_sleep_demo_active_deactive, "field 'txt_sleep_active_deactive'", TextView.class);
    target.rel_prepare = Utils.findRequiredViewAsType(source, R.id.rel_prepare, "field 'rel_prepare'", RelativeLayout.class);
    target.rel_cooking = Utils.findRequiredViewAsType(source, R.id.rel_cooking, "field 'rel_cooking'", RelativeLayout.class);
    target.rel_circadian_demo = Utils.findRequiredViewAsType(source, R.id.rel_circadian_demo, "field 'rel_circadian_demo'", RelativeLayout.class);
    target.demo_rel_dawn_comfort = Utils.findRequiredViewAsType(source, R.id.rel_dawn_demo, "field 'demo_rel_dawn_comfort'", RelativeLayout.class);
    target.demo_rel_display_comfort = Utils.findRequiredViewAsType(source, R.id.rel_display_demo, "field 'demo_rel_display_comfort'", RelativeLayout.class);
    target.demo_rel__sleep_comfort = Utils.findRequiredViewAsType(source, R.id.rel_sleep_demo, "field 'demo_rel__sleep_comfort'", RelativeLayout.class);
    target.rel_energise = Utils.findRequiredViewAsType(source, R.id.rel_energise, "field 'rel_energise'", RelativeLayout.class);
    target.rel_relax = Utils.findRequiredViewAsType(source, R.id.rel_relax, "field 'rel_relax'", RelativeLayout.class);
    target.rel_comfort = Utils.findRequiredViewAsType(source, R.id.rel_comfort, "field 'rel_comfort'", RelativeLayout.class);
    target.lyt_dawn_demo = Utils.findRequiredViewAsType(source, R.id.lyt_dawn_demo, "field 'lyt_dawn_demo'", RelativeLayout.class);
    target.lyt_display_demo = Utils.findRequiredViewAsType(source, R.id.lyt_display_demo, "field 'lyt_display_demo'", RelativeLayout.class);
    target.lyt_sleep_demo = Utils.findRequiredViewAsType(source, R.id.lyt_sleep_demo, "field 'lyt_sleep_demo'", RelativeLayout.class);
    target.lyt_circadian_demo = Utils.findRequiredViewAsType(source, R.id.lyt_circadian_demo, "field 'lyt_circadian_demo'", RelativeLayout.class);
    target.lin_comfort_svg = Utils.findRequiredViewAsType(source, R.id.lin_comfort_svg, "field 'lin_comfort_svg'", LinearLayout.class);
    target.lin_cook_svg = Utils.findRequiredViewAsType(source, R.id.lin_cook_svg, "field 'lin_cook_svg'", LinearLayout.class);
    target.lin_eng_svg = Utils.findRequiredViewAsType(source, R.id.lin_eng_svg, "field 'lin_eng_svg'", LinearLayout.class);
    target.lin_relax_svg = Utils.findRequiredViewAsType(source, R.id.lin_relax_svg, "field 'lin_relax_svg'", LinearLayout.class);
    target.lin_sleep_svg = Utils.findRequiredViewAsType(source, R.id.lin_sleep_svg, "field 'lin_sleep_svg'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.lin_circadian_demo, "field 'lin_circadian_demo' and method 'onClick'");
    target.lin_circadian_demo = Utils.castView(view, R.id.lin_circadian_demo, "field 'lin_circadian_demo'", LinearLayout.class);
    view2131296801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.lin_dawn_demo_svg = Utils.findRequiredViewAsType(source, R.id.lin_dawn_demo_svg, "field 'lin_dawn_demo_svg'", LinearLayout.class);
    target.lin_display_demo_svg = Utils.findRequiredViewAsType(source, R.id.lin_display_demo_svg, "field 'lin_display_demo_svg'", LinearLayout.class);
    target.lin_sleep_demo_svg = Utils.findRequiredViewAsType(source, R.id.lin_sleep_demo_svg, "field 'lin_sleep_demo_svg'", LinearLayout.class);
    target.txt_comfort_rooms = Utils.findRequiredViewAsType(source, R.id.txt_comfort_rooms, "field 'txt_comfort_rooms'", TextView.class);
    target.txt_prepare_for_sleep_rooms = Utils.findRequiredViewAsType(source, R.id.txt_prepare_for_sleep_rooms, "field 'txt_prepare_for_sleep_rooms'", TextView.class);
    target.txt_energise_rooms = Utils.findRequiredViewAsType(source, R.id.txt_energise_rooms, "field 'txt_energise_rooms'", TextView.class);
    target.txt_relax_rooms = Utils.findRequiredViewAsType(source, R.id.txt_relax_rooms, "field 'txt_relax_rooms'", TextView.class);
    target.txt_cooking_rooms = Utils.findRequiredViewAsType(source, R.id.txt_cooking_rooms, "field 'txt_cooking_rooms'", TextView.class);
    target.txt_disp_rooms = Utils.findRequiredViewAsType(source, R.id.txt_disp_rooms, "field 'txt_disp_rooms'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lin_prepare, "method 'onClick'");
    view2131296859 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_cooking, "method 'onClick'");
    view2131296809 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_energise, "method 'onClick'");
    view2131296822 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_relax, "method 'onClick'");
    view2131296863 = view;
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
    view = Utils.findRequiredView(source, R.id.lin_sleep_demo, "method 'onClick'");
    view2131296877 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_dawn_demo, "method 'onClick'");
    view2131296811 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_display, "method 'onClick'");
    view2131296818 = view;
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
    ActivitiesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_comfort = null;
    target.img_prepare_for_sleep = null;
    target.img_energize = null;
    target.img_circadian_demo = null;
    target.img_relax = null;
    target.img_cooking = null;
    target.txt_cooking_active_deactive = null;
    target.txt_circadian_demo_active_deactive = null;
    target.txt_prepare_active_deactive = null;
    target.txt_egerise_active_deactive = null;
    target.txt_relax_active_deactive = null;
    target.txt_comfort_active_deactive = null;
    target.txt_dawn_active_deactive = null;
    target.txt_display_active_deactive = null;
    target.txt_sleep_active_deactive = null;
    target.rel_prepare = null;
    target.rel_cooking = null;
    target.rel_circadian_demo = null;
    target.demo_rel_dawn_comfort = null;
    target.demo_rel_display_comfort = null;
    target.demo_rel__sleep_comfort = null;
    target.rel_energise = null;
    target.rel_relax = null;
    target.rel_comfort = null;
    target.lyt_dawn_demo = null;
    target.lyt_display_demo = null;
    target.lyt_sleep_demo = null;
    target.lyt_circadian_demo = null;
    target.lin_comfort_svg = null;
    target.lin_cook_svg = null;
    target.lin_eng_svg = null;
    target.lin_relax_svg = null;
    target.lin_sleep_svg = null;
    target.lin_circadian_demo = null;
    target.lin_dawn_demo_svg = null;
    target.lin_display_demo_svg = null;
    target.lin_sleep_demo_svg = null;
    target.txt_comfort_rooms = null;
    target.txt_prepare_for_sleep_rooms = null;
    target.txt_energise_rooms = null;
    target.txt_relax_rooms = null;
    target.txt_cooking_rooms = null;
    target.txt_disp_rooms = null;

    view2131296680.setOnClickListener(null);
    view2131296680 = null;
    view2131296720.setOnClickListener(null);
    view2131296720 = null;
    view2131296687.setOnClickListener(null);
    view2131296687 = null;
    view2131296677.setOnClickListener(null);
    view2131296677 = null;
    view2131296723.setOnClickListener(null);
    view2131296723 = null;
    view2131296682.setOnClickListener(null);
    view2131296682 = null;
    view2131296801.setOnClickListener(null);
    view2131296801 = null;
    view2131296859.setOnClickListener(null);
    view2131296859 = null;
    view2131296809.setOnClickListener(null);
    view2131296809 = null;
    view2131296822.setOnClickListener(null);
    view2131296822 = null;
    view2131296863.setOnClickListener(null);
    view2131296863 = null;
    view2131296803.setOnClickListener(null);
    view2131296803 = null;
    view2131296877.setOnClickListener(null);
    view2131296877 = null;
    view2131296811.setOnClickListener(null);
    view2131296811 = null;
    view2131296818.setOnClickListener(null);
    view2131296818 = null;
  }
}
