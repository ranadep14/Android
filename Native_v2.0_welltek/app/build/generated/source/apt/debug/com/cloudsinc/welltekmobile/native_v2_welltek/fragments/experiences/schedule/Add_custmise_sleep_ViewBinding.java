// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Add_custmise_sleep_ViewBinding implements Unbinder {
  private Add_custmise_sleep target;

  private View view2131296313;

  private View view2131296423;

  private View view2131297447;

  private View view2131297539;

  private View view2131297553;

  private View view2131297534;

  private View view2131297425;

  private View view2131297504;

  private View view2131297522;

  private View view2131296684;

  private View view2131296402;

  private View view2131297506;

  private View view2131296670;

  @UiThread
  public Add_custmise_sleep_ViewBinding(final Add_custmise_sleep target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.Timepicker, "field 'showTime' and method 'pickTime'");
    target.showTime = Utils.castView(view, R.id.Timepicker, "field 'showTime'", TextView.class);
    view2131296313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pickTime();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btn_save' and method 'btn_save'");
    target.btn_save = Utils.castView(view, R.id.btn_save, "field 'btn_save'", Button.class);
    view2131296423 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_save();
      }
    });
    target.lbl_mon = Utils.findRequiredViewAsType(source, R.id.lbl_mon, "field 'lbl_mon'", TextView.class);
    target.lbl_tue = Utils.findRequiredViewAsType(source, R.id.lbl_tue, "field 'lbl_tue'", TextView.class);
    target.lbl_wed = Utils.findRequiredViewAsType(source, R.id.lbl_wed, "field 'lbl_wed'", TextView.class);
    target.lbl_thu = Utils.findRequiredViewAsType(source, R.id.lbl_thu, "field 'lbl_thu'", TextView.class);
    target.lbl_fri = Utils.findRequiredViewAsType(source, R.id.lbl_fri, "field 'lbl_fri'", TextView.class);
    target.lbl_sat = Utils.findRequiredViewAsType(source, R.id.lbl_sat, "field 'lbl_sat'", TextView.class);
    target.lbl_sun = Utils.findRequiredViewAsType(source, R.id.lbl_sun, "field 'lbl_sun'", TextView.class);
    target.lbl_repeat = Utils.findRequiredViewAsType(source, R.id.lbl_repeat, "field 'lbl_repeat'", TextView.class);
    view = Utils.findRequiredView(source, R.id.txt_mon, "field 'txt_mon' and method 'textOnclick'");
    target.txt_mon = Utils.castView(view, R.id.txt_mon, "field 'txt_mon'", TextView.class);
    view2131297447 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_tue, "field 'txt_tue' and method 'textOnclick'");
    target.txt_tue = Utils.castView(view, R.id.txt_tue, "field 'txt_tue'", TextView.class);
    view2131297539 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_wen, "field 'txt_wen' and method 'textOnclick'");
    target.txt_wen = Utils.castView(view, R.id.txt_wen, "field 'txt_wen'", TextView.class);
    view2131297553 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_thu, "field 'txt_thu' and method 'textOnclick'");
    target.txt_thu = Utils.castView(view, R.id.txt_thu, "field 'txt_thu'", TextView.class);
    view2131297534 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_fri, "field 'txt_fri' and method 'textOnclick'");
    target.txt_fri = Utils.castView(view, R.id.txt_fri, "field 'txt_fri'", TextView.class);
    view2131297425 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_sat, "field 'txt_sat' and method 'textOnclick'");
    target.txt_sat = Utils.castView(view, R.id.txt_sat, "field 'txt_sat'", TextView.class);
    view2131297504 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_sun, "field 'txt_sun' and method 'textOnclick'");
    target.txt_sun = Utils.castView(view, R.id.txt_sun, "field 'txt_sun'", TextView.class);
    view2131297522 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.textOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_delete, "field 'img_delete' and method 'imgOnclick'");
    target.img_delete = Utils.castView(view, R.id.img_delete, "field 'img_delete'", TextView.class);
    view2131296684 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imgOnclick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'btn_cancel' and method 'btn_cancel'");
    target.btn_cancel = Utils.castView(view, R.id.btn_cancel, "field 'btn_cancel'", TextView.class);
    view2131296402 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_cancel();
      }
    });
    view = source.findViewById(R.id.txt_save);
    target.txt_save = Utils.castView(view, R.id.txt_save, "field 'txt_save'", TextView.class);
    if (view != null) {
      view2131297506 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.txt_savedelete();
        }
      });
    }
    target.txt_fragment_title = Utils.findOptionalViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = source.findViewById(R.id.img_back);
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
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
    Add_custmise_sleep target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.showTime = null;
    target.btn_save = null;
    target.lbl_mon = null;
    target.lbl_tue = null;
    target.lbl_wed = null;
    target.lbl_thu = null;
    target.lbl_fri = null;
    target.lbl_sat = null;
    target.lbl_sun = null;
    target.lbl_repeat = null;
    target.txt_mon = null;
    target.txt_tue = null;
    target.txt_wen = null;
    target.txt_thu = null;
    target.txt_fri = null;
    target.txt_sat = null;
    target.txt_sun = null;
    target.img_delete = null;
    target.btn_cancel = null;
    target.txt_save = null;
    target.txt_fragment_title = null;
    target.img_back = null;

    view2131296313.setOnClickListener(null);
    view2131296313 = null;
    view2131296423.setOnClickListener(null);
    view2131296423 = null;
    view2131297447.setOnClickListener(null);
    view2131297447 = null;
    view2131297539.setOnClickListener(null);
    view2131297539 = null;
    view2131297553.setOnClickListener(null);
    view2131297553 = null;
    view2131297534.setOnClickListener(null);
    view2131297534 = null;
    view2131297425.setOnClickListener(null);
    view2131297425 = null;
    view2131297504.setOnClickListener(null);
    view2131297504 = null;
    view2131297522.setOnClickListener(null);
    view2131297522 = null;
    view2131296684.setOnClickListener(null);
    view2131296684 = null;
    view2131296402.setOnClickListener(null);
    view2131296402 = null;
    if (view2131297506 != null) {
      view2131297506.setOnClickListener(null);
      view2131297506 = null;
    }
    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
  }
}