package com.sukhajata.everyday;

/**
 * Created by Administrator on 29/07/2015.
 */
public class SubCategory {
    public final int subCategoryId;
    public final int categoryId;
    public final String subCategoryName;
    public int locked;

    public SubCategory (
            int _subCatId,
            int _catId,
            String _subCatName,
            int _locked)
    {
        subCategoryId = _subCatId;
        categoryId = _catId;
        subCategoryName = _subCatName;
        locked = _locked;
    }
}
