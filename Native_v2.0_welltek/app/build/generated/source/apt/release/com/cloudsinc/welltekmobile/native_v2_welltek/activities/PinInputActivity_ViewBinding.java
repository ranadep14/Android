// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.poovam.pinedittextfield.SquarePinField;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PinInputActivity_ViewBinding implements Unbinder {
  private PinInputActivity target;

  private View view2131296423;

  @UiThread
  public PinInputActivity_ViewBinding(PinInputActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PinInputActivity_ViewBinding(final PinInputActivity target, View source) {
    this.target = target;

    View view;
    target.linePinField = Utils.findRequiredViewAsType(source, R.id.linePinField, "field 'linePinField'", SquarePinField.class);
    view = Utils.findRequiredView(source, R.id.btn_save, "method 'btn_save'");
    view2131296423 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_save();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PinInputActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linePinField = null;

    view2131296423.setOnClickListener(null);
    view2131296423 = null;
  }
}
