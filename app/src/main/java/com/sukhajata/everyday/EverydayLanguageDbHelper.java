package com.sukhajata.everyday;


import android.database.Cursor;
import android.database.sqlite.*;
import android.content.Context;

import java.util.ArrayList;

public class EverydayLanguageDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 63;
    public static final String DATABASE_NAME = "EverydayThai.db";
    private Context mContext;
    private static EverydayLanguageDbHelper mInstance;


    private EverydayLanguageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static EverydayLanguageDbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EverydayLanguageDbHelper(context);
        }

        return mInstance;
    }

    public void onCreate(SQLiteDatabase db) {
        //Log.d("onCreate v", EverydayLanguageContract.getSqlCreateTablePhrasesF());
        try
        {
            //Create Tables
            db.execSQL(EverydayLanguageContract.getSqlCreateTableSettings());
            db.execSQL(EverydayLanguageContract.getSqlCreateTableCategories());
            db.execSQL(EverydayLanguageContract.getSqlCreateTableSubcategories());
            //Log.d("create", EverydayLanguageContract.getSqlCreateTablePhrasesF());
            db.execSQL(EverydayLanguageContract.getSqlCreateTablePhrasesF());
            db.execSQL(EverydayLanguageContract.getSqlCreateTablePhrasesM());
            db.execSQL(EverydayLanguageContract.getSqlCreateTableSearch());

            //insert settings
           // String sqlInsertSettings = IOHelper.readFromFile(mContext, R.raw.settings);
            //db.execSQL(sqlInsertSettings);

            //insert categories
            String sqlInsertCategories = IOHelper.readFromFile(mContext, R.raw.categories);
            db.execSQL(sqlInsertCategories);

            //insert subcategories
            String sqlInsertSubCategories = IOHelper.readFromFile(mContext, R.raw.subcategories);
            db.execSQL(sqlInsertSubCategories);

            //insert phrasesF. Split in 3 files as sqlite select limit is 500 rows
            String sqlInsertPhrases = IOHelper.readFromFile(mContext, R.raw.phrases_f1);
            db.execSQL(sqlInsertPhrases);
            sqlInsertPhrases = IOHelper.readFromFile(mContext, R.raw.phrases_f2);
            db.execSQL(sqlInsertPhrases);
            sqlInsertPhrases = IOHelper.readFromFile(mContext, R.raw.phrases_f3);
            db.execSQL(sqlInsertPhrases);

            //insert phrasesM
            sqlInsertPhrases = IOHelper.readFromFile(mContext, R.raw.phrases_m1);
            db.execSQL(sqlInsertPhrases);
            sqlInsertPhrases = IOHelper.readFromFile(mContext, R.raw.phrases_m2);
            db.execSQL(sqlInsertPhrases);
            sqlInsertPhrases = IOHelper.readFromFile(mContext, R.raw.phrases_m3);
            db.execSQL(sqlInsertPhrases);


            //insert settings
            String sqlInsertSettings =
                    EverydayLanguageContract.getSqlInsertOrReplaceSettings(2, "usage", 1);
            db.execSQL(sqlInsertSettings);

            sqlInsertSettings =
                    EverydayLanguageContract.getSqlInsertOrReplaceSettings(3, "donated", 0);
            db.execSQL(sqlInsertSettings);
        }
        catch(SQLiteException ex)
        {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try
        {
            //Log.d("upgrade", "upgrading");
            db.execSQL(EverydayLanguageContract.getSqlDeleteSettings());
            db.execSQL(EverydayLanguageContract.getSqlDeleteTablePhrasesF());
            db.execSQL(EverydayLanguageContract.getSqlDeleteTablePhrasesM());
            db.execSQL(EverydayLanguageContract.getSqlDeleteTableSubcategories());
            db.execSQL(EverydayLanguageContract.getSqlDeleteTableCategories());
            db.execSQL(EverydayLanguageContract.getSqlDeleteTableSearch());
            onCreate(db);
        }
        catch (SQLiteException ex)
        {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void populateSearchTable() {
        //populate search table
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = EverydayLanguageContract.getSqlInsertTableSearch(GlobalData.getInstance(mContext).currentVoice);
        db.execSQL(sql);
    }

    public void incrementUsage()
    {
        String sql =
                EverydayLanguageContract.getSqlIncrementSetting(2);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public void updateDonated() {
        String sql = EverydayLanguageContract.getSqlUpdateSetting(3, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public int getUsage()
    {
        String sql =
                EverydayLanguageContract.getSqlSelectSetting(2);
        int usage = 0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                usage = cursor.getInt((cursor.getColumnIndex(EverydayLanguageContract.Table_Settings.COLUMN_NAME_SETTING_VALUE)));
                //Log.d("getsettings", String.valueOf(voice));
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return usage;
    }


    public int getVoice() {
        String sql = EverydayLanguageContract.getSqlSelectSetting(1);
       // Log.d("SQL", sql);
        int voice = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                voice = cursor.getInt((cursor.getColumnIndex(EverydayLanguageContract.Table_Settings.COLUMN_NAME_SETTING_VALUE)));
               // Log.d("getSettings", String.valueOf(voice));
            }
            else {
               // Log.d("getVoice", "no results");
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return voice;
    }

    public int getDonated() {
        String sql = EverydayLanguageContract.getSqlSelectSetting(3);
        // Log.d("SQL", sql);
        int voice = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                voice = cursor.getInt((cursor.getColumnIndex(EverydayLanguageContract.Table_Settings.COLUMN_NAME_SETTING_VALUE)));
               // Log.d("getSettings", String.valueOf(voice));
            }
            else {
              //  Log.d("getDonated", "no results");
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return voice;
    }



    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        String sql = EverydayLanguageContract.getSqlSelectCategories();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    int catId = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_Categories.COLUMN_NAME_CATEGORYID));
                    String catName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Categories.COLUMN_NAME_CATEGORY_NAME));
                    categories.add(new Category(catId, catName));
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return categories;
    }

    public ArrayList<SubCategory> getSubCategories(int catId) {
        String sql = EverydayLanguageContract.sqlSelectSubCategories(catId);
        ArrayList<SubCategory> subCategories = new ArrayList<SubCategory>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    int subCatId = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORYID));
                    String subCatName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME));
                    int locked = cursor.getInt(cursor.getColumnIndex((EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_LOCKED)));
                    subCategories.add(new SubCategory(subCatId, catId, subCatName, locked));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }
        return subCategories;
    }

    public int getNextSubCategoryId(int currentSubCatId) {
        String sql = EverydayLanguageContract.getSqlSelectNextSubCategoryId(currentSubCatId);
        int nextSubCatId = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                nextSubCatId = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORYID));
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return nextSubCatId;
    }

    public int getPreviousSubCategoryId(int currentSubCatId) {
        String sql = EverydayLanguageContract.getSqlSelectPreviousSubCategoryId(currentSubCatId);
        int previousSubCatId = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                previousSubCatId = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORYID));
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return previousSubCatId;
    }

    public String getSubCategoryName(int subCatId) {
        String sql = EverydayLanguageContract.getSqlSelectSubCategoryName(subCatId);
        String name = "";;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME));
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return name;
    }


    public ArrayList<Phrase> getPhrases(int subCategoryId) {
        ArrayList<Phrase> phrases = new ArrayList<Phrase>();
        String sql = "";
        if (GlobalData.getInstance(mContext).currentVoice == 0) {
            sql = EverydayLanguageContract.getSqlSelectPhrasesM(subCategoryId);
        } else {
            sql = EverydayLanguageContract.getSqlSelectPhrasesF(subCategoryId);
        }
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            //Log.d("Cursor", String.valueOf(cursor.getColumnCount()));
            if (cursor.moveToFirst()) {
                do {
                    //Log.d("Cursor first", cursor.getColumnName(0));
                    int pid = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_PID));
                    String firstLanguage = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE));
                    String secondLanguage = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE));
                    String romanisation = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_ROMANISATION));
                    String literalTranstion = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION));
                    String notes = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_NOTES));
                    String fileName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_FILE_NAME));
                    int isFav = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_IS_FAVOURITE));
                    Boolean isFavourite = isFav == 0? false : true;
                    phrases.add(new Phrase(pid, firstLanguage, secondLanguage, romanisation, literalTranstion, notes, fileName, isFavourite));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return phrases;
    }

    public void updateIsFavourite(Boolean isFavourite, int pid, String firstLanguage)
    {
        int isFav = isFavourite? 1 : 0;

        String sql = EverydayLanguageContract.getSqlUpdateFavourite(GlobalData.getInstance(mContext).currentVoice, isFav, pid);
        //Log.d("favourite", sql);
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sql);
            //update non active table as well to keep in sync
            sql = EverydayLanguageContract.getSqlUpdateIsFavouriteSecondary(
                    GlobalData.getInstance(mContext).currentVoice, isFav, firstLanguage);
            //Log.d("updateFavourite", sql);
            db.execSQL(sql);
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }
    }

    public void switchVoice() {

            //update settings table
            String sql = EverydayLanguageContract.getSqlInsertOrReplaceSettings(1, "voice", GlobalData.getInstance(mContext).currentVoice);
            //Log.d("switch", sql);
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(sql);

                /*
                sql = EverydayLanguageContract.getSqlSelectSetting(1);
                Cursor cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    do {
                        int value = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_Settings.COLUMN_NAME_SETTING_VALUE));
                        Log.d("setting", String.valueOf(value));
                    } while (cursor.moveToNext());
                }
                */

                //repopulate search table
                sql = EverydayLanguageContract.getSqlClearTableSearch();
                db.execSQL(sql);
                sql = EverydayLanguageContract.getSqlInsertTableSearch(GlobalData.getInstance(mContext).currentVoice);
                db.execSQL(sql);

            } catch (SQLiteException ex) {
                GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
            }

    }

    public void unlockSubCategories()
    {
        String sql = EverydayLanguageContract.getSqlUnlockSubCategories();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sql);
        } catch (SQLiteException ex) {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }
    }

    public ArrayList<SearchResult> getFavourites()
    {
        ArrayList<SearchResult> favourites = new ArrayList<SearchResult>();
        String sql = EverydayLanguageContract.getSqlSelectFavourites(GlobalData.getInstance(mContext).currentVoice);

        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    int pid = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_PID));
                    int subCategoryId = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORYID));
                    String categoryName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Categories.COLUMN_NAME_CATEGORY_NAME));
                    String subCategoryName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME));
                    String firstLanguage = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE));
                    String secondLanguage = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE));
                    String romanisation = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_ROMANISATION));
                    String literalTranslation = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION));
                    String notes = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_NOTES));
                    String fileName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_FILE_NAME));

                    SearchResult fav = new SearchResult(
                            pid,
                            categoryName,
                            subCategoryId,
                            subCategoryName,
                            firstLanguage,
                            secondLanguage,
                            romanisation,
                            literalTranslation,
                            notes,
                            fileName
                    );
                    favourites.add(fav);
                }
                while (cursor.moveToNext());
            }
        }
        catch (SQLiteException ex)
        {
            GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
        }

        return favourites;
    }

    public ArrayList<SearchResult> getSearchResults(String search)
    {
        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

        //(don't) split search into words
       // String[] searchTerms = search.split("\\s+");
       // for (String searchTerm : searchTerms)
        //{
            String sql = EverydayLanguageContract.getSqlSelectSearchResults(search, GlobalData.getInstance(mContext).currentVoice);
           // Log.d("searchquery", sql);
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(sql, null);
                //Log.d("Cursor", String.valueOf(cursor.getColumnCount()));
                if (cursor.moveToFirst()) {
                    do {
                        //Log.d("Cursor first", cursor.getColumnName(0));
                        int pid = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_PID));
                        int subCategoryId = cursor.getInt(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORYID));
                        String categoryName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Categories.COLUMN_NAME_CATEGORY_NAME));
                        String subCategoryName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME));
                        String firstLanguage = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE));
                        String secondLanguage = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE));
                        String romanisation = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_ROMANISATION));
                        String literalTranslation = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION));
                        String notes = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_NOTES));
                        String fileName = cursor.getString(cursor.getColumnIndex(EverydayLanguageContract.Table_Phrases_F.COLUMN_NAME_FILE_NAME));
                        searchResults.add(
                                new SearchResult(
                                        pid,
                                        categoryName,
                                        subCategoryId,
                                        subCategoryName,
                                        firstLanguage,
                                        secondLanguage,
                                        romanisation,
                                        literalTranslation,
                                        notes,
                                        fileName));
                    } while (cursor.moveToNext());
                }
            } catch (SQLiteException ex) {
                GlobalData.getInstance(mContext).handleError("SQLiteException", ex.getMessage());
            }

        //}

        return searchResults;

    }

}