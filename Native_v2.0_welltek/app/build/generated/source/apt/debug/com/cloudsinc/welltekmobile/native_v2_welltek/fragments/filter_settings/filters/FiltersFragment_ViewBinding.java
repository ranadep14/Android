// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.filters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FiltersFragment_ViewBinding implements Unbinder {
  private FiltersFragment target;

  private View view2131296670;

  private View view2131296433;

  private View view2131296398;

  @UiThread
  public FiltersFragment_ViewBinding(final FiltersFragment target, View source) {
    this.target = target;

    View view;
    target.txtwaterDayCount = Utils.findRequiredViewAsType(source, R.id.txt_water_days, "field 'txtwaterDayCount'", TextView.class);
    target.txtairDayCount = Utils.findRequiredViewAsType(source, R.id.txt_air_days, "field 'txtairDayCount'", TextView.class);
    target.txt_air_title = Utils.findRequiredViewAsType(source, R.id.txt_air_title, "field 'txt_air_title'", TextView.class);
    target.txt_water_title = Utils.findRequiredViewAsType(source, R.id.txt_water_title, "field 'txt_water_title'", TextView.class);
    target.no_water_filter = Utils.findRequiredViewAsType(source, R.id.txt_water_txt, "field 'no_water_filter'", TextView.class);
    target.no_air_filter = Utils.findRequiredViewAsType(source, R.id.txt_air_txt, "field 'no_air_filter'", TextView.class);
    target.water_progress_bar = Utils.findRequiredViewAsType(source, R.id.water_progress_bar, "field 'water_progress_bar'", RelativeLayout.class);
    target.air_progress_bar = Utils.findRequiredViewAsType(source, R.id.air_progress_bar, "field 'air_progress_bar'", RelativeLayout.class);
    target.waterdonutProgress = Utils.findRequiredViewAsType(source, R.id.prog_water_filter, "field 'waterdonutProgress'", DonutProgress.class);
    target.airdonutProgress = Utils.findRequiredViewAsType(source, R.id.prog_air_filter, "field 'airdonutProgress'", DonutProgress.class);
    target.lyt_linn_water_filter = Utils.findRequiredViewAsType(source, R.id.lyt_linn_water_filter, "field 'lyt_linn_water_filter'", LinearLayout.class);
    target.lyt_linn_air_filter = Utils.findRequiredViewAsType(source, R.id.lyt_linn_air_filter, "field 'lyt_linn_air_filter'", LinearLayout.class);
    target.txt_no_air = Utils.findRequiredViewAsType(source, R.id.txt_no_air, "field 'txt_no_air'", TextView.class);
    target.txt_no_water = Utils.findRequiredViewAsType(source, R.id.txt_no_water, "field 'txt_no_water'", TextView.class);
    target.no_filters = Utils.findRequiredViewAsType(source, R.id.no_filters, "field 'no_filters'", TextView.class);
    target.txt_fragment_title = Utils.findOptionalViewAsType(source, R.id.txt_fragment_title, "field 'txt_fragment_title'", TextView.class);
    view = source.findViewById(R.id.img_back);
    target.img_back = Utils.castView(view, R.id.img_back, "field 'img_back'", ImageView.class);
    if (view != null) {
      view2131296670 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.img_back();
        }
      });
    }
    view = Utils.findRequiredView(source, R.id.btn_water_purchase, "method 'btn_oder_filter'");
    view2131296433 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_oder_filter();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_air_purchase, "method 'btn_oder_air_filter'");
    view2131296398 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btn_oder_air_filter();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FiltersFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtwaterDayCount = null;
    target.txtairDayCount = null;
    target.txt_air_title = null;
    target.txt_water_title = null;
    target.no_water_filter = null;
    target.no_air_filter = null;
    target.water_progress_bar = null;
    target.air_progress_bar = null;
    target.waterdonutProgress = null;
    target.airdonutProgress = null;
    target.lyt_linn_water_filter = null;
    target.lyt_linn_air_filter = null;
    target.txt_no_air = null;
    target.txt_no_water = null;
    target.no_filters = null;
    target.txt_fragment_title = null;
    target.img_back = null;

    if (view2131296670 != null) {
      view2131296670.setOnClickListener(null);
      view2131296670 = null;
    }
    view2131296433.setOnClickListener(null);
    view2131296433 = null;
    view2131296398.setOnClickListener(null);
    view2131296398 = null;
  }
}
