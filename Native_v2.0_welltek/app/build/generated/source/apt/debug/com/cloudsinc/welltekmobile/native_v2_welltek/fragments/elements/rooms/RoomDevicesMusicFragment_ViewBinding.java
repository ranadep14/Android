// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomDevicesMusicFragment_ViewBinding implements Unbinder {
  private RoomDevicesMusicFragment target;

  private View view2131296710;

  private View view2131296711;

  private View view2131296709;

  private View view2131296713;

  private View view2131296712;

  private View view2131296708;

  private View view2131296739;

  private View view2131296670;

  private View view2131296407;

  private View view2131296428;

  private View view2131296408;

  private View view2131296429;

  @UiThread
  public RoomDevicesMusicFragment_ViewBinding(final RoomDevicesMusicFragment target, View source) {
    this.target = target;

    View view;
    target.rec_default_music = Utils.findRequiredViewAsType(source, R.id.rec_default_music, "field 'rec_default_music'", RecyclerView.class);
    target.rec_radio_stations = Utils.findRequiredViewAsType(source, R.id.rec_radio_stations, "field 'rec_radio_stations'", RecyclerView.class);
    target.rec_playlists = Utils.findRequiredViewAsType(source, R.id.rec_playlists, "field 'rec_playlists'", RecyclerView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.img_room_type = Utils.findRequiredViewAsType(source, R.id.img_room_type, "field 'img_room_type'", ImageView.class);
    target.img_whole_home = Utils.findRequiredViewAsType(source, R.id.img_whole_home, "field 'img_whole_home'", CircularImageView.class);
    target.lin_default_music = Utils.findRequiredViewAsType(source, R.id.lin_default_music, "field 'lin_default_music'", LinearLayout.class);
    target.lin_playlists = Utils.findRequiredViewAsType(source, R.id.lin_playlists, "field 'lin_playlists'", LinearLayout.class);
    target.lin_radio_stations = Utils.findRequiredViewAsType(source, R.id.lin_radio_stations, "field 'lin_radio_stations'", LinearLayout.class);
    target.lin_music_panel = Utils.findRequiredViewAsType(source, R.id.lin_music_panel, "field 'lin_music_panel'", LinearLayout.class);
    target.txt_no_devices = Utils.findRequiredViewAsType(source, R.id.txt_no_devices, "field 'txt_no_devices'", TextView.class);
    target.txt_muisc_artist_name = Utils.findRequiredViewAsType(source, R.id.txt_muisc_artist_name, "field 'txt_muisc_artist_name'", TextView.class);
    target.txt_music_title = Utils.findRequiredViewAsType(source, R.id.txt_music_title, "field 'txt_music_title'", TextView.class);
    target.seek_music_vol = Utils.findRequiredViewAsType(source, R.id.seek_music_vol, "field 'seek_music_vol'", SeekBar.class);
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
    target.lin_no_playlist = Utils.findRequiredViewAsType(source, R.id.lin_no_playlist, "field 'lin_no_playlist'", LinearLayout.class);
    target.lin_volume = Utils.findRequiredViewAsType(source, R.id.lin_volume, "field 'lin_volume'", LinearLayout.class);
    target.lin_controls = Utils.findRequiredViewAsType(source, R.id.lin_controls, "field 'lin_controls'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.img_up_music, "field 'img_up_music' and method 'img_up_music'");
    target.img_up_music = Utils.castView(view, R.id.img_up_music, "field 'img_up_music'", ImageView.class);
    view2131296739 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_up_music();
      }
    });
    target.txt_fragment_title = Utils.findOptionalViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
    target.lin_dawn_running = Utils.findRequiredViewAsType(source, R.id.lin_dawn_running, "field 'lin_dawn_running'", LinearLayout.class);
    target.lin_sleep_running = Utils.findRequiredViewAsType(source, R.id.lin_sleep_running, "field 'lin_sleep_running'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_dawn_cancle, "method 'btn_dawn_cancle'");
    view2131296407 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_dawn_cancle();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_sleep_cancle, "method 'btn_sleep_cancle'");
    view2131296428 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_sleep_cancle();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_dawn_confirm, "method 'btn_dawn_confirm'");
    view2131296408 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_dawn_confirm();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_sleep_confirm, "method 'btn_sleep_confirm'");
    view2131296429 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_sleep_confirm();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomDevicesMusicFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rec_default_music = null;
    target.rec_radio_stations = null;
    target.rec_playlists = null;
    target.rel_loading = null;
    target.txt_room_title = null;
    target.img_room_type = null;
    target.img_whole_home = null;
    target.lin_default_music = null;
    target.lin_playlists = null;
    target.lin_radio_stations = null;
    target.lin_music_panel = null;
    target.txt_no_devices = null;
    target.txt_muisc_artist_name = null;
    target.txt_music_title = null;
    target.seek_music_vol = null;
    target.img_music_play_pause = null;
    target.img_music_prev = null;
    target.img_music_next = null;
    target.img_album_image = null;
    target.img_music_shuffule = null;
    target.img_music_repeat = null;
    target.img_music_mute = null;
    target.lin_no_playlist = null;
    target.lin_volume = null;
    target.lin_controls = null;
    target.img_up_music = null;
    target.txt_fragment_title = null;
    target.img_back = null;
    target.lin_dawn_running = null;
    target.lin_sleep_running = null;

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
    view2131296739.setOnClickListener(null);
    view2131296739 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
    view2131296407.setOnClickListener(null);
    view2131296407 = null;
    view2131296428.setOnClickListener(null);
    view2131296428 = null;
    view2131296408.setOnClickListener(null);
    view2131296408 = null;
    view2131296429.setOnClickListener(null);
    view2131296429 = null;
  }
}
