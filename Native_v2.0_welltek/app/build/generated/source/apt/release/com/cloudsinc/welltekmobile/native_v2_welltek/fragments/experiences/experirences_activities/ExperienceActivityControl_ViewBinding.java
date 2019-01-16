// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.experirences_activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ExperienceActivityControl_ViewBinding implements Unbinder {
  private ExperienceActivityControl target;

  private View view2131296670;

  private View view2131296393;

  private View view2131296679;

  @UiThread
  public ExperienceActivityControl_ViewBinding(final ExperienceActivityControl target,
      View source) {
    this.target = target;

    View view;
    target.listview = Utils.findRequiredViewAsType(source, R.id.list_add_rooms, "field 'listview'", RecyclerView.class);
    target.notext = Utils.findRequiredViewAsType(source, R.id.no_text, "field 'notext'", TextView.class);
    target.desc_text = Utils.findRequiredViewAsType(source, R.id.desc_text, "field 'desc_text'", TextView.class);
    target.txt_scene_title = Utils.findRequiredViewAsType(source, R.id.txt_scene_title, "field 'txt_scene_title'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.divider = Utils.findRequiredView(source, R.id.divider, "field 'divider'");
    target.img1 = Utils.findRequiredViewAsType(source, R.id.img1, "field 'img1'", ImageView.class);
    target.img2 = Utils.findRequiredViewAsType(source, R.id.img2, "field 'img2'", ImageView.class);
    target.img3 = Utils.findRequiredViewAsType(source, R.id.img3, "field 'img3'", ImageView.class);
    target.img4 = Utils.findRequiredViewAsType(source, R.id.img4, "field 'img4'", ImageView.class);
    target.img5 = Utils.findRequiredViewAsType(source, R.id.img5, "field 'img5'", ImageView.class);
    target.img6 = Utils.findRequiredViewAsType(source, R.id.img6, "field 'img6'", ImageView.class);
    target.img7 = Utils.findRequiredViewAsType(source, R.id.img7, "field 'img7'", ImageView.class);
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
    view = Utils.findRequiredView(source, R.id.btn_add, "field 'btn_add' and method 'btn_add'");
    target.btn_add = Utils.castView(view, R.id.btn_add, "field 'btn_add'", Button.class);
    view2131296393 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_add();
      }
    });
    target.txt_fragment_title = Utils.findOptionalViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.lin_main = Utils.findRequiredViewAsType(source, R.id.lin_main, "field 'lin_main'", LinearLayout.class);
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
  }

  @Override
  @CallSuper
  public void unbind() {
    ExperienceActivityControl target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listview = null;
    target.notext = null;
    target.desc_text = null;
    target.txt_scene_title = null;
    target.rel_loading = null;
    target.divider = null;
    target.img1 = null;
    target.img2 = null;
    target.img3 = null;
    target.img4 = null;
    target.img5 = null;
    target.img6 = null;
    target.img7 = null;
    target.img_back = null;
    target.btn_add = null;
    target.txt_fragment_title = null;
    target.lin_main = null;

    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
    view2131296393.setOnClickListener(null);
    view2131296393 = null;
    if (view2131296679 != null) {
      view2131296679.setOnClickListener(null);
      view2131296679 = null;
    }
  }
}
