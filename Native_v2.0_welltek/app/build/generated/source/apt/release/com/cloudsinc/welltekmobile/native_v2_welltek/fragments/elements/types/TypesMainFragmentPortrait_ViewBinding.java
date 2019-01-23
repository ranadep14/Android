// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TypesMainFragmentPortrait_ViewBinding implements Unbinder {
  private TypesMainFragmentPortrait target;

  private View view2131296936;

  private View view2131296924;

  private View view2131296923;

  @UiThread
  public TypesMainFragmentPortrait_ViewBinding(final TypesMainFragmentPortrait target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lyt_lights, "field 'lyt_lights' and method 'type_light'");
    target.lyt_lights = Utils.castView(view, R.id.lyt_lights, "field 'lyt_lights'", LinearLayout.class);
    view2131296936 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.type_light();
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_blinds, "field 'lyt_blinds' and method 'type_blinds'");
    target.lyt_blinds = Utils.castView(view, R.id.lyt_blinds, "field 'lyt_blinds'", LinearLayout.class);
    view2131296924 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.type_blinds();
      }
    });
    view = Utils.findRequiredView(source, R.id.lyt_audio, "field 'lyt_audio' and method 'type_audio'");
    target.lyt_audio = Utils.castView(view, R.id.lyt_audio, "field 'lyt_audio'", LinearLayout.class);
    view2131296923 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.type_audio();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    TypesMainFragmentPortrait target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lyt_lights = null;
    target.lyt_blinds = null;
    target.lyt_audio = null;

    view2131296936.setOnClickListener(null);
    view2131296936 = null;
    view2131296924.setOnClickListener(null);
    view2131296924 = null;
    view2131296923.setOnClickListener(null);
    view2131296923 = null;
  }
}
