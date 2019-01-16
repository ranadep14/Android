// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.favorites;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoritesMainFragment_ViewBinding implements Unbinder {
  private FavoritesMainFragment target;

  @UiThread
  public FavoritesMainFragment_ViewBinding(FavoritesMainFragment target, View source) {
    this.target = target;

    target.rec_fav = Utils.findRequiredViewAsType(source, R.id.rec_fav, "field 'rec_fav'", RecyclerView.class);
    target.lin_no_data = Utils.findRequiredViewAsType(source, R.id.lin_no_data, "field 'lin_no_data'", LinearLayout.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoritesMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rec_fav = null;
    target.lin_no_data = null;
    target.rel_loading = null;
  }
}
