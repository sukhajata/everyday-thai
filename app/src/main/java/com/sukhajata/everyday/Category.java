package com.sukhajata.everyday;

import java.util.ArrayList;

/**
 * Created by Administrator on 29/07/2015.
 */
public class Category {
    public final int categoryId;
    public final String categoryName;

    private ArrayList<SubCategory> subCategories;

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public Category(int _categoryId, String _categoryName) {
        categoryId = _categoryId;
        categoryName = _categoryName;
    }
}
