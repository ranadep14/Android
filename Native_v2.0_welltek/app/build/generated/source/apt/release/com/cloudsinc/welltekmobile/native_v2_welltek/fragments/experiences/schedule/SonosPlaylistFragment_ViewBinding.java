// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.schedule;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SonosPlaylistFragment_ViewBinding implements Unbinder {
  private SonosPlaylistFragment target;

  private View view2131297097;

  private View view2131296399;

  private View view2131296679;

  @UiThread
  public SonosPlaylistFragment_ViewBinding(final SonosPlaylistFragment target, View source) {
    this.target = target;

    View view;
    target.list_sound = Utils.findRequiredViewAsType(source, R.id.play_list_sound, "field 'list_sound'", ListView.class);
    view = Utils.findRequiredView(source, R.id.rb_playlist, "field 'rb_playlist' and method 'rb_playlist'");
    target.rb_playlist = Utils.castView(view, R.id.rb_playlist, "field 'rb_playlist'", RadioButton.class);
    view2131297097 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.rb_playlist();
      }
    });
    target.no_text = Utils.findRequiredViewAsType(source, R.id.no_text, "field 'no_text'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_apply, "field 'btn_apply' and method 'btn_apply'");
    target.btn_apply = Utils.castView(view, R.id.btn_apply, "field 'btn_apply'", TextView.class);
    view2131296399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_apply();
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
    SonosPlaylistFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.list_sound = null;
    target.rb_playlist = null;
    target.no_text = null;
    target.btn_apply = null;

    view2131297097.setOnClickListener(null);
    view2131297097 = null;
    view2131296399.setOnClickListener(null);
    view2131296399 = null;
    view2131296679.setOnClickListener(null);
    view2131296679 = null;
  }
}
