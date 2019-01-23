// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LegalFragent_ViewBinding implements Unbinder {
  private LegalFragent target;

  @UiThread
  public LegalFragent_ViewBinding(LegalFragent target, View source) {
    this.target = target;

    target.discover_web = Utils.findRequiredViewAsType(source, R.id.webview, "field 'discover_web'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LegalFragent target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.discover_web = null;
  }
}
