package com.cloudsinc.welltekmobile.native_v2_welltek.models;

import rx.Observable;
import rx.Observer;

/**
 * Created by com.cloudsinc.welltekmobile.welltek_native_V1 on 27/5/17.
 */

public class Subcriber {
    Observable<String> observable;
    Observer<String> observer;
    public void setObservable(Observable<String> observable)
    {
        this.observable=observable;
    }
    public Observable<String> getObservable()
    {
        return observable;
    }
    public void setObserver(Observer<String> observer)
    {
        this.observer=observer;
    }
    public Observer<String> getObserver()
    {
        return observer;
    }

}
