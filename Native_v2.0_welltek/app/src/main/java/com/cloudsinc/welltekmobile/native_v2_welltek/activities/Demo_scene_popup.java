package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class Demo_scene_popup  extends android.support.v4.app.DialogFragment {
    private Observer<String> myObserver;
    private Observable<String> mobservable;
    Context mcontext;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_demo_scene_popup, container,
                false);
        mcontext=rootView.getContext();
      //  Dialog.setCancelable(false)
        setSubcriber();
      //  //Toast.makeText(this, "Demo Scene activated", Toast.LENGTH_LONG).show();

      /*  final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                .setView(R.layout.custom_dialog_demo)
                .create();
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        dialog.show();


        TextView msg= dialog.findViewById(R.id.msg);
        msg.setText("Currently your demo scene is active. \n Please wait for 1 minute.");

        dialog.setCanceledOnTouchOutside(false);*/
        // ReplaceFragment.replaceFragment(mcontext, ChangePassword.newInstance());
    return rootView;
    }

    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(""+App.getRoomData());
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
                removePopup();

            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);


    }
    public void callParentMethod(){
        getActivity().onBackPressed();
    }
    public void onBackPressed() {
        //super.onBackPressed();
        //create a dialog to ask yes no question whether or not the user wants to exit
    }

    private void removePopup() {

        try {
            JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            if ((jsonObject.has("demo_status"))&&jsonObject.getBoolean("demo_status")==false)
            {

                this.dismiss();

               /* this.finish();
                Intent j = new Intent(this, MainActivity.class);
                startActivity(j);
                App.setCallfrom("demo_scene");*/
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
