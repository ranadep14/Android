// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.exprFavorites;

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

public class FavoritesExprFragment_ViewBinding implements Unbinder {
  private FavoritesExprFragment target;

  @UiThread
  public FavoritesExprFragment_ViewBinding(FavoritesExprFragment target, View source) {
    this.target = target;

    target.txt_comfort_room_count = Utils.findRequiredViewAsType(source, R.id.txt_comfort_room_count, "field 'txt_comfort_room_count'", TextView.class);
    target.txt_prepare_room_count = Utils.findRequiredViewAsType(source, R.id.txt_prepare_room_count, "field 'txt_prepare_room_count'", TextView.class);
    target.txt_read_room_count = Utils.findRequiredViewAsType(source, R.id.txt_read_room_count, "field 'txt_read_room_count'", TextView.class);
    target.txt_leave_room_count = Utils.findRequiredViewAsType(source, R.id.txt_leave_room_count, "field 'txt_leave_room_count'", TextView.class);
    target.txt_watch_room_count = Utils.findRequiredViewAsType(source, R.id.txt_watch_room_count, "field 'txt_watch_room_count'", TextView.class);
    target.txt_cook_room = Utils.findRequiredViewAsType(source, R.id.txt_cook_room, "field 'txt_cook_room'", TextView.class);
    target.txt_dinner_party_room = Utils.findRequiredViewAsType(source, R.id.txt_dinner_party_room, "field 'txt_dinner_party_room'", TextView.class);
    target.img_comfort = Utils.findRequiredViewAsType(source, R.id.img_comfort, "field 'img_comfort'", ImageView.class);
    target.img_prepare = Utils.findRequiredViewAsType(source, R.id.img_prepare, "field 'img_prepare'", ImageView.class);
    target.img_watch_movie = Utils.findRequiredViewAsType(source, R.id.img_watch_movie, "field 'img_watch_movie'", ImageView.class);
    target.img_cook = Utils.findRequiredViewAsType(source, R.id.img_cook, "field 'img_cook'", ImageView.class);
    target.img_read = Utils.findRequiredViewAsType(source, R.id.img_read, "field 'img_read'", ImageView.class);
    target.img_leave_home = Utils.findRequiredViewAsType(source, R.id.img_leave_home, "field 'img_leave_home'", ImageView.class);
    target.img_dinner = Utils.findRequiredViewAsType(source, R.id.img_dinner, "field 'img_dinner'", ImageView.class);
    target.img_entertain = Utils.findRequiredViewAsType(source, R.id.img_entertain, "field 'img_entertain'", ImageView.class);
    target.img_mid_break = Utils.findRequiredViewAsType(source, R.id.img_mid_break, "field 'img_mid_break'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoritesExprFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_comfort_room_count = null;
    target.txt_prepare_room_count = null;
    target.txt_read_room_count = null;
    target.txt_leave_room_count = null;
    target.txt_watch_room_count = null;
    target.txt_cook_room = null;
    target.txt_dinner_party_room = null;
    target.img_comfort = null;
    target.img_prepare = null;
    target.img_watch_movie = null;
    target.img_cook = null;
    target.img_read = null;
    target.img_leave_home = null;
    target.img_dinner = null;
    target.img_entertain = null;
    target.img_mid_break = null;
  }
}
