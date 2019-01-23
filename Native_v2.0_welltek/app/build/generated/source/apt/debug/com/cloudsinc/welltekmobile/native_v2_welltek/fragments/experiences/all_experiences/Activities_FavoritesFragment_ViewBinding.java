// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Activities_FavoritesFragment_ViewBinding implements Unbinder {
  private Activities_FavoritesFragment target;

  private View view2131296859;

  private View view2131296809;

  private View view2131296822;

  private View view2131296863;

  private View view2131296803;

  private View view2131296519;

  private View view2131296520;

  private View view2131296810;

  private View view2131296818;

  @UiThread
  public Activities_FavoritesFragment_ViewBinding(final Activities_FavoritesFragment target,
      View source) {
    this.target = target;

    View view;
    target.txt_cooking_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_cooking_active_deactive, "field 'txt_cooking_active_deactive'", TextView.class);
    target.txt_prepare_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_prepare_active_deactive, "field 'txt_prepare_active_deactive'", TextView.class);
    target.txt_egerise_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_egerise_active_deactive, "field 'txt_egerise_active_deactive'", TextView.class);
    target.txt_relax_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_relax_active_deactive, "field 'txt_relax_active_deactive'", TextView.class);
    target.txt_comfort_active_deactive = Utils.findRequiredViewAsType(source, R.id.txt_comfort_active_deactive, "field 'txt_comfort_active_deactive'", TextView.class);
    target.demo_txt_comfort_active_deactive = Utils.findRequiredViewAsType(source, R.id.demo_txt_comfort_active_deactive, "field 'demo_txt_comfort_active_deactive'", TextView.class);
    target.demo_txt_dawn_active_deactive = Utils.findRequiredViewAsType(source, R.id.demo_txt_dawn_active_deactive, "field 'demo_txt_dawn_active_deactive'", TextView.class);
    target.demo_txt_display_active_deactive = Utils.findRequiredViewAsType(source, R.id.demo_txt_display_active_deactive, "field 'demo_txt_display_active_deactive'", TextView.class);
    target.demo_txt_sleep_active_deactive = Utils.findRequiredViewAsType(source, R.id.demo_txt_sleep_active_deactive, "field 'demo_txt_sleep_active_deactive'", TextView.class);
    target.rel_prepare = Utils.findRequiredViewAsType(source, R.id.rel_prepare, "field 'rel_prepare'", RelativeLayout.class);
    target.rel_cooking = Utils.findRequiredViewAsType(source, R.id.rel_cooking, "field 'rel_cooking'", RelativeLayout.class);
    target.rel_energise = Utils.findRequiredViewAsType(source, R.id.rel_energise, "field 'rel_energise'", RelativeLayout.class);
    target.rel_relax = Utils.findRequiredViewAsType(source, R.id.rel_relax, "field 'rel_relax'", RelativeLayout.class);
    target.rel_comfort = Utils.findRequiredViewAsType(source, R.id.rel_comfort, "field 'rel_comfort'", RelativeLayout.class);
    target.demo_rel_comfort = Utils.findRequiredViewAsType(source, R.id.demo_rel_comfort, "field 'demo_rel_comfort'", RelativeLayout.class);
    target.demo_rel_dawn_comfort = Utils.findRequiredViewAsType(source, R.id.demo_rel_sleep, "field 'demo_rel_dawn_comfort'", RelativeLayout.class);
    target.demo_rel_display_comfort = Utils.findRequiredViewAsType(source, R.id.demo_rel_display, "field 'demo_rel_display_comfort'", RelativeLayout.class);
    target.demo_rel__sleep_comfort = Utils.findRequiredViewAsType(source, R.id.demo_rel_dawn, "field 'demo_rel__sleep_comfort'", RelativeLayout.class);
    target.demo_scene_lyt = Utils.findRequiredViewAsType(source, R.id.demo_scene_lyt, "field 'demo_scene_lyt'", LinearLayout.class);
    target.lyt_dawn_demo = Utils.findRequiredViewAsType(source, R.id.lyt_dawn_demo, "field 'lyt_dawn_demo'", RelativeLayout.class);
    target.lyt_display_demo = Utils.findRequiredViewAsType(source, R.id.lyt_display_demo, "field 'lyt_display_demo'", RelativeLayout.class);
    target.lyt_sleep_demo = Utils.findRequiredViewAsType(source, R.id.lyt_sleep_demo, "field 'lyt_sleep_demo'", RelativeLayout.class);
    target.lyt_circadian_demo = Utils.findRequiredViewAsType(source, R.id.lyt_circadian_demo, "field 'lyt_circadian_demo'", RelativeLayout.class);
    target.cir_cnt = Utils.findRequiredViewAsType(source, R.id.cir_cnt, "field 'cir_cnt'", TextView.class);
    target.demo_cir_cnt = Utils.findRequiredViewAsType(source, R.id.demo_cir_cnt, "field 'demo_cir_cnt'", TextView.class);
    target.sleep_cnt = Utils.findRequiredViewAsType(source, R.id.sleep_cnt, "field 'sleep_cnt'", TextView.class);
    target.eng_cnt = Utils.findRequiredViewAsType(source, R.id.eng_cnt, "field 'eng_cnt'", TextView.class);
    target.rlax_cnt = Utils.findRequiredViewAsType(source, R.id.relax_cnt, "field 'rlax_cnt'", TextView.class);
    target.cooking_cnt = Utils.findRequiredViewAsType(source, R.id.cooking_cnt, "field 'cooking_cnt'", TextView.class);
    target.display_cnt = Utils.findRequiredViewAsType(source, R.id.display_cnt, "field 'display_cnt'", TextView.class);
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
    view = Utils.findRequiredView(source, R.id.demo_lin_comfort, "method 'onClick'");
    view2131296519 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.demo_lin_sleep, "method 'onClick'");
    view2131296520 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_dawn, "method 'onClick'");
    view2131296810 = view;
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
    Activities_FavoritesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_cooking_active_deactive = null;
    target.txt_prepare_active_deactive = null;
    target.txt_egerise_active_deactive = null;
    target.txt_relax_active_deactive = null;
    target.txt_comfort_active_deactive = null;
    target.demo_txt_comfort_active_deactive = null;
    target.demo_txt_dawn_active_deactive = null;
    target.demo_txt_display_active_deactive = null;
    target.demo_txt_sleep_active_deactive = null;
    target.rel_prepare = null;
    target.rel_cooking = null;
    target.rel_energise = null;
    target.rel_relax = null;
    target.rel_comfort = null;
    target.demo_rel_comfort = null;
    target.demo_rel_dawn_comfort = null;
    target.demo_rel_display_comfort = null;
    target.demo_rel__sleep_comfort = null;
    target.demo_scene_lyt = null;
    target.lyt_dawn_demo = null;
    target.lyt_display_demo = null;
    target.lyt_sleep_demo = null;
    target.lyt_circadian_demo = null;
    target.cir_cnt = null;
    target.demo_cir_cnt = null;
    target.sleep_cnt = null;
    target.eng_cnt = null;
    target.rlax_cnt = null;
    target.cooking_cnt = null;
    target.display_cnt = null;

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
    view2131296519.setOnClickListener(null);
    view2131296519 = null;
    view2131296520.setOnClickListener(null);
    view2131296520 = null;
    view2131296810.setOnClickListener(null);
    view2131296810 = null;
    view2131296818.setOnClickListener(null);
    view2131296818 = null;
  }
}
