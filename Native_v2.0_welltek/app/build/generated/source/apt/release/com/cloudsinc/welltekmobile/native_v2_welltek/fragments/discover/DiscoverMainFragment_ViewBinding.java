// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.discover;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DiscoverMainFragment_ViewBinding implements Unbinder {
  private DiscoverMainFragment target;

  private View view2131296670;

  @UiThread
  public DiscoverMainFragment_ViewBinding(final DiscoverMainFragment target, View source) {
    this.target = target;

    View view;
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.prog = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'prog'", RelativeLayout.class);
    target.discover_web = Utils.findRequiredViewAsType(source, R.id.discover_web, "field 'discover_web'", WebView.class);
    view = source.findViewById(R.id.img_back);
    if (view != null) {
      view2131296670 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_back();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    DiscoverMainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_fragment_title = null;
    target.prog = null;
    target.discover_web = null;

    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
  }
}
