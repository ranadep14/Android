package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Blind;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.Logs;
import com.xw.repo.BubbleSeekBar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.cloudsinc.welltekmobile.native_v2_welltek.fragments.elements.types.DevicesByType.checkDawnSleep;
import static com.cloudsinc.welltekmobile.native_v2_welltek.utils.FavoritesOperations.getRoomId;
/**
 * This adpter is for displaying blind groups and perform actions on it.
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 *
 */
public class BlindsGroupListAdapter extends RecyclerView.Adapter<BlindsGroupListAdapter.ViewHolder> {
    private ArrayList<Blind> arrayList;
    private int open_flag[];
    private int close_flag[];
    private Context mcontext;
    private String TAG=BlindsGroupListAdapter.this.getClass().getName();
    private boolean favflag[];
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txt_id;
        private TextView txt_title ;
        private TextView txt_room_name ;
        private Button btn_open;
        private Button btn_close;
        private Button btn_stop;
        private ImageView img_blind_indicator ;
        private LinearLayout group_blind_indicator ;
        private BubbleSeekBar seek_blinds ;
        private ImageView  img_add_fav;
        public ViewHolder(View v) {
            super(v);
            txt_id = v.findViewById(R.id.txt_id); // title
            txt_title = v.findViewById(R.id.txt_title); // artist name
            txt_room_name = v.findViewById(R.id.txt_room_name); // artist name
            btn_open = v.findViewById(R.id.btn_open); // artist name
            btn_close = v.findViewById(R.id.btn_close); // artist name
            btn_stop = v.findViewById(R.id.btn_stop); // artist name
            seek_blinds = v.findViewById(R.id.seek_blind); // artist name
            img_add_fav = v.findViewById(R.id.img_add_fav);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public BlindsGroupListAdapter(Context context,ArrayList<Blind> myDataset) {
        arrayList = myDataset;
        mcontext=context;
        favflag =new boolean[arrayList.size()];
        for (int i=0;i<arrayList.size();i++)
        {
            favflag[i]=false;
        }
    }
    // Create new views (invoked by the layout manager)
    @Override
    public BlindsGroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_blind_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Blind blind = arrayList.get(position);
         Logs.error(TAG+"_Hvac", "" + blind.getId());
         Logs.error(TAG+"_HvacTitleeeeeeeeeee", "" + blind.getTitle()+"state"+blind.isState()+"---"+blind.getPoint());
        open_flag=new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
        {
            open_flag[i]=0;
        }
        close_flag=new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
        {
            close_flag[i]=0;
        }
        holder.txt_title.setText(blind.getTitle());
        holder.txt_room_name.setText(blind.getRoom_title());
        holder.txt_id.setText(blind.getId());
        holder.seek_blinds.setProgress(blind.getPoint());
        holder.seek_blinds.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.seek_blinds.correctOffsetWhenContainerOnScrolling();
                return false;
            }
        });
        if(blind.isState()) {
            open_flag[position]=1;
            setButtonSelection(holder.btn_open,holder.btn_open,holder.btn_close);
        }
        else
        {
            close_flag[position]=0;
            setButtonSelection(holder.btn_close,holder.btn_open,holder.btn_close);
        }
        holder.seek_blinds.setTag(position);
        holder.btn_close.setTag(position);
        holder.btn_open.setTag(position);
        holder.btn_stop.setTag(position);
        holder.img_add_fav.setTag(position);
        holder.seek_blinds.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                int pos =(int)bubbleSeekBar.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    setBlindPoint(progress, (int) holder.seek_blinds.getTag());
                }
            }
        });
        holder.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(open_flag[(int) holder.btn_open.getTag()]==0) {*/
                int pos =(int)view.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    setButtonSelection(holder.btn_close, holder.btn_open, holder.btn_close);
                    setBlindState(true, (int) holder.btn_open.getTag());
                }
               // }
            }
        });
        holder.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(close_flag[(int) holder.btn_close.getTag()]==0) {*/
                int pos =(int)view.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    setButtonSelection(holder.btn_close, holder.btn_open, holder.btn_close);
                    setBlindState(false, (int) holder.btn_close.getTag());
                }
               // }
            }
        });
        holder.btn_stop.setTag(position);
        holder.btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos =(int)view.getTag();
                if(!checkDawnSleep(getRoomId(arrayList.get(pos).getId()),mcontext)) {
                    stopBlinds((int) holder.btn_stop.getTag());
                }
            }
        });
        holder.img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(favflag[pos])
                {
                    FavoritesOperations.deleteFav(arrayList.get(pos).getFav_id());
                    holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
                }
                else
                {
                    FavoritesOperations.addFav(arrayList.get(pos).getId());
                    favflag[pos]=true;
                    holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
                }
            }
        });
        if(arrayList.get(position).getFav_id().equals(""))
        {
            favflag[position]=false;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_unfill));
        }
        else
        {
            favflag[position]=true;
            holder.img_add_fav.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     *  set blind points
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param pos int position of blind group from list
     * @param value int value
     *
     */
    public void setBlindPoint(int value,int pos)
    {
        try
        {
            if(arrayList.get(pos).getType().equals("ind")) {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("CML_SET_POINT", value).put("Id", arrayList.get(pos).getId()));
                obj.put("type", "set_blind_setpoint");
                 Logs.info(TAG+"_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            }
            else
            {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("setpoint", value).put("group", arrayList.get(pos).getId()));
                obj.put("type", "group_setpoint");
                 Logs.info(TAG+"_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            }
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.error(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    /**
     *  set blind state open/close
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param pos int position of blind group from list
     * @param value boolean value
     *
     */
    public void setBlindState(boolean value,int pos)
    {
        try
        {
            if(arrayList.get(pos).getType().equals("ind")) {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("CML_POWER", value).put("Id", arrayList.get(pos).getId()));
                obj.put("type", "blind_power");
                 Logs.info(TAG+"_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            }
            else
            {
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject().put("state", value).put("group",getBlingJson(arrayList.get(pos).getId()) ));
                obj.put("type", "group_power");
                 Logs.info(TAG+"_SimulationRequestSend", "" + obj);
                App.getSocket().emit("action", obj);
            }
            //  App.getSocket().emit("trigger_dawn_simulation", JsonObjectCreater.getJsonObject("trigger_dawn_simulation", obj));
        }
        catch (Exception ex){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
             Logs.error(TAG+"_SimulationRequestError",""+ex.getMessage());
        }
    }
    /**
     *  returns json object
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param str_id string cml_id
     *
     *
     */
    public JSONObject getBlingJson(String str_id)
    {
        JSONObject blindJson=null;
        try {
            JSONArray jsonArray = App.getGroupJson().getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.getString("Id").equals(str_id))
                {
                    blindJson=jsonObject;
                }
            }
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_BlindGroupError",""+ex.getMessage());
        }
        return blindJson;
    }
    /**
     *  set button selection
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param btn_close instance of close button
     * @param btn_open instatace of open button
     * @param btn_selection instane of current action button
     *
     */
    public void setButtonSelection(Button btn_selection,Button btn_open,Button btn_close)
    {
        btn_open.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_border_white));
        btn_close.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_border_white));
        btn_selection.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_fill_color));
    }
    /**
     *  for stop blinds
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param pos int position of blind group from list
     *
     *
     */
    public void stopBlinds(int pos)
    {
        try {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("group", arrayList.get(pos).getId()));
            obj.put("type", "group_stop");
             Logs.info(TAG+"_SimulationRequestSend", "" + obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex)
        {
             Logs.error(TAG+"_BlindStopException",ex.getMessage());
        }
    }
}
