package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;


import android.support.v4.app.Fragment;

/**
 *
 * @author  Jaid Shaikh
 * @version 1.0
 * @since   2017-11-8
 */
public class DawnNotificationFragment extends Fragment {
    private int local_dawn_id=0;

    public static DawnNotificationFragment newInstance() {
        return new DawnNotificationFragment();
    }

    /*View view;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.img_back)ImageView img_back;

    @BindView(R.id.dwan_notif_list)
    ListView dwan_notif_list;
 @BindView(R.id.sleep_notif_list)
    ListView sleep_notif_list;


    @BindView(R.id.hub_time)
    TextView hub_time;

    @BindView(R.id.device_time)
    TextView device_time;

    ArrayList<DawnNotification> dataList=new ArrayList<>();
    ArrayList<SleepNotification> sleepdataList=new ArrayList<>();
    DawnListAdapter adapter;
    SleepListAdapter sleepadapter;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    PrefManager prefManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Context mcontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dawn_notification, container, false);
        mcontext = view.getContext();
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        ButterKnife.bind(this, view);


        String deviceTimeZone= TimeZone.getDefault().getDisplayName();
        device_time.setText(""+deviceTimeZone);
        try {
            if (App.getSocket() != null) {
                emitNotification("Dawn");
                emitNotification("Sleep");
                App.getSocket().emit("action", JsonObjectCreater.getJsonObject("get_info", new JSONObject()));
            }
    } catch (Exception e) {
        Log.e("Error in request",""+e.getMessage());
    }


        setSubcriber();
        return view;
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

                if (string.equals("info")) {

                    try {
                        JSONArray jsonArray = App.getHubInfo().getJSONArray("data");
                        Log.i("Hubinfo_Array", "" + jsonArray);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.i("Hubinfo_obj", "" + jsonObject);

                             String hub_timezone = jsonObject.getString("CML_TIMEZONE");
                            hub_time.setText(""+hub_timezone);
                        }


                } catch (Exception ex) {
                    Log.e("Array_errorrrrr", "" + ex.getMessage());


            }

                }

                        try {

                            if (App.getDawnNotifJson().getJSONArray("data").length()>0){

                                JSONArray jsonArray = App.getDawnNotifJson().getJSONArray("data");
                                Log.i("Result_sim_notif_Array", "" + jsonArray);
                                dataList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.i("Result_sim_notif_obj", "" + jsonObject);
                                    ////Toast.makeText(mcontext, "Array", Toast.LENGTH_SHORT).show();

                                    try {
                                        if (jsonObject.getString("type").equals("Dawn")) {

                                            int hr = jsonObject.getInt("hour");
                                            int min = (jsonObject.getInt("minutes") - 30);
                                            String time = jsonObject.getInt("hour") + ":" + jsonObject.getInt("minutes");

                                            Calendar c = Calendar.getInstance();
                                            c.set(Calendar.HOUR_OF_DAY, hr);
                                            c.set(Calendar.MINUTE, min);
                                            c.set(Calendar.SECOND, 0);

                                            c.getTime().getHours();
                                            c.getTime().getMinutes();

                                            String timeee = c.getTime().getHours()+ ":" + c.getTime().getMinutes();



                                            int mintwo = (jsonObject.getInt("minutes") -2);

                                            Calendar cc = Calendar.getInstance();
                                            cc.set(Calendar.HOUR_OF_DAY, hr);
                                            cc.set(Calendar.MINUTE, mintwo);
                                            cc.set(Calendar.SECOND, 0);

                                            cc.getTime().getHours();
                                            cc.getTime().getMinutes();

                                            String timeeetwo = cc.getTime().getHours()+ ":" + cc.getTime().getMinutes();


                                            String iddd= jsonObject.getString("notif_id").split("#")[1];
                                          //  int notif_if = id;
                                            String notif_id=jsonObject.getString("notif_id");

                                            Log.i("Notif****************", "" + timeee);
                                          //  //Toast.makeText(mcontext, "" + notif_id, Toast.LENGTH_SHORT).show();


                                          *//*  String  tmpStr = String.valueOf(iddd);
                                            String last4Str = tmpStr.substring(tmpStr.length() -5);*//*
                                            String notificationId = notif_id;


                                                DawnNotification version = new DawnNotification();

                                            version.set_sim_id(""+notificationId);
                                            version.set_time(""+timeee);
                                            version.set_two_time(""+timeeetwo);


                                            dataList.add(version);



                                        }
                                        adapter = new DawnListAdapter(mcontext, R.layout.dawn_view_row, dataList);
                                        dwan_notif_list.setAdapter(adapter);
                                    } catch (Exception ex) {
                                        Log.e("obj_errorrrrr", "" + ex.getMessage());

                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("Array_errorrrrr", "" + ex.getMessage());

                        }



                try {

                    if (App.getSleepNotifJson().getJSONArray("data").length()>0){

                        JSONArray jsonArray = App.getSleepNotifJson().getJSONArray("data");
                        Log.i("Result_sleep_notifArray", "" + jsonArray);
                        sleepdataList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.i("Result_Sleep_notif_obj", "" + jsonObject);
                            //.//Toast.makeText(mcontext, "Array", Toast.LENGTH_SHORT).show();

                            try {
                                if (jsonObject.getString("type").equals("Sleep")) {

                                    int hr = jsonObject.getInt("hour");
                                    int min = (jsonObject.getInt("minutes"));
                                    Calendar c = Calendar.getInstance();
                                    c.set(Calendar.HOUR_OF_DAY, hr);
                                    c.set(Calendar.MINUTE, min+15);
                                    c.set(Calendar.SECOND, 0);

                                    c.getTime().getHours();
                                    c.getTime().getMinutes();

                                    String timeee = c.getTime().getHours()+ ":" + c.getTime().getMinutes();                                    //  int id = Integer.parseInt(jsonObject.getString("notif_id").split("#")[1]);
                                    //int notif_if = id;
                                   String notif_id=jsonObject.getString("notif_id");

                                   // Log.i("SleepNotif**********", "" + hr + min + notif_id);
                                    //  //Toast.makeText(mcontext, "" + notif_id, Toast.LENGTH_SHORT).show();

                                    String iddd= jsonObject.getString("notif_id").split("#")[1];

                                    Log.i("Notif****************", "" + notif_id);
                                    //  //Toast.makeText(mcontext, "" + notif_id, Toast.LENGTH_SHORT).show();


                                  *//*  String  tmpStr = String.valueOf(iddd);
                                    String last4Str = tmpStr.substring(tmpStr.length() -5);
                                   *//* String notificationId = notif_id;




                                    SleepNotification version = new SleepNotification();

                                    version.set_sim_id(""+notificationId);
                                    version.set_time(""+timeee);


                                    sleepdataList.add(version);



                                }

                                sleepadapter = new SleepListAdapter(mcontext, R.layout.dawn_view_row, sleepdataList);
                                sleep_notif_list.setAdapter(sleepadapter);
                            } catch (Exception ex) {
                                Log.e("obj_errorrrrr", "" + ex.getMessage());

                            }
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Array_errorrrrr", "" + ex.getMessage());

                }

                }

        };
        Subcriber s = new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);

    }

    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(DawnNotificationFragment.this,R.id.frm_filter_main_container, SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(DawnNotificationFragment.this,R.id.frm_main_container, SettingMainFragment.newInstance());
    }
*/

}