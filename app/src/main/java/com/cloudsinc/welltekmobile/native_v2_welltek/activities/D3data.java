package com.cloudsinc.welltekmobile.native_v2_welltek.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.DateAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class D3data extends DialogFragment implements OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener{
    private Context mcontext;
    private Observable<String> mobservable;
    private Observer<String> myObserver;

    private LineChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private float maxval=120f;
    View v;

    public static D3data newInstance() {
        return new D3data();
    }
    ArrayList<Entry> values = new ArrayList<Entry>();
    Calendar calendar=Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_d3_main, container,
                false);

        mcontext = v.getContext();

        ButterKnife.bind(this, v);

        tvX = v.findViewById(R.id.tvXMax);

        mSeekBarX = v.findViewById(R.id.seekBar1);

        mSeekBarX.setProgress(100);

        mSeekBarX.setOnSeekBarChangeListener(this);

        mChart = v.findViewById(R.id.chart1);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);

        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DateAxisValueFormatter());
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaximum(maxval);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawZeroLine(false);
        mChart.getAxisRight().setEnabled(false);

        mChart.setNoDataText("Loading");
        mChart.setNoDataTextColor(Color.BLACK);


        setSubcriber();
        getGraph();
        leftAxis.setAxisMaximum(maxval);



        mChart.animateX(2500);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

return v;

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData() {




        LineDataSet set1;
        System.out.println("sssssssssssssssssssssssssssssssssssssss"+values.size());
        if (values.size() >0 && mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");

            set1.setDrawIcons(false);
            set1.setColor(Color.TRANSPARENT);
            set1.setCircleColor(Color.TRANSPARENT);
            set1.setLineWidth(1f);
            set1.setCircleRadius(0f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(0);
            set1.setDrawFilled(true);


            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(mcontext, R.color.filtergreen);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    private void setSubcriber() {


        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("" + App.getRoomData());
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
                Log.i("#######",""+string);
                if(string.equals("graph_data")){

                    try {
                        int val=0;
                       if(App.getGraphData().has("data")) {
                            JSONArray jsonArray = App.getGraphData().getJSONArray("data");

                            values.clear();
                            Log.i("graph_data", "" + jsonArray);
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i("graph_data_obj",""+jsonObject);
                               if(jsonObject.has("CML_VALUE")&&jsonObject.has("TIMESTAMP"))
                               {
                                   Log.i("SENSOR_REF_ID",""+jsonObject.getString("SENSOR_REF_ID"));


                                   if(jsonObject.has("SENSOR_REF_ID"))
                                   {
                                       if (jsonObject.getString("SENSOR_REF_ID") .equals( "64") || jsonObject.getString("SENSOR_REF_ID").equals("246") || jsonObject.getString("SENSOR_REF_ID") .equals( "248")) {


                                           val = (int) Float.parseFloat(jsonObject.getString("CML_VALUE"));
                                           Log.i("SENSOR_REF_ID__1: ", "" + (int) Float.parseFloat(jsonObject.getString("CML_VALUE")));


                                       } else {
                                           val = Math.abs(100 - (int) Float.parseFloat(jsonObject.getString("CML_VALUE")));

                                       }
                                   }
                                   String times = jsonObject.getString("TIMESTAMP");
                                   long ts = Long.parseLong(times.substring(0, times.length() - 3));

                                   values.add(new Entry(ts, val, getResources().getDrawable(R.color.filtergreen)));
                               }

                            }

                            setData();

                        }
                        else {
                           mChart.setNoDataText("No Graph Data Available");

                       }

            } catch (Exception e) {
                Log.e("Error_sensor_data",""+e.getMessage());
            }

             }

                else {
                    mChart.setNoDataText("No Graph Data Available");

                }
            }


        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setDialog_subcriber(s);


    }
    public void getGraph() {
        String hub = "";
        try {
            if (App.getHubInfo() != null) {

                JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
                hub = App.getHubInfo().getJSONArray("data").getJSONObject(0).getString("CML_SERIAL");
            }


            if (App.getGraphData()==null) {

                Log.e("Tab1","getHubInfo() == null");

                JSONObject dataObject = new JSONObject();

                dataObject.put("SENSOR_ID", "" + getArguments().getString("sensor_id"));//co= pm
                Log.e("Tab1_SENSOR_ID","" + getArguments().getString("sensor_id"));
                String sensor_id= getArguments().getString("sensor_id");


                dataObject.put("MAC_ID", "" + App.getTemp_bundle().getString("mac_id"));//121209876
                dataObject.put("HUB_ID", "" + hub);


                JSONObject objjj = new JSONObject();
                objjj.put("data", dataObject);
                objjj.put("type", "get_graph_data");
                App.getSocket().emit("action", objjj);
                Log.d("obj_create_dawn_sim : ", "" + objjj);


                    switch (sensor_id)
                    {
                        case "181":
                           maxval=100f;
                            break;
                        case "33":
                            maxval=100f;
                            break;
                        case "34":
                            maxval=100f;
                            break;
                        case "64":
                            maxval=500f;
                            break;
                        case "122":
                            maxval=100f;
                            break;
                        case "246":
                            maxval=100f;
                            break;

                        case "248":
                            maxval=100f;
                            break;
                    }


            }
            else
            {
                Log.e("Tab1","else");

                Observable.just("graph_data").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(myObserver);
            }
        } catch (JSONException e) {
            Log.e("Error_in_call", "" + e.getMessage());
        }


    }

    @OnClick(R.id.close_nav)
    public void close_nav() {
        this.dismiss();

    }
}
