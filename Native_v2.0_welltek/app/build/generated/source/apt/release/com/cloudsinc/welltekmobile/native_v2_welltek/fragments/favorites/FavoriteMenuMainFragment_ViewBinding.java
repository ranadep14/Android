// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.favorites;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoriteMenuMainFragment_ViewBinding implements Unbinder {
  private FavoriteMenuMainFragment target;

  private View view2131296670;

  @UiThread
  public FavoriteMenuMainFragment_ViewBinding(final FavoriteMenuMainFragment target, View source) {
    this.target = target;

    View view;
    target.rec_fav = Utils.findRequiredViewAsType(source, R.id.rec_fav, "field 'rec_fav'", RecyclerView.class);
    target.rec_exp_fav = Utils.findRequiredViewAsType(source, R.id.rec_exp_fav, "field 'rec_exp_fav'", RecyclerView.class);
    target.lin_no_data = Utils.findRequiredViewAsType(source, R.id.lin_no_data, "field 'lin_no_data'", LinearLayout.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.lin_fav_exp = Utils.findRequiredViewAsType(source, R.id.lin_fav_exp, "field 'lin_fav_exp'", LinearLayout.class);
    target.lin_fav_element = Utils.findRequiredViewAsType(source, R.id.lin_fav_element, "field 'lin_fav_element'", LinearLayout.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.textContent = Utils.findRequiredViewAsType(source, R.id.textContent, "field 'textContent'", WebView.class);
    target.rel_no_elements = Utils.findRequiredViewAsType(source, R.id.rel_no_elements, "field 'rel_no_elements'", RelativeLayout.class);
    target.rel_no_experiences = Utils.findRequiredViewAsType(source, R.id.rel_no_experiences, "field 'rel_no_experiences'", RelativeLayout.class);
    target.rel_element_main = Utils.findRequiredViewAsType(source, R.id.rel_element_main, "field 'rel_element_main'", RelativeLayout.class);
    target.rel_experiences_main = Utils.findRequiredViewAsType(source, R.id.rel_experiences_main, "field 'rel_experiences_main'", RelativeLayout.class);
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
    FavoriteMenuMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rec_fav = null;
    target.rec_exp_fav = null;
    target.lin_no_data = null;
    target.rel_loading = null;
    target.lin_fav_exp = null;
    target.lin_fav_element = null;
    target.txt_fragment_title = null;
    target.textContent = null;
    target.rel_no_elements = null;
    target.rel_no_experiences = null;
    target.rel_element_main = null;
    target.rel_experiences_main = null;

    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
