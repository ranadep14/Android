// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.GridView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Wake_SelectSound_ViewBinding implements Unbinder {
  private Wake_SelectSound target;

  private View view2131296679;

  private View view2131296399;

  @UiThread
  public Wake_SelectSound_ViewBinding(final Wake_SelectSound target, View source) {
    this.target = target;

    View view;
    target.list_sound = Utils.findRequiredViewAsType(source, R.id.list_sound, "field 'list_sound'", GridView.class);
    view = Utils.findRequiredView(source, R.id.img_close, "method 'img_close'");
    view2131296679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_close();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_apply, "method 'btn_apply'");
    view2131296399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_apply();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Wake_SelectSound target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.list_sound = null;

    view2131296679.setOnClickListener(null);
    view2131296679 = null;
    view2131296399.setOnClickListener(null);
    view2131296399 = null;
  }
}
