// Generated code from Butter Knife. Do not modify!
package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131296795;

  private View view2131296824;

  private View view2131296810;

  private View view2131296813;

  private View view2131296820;

  private View view2131296935;

  private View view2131296823;

  private View view2131296885;

  private View view2131297414;

  private View view2131297505;

  private View view2131297517;

  private View view2131297477;

  private View view2131297388;

  private View view2131296484;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = source.findViewById(R.id.lin_climate);
    target.lin_climate = Utils.castView(view, R.id.lin_climate, "field 'lin_climate'", LinearLayout.class);
    if (view != null) {
      view2131296795 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.lin_home);
    target.lin_home = Utils.castView(view, R.id.lin_home, "field 'lin_home'", LinearLayout.class);
    if (view != null) {
      view2131296824 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.lin_discover);
    target.lin_discover = Utils.castView(view, R.id.lin_discover, "field 'lin_discover'", LinearLayout.class);
    if (view != null) {
      view2131296810 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.lin_elements);
    target.lin_elements = Utils.castView(view, R.id.lin_elements, "field 'lin_elements'", LinearLayout.class);
    if (view != null) {
      view2131296813 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.lin_experiences);
    target.lin_experiences = Utils.castView(view, R.id.lin_experiences, "field 'lin_experiences'", LinearLayout.class);
    if (view != null) {
      view2131296820 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = source.findViewById(R.id.lyt_family_name);
    target.lyt_family_name = Utils.castView(view, R.id.lyt_family_name, "field 'lyt_family_name'", LinearLayout.class);
    if (view != null) {
      view2131296935 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    target.txt_family_name = Utils.findOptionalViewAsType(source, R.id.txt_family_name, "field 'txt_family_name'", TextView.class);
    target.txt_family_initial = Utils.findOptionalViewAsType(source, R.id.txt_family_initial, "field 'txt_family_initial'", TextView.class);
    target.rel_family_inisiatl = Utils.findOptionalViewAsType(source, R.id.rel_family_inisiatl, "field 'rel_family_inisiatl'", RelativeLayout.class);
    target.txt_whole_home_light = Utils.findOptionalViewAsType(source, R.id.txt_whole_home_light, "field 'txt_whole_home_light'", TextView.class);
    target.txt_tab_climate = Utils.findOptionalViewAsType(source, R.id.txt_tab_climate, "field 'txt_tab_climate'", TextView.class);
    target.txt_tab_elements = Utils.findOptionalViewAsType(source, R.id.txt_tab_elements, "field 'txt_tab_elements'", TextView.class);
    target.txt_tab_experince = Utils.findOptionalViewAsType(source, R.id.txt_tab_experince, "field 'txt_tab_experince'", TextView.class);
    target.txt_tab_home = Utils.findOptionalViewAsType(source, R.id.txt_tab_home, "field 'txt_tab_home'", TextView.class);
    target.txt_tab_discover = Utils.findOptionalViewAsType(source, R.id.txt_tab_discover, "field 'txt_tab_discover'", TextView.class);
    target.img_tab_experinces = Utils.findOptionalViewAsType(source, R.id.img_tab_experinces, "field 'img_tab_experinces'", ImageView.class);
    target.img_tab_elements = Utils.findOptionalViewAsType(source, R.id.img_tab_elements, "field 'img_tab_elements'", ImageView.class);
    target.img_tab_home = Utils.findOptionalViewAsType(source, R.id.img_tab_home, "field 'img_tab_home'", ImageView.class);
    target.img_whole_home_light = Utils.findOptionalViewAsType(source, R.id.img_whole_home_light, "field 'img_whole_home_light'", ImageView.class);
    target.img_tab_discover = Utils.findOptionalViewAsType(source, R.id.img_tab_discover, "field 'img_tab_discover'", ImageView.class);
    target.img_tab_climate = Utils.findOptionalViewAsType(source, R.id.img_tab_climate, "field 'img_tab_climate'", ImageView.class);
    target.nav_view = Utils.findOptionalViewAsType(source, R.id.nav_view, "field 'nav_view'", NavigationView.class);
    target.lin_activity_main = Utils.findOptionalViewAsType(source, R.id.lin_activity_main, "field 'lin_activity_main'", LinearLayout.class);
    view = source.findViewById(R.id.lin_favorites);
    if (view != null) {
      view2131296823 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }
    view = Utils.findRequiredView(source, R.id.lin_whole_home_light, "method 'lin_whole_home_light'");
    view2131296885 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.lin_whole_home_light();
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_filters, "method 'onNavigationItemSelected'");
    view2131297414 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNavigationItemSelected(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_setting, "method 'onNavigationItemSelected'");
    view2131297505 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNavigationItemSelected(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_supports, "method 'onNavigationItemSelected'");
    view2131297517 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNavigationItemSelected(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_privacy, "method 'onNavigationItemSelected'");
    view2131297477 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNavigationItemSelected(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_discover, "method 'onNavigationItemSelected'");
    view2131297388 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNavigationItemSelected(p0);
      }
    });
    view = source.findViewById(R.id.close_nav);
    if (view != null) {
      view2131296484 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.close_nav();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lin_climate = null;
    target.lin_home = null;
    target.lin_discover = null;
    target.lin_elements = null;
    target.lin_experiences = null;
    target.lyt_family_name = null;
    target.txt_family_name = null;
    target.txt_family_initial = null;
    target.rel_family_inisiatl = null;
    target.txt_whole_home_light = null;
    target.txt_tab_climate = null;
    target.txt_tab_elements = null;
    target.txt_tab_experince = null;
    target.txt_tab_home = null;
    target.txt_tab_discover = null;
    target.img_tab_experinces = null;
    target.img_tab_elements = null;
    target.img_tab_home = null;
    target.img_whole_home_light = null;
    target.img_tab_discover = null;
    target.img_tab_climate = null;
    target.nav_view = null;
    target.lin_activity_main = null;

    if (view2131296795 != null) {
      view2131296795.setOnClickListener(null);
      view2131296795 = null;
    }
    if (view2131296824 != null) {
      view2131296824.setOnClickListener(null);
      view2131296824 = null;
    }
    if (view2131296810 != null) {
      view2131296810.setOnClickListener(null);
      view2131296810 = null;
    }
    if (view2131296813 != null) {
      view2131296813.setOnClickListener(null);
      view2131296813 = null;
    }
    if (view2131296820 != null) {
      view2131296820.setOnClickListener(null);
      view2131296820 = null;
    }
    if (view2131296935 != null) {
      view2131296935.setOnClickListener(null);
      view2131296935 = null;
    }
    if (view2131296823 != null) {
      view2131296823.setOnClickListener(null);
      view2131296823 = null;
    }
    view2131296885.setOnClickListener(null);
    view2131296885 = null;
    view2131297414.setOnClickListener(null);
    view2131297414 = null;
    view2131297505.setOnClickListener(null);
    view2131297505 = null;
    view2131297517.setOnClickListener(null);
    view2131297517 = null;
    view2131297477.setOnClickListener(null);
    view2131297477 = null;
    view2131297388.setOnClickListener(null);
    view2131297388 = null;
    if (view2131296484 != null) {
      view2131296484.setOnClickListener(null);
      view2131296484 = null;
    }
  }
}
