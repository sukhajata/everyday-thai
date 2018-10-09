package com.sukhajata.everyday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MenuExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Category> categories;

    public MenuExpandableListAdapter(Activity context,ArrayList<Category> _categories) {
        this.context = context;
        this.categories = _categories;
    }

    @Override
    public SubCategory getChild(int groupPosition, int childPosition) {
        Category category = categories.get(groupPosition);
        return category.getSubCategories().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Category category = categories.get(groupPosition);
        final SubCategory subCategory = category.getSubCategories().get(childPosition);
        final String subCatName = subCategory.subCategoryName;
        //Log.d("subcatname", subCatName);
        TextView txtView = null;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.subcategory_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.subcategory);
        item.setText(subCatName);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalData.getInstance(context).currentSubCatId = subCategory.subCategoryId;
                ((MainActivity)context).switchContent(subCatName);
            }
        });
        /*
        ImageView img = (ImageView)convertView.findViewById(R.id.locked);
        if (subCategory.locked == 1) {
            img.setImageResource(R.drawable.ic_locked);

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalData.getInstance().currentSubCatId = subCategory.subCategoryId;
                    GlobalData.getInstance().mainActivity.showUnlockDialog();
                }
            });
        } else {
            img.setImageResource(R.color.light_primary);

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalData.getInstance().currentSubCatId = subCategory.subCategoryId;
                    GlobalData.getInstance().mainActivity.switchContent(subCatName);
                }
            });
        }
*/


        return convertView;

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView txtCategory = null;
        Category category = categories.get(groupPosition);
        String categoryName = category.categoryName;

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category, null);
        }
        txtCategory = (TextView) convertView.findViewById(R.id.category);
        txtCategory.setText(categoryName);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Category category = categories.get(groupPosition);
        ArrayList<SubCategory>  subCategories = category.getSubCategories();
        return subCategories.size();
    }

    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    public int getGroupCount() {
        return categories.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}