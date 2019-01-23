// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.poovam.pinedittextfield.CirclePinField;
import com.poovam.pinedittextfield.SquarePinField;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PinInputDialog_ViewBinding implements Unbinder {
  private PinInputDialog target;

  private View view2131296423;

  @UiThread
  public PinInputDialog_ViewBinding(final PinInputDialog target, View source) {
    this.target = target;

    View view;
    target.circlePinField = Utils.findRequiredViewAsType(source, R.id.circlePinField, "field 'circlePinField'", CirclePinField.class);
    target.linePinField = Utils.findRequiredViewAsType(source, R.id.linePinField, "field 'linePinField'", SquarePinField.class);
    target.txt_valid_text = Utils.findRequiredViewAsType(source, R.id.txt_valid_text, "field 'txt_valid_text'", TextView.class);
    target.txt_pin_title = Utils.findRequiredViewAsType(source, R.id.txt_pin_title, "field 'txt_pin_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btn_save' and method 'btn_save'");
    target.btn_save = Utils.castView(view, R.id.btn_save, "field 'btn_save'", Button.class);
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
    PinInputDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.circlePinField = null;
    target.linePinField = null;
    target.txt_valid_text = null;
    target.txt_pin_title = null;
    target.btn_save = null;

    view2131296423.setOnClickListener(null);
    view2131296423 = null;
  }
}
