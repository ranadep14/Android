package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.filter_settings.settings;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cloudsinc.welltekmobile.native_v2_welltek.BuildConfig;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.dialogs.CustomDialogShow;
import com.cloudsinc.welltekmobile.native_v2_welltek.fragments.SocketConnectionTest.LoginSocketConnectionTest;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Subcriber;
import com.cloudsinc.welltekmobile.native_v2_welltek.sharedprefrences.PrefManager;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.ReplaceFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import static com.cloudsinc.welltekmobile.native_v2_welltek.activities.MainActivity.hideSystemUI;
/**
 * The ProfileSettingFragment is for setting user profile,invite new user.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class ProfileSettingFragment extends Fragment {
    public static ProfileSettingFragment newInstance() {
        return new ProfileSettingFragment();
    }
    View view;
    TextView pwd_change;
    Context mcontext;
    @BindView(R.id.txt_username)TextView txt_username;
    @BindView(R.id.txt_email)TextView txt_email;
    @BindView(R.id.txt_password)TextView txt_password;
    @BindView(R.id.txt_user_name)TextView txt_userprofilename;
    @BindView(R.id.validenmail)TextView validenmail;
    @BindView(R.id.validname)TextView validname;
    @BindView(R.id.edt_email)EditText edt_email;
    @BindView(R.id.profile_image)CircleImageView profile_image;
    @BindView(R.id.edt_profile)ImageView edt_profile;
    @BindView(R.id.rel_loading)RelativeLayout rel_loading;
    @BindView(R.id.txt_fragment_title)TextView txt_fragment_title;
    @BindView(R.id.edt_first_name)EditText edt_first_name;
    @BindView(R.id.img_back)ImageView img_back;
    @BindView(R.id.txt_send_invite)TextView txt_send_invite;
    @BindView(R.id.lin_user_profile)LinearLayout lin_user_profile;
    String TAG=ProfileSettingFragment.this.getClass().getName();
    @BindView(R.id.lin_no_user_profile)RelativeLayout lin_no_user_profile;
    @BindView(R.id.txt_recovery_email)TextView txt_recovery_email;
    private Observable<String> mobservable;
    private Observer<String> myObserver;
    boolean upload_flag=false;
    boolean flag_first_name=false,flag_emailid=false;
    String email,emailPattern,imagePath,sendemail;
    PrefManager pref;
    int RESULT_LOAD_IMAGE=123;
    Bitmap bitmap;
    JSONObject imgjson;
    Uri imageCarmeraUri;
    ContentValues contentValues;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_CAMERA = 1;
    ArrayList<byte[]> exp_image;
    LoginSocketConnectionTest lct=new LoginSocketConnectionTest();
    String serverImagPath="",uname="";
    private static final int SELECT_FILE = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.AppLogin, true);
        view = inflater.inflate(R.layout.fragment_profile_setting,container, false);
        final Typeface mFont = Typeface.createFromAsset(view.getContext().getAssets(),
                "fonts/gotham-light-591d8629985e3.otf");
        final ViewGroup mContainer = (ViewGroup) view.getRootView();
        FontUtility.setAppFont(mContainer, mFont);
        mcontext=view.getContext();
        if(isConnected(mcontext)) {
            if (App.getLoginSocket() == null) {
                PrefManager spm=new PrefManager(mcontext);
             //  App.setLoginSocket(lct.connect(spm.getValue("server_url") + "/login", App.getLoginSocket(), mcontext));
               // App.setLoginSocket(lct.connect("https://10.14.10.72:8036" + "/login", App.getLoginSocket(), mcontext));
            }
        }
        ButterKnife.bind(this,view);
        pref=new PrefManager(mcontext);
        uname= new PrefManager(mcontext).getValue("User_Id");
       // BackButtonVisiblity.setVisiblity(img_back); Logs.info(TAG+"_Profile_image",""+pref.getValue("User_Prof_Image"));
/*
        Glide.with(mcontext).load(pref.getValue("User_Prof_Image"))
                .thumbnail(Glide.with(mcontext).load(R.drawable.ic_loading_gif))
                .error(R.drawable.ic_user_logo)
                .override(300,300)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);*/
        txt_fragment_title.setText("Profile Settings");
        if(!upload_flag) {
            Glide.with(mcontext).
                    load(pref.getValue("User_Prof_Image"))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            rel_loading.setVisibility(View.GONE);
                            profile_image.setVisibility(View.VISIBLE);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                           if( App.isFlag_upload_prof())
                            {
                                App.setFlag_upload_prof(false);
                                CustomDialogShow.dispDialogWithOK(mcontext, "Profile updated successfully.");
                            }
                            rel_loading.setVisibility(View.GONE);
                            profile_image.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .override(300, 300)
                    .dontAnimate()
                    .error(R.drawable.ic_user_logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profile_image);
        }
       /* if(pref.getValue("Old_Profile_image").equals(pref.getValue("User_Prof_Image")))
        {
            Glide.with(mcontext).load(pref.getValue("User_Prof_Image"))
                    .thumbnail(0.5f)
                    .crossFade()
                    .override(300,300)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profile_image);
        }
        {
            Glide.with(mcontext).load(pref.getValue("User_Prof_Image"))
                    .thumbnail(0.5f)
                    .crossFade()
                    .override(300,300)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profile_image);
        }
*/
        email = edt_email.getEditableText().toString().trim();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(edt_email,getActivity());
                }
            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!checkEmailValidity(edt_email))
                {
                    validenmail.setVisibility(View.VISIBLE);
                    flag_emailid=false;
                }
                else
                {
                    validenmail.setVisibility(View.GONE);
                    flag_emailid=true;
                }
                if(flag_emailid&&flag_first_name)
                {
                    txt_send_invite.setAlpha(1.0f);
                    txt_send_invite.setEnabled(true);
                }
                else {
                    txt_send_invite.setAlpha(0.5f);
                    txt_send_invite.setEnabled(false);
                }
                // other stuffs
            }
        });
        edt_first_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    flag_first_name=true;
                    validname.setVisibility(View.GONE);
                }
                else
                {
                    flag_first_name=false;
                    validenmail.setVisibility(View.VISIBLE);
                }
                if(flag_emailid&&flag_first_name)
                {
                    txt_send_invite.setAlpha(1.0f);
                    txt_send_invite.setEnabled(true);
                }
                else
                {
                    txt_send_invite.setAlpha(0.5f);
                    txt_send_invite.setEnabled(false);
                }
                // other stuffs
            }
        });
        edt_first_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(edt_first_name,getActivity());
                }
            }
        });
        pwd_change= view.findViewById(R.id.txt_pawd_change);
        pwd_change.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(App.isOrientationFlag())ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_filter_main_container,ChangePassword.newInstance());
                else ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_main_container,ChangePassword.newInstance());
            }
        });
       // getActivity().findViewById(R.id.btn_setting).setVisibility(View.GONE);
       /* TextView txt_save=(TextView)getActivity().findViewById(R.id.txt_save);
        txt_save.setVisibility(View.GONE);*/
      /*  TextView txt_back=(TextView)getActivity().findViewById(R.id.txt_cancle);
        txt_back.setText("Cancel");
        txt_back.setVisibility(View.VISIBLE);*/
       /* txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment.replaceFragmentWithRotation(ProfileSettingFragment.this, SettingMainFragment.newInstance());
            }
        });*/
        //getActivity().findViewById(R.id.btn_back_home_health).setVisibility(View.GONE);
        txt_email.setText(""+new PrefManager(mcontext).getValue("User_Id"));
        txt_username.setText(""+new PrefManager(mcontext).getValue("User_Name"));
        txt_recovery_email.setText(""+new PrefManager(mcontext).getValue("Recovery_Mail"));
        txt_userprofilename.setText(""+new PrefManager(mcontext).getValue("User_Name"));
        if(new PrefManager(mcontext).getValue("User_Name").equals("No Record"))
        {
            lin_no_user_profile.setVisibility(View.VISIBLE);
            lin_user_profile.setVisibility(View.GONE);
        }
        else
        {
            lin_no_user_profile.setVisibility(View.GONE);
            lin_user_profile.setVisibility(View.VISIBLE);
        }
        setSubcriber();
        return view;
    }
    public static void hideKeyboard(View view,Activity mactivity) {
        hideSystemUI(mactivity);
        InputMethodManager inputMethodManager =(InputMethodManager)mactivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @OnClick(R.id.edt_profile)
    public void edt_profile()
    {
        File fileDir = new File(Environment.getExternalStorageDirectory()
                + "/uploads");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        imagePath = Environment.getExternalStorageDirectory() + "/uploads/"
                + System.currentTimeMillis() + ".jpg";
        File carmeraFile = new File(imagePath);
        // imageCarmeraUri = Uri.fromFile(carmeraFile);
        imageCarmeraUri= FileProvider.getUriForFile(mcontext,
                BuildConfig.APPLICATION_ID + ".provider",
                carmeraFile);
        final CharSequence[] items = {"Take From Camera", "Take From Gallery", "EXIT"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mcontext);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take From Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCarmeraUri);
                    try {
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        // Do nothing for now
                    }
                } else if (items[item].equals("Take From Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent,SELECT_FILE);
                } else if (items[item].equals("EXIT")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
       /* Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);*/
    }
    @OnClick(R.id.txt_pawd_change)
    public void txt_pawd_change()
    {
        ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_filter_main_container ,ChangePassword.newInstance());
    }
    public boolean checkEmailValidity(EditText emailFormat){
        String getEmail = emailFormat.getText().toString().trim().toLowerCase();
        boolean getEnd;
        //CHECK STARTING STRING IF THE USER
        //entered @gmail.com / @yahoo.com / @outlook.com only
        boolean getResult = !TextUtils.isEmpty(getEmail) && android.util.Patterns.EMAIL_ADDRESS.matcher(getEmail).matches();
        //CHECK THE EMAIL EXTENSION IF IT ENDS CORRECTLY
       /* if (getEmail.endsWith("@mstorm.com")){
            getEnd = true;
        }else {
            getEnd = false;
        }*/
        //TEST THE START AND END
        return getResult;//(getResult && getEnd);
    }
  /*  @OnClick(R.id.btn_pawd_change)
    public void submit() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ChangePassword.newInstance());
        transaction.commit();    }
*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = mcontext.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }*/
        if (requestCode == REQUEST_CAMERA) {
            if(resultCode== Activity.RESULT_OK) {
                imageUpload(imagePath);
              //  BackFunctionality.goBack(ProfileSettingFragment.this, R.id.frame_layout, ProfileSettingFragment.newInstance());
            }
        }
        if (requestCode == SELECT_FILE) {
            if(resultCode== Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                imagePath = getRealPathFromURI(selectedImage);
                //
                imageUpload(imagePath);
              //  BackFunctionality.goBack(ProfileSettingFragment.this, R.id.frame_layout, ProfileSettingFragment.newInstance());
            }
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(mcontext, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    /*
        private void uploadImagePath(final String imageP) {
            StringRequest smr = new StringRequest(Request.Method.GET, "https://www.clouzer.com/fs?path="+imageP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(mcontext, response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // //Toast.makeText(mcontext, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
            //Adding request to the queue
            requestQueue.add(smr);
        }*/
    private void imageUpload(final String imagePath) {
        profile_image.setVisibility(View.GONE);
        rel_loading.setVisibility(View.VISIBLE);
        App.setFlag_upload_prof(true);
        PrefManager pref=new PrefManager(mcontext);
        /*File files=new File(imagePath);
        SimpleMultiPartRequest request = new SimpleMultiPartRequest(pref.getValue("server_url")+"/upload",   new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 Logs.info(TAG+"_Respponse",response);
                try {
                    JSONObject json = new JSONObject(response);
                    imgjson = json.getJSONArray("uploadFiles").getJSONObject(0);
                    serverImagPath=imgjson.getString("path");
                    profupload();
                }
                catch (Exception ex){}
                // //Toast.makeText(mcontext, ""+response, Toast.LENGTH_SHORT).show();
            }
        },new  Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // //Toast.makeText(mcontext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        } ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                PrefManager smt=new PrefManager(mcontext);
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", ""+smt.getValue("jwt_token"));
                 Logs.info(TAG+"_token",smt.getValue("jwt_token"));
                return params;
            }};
        request.addFile("stream",imagePath);
        request.addStringParam("name",imagePath.substring(imagePath.lastIndexOf("/")+1));
        // smr.addFile("stream", imagePath);
        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        //Adding request to the queue
        requestQueue.add(request);*/
    }
    public void profupload()
    {
        PrefManager pref=new PrefManager(mcontext);
        String cml_id=pref.getValue("CML_ID");
        String first_name=pref.getValue("First_Name");
        String last_name=pref.getValue("Last_Name");
        String sub_cat=pref.getValue("Sub_Cat");
        String key_type=pref.getValue("Key_Type");
        String Socket_Id=pref.getValue("Socket_Id");
        String username=new PrefManager(mcontext).getValue("User_Id");
        String image_name=pref.getValue("image_title")+".jpg";
         Logs.info(TAG+"_CML_ID",cml_id);
        //  setSubcriber();
        if (lct.isSocketConnect(App.getLoginSocket())) {
            try {
              /*  obj.put("ACTION_ARRAY", action_array);
                obj.put("USM_FIRST_NAME", "nikhil");
                obj.put("USM_IMAGE_TITLE", imagePath.substring(imagePath.lastIndexOf("/")+1));
                obj.put("USM_IMAGE_PATH",imagePath);
                obj.put("socketId", Socket_Id);
                obj.put("USM_LAST_NAME", "vharamble");
                obj.put("username",""+username);*/
                JSONArray action_array = new JSONArray();
                action_array.put(0, "UPDATE_PROFILE");
                JSONObject obj = new JSONObject();
                obj.put("ACTION_ARRAY", action_array);
                obj.put("USM_FIRST_NAME", first_name);
                obj.put("USM_IMAGE_TITLE", serverImagPath.substring(serverImagPath.lastIndexOf("/")+1));
                obj.put("USM_IMAGE_PATH",serverImagPath);
                obj.put("socketId", Socket_Id);
                obj.put("USM_LAST_NAME", last_name);
                obj.put("username",""+username);
                 Logs.info(TAG+"_USER_PROFILE", "" + obj);
                // System.out.println("Profile Obj: "+ obj);
                App.getLoginSocket().emit("LOGIN", obj);
                 Logs.info(TAG+"_LoginObject",""+obj);
                //set empty
                 Logs.info(TAG+"_inside REGISTER socket", "");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            CustomDialogShow.dispDialogWithOK( mcontext, "Socket Not Connected");
        }
    }
    private void setSubcriber() {
        mobservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("");
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
                try {
                    if(App.getLoginData()!=null) {
                        JSONObject loginjson = App.getLoginData();
                        final JSONArray userJarr = loginjson.getJSONArray("ACTION_ARRAY");
                        if ("ERROR".equals(userJarr.getString(0))) {
                            CustomDialogShow.dispDialogWithOK(mcontext, "" + loginjson.getString("MSG"));
                        } else if ("UPDATE_PROFILE".equals(userJarr.getString(0))) {
                            PrefManager spm = new PrefManager(mcontext);
                            spm.setValue("User_Prof_Image", spm.getValue("server_url") + "" + loginjson.getString("USM_IMAGE_PATH"));
                            spm.setValue("Old_Profile_image", spm.getValue("server_url") + "" + loginjson.getString("USM_IMAGE_PATH"));
                            if(App.isOrientationFlag())ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_filter_main_container, ProfileSettingFragment.newInstance());
                            else  ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_main_container, ProfileSettingFragment.newInstance());
                            App.setLoginData(null);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Subcriber s=new Subcriber();
        s.setObservable(mobservable);
        s.setObserver(myObserver);
        App.setCurrentSubcriber(s);
    }
    @OnClick(R.id.txt_send_invite)
    public void txt_send_invite()
    {
        if(flag_first_name&&flag_emailid)
        {
            validenmail.setVisibility(View.GONE);
            sendemail=edt_email.getText().toString();
           String first_name=edt_first_name.getText().toString();
            if (App.getSocket()!=null) {
                try {
                    JSONObject resultJsonObject = new JSONObject();
                    JSONObject dataObj=new JSONObject();
                    dataObj.put("email",sendemail);
                    dataObj.put("fromFirstname",new PrefManager(mcontext).getValue("First_Name"));
                    dataObj.put("toFirstname",first_name);
                    dataObj.put("from",uname);
                    resultJsonObject.put("data",dataObj);
                    resultJsonObject.put("type","send_invite");
                    App.getSocket().emit("action",resultJsonObject);
                    CustomDialogShow.dispDialogWithOK(mcontext,"Invite sent successfully");
                    edt_email.getText().clear();
                    edt_first_name.getText().clear();
                    validenmail.setVisibility(View.GONE);
                } catch (Exception ex) {
                    Logs.error(TAG,""+ex.getMessage());
                }
            } else {
                CustomDialogShow.dispDialogWithOK(mcontext,"Socket Not Connected.");
            }
        }
        else
        {
            CustomDialogShow.dispDialogWithOK(mcontext,"Please enter valid first name & emailid...");
        }
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
    @OnClick(R.id.img_back)
    public void img_back()
    {
       if(App.isOrientationFlag())ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_filter_main_container, SettingMainFragment.newInstance());
        else ReplaceFragment.replaceFragment(ProfileSettingFragment.this,R.id.frm_main_container, SettingMainFragment.newInstance());
    }
}