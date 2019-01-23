// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeScreenGifsFragment_ViewBinding implements Unbinder {
  private HomeScreenGifsFragment target;

  @UiThread
  public HomeScreenGifsFragment_ViewBinding(HomeScreenGifsFragment target, View source) {
    this.target = target;

    target.view1 = Utils.findRequiredView(source, R.id.view1, "field 'view1'");
    target.view2 = Utils.findRequiredView(source, R.id.view2, "field 'view2'");
    target.view3 = Utils.findRequiredView(source, R.id.view3, "field 'view3'");
    target.view4i = Utils.findRequiredView(source, R.id.view4, "field 'view4i'");
    target.gif_title = Utils.findRequiredViewAsType(source, R.id.gif_title, "field 'gif_title'", TextView.class);
    target.rel_lyt = Utils.findRequiredViewAsType(source, R.id.rel_lyt, "field 'rel_lyt'", RelativeLayout.class);
    target.txt_no_action = Utils.findRequiredViewAsType(source, R.id.txt_no_action, "field 'txt_no_action'", TextView.class);
    target.current_action_prog_bar = Utils.findRequiredViewAsType(source, R.id.current_action_prog_bar, "field 'current_action_prog_bar'", ProgressBar.class);
    target.lines = Utils.findRequiredViewAsType(source, R.id.lines, "field 'lines'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeScreenGifsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.view1 = null;
    target.view2 = null;
    target.view3 = null;
    target.view4i = null;
    target.gif_title = null;
    target.rel_lyt = null;
    target.txt_no_action = null;
    target.current_action_prog_bar = null;
    target.lines = null;
  }
}
