// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Wake_PlaylistSound_ViewBinding implements Unbinder {
  private Wake_PlaylistSound target;

  private View view2131296399;

  private View view2131297104;

  private View view2131296679;

  @UiThread
  public Wake_PlaylistSound_ViewBinding(final Wake_PlaylistSound target, View source) {
    this.target = target;

    View view;
    target.lstplaylist = Utils.findRequiredViewAsType(source, R.id.list_sound_mob, "field 'lstplaylist'", ListView.class);
    target.no_data_txt = Utils.findRequiredViewAsType(source, R.id.no_data_txt, "field 'no_data_txt'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_apply, "field 'btn_apply' and method 'btn_apply'");
    target.btn_apply = Utils.castView(view, R.id.btn_apply, "field 'btn_apply'", Button.class);
    view2131296399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_apply();
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_playlist, "field 'rb_playlist' and method 'rb_playlist'");
    target.rb_playlist = Utils.castView(view, R.id.rb_playlist, "field 'rb_playlist'", RadioButton.class);
    view2131297104 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.rb_playlist();
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
    Wake_PlaylistSound target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lstplaylist = null;
    target.no_data_txt = null;
    target.btn_apply = null;
    target.rb_playlist = null;

    view2131296399.setOnClickListener(null);
    view2131296399 = null;
    view2131297104.setOnClickListener(null);
    view2131297104 = null;
    view2131296679.setOnClickListener(null);
    view2131296679 = null;
  }
}
