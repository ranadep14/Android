// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.rooms;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.wonderkiln.blurkit.BlurLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomDevicesFragment_ViewBinding implements Unbinder {
  private RoomDevicesFragment target;

  private View view2131296669;

  private View view2131296745;

  private View view2131296742;

  private View view2131296746;

  private View view2131296750;

  private View view2131296715;

  private View view2131296434;

  private View view2131296435;

  private View view2131296436;

  private View view2131296748;

  private View view2131297610;

  private View view2131296749;

  private View view2131296747;

  private View view2131296670;

  private View view2131296407;

  private View view2131296428;

  private View view2131296408;

  private View view2131296429;

  @UiThread
  public RoomDevicesFragment_ViewBinding(final RoomDevicesFragment target, View source) {
    this.target = target;

    View view;
    target.lin_light_list = Utils.findRequiredViewAsType(source, R.id.lin_light_list, "field 'lin_light_list'", LinearLayout.class);
    target.lin_light_group_list = Utils.findRequiredViewAsType(source, R.id.lin_light_group_list, "field 'lin_light_group_list'", LinearLayout.class);
    target.lin_hvac_list = Utils.findRequiredViewAsType(source, R.id.lin_hvac_list, "field 'lin_hvac_list'", LinearLayout.class);
    target.lin_music_list = Utils.findRequiredViewAsType(source, R.id.lin_music_list, "field 'lin_music_list'", LinearLayout.class);
    target.lin_blind_list = Utils.findRequiredViewAsType(source, R.id.lin_blind_list, "field 'lin_blind_list'", LinearLayout.class);
    target.lin_blind_group_list = Utils.findRequiredViewAsType(source, R.id.lin_blind_group_list, "field 'lin_blind_group_list'", LinearLayout.class);
    target.rec_lights = Utils.findRequiredViewAsType(source, R.id.rec_lights, "field 'rec_lights'", RecyclerView.class);
    target.rec_blinds = Utils.findRequiredViewAsType(source, R.id.rec_blinds, "field 'rec_blinds'", RecyclerView.class);
    target.rec_music = Utils.findRequiredViewAsType(source, R.id.rec_audio, "field 'rec_music'", RecyclerView.class);
    target.rec_blinds_group = Utils.findRequiredViewAsType(source, R.id.rec_blinds_group, "field 'rec_blinds_group'", RecyclerView.class);
    target.rec_lights_group = Utils.findRequiredViewAsType(source, R.id.rec_lights_group, "field 'rec_lights_group'", RecyclerView.class);
    target.rec_hvac = Utils.findRequiredViewAsType(source, R.id.rec_hvac, "field 'rec_hvac'", RecyclerView.class);
    target.txt_room_title = Utils.findRequiredViewAsType(source, R.id.txt_room_title, "field 'txt_room_title'", TextView.class);
    target.img_room_type = Utils.findRequiredViewAsType(source, R.id.img_room_type, "field 'img_room_type'", ImageView.class);
    target.img_whole_home = Utils.findRequiredViewAsType(source, R.id.img_whole_home, "field 'img_whole_home'", CircularImageView.class);
    target.txt_room_id = Utils.findRequiredViewAsType(source, R.id.txt_room_id, "field 'txt_room_id'", TextView.class);
    target.txt_room_type = Utils.findRequiredViewAsType(source, R.id.txt_room_type, "field 'txt_room_type'", TextView.class);
    target.lin_device_container = Utils.findRequiredViewAsType(source, R.id.lin_device_container, "field 'lin_device_container'", LinearLayout.class);
    target.lin_dawn_running = Utils.findRequiredViewAsType(source, R.id.lin_dawn_running, "field 'lin_dawn_running'", LinearLayout.class);
    target.blurLayout = Utils.findRequiredViewAsType(source, R.id.blurLayout, "field 'blurLayout'", BlurLayout.class);
    target.activity_main = Utils.findRequiredViewAsType(source, R.id.activity_main, "field 'activity_main'", RelativeLayout.class);
    target.lin_sleep_running = Utils.findRequiredViewAsType(source, R.id.lin_sleep_running, "field 'lin_sleep_running'", LinearLayout.class);
    target.whole_on_off = Utils.findRequiredViewAsType(source, R.id.whole_on_off, "field 'whole_on_off'", Switch.class);
    target.seek_all_light_brightness = Utils.findRequiredViewAsType(source, R.id.seek_all_light_brightness, "field 'seek_all_light_brightness'", SeekBar.class);
    view = Utils.findRequiredView(source, R.id.img_all_fav, "field 'img_all_fav' and method 'img_all_fav'");
    target.img_all_fav = Utils.castView(view, R.id.img_all_fav, "field 'img_all_fav'", ImageView.class);
    view2131296669 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_all_fav();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_whole_light_add_fav, "field 'img_whole_light_add_fav' and method 'img_whole_light_add_fav'");
    target.img_whole_light_add_fav = Utils.castView(view, R.id.img_whole_light_add_fav, "field 'img_whole_light_add_fav'", ImageView.class);
    view2131296745 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_light_add_fav();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_whole_blind_add_fav, "field 'img_whole_blind_add_fav' and method 'img_whole_blind_add_fav'");
    target.img_whole_blind_add_fav = Utils.castView(view, R.id.img_whole_blind_add_fav, "field 'img_whole_blind_add_fav'", ImageView.class);
    view2131296742 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_blind_add_fav();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_whole_music_add_fav, "field 'img_whole_music_add_fav' and method 'img_whole_music_add_fav'");
    target.img_whole_music_add_fav = Utils.castView(view, R.id.img_whole_music_add_fav, "field 'img_whole_music_add_fav'", ImageView.class);
    view2131296746 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_music_add_fav();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_whole_playlist, "field 'img_whole_playlist' and method 'img_whole_playlist'");
    target.img_whole_playlist = Utils.castView(view, R.id.img_whole_playlist, "field 'img_whole_playlist'", ImageView.class);
    view2131296750 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_playlist();
      }
    });
    target.txt_no_devices = Utils.findRequiredViewAsType(source, R.id.txt_no_devices, "field 'txt_no_devices'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_option, "field 'img_option' and method 'img_option'");
    target.img_option = Utils.castView(view, R.id.img_option, "field 'img_option'", ImageView.class);
    view2131296715 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_option();
      }
    });
    target.scroll_container = Utils.findOptionalViewAsType(source, R.id.scroll_container, "field 'scroll_container'", NestedScrollView.class);
    target.lin_whole_home_blind = Utils.findRequiredViewAsType(source, R.id.lin_whole_home_blind, "field 'lin_whole_home_blind'", LinearLayout.class);
    target.lin_whole_home_music = Utils.findRequiredViewAsType(source, R.id.lin_whole_home_music, "field 'lin_whole_home_music'", LinearLayout.class);
    target.lin_whole_home_light = Utils.findRequiredViewAsType(source, R.id.lin_whole_home_light, "field 'lin_whole_home_light'", LinearLayout.class);
    target.lin_whole_home_light_offline = Utils.findRequiredViewAsType(source, R.id.lin_whole_home_light_offline, "field 'lin_whole_home_light_offline'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_whole_close, "field 'btn_whole_close' and method 'btn_whole_close'");
    target.btn_whole_close = Utils.castView(view, R.id.btn_whole_close, "field 'btn_whole_close'", Button.class);
    view2131296434 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_whole_close();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_whole_open, "field 'btn_whole_open' and method 'btn_whole_open'");
    target.btn_whole_open = Utils.castView(view, R.id.btn_whole_open, "field 'btn_whole_open'", Button.class);
    view2131296435 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_whole_open();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_whole_stop, "field 'btn_whole_stop' and method 'btn_whole_stop'");
    target.btn_whole_stop = Utils.castView(view, R.id.btn_whole_stop, "field 'btn_whole_stop'", Button.class);
    view2131296436 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_whole_stop();
      }
    });
    target.rel_visiblity = Utils.findRequiredViewAsType(source, R.id.rel_visiblity, "field 'rel_visiblity'", RelativeLayout.class);
    target.txt_whole_music_title = Utils.findRequiredViewAsType(source, R.id.txt_whole_music_title, "field 'txt_whole_music_title'", TextView.class);
    target.txt_whole_muisc_artist_name = Utils.findRequiredViewAsType(source, R.id.txt_whole_muisc_artist_name, "field 'txt_whole_muisc_artist_name'", TextView.class);
    target.img_whole_album_image = Utils.findRequiredViewAsType(source, R.id.img_whole_album_image, "field 'img_whole_album_image'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.img_whole_music_play_pause, "field 'img_whole_music_play_pause' and method 'img_whole_music_play_pause'");
    target.img_whole_music_play_pause = Utils.castView(view, R.id.img_whole_music_play_pause, "field 'img_whole_music_play_pause'", ImageView.class);
    view2131296748 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_music_play_pause();
      }
    });
    view = Utils.findRequiredView(source, R.id.whole_img_option, "field 'whole_img_option' and method 'whole_img_option'");
    target.whole_img_option = Utils.castView(view, R.id.whole_img_option, "field 'whole_img_option'", ImageView.class);
    view2131297610 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.whole_img_option();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_whole_music_prev, "field 'img_whole_music_prev' and method 'img_whole_music_prev'");
    target.img_whole_music_prev = Utils.castView(view, R.id.img_whole_music_prev, "field 'img_whole_music_prev'", ImageView.class);
    view2131296749 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_music_prev();
      }
    });
    view = Utils.findRequiredView(source, R.id.img_whole_music_next, "field 'img_whole_music_next' and method 'img_whole_music_next'");
    target.img_whole_music_next = Utils.castView(view, R.id.img_whole_music_next, "field 'img_whole_music_next'", ImageView.class);
    view2131296747 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_whole_music_next();
      }
    });
    target.txt_sleep_desc = Utils.findRequiredViewAsType(source, R.id.txt_sleep_desc, "field 'txt_sleep_desc'", TextView.class);
    target.rel_loading = Utils.findRequiredViewAsType(source, R.id.rel_loading, "field 'rel_loading'", RelativeLayout.class);
    target.circular_whole_home_on_off = Utils.findRequiredViewAsType(source, R.id.circular_whole_home_on_off, "field 'circular_whole_home_on_off'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'img_back'");
    view2131296670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.img_back();
      }
    });
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
    RoomDevicesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lin_light_list = null;
    target.lin_light_group_list = null;
    target.lin_hvac_list = null;
    target.lin_music_list = null;
    target.lin_blind_list = null;
    target.lin_blind_group_list = null;
    target.rec_lights = null;
    target.rec_blinds = null;
    target.rec_music = null;
    target.rec_blinds_group = null;
    target.rec_lights_group = null;
    target.rec_hvac = null;
    target.txt_room_title = null;
    target.img_room_type = null;
    target.img_whole_home = null;
    target.txt_room_id = null;
    target.txt_room_type = null;
    target.lin_device_container = null;
    target.lin_dawn_running = null;
    target.blurLayout = null;
    target.activity_main = null;
    target.lin_sleep_running = null;
    target.whole_on_off = null;
    target.seek_all_light_brightness = null;
    target.img_all_fav = null;
    target.img_whole_light_add_fav = null;
    target.img_whole_blind_add_fav = null;
    target.img_whole_music_add_fav = null;
    target.img_whole_playlist = null;
    target.txt_no_devices = null;
    target.img_option = null;
    target.scroll_container = null;
    target.lin_whole_home_blind = null;
    target.lin_whole_home_music = null;
    target.lin_whole_home_light = null;
    target.lin_whole_home_light_offline = null;
    target.btn_whole_close = null;
    target.btn_whole_open = null;
    target.btn_whole_stop = null;
    target.rel_visiblity = null;
    target.txt_whole_music_title = null;
    target.txt_whole_muisc_artist_name = null;
    target.img_whole_album_image = null;
    target.img_whole_music_play_pause = null;
    target.whole_img_option = null;
    target.img_whole_music_prev = null;
    target.img_whole_music_next = null;
    target.txt_sleep_desc = null;
    target.rel_loading = null;
    target.circular_whole_home_on_off = null;

    view2131296669.setOnClickListener(null);
    view2131296669 = null;
    view2131296745.setOnClickListener(null);
    view2131296745 = null;
    view2131296742.setOnClickListener(null);
    view2131296742 = null;
    view2131296746.setOnClickListener(null);
    view2131296746 = null;
    view2131296750.setOnClickListener(null);
    view2131296750 = null;
    view2131296715.setOnClickListener(null);
    view2131296715 = null;
    view2131296434.setOnClickListener(null);
    view2131296434 = null;
    view2131296435.setOnClickListener(null);
    view2131296435 = null;
    view2131296436.setOnClickListener(null);
    view2131296436 = null;
    view2131296748.setOnClickListener(null);
    view2131296748 = null;
    view2131297610.setOnClickListener(null);
    view2131297610 = null;
    view2131296749.setOnClickListener(null);
    view2131296749 = null;
    view2131296747.setOnClickListener(null);
    view2131296747 = null;
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
