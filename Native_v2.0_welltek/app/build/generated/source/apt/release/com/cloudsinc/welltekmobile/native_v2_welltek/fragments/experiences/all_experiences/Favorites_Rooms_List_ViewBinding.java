// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Favorites_Rooms_List_ViewBinding implements Unbinder {
  private Favorites_Rooms_List target;

  @UiThread
  public Favorites_Rooms_List_ViewBinding(Favorites_Rooms_List target, View source) {
    this.target = target;

    target.listview = Utils.findRequiredViewAsType(source, R.id.list_add_rooms, "field 'listview'", ListView.class);
    target.notext = Utils.findRequiredViewAsType(source, R.id.no_text, "field 'notext'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Favorites_Rooms_List target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listview = null;
    target.notext = null;
  }
}
