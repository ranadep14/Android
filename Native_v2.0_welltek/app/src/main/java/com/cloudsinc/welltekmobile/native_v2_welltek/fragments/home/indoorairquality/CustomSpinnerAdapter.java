package com.cloudsinc.welltekmobile.native_v2_welltek.fragments.home.indoorairquality;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudsinc.welltekmobile.native_v2_welltek.R;

/**
 * Created by developers on 15/11/17.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private String[] spinner_values;
    private Activity context;
    private int mSelectedIndex = -1;

    public CustomSpinnerAdapter(Activity context, int layout_id, String[] spinner_values) {
        super(context,layout_id,spinner_values);
        this.context=context;
        this.spinner_values=spinner_values;
    }
    public void setSelectionItem(int position) {
        mSelectedIndex =  position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.spinner_list, null, true);
        ImageView imageView= listViewItem.findViewById(R.id.bullet);
        imageView.setVisibility(View.GONE);
        ImageView seperator= listViewItem.findViewById(R.id.seperator);
        seperator.setVisibility(View.GONE);
        ImageView selection= listViewItem.findViewById(R.id.selected_spinner_item);
        selection.setVisibility(View.GONE);
        TextView textSpinnerValue = listViewItem.findViewById(R.id.spinnertext);

        textSpinnerValue.setText(spinner_values[position]);

        //textViewEmail.setText(emails[position]);

        return listViewItem;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.spinner_list, null, true);

        TextView textSpinnerValue = listViewItem.findViewById(R.id.spinnertext);
        ImageView selection= listViewItem.findViewById(R.id.selected_spinner_item);

        textSpinnerValue.setText(spinner_values[position]);
        if (position == mSelectedIndex) {
            selection.setVisibility(View.VISIBLE);
        } else {
            selection.setVisibility(View.INVISIBLE);
        }

        //textViewEmail.setText(emails[position]);

        return listViewItem;
    }
}
