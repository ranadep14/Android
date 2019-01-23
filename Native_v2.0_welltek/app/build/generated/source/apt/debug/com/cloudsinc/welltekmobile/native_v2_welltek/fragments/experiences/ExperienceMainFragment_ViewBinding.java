// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ExperienceMainFragment_ViewBinding implements Unbinder {
  private ExperienceMainFragment target;

  private View view2131296952;

  private View view2131296956;

  @UiThread
  public ExperienceMainFragment_ViewBinding(final ExperienceMainFragment target, View source) {
    this.target = target;

    View view;
    target.view_exp_select = Utils.findRequiredView(source, R.id.view_exp_select, "field 'view_exp_select'");
    target.view_schedule_select = Utils.findRequiredView(source, R.id.view_schedule_select, "field 'view_schedule_select'");
    view = Utils.findRequiredView(source, R.id.lyt_rel_exp, "field 'lyt_rel_exp'");
    target.lyt_rel_exp = Utils.castView(view, R.id.lyt_rel_exp, "field 'lyt_rel_exp'", RelativeLayout.class);
    view2131296952 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_rel_schedule, "field 'lyt_rel_schedule'");
    target.lyt_rel_schedule = Utils.castView(view, R.id.lyt_rel_schedule, "field 'lyt_rel_schedule'", RelativeLayout.class);
    view2131296956 = view;
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
    ExperienceMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.view_exp_select = null;
    target.view_schedule_select = null;
    target.lyt_rel_exp = null;
    target.lyt_rel_schedule = null;

    view2131296952.setOnClickListener(null);
    view2131296952 = null;
    view2131296956.setOnClickListener(null);
    view2131296956 = null;
  }
}
