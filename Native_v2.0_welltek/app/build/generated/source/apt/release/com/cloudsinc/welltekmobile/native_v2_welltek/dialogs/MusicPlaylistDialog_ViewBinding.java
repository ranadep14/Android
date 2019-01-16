// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MusicPlaylistDialog_ViewBinding implements Unbinder {
  private MusicPlaylistDialog target;

  private View view2131296679;

  @UiThread
  public MusicPlaylistDialog_ViewBinding(final MusicPlaylistDialog target, View source) {
    this.target = target;

    View view;
    target.lstplaylist = Utils.findRequiredViewAsType(source, R.id.lstplaylist, "field 'lstplaylist'", ListView.class);
    target.txt_no_playlist = Utils.findRequiredViewAsType(source, R.id.txt_no_playlist, "field 'txt_no_playlist'", TextView.class);
    target.circular_progress_bar = Utils.findRequiredViewAsType(source, R.id.circular_progress_bar, "field 'circular_progress_bar'", ProgressBar.class);
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
    MusicPlaylistDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lstplaylist = null;
    target.txt_no_playlist = null;
    target.circular_progress_bar = null;

    view2131296679.setOnClickListener(null);
    view2131296679 = null;
  }
}
