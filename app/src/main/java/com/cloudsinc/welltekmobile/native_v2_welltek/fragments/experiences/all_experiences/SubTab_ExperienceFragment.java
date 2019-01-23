package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.exprFavorites.FavoritesExprFragment;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


/**
 *
 * Created by developers on 17/5/17.
 */

public class SubTab_ExperienceFragment extends Fragment {

    public static SubTab_ExperienceFragment newInstance() {
        return new SubTab_ExperienceFragment();
    }
    private View v;


    @BindView(R.id.view_fav_select)View view_fav_select;
    @BindView(R.id.view_allexp_select)View view_allexp_select;

    @BindView(R.id.lyt_rel_favorites_exp)RelativeLayout lyt_rel_favorites_exp;
    @BindView(R.id.lyt_rel_all_exp)RelativeLayout lyt_rel_all_exp;


    String TAG="ExperiencesFragment";
    Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
        v=inflater.inflate(R.layout.fragment_all_experinces, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);
        ReplaceFragment.replaceFragment(this,R.id.frm_all_exprience_container, AllFavoritesFragment.newInstance());
        view_allexp_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));

        return v;
    }


    @Optional
    @OnClick({R.id.lyt_rel_all_exp, R.id.lyt_rel_favorites_exp})
    public void onClick(View view) {
        setDefaultColor();
        Fragment mfragment=null;
        switch (view.getId())
        {
            case R.id.lyt_rel_favorites_exp:
                mfragment= FavoritesExprFragment.newInstance();

                ReplaceFragment.replaceFragment(this,R.id.frm_all_exprience_container,mfragment);

              //  ReplaceFragment.replaceFragment(this,R.id.frm_sub_expr_container, FavoritesExprFragment.newInstance());
                view_fav_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                break;
            case R.id.lyt_rel_all_exp:
                mfragment= Activities_FavoritesFragment.newInstance();
                view_allexp_select.setBackgroundColor(this.getResources().getColor(R.color.dialer_selected_color));
                ReplaceFragment.replaceFragment(this,R.id.frm_all_exprience_container,mfragment);

                break;

        }
    }

    public void setDefaultColor()
    {
        view_fav_select.setBackgroundColor(this.getResources().getColor(R.color.fragment_back_color));
        view_allexp_select.setBackgroundColor(this.getResources().getColor(R.color.fragment_back_color));

    }


}