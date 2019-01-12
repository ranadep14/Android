package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static com.google.android.gms.internal.zzagz.runOnUiThread;


public class HomeScreenGifsFragment extends Fragment {
    public static HomeScreenGifsFragment newInstance() {
        return new HomeScreenGifsFragment();
    }

    View v;
    private boolean gif_flag=false;

    ImageView imageView;
    GlideDrawableImageViewTarget imageViewTarget;
    @BindView(R.id.view1)View view1;
    @BindView(R.id.view2)View view2;
    @BindView(R.id.view3)View view3;
    @BindView(R.id.view4)View view4i;

    @BindView(R.id.gif_title)
    TextView gif_title;

    @BindView(R.id.rel_lyt)
    RelativeLayout rel_lyt;
    @BindView(R.id.txt_no_action)
    TextView txt_no_action;
    @BindView(R.id.current_action_prog_bar)
    ProgressBar current_action_prog_bar;

    boolean circadian_flag=false,air_flag=false,water_flag=false,audio_flag=false;

    private Timer timer_gif;
    @BindView(R.id.lines)LinearLayout lines;
    int count_for_flag=0;
    boolean flag_gif=false;
    int delay=0;
    private Context mcontext;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    pl.droidsonroids.gif.GifImageView webView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_home_screen_gifs, container, false);
        mcontext = v.getContext();
      //  current_action_prog_bar=(ProgressBar)v.findViewById(R.id.current_action_prog_bar);
        ButterKnife.bind(this, v);
        imageView= v.findViewById(R.id.home_screen_sliding_gif);
        imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        webView= v.findViewById(R.id.web_view_gif);

        timer_gif = new Timer();

        setSubcriber();

        gifrotation();
        return v;
    }

    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("" + App.getRoomData());


                    }
                }
        );


        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String string) {


                Log.e("WhereIm",""+this.getClass().getName()+"----"+string);

                try {

                    if(App.getcurrent_actions()!=null) {
                        gif_flag=true;
                        JSONArray jsonArray = App.getcurrent_actions().getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                      //  current_action_prog_bar.setVisibility(View.GONE);
                        rel_lyt.setVisibility(View.VISIBLE);
                        txt_no_action.setVisibility(View.GONE);
                        lines.setVisibility(View.VISIBLE);
                        gif_title.setVisibility(View.VISIBLE);

                        int visible_cnt = 0;
                        circadian_flag = false;
                        audio_flag = false;
                        air_flag = false;
                        water_flag = false;
                        Log.e("Current_Actions", "" + App.getcurrent_actions());

                        if (jsonObject.has("circadian_status") && jsonObject.getBoolean("circadian_status") == true) {
                            circadian_flag = true;
                        } else {
                            view1.setVisibility(View.GONE);
                            visible_cnt++;

                        }

                        if (jsonObject.has("air_status") && jsonObject.getBoolean("air_status") == true) {
                            air_flag = true;
                        } else {
                            view3.setVisibility(View.GONE);
                            visible_cnt++;
                        }
                        if (jsonObject.has("water_status") && jsonObject.getBoolean("water_status") == true) {
                            view2.setVisibility(View.GONE);
                            water_flag = true;

                        } else {
                            visible_cnt++;
                        }
                        if (jsonObject.has("sound_status") && jsonObject.getBoolean("sound_status") == true) {
                            audio_flag = true;

                        } else {
                            view4i.setVisibility(View.GONE);
                            visible_cnt++;
                        }

                        if (visible_cnt == 4) {
                            current_action_prog_bar.setVisibility(View.GONE);
                            txt_no_action.setVisibility(View.VISIBLE);
                            rel_lyt.setVisibility(View.GONE);
                        }

                    }
                    else {
                        current_action_prog_bar.setVisibility(View.GONE);
                        txt_no_action.setVisibility(View.VISIBLE);
                        rel_lyt.setVisibility(View.GONE);
                        lines.setVisibility(View.GONE);
                        gif_title.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    {
                        Log.e("Current_action Error", "" + e.getMessage());
                    }
                }

                /***********/


            }

        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setRoom_subcriber(s);


    }

    private void gifrotation()
    {
        // This timer task will be executed every 1 sec.
        current_action_prog_bar.setVisibility(View.GONE);

        timer_gif.schedule(new TimerTask() {
            @Override
            public void run() {


                Log.e("counter_flag",""+count_for_flag);
                final boolean[] flag_arr={circadian_flag,water_flag,air_flag,audio_flag};
                final int[] str_gif_url={R.drawable.new_circadian_nine,R.drawable.new_water_nine,R.drawable.new_air_nine,R.drawable.new_audio_nine};
                final String[] str_gif_title={"Circadian","Water Purification","Air Purification","Sound"};
                final View[] view_gif={view1,view2,view3,view4i};

              /*  if(!flag_gif) {
                    flag_gif=true;
                    delay=0;
                }
                else
                {
                    delay=2000;
                }*/
                if(count_for_flag>3)count_for_flag=0;

                if(flag_arr[count_for_flag]) {

                    changeGif(str_gif_url[count_for_flag],str_gif_title[count_for_flag],view_gif[count_for_flag]);

                }
                count_for_flag++;


            }
        }, 0, 1800);


    }

    public void changeGif(final int url,final String title,final View v)
    {

        runOnUiThread(new Runnable() {
            public void run() {



                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(dpToPx(230),dpToPx(230));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                webView.setLayoutParams(params);

                webView.setImageResource(url);
                gif_title.setVisibility(View.VISIBLE);
                gif_title.setText(title);
                view1.setBackgroundColor(getActivity().getResources().getColor(R.color.background));
                view2.setBackgroundColor(getActivity().getResources().getColor(R.color.background));
                view3.setBackgroundColor(getActivity().getResources().getColor(R.color.background));
                view4i.setBackgroundColor(getActivity().getResources().getColor(R.color.background));
                v.setBackgroundColor(getResources().getColor(R.color.dialer_color));

            }
        });


    }

    public int dpToPx(int dp) {
        float density = getActivity().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onResume() {
        super.onResume();

        timer_gif.cancel();
        timer_gif = new Timer();
        gifrotation();


    }

    @Override
    public void onStop() {
        super.onStop();
        timer_gif.cancel();

    }

}
