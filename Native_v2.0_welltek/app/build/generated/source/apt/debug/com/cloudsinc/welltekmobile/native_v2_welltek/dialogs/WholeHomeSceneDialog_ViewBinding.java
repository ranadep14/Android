// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WholeHomeSceneDialog_ViewBinding implements Unbinder {
  private WholeHomeSceneDialog target;

  private View view2131296723;

  private View view2131296681;

  private View view2131296676;

  private View view2131296687;

  private View view2131296745;

  private View view2131296679;

  private View view2131296402;

  private View view2131296399;

  @UiThread
  public WholeHomeSceneDialog_ViewBinding(final WholeHomeSceneDialog target, View source) {
    this.target = target;

    View view;
    view = source.findViewById(R.id.img_relax);
    target.img_relax = Utils.castView(view, R.id.img_relax, "field 'img_relax'", ImageView.class);
    if (view != null) {
      view2131296723 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.img_cook);
    target.img_cook = Utils.castView(view, R.id.img_cook, "field 'img_cook'", ImageView.class);
    if (view != null) {
      view2131296681 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.img_circadian);
    target.img_circadian = Utils.castView(view, R.id.img_circadian, "field 'img_circadian'", ImageView.class);
    if (view != null) {
      view2131296676 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.img_energize);
    target.img_energize = Utils.castView(view, R.id.img_energize, "field 'img_energize'", ImageView.class);
    if (view != null) {
      view2131296687 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = Utils.findRequiredView(source, R.id.img_whole_light_add_fav, "method 'img_whole_light_add_fav'");
    target.img_whole_light_add_fav = Utils.castView(view, R.id.img_whole_light_add_fav, "field 'img_whole_light_add_fav'", ImageView.class);
    view2131296745 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_light_add_fav();
      }
    });
    target.lin_scene_selection = Utils.findOptionalViewAsType(source, R.id.lin_scene_selection, "field 'lin_scene_selection'", LinearLayout.class);
    target.rel_main = Utils.findOptionalViewAsType(source, R.id.rel_main, "field 'rel_main'", RelativeLayout.class);
    target.whole_on_off = Utils.findRequiredViewAsType(source, R.id.whole_on_off, "field 'whole_on_off'", Switch.class);
    target.circular_whole_home_on_off = Utils.findRequiredViewAsType(source, R.id.circular_whole_home_on_off, "field 'circular_whole_home_on_off'", ProgressBar.class);
    view = source.findViewById(R.id.img_close);
    if (view != null) {
      view2131296679 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_close();
        }
      });
    }
    view = source.findViewById(R.id.btn_cancel);
    if (view != null) {
      view2131296402 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.btn_cancel();
        }
      });
    }
    view = source.findViewById(R.id.btn_apply);
    if (view != null) {
      view2131296399 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.btn_apply();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    WholeHomeSceneDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_relax = null;
    target.img_cook = null;
    target.img_circadian = null;
    target.img_energize = null;
    target.img_whole_light_add_fav = null;
    target.lin_scene_selection = null;
    target.rel_main = null;
    target.whole_on_off = null;
    target.circular_whole_home_on_off = null;

    if (view2131296723 != null) {
      view2131296723.setOnClickListener(null);
      view2131296723 = null;
    }
    if (view2131296681 != null) {
      view2131296681.setOnClickListener(null);
      view2131296681 = null;
    }
    if (view2131296676 != null) {
      view2131296676.setOnClickListener(null);
      view2131296676 = null;
    }
    if (view2131296687 != null) {
      view2131296687.setOnClickListener(null);
      view2131296687 = null;
    }
    view2131296745.setOnClickListener(null);
    view2131296745 = null;
    if (view2131296679 != null) {
      view2131296679.setOnClickListener(null);
      view2131296679 = null;
    }
    if (view2131296402 != null) {
      view2131296402.setOnClickListener(null);
      view2131296402 = null;
    }
    if (view2131296399 != null) {
      view2131296399.setOnClickListener(null);
      view2131296399 = null;
    }
  }
}
