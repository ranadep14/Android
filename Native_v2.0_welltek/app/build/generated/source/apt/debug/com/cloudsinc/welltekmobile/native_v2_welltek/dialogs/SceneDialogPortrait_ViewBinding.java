// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SceneDialogPortrait_ViewBinding implements Unbinder {
  private SceneDialogPortrait target;

  private View view2131296723;

  private View view2131296681;

  private View view2131296676;

  private View view2131296687;

  private View view2131296399;

  private View view2131296402;

  @UiThread
  public SceneDialogPortrait_ViewBinding(final SceneDialogPortrait target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_relax, "field 'img_relax'");
    target.img_relax = Utils.castView(view, R.id.img_relax, "field 'img_relax'", ImageView.class);
    view2131296723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_cook, "field 'img_cook'");
    target.img_cook = Utils.castView(view, R.id.img_cook, "field 'img_cook'", ImageView.class);
    view2131296681 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_circadian, "field 'img_circadian'");
    target.img_circadian = Utils.castView(view, R.id.img_circadian, "field 'img_circadian'", ImageView.class);
    view2131296676 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_energize, "field 'img_energize'");
    target.img_energize = Utils.castView(view, R.id.img_energize, "field 'img_energize'", ImageView.class);
    view2131296687 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.txt_cooking = Utils.findRequiredViewAsType(source, R.id.txt_cooking, "field 'txt_cooking'", TextView.class);
    target.txt_circadian = Utils.findRequiredViewAsType(source, R.id.txt_circadian, "field 'txt_circadian'", TextView.class);
    target.txt_energize = Utils.findRequiredViewAsType(source, R.id.txt_energize, "field 'txt_energize'", TextView.class);
    target.txt_relax = Utils.findRequiredViewAsType(source, R.id.txt_relax, "field 'txt_relax'", TextView.class);
    target.lin_cook = Utils.findRequiredViewAsType(source, R.id.lin_cook, "field 'lin_cook'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_apply, "field 'btn_apply' and method 'btn_apply'");
    target.btn_apply = Utils.castView(view, R.id.btn_apply, "field 'btn_apply'", Button.class);
    view2131296399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_apply();
      }
    });
    target.txt_title = Utils.findRequiredViewAsType(source, R.id.txt_title, "field 'txt_title'", TextView.class);
    target.txt_room_name = Utils.findRequiredViewAsType(source, R.id.txt_room_name, "field 'txt_room_name'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'btn_cancel' and method 'btn_cancel'");
    target.btn_cancel = Utils.castView(view, R.id.btn_cancel, "field 'btn_cancel'", Button.class);
    view2131296402 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_cancel();
      }
    });
    target.lin_main = Utils.findRequiredViewAsType(source, R.id.lin_main, "field 'lin_main'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SceneDialogPortrait target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_relax = null;
    target.img_cook = null;
    target.img_circadian = null;
    target.img_energize = null;
    target.txt_cooking = null;
    target.txt_circadian = null;
    target.txt_energize = null;
    target.txt_relax = null;
    target.lin_cook = null;
    target.btn_apply = null;
    target.txt_title = null;
    target.txt_room_name = null;
    target.btn_cancel = null;
    target.lin_main = null;

    view2131296723.setOnClickListener(null);
    view2131296723 = null;
    view2131296681.setOnClickListener(null);
    view2131296681 = null;
    view2131296676.setOnClickListener(null);
    view2131296676 = null;
    view2131296687.setOnClickListener(null);
    view2131296687 = null;
    view2131296399.setOnClickListener(null);
    view2131296399 = null;
    view2131296402.setOnClickListener(null);
    view2131296402 = null;
  }
}
