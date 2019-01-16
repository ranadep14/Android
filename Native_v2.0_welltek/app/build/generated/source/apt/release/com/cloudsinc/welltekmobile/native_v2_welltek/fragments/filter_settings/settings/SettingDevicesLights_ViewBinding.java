// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingDevicesLights_ViewBinding implements Unbinder {
  private SettingDevicesLights target;

  private View view2131297433;

  private View view2131296891;

  private View view2131296863;

  private View view2131296826;

  private View view2131296866;

  private View view2131297167;

  private View view2131296670;

  @UiThread
  public SettingDevicesLights_ViewBinding(final SettingDevicesLights target, View source) {
    this.target = target;

    View view;
    target.txt_room = Utils.findRequiredViewAsType(source, R.id.txt_room, "field 'txt_room'", TextView.class);
    target.txt_device_name = Utils.findRequiredViewAsType(source, R.id.txt_dev_name, "field 'txt_device_name'", TextView.class);
    view = Utils.findRequiredView(source, R.id.txt_install_date, "field 'txt_install_date' and method 'txt_clock'");
    target.txt_install_date = Utils.castView(view, R.id.txt_install_date, "field 'txt_install_date'", TextView.class);
    view2131297433 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_clock();
      }
    });
    target.txt_scenes = Utils.findRequiredViewAsType(source, R.id.txt_scenes, "field 'txt_scenes'", TextView.class);
    target.edt_DevName = Utils.findRequiredViewAsType(source, R.id.edt_DevName, "field 'edt_DevName'", EditText.class);
    target.lin_other_devices = Utils.findRequiredViewAsType(source, R.id.lin_other_devices, "field 'lin_other_devices'", LinearLayout.class);
    target.lin_water_air = Utils.findRequiredViewAsType(source, R.id.lin_water_air, "field 'lin_water_air'", LinearLayout.class);
    target.lin_sensor = Utils.findRequiredViewAsType(source, R.id.lin_sensor, "field 'lin_sensor'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.lin_zone, "field 'lin_zone' and method 'lin_zone'");
    target.lin_zone = Utils.castView(view, R.id.lin_zone, "field 'lin_zone'", LinearLayout.class);
    view2131296891 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_zone();
      }
    });
    view = Utils.findRequiredView(source, R.id.lin_scence, "field 'lin_scence' and method 'onScenes'");
    target.lin_scence = Utils.castView(view, R.id.lin_scence, "field 'lin_scence'", LinearLayout.class);
    view2131296863 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onScenes();
      }
    });
    target.txt_zone = Utils.findRequiredViewAsType(source, R.id.txt_zone, "field 'txt_zone'", TextView.class);
    target.txt_air_water_name = Utils.findRequiredViewAsType(source, R.id.txt_air_water_name, "field 'txt_air_water_name'", TextView.class);
    target.txt_fragment_title = Utils.findRequiredViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    target.txt_save = Utils.findRequiredViewAsType(source, R.id.txt_save, "field 'txt_save'", TextView.class);
    target.txt_sensor_name = Utils.findRequiredViewAsType(source, R.id.txt_sensor_name, "field 'txt_sensor_name'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lin_hvac_zone, "field 'lin_hvac_zone' and method 'lin_hvac_zone'");
    target.lin_hvac_zone = Utils.castView(view, R.id.lin_hvac_zone, "field 'lin_hvac_zone'", LinearLayout.class);
    view2131296826 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_hvac_zone();
      }
    });
    target.txt_hvac_zone = Utils.findRequiredViewAsType(source, R.id.txt_hvac_zone, "field 'txt_hvac_zone'", TextView.class);
    target.txt_sensor_room = Utils.findRequiredViewAsType(source, R.id.txt_sensor_room, "field 'txt_sensor_room'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lin_sensor_room, "field 'lin_sensor_room' and method 'lin_sensor_room'");
    target.lin_sensor_room = Utils.castView(view, R.id.lin_sensor_room, "field 'lin_sensor_room'", LinearLayout.class);
    view2131296866 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_sensor_room();
      }
    });
    view = Utils.findRequiredView(source, R.id.roomlyt, "field 'lin_room' and method 'txt_room'");
    target.lin_room = Utils.castView(view, R.id.roomlyt, "field 'lin_room'", LinearLayout.class);
    view2131297167 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.txt_room();
      }
    });
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
    SettingDevicesLights target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_room = null;
    target.txt_device_name = null;
    target.txt_install_date = null;
    target.txt_scenes = null;
    target.edt_DevName = null;
    target.lin_other_devices = null;
    target.lin_water_air = null;
    target.lin_sensor = null;
    target.lin_zone = null;
    target.lin_scence = null;
    target.txt_zone = null;
    target.txt_air_water_name = null;
    target.txt_fragment_title = null;
    target.txt_save = null;
    target.txt_sensor_name = null;
    target.lin_hvac_zone = null;
    target.txt_hvac_zone = null;
    target.txt_sensor_room = null;
    target.lin_sensor_room = null;
    target.lin_room = null;

    view2131297433.setOnClickListener(null);
    view2131297433 = null;
    view2131296891.setOnClickListener(null);
    view2131296891 = null;
    view2131296863.setOnClickListener(null);
    view2131296863 = null;
    view2131296826.setOnClickListener(null);
    view2131296826 = null;
    view2131296866.setOnClickListener(null);
    view2131296866 = null;
    view2131297167.setOnClickListener(null);
    view2131297167 = null;
    view2131296670.setOnClickListener(null);
    view2131296670 = null;
  }
}
