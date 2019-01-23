// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

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

public class SubTab_ExperienceFragment_ViewBinding implements Unbinder {
  private SubTab_ExperienceFragment target;

  private View view2131296946;

  private View view2131296944;

  @UiThread
  public SubTab_ExperienceFragment_ViewBinding(final SubTab_ExperienceFragment target,
      View source) {
    this.target = target;

    View view;
    target.view_fav_select = Utils.findRequiredView(source, R.id.view_fav_select, "field 'view_fav_select'");
    target.view_allexp_select = Utils.findRequiredView(source, R.id.view_allexp_select, "field 'view_allexp_select'");
    view = Utils.findRequiredView(source, R.id.lyt_rel_favorites_exp, "field 'lyt_rel_favorites_exp'");
    target.lyt_rel_favorites_exp = Utils.castView(view, R.id.lyt_rel_favorites_exp, "field 'lyt_rel_favorites_exp'", RelativeLayout.class);
    view2131296946 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_rel_all_exp, "field 'lyt_rel_all_exp'");
    target.lyt_rel_all_exp = Utils.castView(view, R.id.lyt_rel_all_exp, "field 'lyt_rel_all_exp'", RelativeLayout.class);
    view2131296944 = view;
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
    SubTab_ExperienceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.view_fav_select = null;
    target.view_allexp_select = null;
    target.lyt_rel_favorites_exp = null;
    target.lyt_rel_all_exp = null;

    view2131296946.setOnClickListener(null);
    view2131296946 = null;
    view2131296944.setOnClickListener(null);
    view2131296944 = null;
  }
}
