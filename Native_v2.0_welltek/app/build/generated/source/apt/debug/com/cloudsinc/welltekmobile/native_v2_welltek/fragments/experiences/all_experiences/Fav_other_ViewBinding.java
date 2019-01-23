// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Fav_other_ViewBinding implements Unbinder {
  private Fav_other target;

  @UiThread
  public Fav_other_ViewBinding(Fav_other target, View source) {
    this.target = target;

    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.img_room_type = Utils.findRequiredViewAsType(source, R.id.img_room_type, "field 'img_room_type'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Fav_other target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_room_title = null;
    target.img_room_type = null;
  }
}
