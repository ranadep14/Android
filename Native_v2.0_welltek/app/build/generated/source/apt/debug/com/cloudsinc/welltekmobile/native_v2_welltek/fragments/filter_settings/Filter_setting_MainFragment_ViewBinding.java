// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings;

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

public class Filter_setting_MainFragment_ViewBinding implements Unbinder {
  private Filter_setting_MainFragment target;

  private View view2131296954;

  private View view2131296957;

  private View view2131296955;

  @UiThread
  public Filter_setting_MainFragment_ViewBinding(final Filter_setting_MainFragment target,
      View source) {
    this.target = target;

    View view;
    target.view_filter_select = Utils.findRequiredView(source, R.id.view_filter_select, "field 'view_filter_select'");
    target.view_setting_select = Utils.findRequiredView(source, R.id.view_setting_select, "field 'view_setting_select'");
    target.view_legal_select = Utils.findRequiredView(source, R.id.view_legal_select, "field 'view_legal_select'");
    view = Utils.findRequiredView(source, R.id.lyt_rel_filters, "field 'lyt_rel_filters'");
    target.lyt_rel_filters = Utils.castView(view, R.id.lyt_rel_filters, "field 'lyt_rel_filters'", RelativeLayout.class);
    view2131296954 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_rel_setting, "field 'lyt_rel_setting'");
    target.lyt_rel_setting = Utils.castView(view, R.id.lyt_rel_setting, "field 'lyt_rel_setting'", RelativeLayout.class);
    view2131296957 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_rel_legal, "field 'lyt_rel_legal'");
    target.lyt_rel_legal = Utils.castView(view, R.id.lyt_rel_legal, "field 'lyt_rel_legal'", RelativeLayout.class);
    view2131296955 = view;
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
    Filter_setting_MainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.view_filter_select = null;
    target.view_setting_select = null;
    target.view_legal_select = null;
    target.lyt_rel_filters = null;
    target.lyt_rel_setting = null;
    target.lyt_rel_legal = null;

    view2131296954.setOnClickListener(null);
    view2131296954 = null;
    view2131296957.setOnClickListener(null);
    view2131296957 = null;
    view2131296955.setOnClickListener(null);
    view2131296955 = null;
  }
}
