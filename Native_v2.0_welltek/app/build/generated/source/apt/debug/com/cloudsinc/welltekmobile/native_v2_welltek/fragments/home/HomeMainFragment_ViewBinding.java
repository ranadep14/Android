// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
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

public class HomeMainFragment_ViewBinding implements Unbinder {
  private HomeMainFragment target;

  @UiThread
  public HomeMainFragment_ViewBinding(HomeMainFragment target, View source) {
    this.target = target;

    target.rel_no_data = Utils.findRequiredViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
    target.rel_home_health_tab = Utils.findRequiredViewAsType(source, R.id.rel_home_health_tab, "field 'rel_home_health_tab'", RelativeLayout.class);
    target.txt_no_rooms_available = Utils.findRequiredViewAsType(source, R.id.txt_no_rooms_available, "field 'txt_no_rooms_available'", TextView.class);
    target.circular_progress_bar = Utils.findRequiredViewAsType(source, R.id.circular_progress_bar, "field 'circular_progress_bar'", ProgressBar.class);
    target.room_pager = Utils.findRequiredViewAsType(source, R.id.room_pager, "field 'room_pager'", ViewPager.class);
    target.dotsLayout = Utils.findRequiredViewAsType(source, R.id.layoutDots, "field 'dotsLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rel_no_data = null;
    target.rel_home_health_tab = null;
    target.txt_no_rooms_available = null;
    target.circular_progress_bar = null;
    target.room_pager = null;
    target.dotsLayout = null;
  }
}
