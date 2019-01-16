package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.adapters.CustomNotifierAdapter;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Notifier;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings.WaterNotification.setAssignedZone;
/**
 * This class containing functionality related to set air filter notification
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class AirNotification extends Fragment {
    public static AirNotification newInstance() {
        return new AirNotification();
    }
    private Context mcontext;
    private View view;
    @BindView(R.id.txt_save) TextView txt_save;
    private Bundle bundle;
    private String device_name = "", device_id = "";
    private JSONObject devicejsonobject;
    JSONObject airJsonObject;
    ArrayList<Notifier> arr_notifiler = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    @BindView(R.id.spn_notifer)
    Spinner spn_notifer;
    @BindView(R.id.edt_replace_air)
    TextView edt_replace_air;
    @BindView(R.id.txt_zone)
    TextView txt_zone;
    @BindView(R.id.lin_prog)
    LinearLayout lin_prog;
    @BindView(R.id.lin_main)LinearLayout lin_main;
    String zone_id, name = "Air", title, notifier_name, notification_date;
    boolean notification_title_flag = false, select_zone_flag = false, notifier_name_flag = false;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_air_notification, container, false);
        mcontext = view.getContext();
        ButterKnife.bind(this, view);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        txt_zone.setText(setAssignedZone("Air"));
        Bundle bundle = App.getTemp_bundle();
        setSubcriber();
        CustomNotifierAdapter adapter = new CustomNotifierAdapter(mcontext, getNotifierLis());
        spn_notifer.setAdapter(adapter);
        spn_notifer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>0) {
                    TextView txt_noti_name = view.findViewById(R.id.txt_title);
                    notifier_name = txt_noti_name.getText().toString();
                    notifier_name_flag = true;
                    setSaveVisiblity();
                    Log.e("Notifiler_name", notifier_name);
                }
                else
                {
                    notifier_name_flag = false;
                    setSaveVisiblity();
                }
                // your code here
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        String str_air_noti_title = new PrefManager(mcontext).getValue("air_noti_title");
        if (!str_air_noti_title.equals("No Record")) edt_replace_air.setText(str_air_noti_title);
        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWaterAndAirFilterObject();
                PrefManager pref = new PrefManager(mcontext);
                pref.setValue("air_noti_title", edt_replace_air.getText().toString());
                Calendar expdate = Calendar.getInstance();
                try {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("title", edt_replace_air.getText().toString());
                    dataObj.put("filter_type", name);
                    dataObj.put("zone", zone_id);
                    dataObj.put("notifier_email", notifier_name);//notifier_name
                    dataObj.put("notification_date", getAirExpireDate());
                    JSONObject obj = new JSONObject();
                    obj.put("data", dataObj);
                    obj.put("type", "set_filter_Notification");
                    Log.d("Notification_requets", "" + obj);
                    App.getSocket().emit("action", obj);
                } catch (Exception ex) {
                    Log.e("Notification_Error", ex.getMessage());
                }
                lin_prog.setVisibility(View.VISIBLE);
                lin_main.setVisibility(View.GONE);
            }
        });
        edt_replace_air.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    notification_title_flag = true;
                    setSaveVisiblity();
                } else {
                    notification_title_flag = false;
                    setSaveVisiblity();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (bundle != null) {
            if (bundle.containsKey("device_name")) {
                device_name = bundle.getString("device_name");
                edt_replace_air.setText(device_name);
            } else {
                setValue(device_id);
            }
        }
        return view;
    }
    public void setValue(String device_id) {
        Bundle bundle = App.getTemp_bundle();
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(device_id)) {
                    devicejsonobject = jsonObject;
                    App.setSelectedDeviceJson(devicejsonobject);
                    Log.e("device_id_filter", "Got.....filter device");
                    device_name = jsonObject.getString("CML_TITLE");
                    edt_replace_air.setText("" + device_name);
                    App.setTemp_bundle(bundle);
                }
            }
        } catch (Exception ex) {
            Log.e("SimulationError", ex.getMessage());
        }
    }
    public void getAirDateFromJson() {
        try {
            JSONObject jsonObject = airJsonObject;
            Log.e("WaterExpery date", "" + jsonObject.getString("expiryDate"));
            String dt = jsonObject.getString("expiryDate");//airjsonObject
            Calendar expdate = Calendar.getInstance();
            expdate.setTime(sdf.parse(dt));
            expdate.add(Calendar.DATE, -14);
            //scheduleClient.setAlarmForNotification(expdate);
            //Toast.makeText(mcontext, "-------" + expdate.getTime(), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Logs.error(this.getClass().getSimpleName(),"-----------------"+ex.getMessage());
        }
    }
    private void getWaterAndAirFilterObject() {
        try {
            JSONObject jsonObject = App.getProvisionalDevicesData();
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                if (jsonObject1.getString("type").equals("AIr")) {
                    Log.e("Airjson", "" + jsonObject1);
                    airJsonObject = jsonObject1;
                }
            }
            getAirDateFromJson();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public ArrayList<Notifier> getNotifierLis() {
        try {
            arr_notifiler.clear();
            Notifier notifier1 = new Notifier();
            notifier1.setName("Please select notifier");
            arr_notifiler.add(notifier1);
            JSONArray dataarr = App.getNotifierlistJson().getJSONArray("data");
            Log.e("NotifiersListData", "" + dataarr);
            for (int i = 0; i < dataarr.length(); i++) {
                JSONObject jsonObject = dataarr.getJSONObject(i);
                Notifier notifier = new Notifier();
                notifier.setName(jsonObject.getString("emailId"));
                arr_notifiler.add(notifier);
            }
            if (arr_notifiler.size() == 0) {
                Notifier notifier = new Notifier();
                notifier.setName("No notifier available");
                arr_notifiler.add(notifier);
            }
        } catch (Exception ex) {
            Log.e("notilistError", ex.getMessage());
        }
        return arr_notifiler;
    }
    public String getZoneTitle(String str_room_id) {
        Log.e("device_id_litht", "Got.....zone");
        String room_title = "";
        try {
            Log.e("device_id_litht", "Got.....zone" + App.getZoneJson().getJSONArray("data"));
            JSONArray jsonArray = App.getZoneJson().getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("CML_ID").equals(str_room_id)) {
                    Log.e("device_id_litht", "Got.....zone");
                    room_title = jsonObject.getString("CML_TITLE");
                }
            }
        } catch (Exception ex) {
            Log.e("SimulationError", ex.getMessage());
        }
        return room_title;
    }
    public void setDefaultValues() {
    }
    public void setSaveVisiblity() {
        if (notification_title_flag && notifier_name_flag && select_zone_flag) {
            txt_save.setVisibility(View.VISIBLE);
        } else {
            txt_save.setVisibility(View.GONE);
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
                Log.e("NotificationFlag",""+ App.isNotificationSet());
                if(App.isNotificationSet()) {
                    App.setNotificationSet(false);
                    lin_prog.setVisibility(View.GONE);
                    lin_main.setVisibility(View.VISIBLE);
                    final AlertDialog dialog = new AlertDialog.Builder(mcontext)
                            .setView(R.layout.custom_dialog_single)
                            .create();
                    View v = dialog.getWindow().getDecorView();
                    v.setBackgroundResource(android.R.color.transparent);
                    dialog.show();
                    TextView msgt = dialog.findViewById(R.id.title);
                    assert msgt != null;
                    msgt.setText("SAVED !");
                    TextView msg = dialog.findViewById(R.id.msg);
                    assert msg != null;
                    msg.setText("Your FilterNotification has been set. ");
                    Button diagButtonOK = dialog.findViewById(R.id.customDialogOk);
                    assert diagButtonOK != null;
                    diagButtonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(AirNotification.this, R.id.frm_filter_main_container, NotificationSettingFragment.newInstance());
                            else ReplaceFragment.replaceFragment(AirNotification.this, R.id.frm_main_container, NotificationSettingFragment.newInstance());
                        }
                    });
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    public String getAirExpireDate()
    {
        String str_date="";
        try {
            JSONArray jsonArray = App.getProvisionalDevicesData().getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("type").equals("Water"))
                {
                    str_date=jsonObject.getString("expiryDate");
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("WaterJsonErro",ex.getMessage());
        }
        return str_date;
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
        if(App.isOrientationFlag()) ReplaceFragment.replaceFragment(AirNotification.this,R.id.frm_filter_main_container, NotificationSettingFragment.newInstance());
        else ReplaceFragment.replaceFragment(AirNotification.this, R.id.frm_main_container, NotificationSettingFragment.newInstance());
    }
}
