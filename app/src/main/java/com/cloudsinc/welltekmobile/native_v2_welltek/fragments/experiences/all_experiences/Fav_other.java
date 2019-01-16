package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.experiences.all_experiences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * Created by developers on 17/5/17.
 */

public class Fav_other extends Fragment {

    public static Fav_other newInstance() {
        return new Fav_other();
    }

    @BindView(R.id.txt_room_title)TextView txt_room_title;
    @BindView(R.id.img_room_type)ImageView img_room_type;
    private View v;
    Context mcontext;

    String room_id="";
    private Observable<String> mobservable;
    private Observer<String> myObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getFragmentManager().getFragments().clear();
         v=inflater.inflate(R.layout.fragment_fav_other, container, false);
        mcontext=v.getContext();
        ButterKnife.bind(this,v);


        txt_room_title.setText(getArguments().getString("CML_TITLE"));
        room_id=getArguments().getString("CML_ID");
        setTypeImage(getArguments().getString("CML_ROOM_TYPE"));

       // //Toast.makeText(getActivity(), ""+getArguments().getString("CML_ROOM_TYPE"), Toast.LENGTH_SHORT).show();
        setSubcriber();
        return v;
    }
    public void setTypeImage(String type)
    {
        switch (type)
        {

            case "Bathroom":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;
            case "Outdoor":
                img_room_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_bedroom_type));
                break;

        }
    }

    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+ App.getRoomData());
                        sub.onCompleted();
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

                Log.e("Response_String",string);
                //setValues();

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.addFav_subscriber(s);


    }



}