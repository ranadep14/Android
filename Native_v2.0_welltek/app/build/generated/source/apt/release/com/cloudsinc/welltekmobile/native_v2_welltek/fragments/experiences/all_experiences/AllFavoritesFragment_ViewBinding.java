// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

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

public class AllFavoritesFragment_ViewBinding implements Unbinder {
  private AllFavoritesFragment target;

  @UiThread
  public AllFavoritesFragment_ViewBinding(AllFavoritesFragment target, View source) {
    this.target = target;

    target.room_pager = Utils.findRequiredViewAsType(source, R.id.fav_pager, "field 'room_pager'", ViewPager.class);
    target.dotsLayout = Utils.findRequiredViewAsType(source, R.id.layoutDots, "field 'dotsLayout'", LinearLayout.class);
    target.rel_no_data = Utils.findRequiredViewAsType(source, R.id.rel_no_data, "field 'rel_no_data'", RelativeLayout.class);
    target.rel_room_tab = Utils.findRequiredViewAsType(source, R.id.rel_room_tab, "field 'rel_room_tab'", RelativeLayout.class);
    target.txt_no_rooms_available = Utils.findRequiredViewAsType(source, R.id.txt_no_rooms_available, "field 'txt_no_rooms_available'", TextView.class);
    target.circular_progress_bar = Utils.findRequiredViewAsType(source, R.id.circular_progress_bar, "field 'circular_progress_bar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AllFavoritesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.room_pager = null;
    target.dotsLayout = null;
    target.rel_no_data = null;
    target.rel_room_tab = null;
    target.txt_no_rooms_available = null;
    target.circular_progress_bar = null;
  }
}
