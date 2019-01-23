// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences;

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

public class ExperienceMainFragmentPortrait_ViewBinding implements Unbinder {
  private ExperienceMainFragmentPortrait target;

  private View view2131296943;

  private View view2131296949;

  private View view2131296670;

  @UiThread
  public ExperienceMainFragmentPortrait_ViewBinding(final ExperienceMainFragmentPortrait target,
      View source) {
    this.target = target;

    View view;
    target.view_exp_select = Utils.findRequiredView(source, R.id.view_exp_select, "field 'view_exp_select'");
    target.view_schedule_select = Utils.findRequiredView(source, R.id.view_schedule_select, "field 'view_schedule_select'");
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lyt_rel_act, "field 'lyt_rel_act'");
    target.lyt_rel_act = Utils.castView(view, R.id.lyt_rel_act, "field 'lyt_rel_act'", RelativeLayout.class);
    view2131296943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_rel_schedule, "field 'lyt_rel_schedule'");
    target.lyt_rel_schedule = Utils.castView(view, R.id.lyt_rel_schedule, "field 'lyt_rel_schedule'", RelativeLayout.class);
    view2131296949 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ExperienceMainFragmentPortrait target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.view_exp_select = null;
    target.view_schedule_select = null;
    target.txt_fragment_title = null;
    target.lyt_rel_act = null;
    target.lyt_rel_schedule = null;

    view2131296943.setOnClickListener(null);
    view2131296943 = null;
    view2131296949.setOnClickListener(null);
    view2131296949 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
