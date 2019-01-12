package com.cloudsinc.welltekmobile.native_v2_welltek.adapters;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cloudsinc.welltekmobile.native_v2_welltek.R;
import com.cloudsinc.welltekmobile.native_v2_welltek.utils.FontUtility;
import java.util.HashMap;
import java.util.List;
/**
 * This class containing functionality related to displaying setting expandable list
 *
 * @author  nikhil vharamble
 * @version 1.0
 * @since   2017-11-8
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }
    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.setting_list_item, null);
            final Typeface mFont = Typeface.createFromAsset(convertView.getContext().getAssets(),
                    "fonts/gotham-light-591d8629985e3.otf");
            final ViewGroup mContainer = (ViewGroup) convertView.getRootView();
            FontUtility.setAppFont(mContainer, mFont);
        }
        TextView expandedListTextView = convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return 0;
    }
    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }
    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }
    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.setting_list_group, null);
            final Typeface mFont = Typeface.createFromAsset(convertView.getContext().getAssets(),
                    "fonts/gotham-light-591d8629985e3.otf");
            final ViewGroup mContainer = (ViewGroup) convertView.getRootView();
            FontUtility.setAppFont(mContainer, mFont);
        }
        TextView listTitleTextView = convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        ImageView img= convertView.findViewById(R.id.setting_group_img);
        RelativeLayout rel_divider= convertView.findViewById(R.id.rel_divider);
        if(listTitle.equals("Logout")) {
            img.setVisibility(View.GONE);
            rel_divider.setVisibility(View.GONE);
        }
            img.setImageResource(R.drawable.right_white);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}