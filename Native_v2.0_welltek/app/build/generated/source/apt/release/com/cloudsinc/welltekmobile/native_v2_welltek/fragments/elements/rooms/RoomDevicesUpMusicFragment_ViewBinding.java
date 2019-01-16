// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomDevicesUpMusicFragment_ViewBinding implements Unbinder {
  private RoomDevicesUpMusicFragment target;

  private View view2131296710;

  private View view2131296711;

  private View view2131296709;

  private View view2131296713;

  private View view2131296712;

  private View view2131296708;

  private View view2131296670;

  @UiThread
  public RoomDevicesUpMusicFragment_ViewBinding(final RoomDevicesUpMusicFragment target,
      View source) {
    this.target = target;

    View view;
    target.txt_muisc_artist_name = Utils.findRequiredViewAsType(source, R.id.txt_muisc_artist_name, "field 'txt_muisc_artist_name'", TextView.class);
    target.txt_music_title = Utils.findRequiredViewAsType(source, R.id.txt_music_title, "field 'txt_music_title'", TextView.class);
    target.seek_music_vol = Utils.findRequiredViewAsType(source, R.id.seek_music_vol, "field 'seek_music_vol'", SeekBar.class);
    target.song_progress = Utils.findRequiredViewAsType(source, R.id.song_progress, "field 'song_progress'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.img_music_play_pause, "field 'img_music_play_pause' and method 'music_play_pause'");
    target.img_music_play_pause = Utils.castView(view, R.id.img_music_play_pause, "field 'img_music_play_pause'", ImageView.class);
    view2131296710 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.music_play_pause();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_music_prev, "field 'img_music_prev' and method 'img_music_prev'");
    target.img_music_prev = Utils.castView(view, R.id.img_music_prev, "field 'img_music_prev'", ImageView.class);
    view2131296711 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_music_prev();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_music_next, "field 'img_music_next' and method 'img_next'");
    target.img_music_next = Utils.castView(view, R.id.img_music_next, "field 'img_music_next'", ImageView.class);
    view2131296709 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_next();
      }
    });
    target.img_album_image = Utils.findRequiredViewAsType(source, R.id.img_album_image, "field 'img_album_image'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.img_music_shuffule, "field 'img_music_shuffule' and method 'img_music_shuffule'");
    target.img_music_shuffule = Utils.castView(view, R.id.img_music_shuffule, "field 'img_music_shuffule'", ImageView.class);
    view2131296713 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_music_shuffule();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_music_repeat, "field 'img_music_repeat' and method 'img_new'");
    target.img_music_repeat = Utils.castView(view, R.id.img_music_repeat, "field 'img_music_repeat'", ImageView.class);
    view2131296712 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_new();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_music_mute, "field 'img_music_mute' and method 'img_music_mute'");
    target.img_music_mute = Utils.castView(view, R.id.img_music_mute, "field 'img_music_mute'", ImageView.class);
    view2131296708 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_music_mute();
      }
    });
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomDevicesUpMusicFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_muisc_artist_name = null;
    target.txt_music_title = null;
    target.seek_music_vol = null;
    target.song_progress = null;
    target.img_music_play_pause = null;
    target.img_music_prev = null;
    target.img_music_next = null;
    target.img_album_image = null;
    target.img_music_shuffule = null;
    target.img_music_repeat = null;
    target.img_music_mute = null;
    target.txt_fragment_title = null;

    view2131296710.setOnClickListener(null);
    view2131296710 = null;
    view2131296711.setOnClickListener(null);
    view2131296711 = null;
    view2131296709.setOnClickListener(null);
    view2131296709 = null;
    view2131296713.setOnClickListener(null);
    view2131296713 = null;
    view2131296712.setOnClickListener(null);
    view2131296712 = null;
    view2131296708.setOnClickListener(null);
    view2131296708 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
