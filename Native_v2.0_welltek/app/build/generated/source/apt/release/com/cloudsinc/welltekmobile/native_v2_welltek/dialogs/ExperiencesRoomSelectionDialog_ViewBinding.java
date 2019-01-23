// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ExperiencesRoomSelectionDialog_ViewBinding implements Unbinder {
  private ExperiencesRoomSelectionDialog target;

  private View view2131296393;

  private View view2131296679;

  @UiThread
  public ExperiencesRoomSelectionDialog_ViewBinding(final ExperiencesRoomSelectionDialog target,
      View source) {
    this.target = target;

    View view;
    target.listview = Utils.findRequiredViewAsType(source, R.id.list_add_rooms, "field 'listview'", ListView.class);
    target.notext = Utils.findRequiredViewAsType(source, R.id.no_text, "field 'notext'", TextView.class);
    target.desc_text = Utils.findRequiredViewAsType(source, R.id.desc_text, "field 'desc_text'", TextView.class);
    target.no_text_avilable = Utils.findOptionalViewAsType(source, R.id.no_text_avilable, "field 'no_text_avilable'", TextView.class);
    target.txt_scene_title = Utils.findRequiredViewAsType(source, R.id.txt_scene_title, "field 'txt_scene_title'", TextView.class);
    target.cir_prog = Utils.findRequiredViewAsType(source, R.id.cir_prog, "field 'cir_prog'", ProgressBar.class);
    target.img1 = Utils.findRequiredViewAsType(source, R.id.img1, "field 'img1'", ImageView.class);
    target.img2 = Utils.findRequiredViewAsType(source, R.id.img2, "field 'img2'", ImageView.class);
    target.img3 = Utils.findRequiredViewAsType(source, R.id.img3, "field 'img3'", ImageView.class);
    target.img4 = Utils.findRequiredViewAsType(source, R.id.img4, "field 'img4'", ImageView.class);
    target.img5 = Utils.findRequiredViewAsType(source, R.id.img5, "field 'img5'", ImageView.class);
    target.img6 = Utils.findRequiredViewAsType(source, R.id.img6, "field 'img6'", ImageView.class);
    target.img7 = Utils.findRequiredViewAsType(source, R.id.img7, "field 'img7'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btn_add, "field 'btn_add' and method 'btn_add'");
    target.btn_add = Utils.castView(view, R.id.btn_add, "field 'btn_add'", Button.class);
    view2131296393 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_add();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_close, "method 'img_close'");
    view2131296679 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_close();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ExperiencesRoomSelectionDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listview = null;
    target.notext = null;
    target.desc_text = null;
    target.no_text_avilable = null;
    target.txt_scene_title = null;
    target.cir_prog = null;
    target.img1 = null;
    target.img2 = null;
    target.img3 = null;
    target.img4 = null;
    target.img5 = null;
    target.img6 = null;
    target.img7 = null;
    target.btn_add = null;

    view2131296393.setOnClickListener(null);
    view2131296393 = null;
    view2131296679.setOnClickListener(null);
    view2131296679 = null;
  }
}
