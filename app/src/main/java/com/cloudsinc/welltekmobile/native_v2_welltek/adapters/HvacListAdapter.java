package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.application.App;
import com.cloudsinc.welltekmobile.native_v2_welltek.models.Hvac;
import org.json.JSONObject;
import java.util.ArrayList;
/**
 * Created by developers on 8/11/17.
 */
/**
 * This class containing functionality related to displaying HVAC list
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class HvacListAdapter extends RecyclerView.Adapter<HvacListAdapter.ViewHolder> {
    private ArrayList<Hvac> arrayList;
    Context mcontext;
    private int current_temp[];
    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_type ;
        TextView txt_id ;
        TextView txt_title ;
        SeekBar seek_hvac ;
        TextView txt_increment ;
        TextView txt_decrement ;
        public ViewHolder(View v) {
            super(v);
              txt_type = v.findViewById(R.id.txt_type); // title
              txt_id = v.findViewById(R.id.txt_id); // title
              txt_title = v.findViewById(R.id.txt_title); // artist name
              seek_hvac = v.findViewById(R.id.seek_hvac); // artist name
              txt_increment = v.findViewById(R.id.txt_increment); // artist name
              txt_decrement = v.findViewById(R.id.txt_decrement); // artist name
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public HvacListAdapter(Context context, ArrayList<Hvac> myDataset) {
        arrayList = myDataset;
        mcontext=context;
        current_temp=new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            current_temp[i] = 10;
        }
    }
    // Create new views (invoked by the layout manager)
    @Override
    public HvacListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_hvac_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Hvac light = arrayList.get(position);
        Log.e("Hvac", "" + light.getId());
        Log.e("HvacTitleeeeeeeeeee", "" + light.getTitle()+"state"+light.isLigh_state()+"---"+light.getType());
        holder.txt_title.setText(light.getTitle());
        holder.txt_id.setText(light.getId());
        holder.txt_type.setText(light.getType());
        holder.seek_hvac.setProgress(light.getPoint()-10);
        holder.seek_hvac.setTag(position);
        current_temp[position]=light.getPoint();
        holder.txt_decrement.setTag(position);
        holder.txt_increment.setTag(position);
        holder.seek_hvac.setThumb(getThumb(light.getPoint()));
        holder.seek_hvac.setThumbOffset(15);
        holder.txt_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int) holder.txt_increment.getTag();
                if(current_temp[pos]<32) {
                    current_temp[pos]++;
                    //holder.seek_hvac.setProgress(current_temp[pos]);
                    setHvacPoint(current_temp[pos], pos);
                }
            }
        });
        holder.txt_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int) holder.txt_decrement.getTag();
                if(current_temp[pos]>10) {
                    current_temp[pos]--;
                    //  holder.seek_hvac.setProgress(current_temp[pos]);
                    setHvacPoint(current_temp[pos],pos );
                }
            }
        });
        holder.seek_hvac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                holder.seek_hvac.setThumb(getThumb(seekBar.getProgress()+10));
                holder.seek_hvac.setThumbOffset(15);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pos= (int)holder.seek_hvac.getTag();
                current_temp[pos]=holder.seek_hvac.getProgress()+10;
                setHvacPoint(current_temp[pos],pos );
            }
        });
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     * set value to hvac
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param value int value to set
     * @param pos int position of item
     */
    private void setHvacPoint(int value,int pos)
    {
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("data", new JSONObject().put("CML_SET_POINT",value).put("CML_LOWER_POINT",arrayList.get(pos).getLower_point()).put("CML_HIGHEST_POINT",arrayList.get(pos).getHight_point()).put("Id",arrayList.get(pos).getId()));
            obj.put("type", "set_hvac_setpoint");
            Log.d("SimulationRequestSend",""+obj);
            App.getSocket().emit("action", obj);
        }
        catch (Exception ex){
            Log.e("SimulationRequestError",""+ex.getMessage());
        }
    }
    /**
     * get thumb for seekbar
     *
     * @author  nikhil vharamble
     * @version 1.0
     * @since   2017-11-8
     * @param progress int progress to draw on thumb image
     * @return  drawable
     */
    private Drawable getThumb(int progress) {
        View thumbView= LayoutInflater.from(mcontext).inflate(R.layout.layout_seekbar_thumb, null, false);
        ((TextView) thumbView.findViewById(R.id.tvProgress)).setText(progress + "");
        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        thumbView.layout(10, 0, thumbView.getMeasuredWidth()+10, thumbView.getMeasuredHeight());
        thumbView.draw(canvas);
        return new BitmapDrawable(mcontext.getResources(), bitmap);
    }
}
