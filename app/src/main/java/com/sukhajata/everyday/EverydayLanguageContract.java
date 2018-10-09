package com.sukhajata.everyday;

import android.provider.BaseColumns;


//Database Structure
public final class EverydayLanguageContract
{
    public EverydayLanguageContract() {}

    //Settings
    public static abstract class Table_Settings implements BaseColumns
    {
        public static final String TABLE_NAME = "settings";
        public static final String COLUMN_NAME_SID = "sid";
        public static final String COLUMN_NAME_SETTING_NAME = "settingName";
        public static final String COLUMN_NAME_SETTING_VALUE = "settingValue";

    }

    //1 = voice (male = 0, female = 1)
    //2 = usage
    //3 = donated (no = 0, yes = 1)

    private static final String SQL_CREATE_TABLE_SETTINGS =
            "CREATE TABLE " + Table_Settings.TABLE_NAME + " (" +
                    Table_Settings.COLUMN_NAME_SID + " INTEGER PRIMARY KEY, " +
                    Table_Settings.COLUMN_NAME_SETTING_NAME + " TEXT, " +
                    Table_Settings.COLUMN_NAME_SETTING_VALUE + " INTEGER)";

    public static String getSqlCreateTableSettings() {
        return SQL_CREATE_TABLE_SETTINGS;
    }

    private static final String SQL_DELETE_TABLE_SETTINGS =
            "DROP TABLE IF EXISTS " + Table_Settings.TABLE_NAME;

    public static String getSqlDeleteSettings() {
        return SQL_DELETE_TABLE_SETTINGS;
    }

    public static String getSqlSelectSetting(int sid) {
        String sql = "SELECT " + Table_Settings.COLUMN_NAME_SETTING_VALUE + " " +
                "FROM " + Table_Settings.TABLE_NAME + " " +
                "WHERE " + Table_Settings.COLUMN_NAME_SID + " = " + String.valueOf(sid);

        return sql;
    }

    public static String getSqlInsertOrReplaceSettings(int sid, String name, int value) {
        String sql = "REPLACE INTO " + Table_Settings.TABLE_NAME + " (" +
                Table_Settings.COLUMN_NAME_SID + ", " +
                Table_Settings.COLUMN_NAME_SETTING_NAME + ", " +
                Table_Settings.COLUMN_NAME_SETTING_VALUE + ") " +
                "VALUES (" + String.valueOf(sid) + ", " +
                    "'" + name + "', " +
                    String.valueOf(value) + ")";

        return sql;
    }

    public static String getSqlIncrementSetting(int sid)
    {
        String sql = "UPDATE " + Table_Settings.TABLE_NAME + " " +
                    "SET " + Table_Settings.COLUMN_NAME_SETTING_VALUE + " = " +
                        Table_Settings.COLUMN_NAME_SETTING_VALUE + " + 1 " +
                    "WHERE " + Table_Settings.COLUMN_NAME_SID + " = " +
                    String.valueOf(sid);

        return sql;
    }

    public static String getSqlUpdateSetting(int sid, int value) {
        String sql = "UPDATE " + Table_Settings.TABLE_NAME + " " +
                "SET " + Table_Settings.COLUMN_NAME_SETTING_VALUE + " = " +
                String.valueOf(value) + " " +
                "WHERE " + Table_Settings.COLUMN_NAME_SID + " = " +
                String.valueOf(sid);

        return sql;
    }

    //PhrasesF Table
    public static abstract class Table_Phrases_F implements BaseColumns
    {
        public static final String TABLE_NAME = "phrasesF";
        public static final String COLUMN_NAME_PID = "pid";
        public static final String COLUMN_NAME_SUBCATEGORY_ID = "subCategoryid";
        public static final String COLUMN_NAME_FIRST_LANGUAGE = "firstLanguage";
        public static final String COLUMN_NAME_SECOND_LANGUAGE = "secondLanguage";
        public static final String COLUMN_NAME_ROMANISATION = "romanisation";
        public static final String COLUMN_NAME_LITERAL_TRANSLATION = "literalTranslation";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_FILE_NAME = "fileName";
        public static final String COLUMN_NAME_IS_FAVOURITE = "isFavourite";

    }

    private static final String SQL_CREATE_TABLE_PHRASES_F =
            "CREATE TABLE " + Table_Phrases_F.TABLE_NAME + " (" +
                    Table_Phrases_F.COLUMN_NAME_PID + " INTEGER PRIMARY KEY, " +
                    Table_Phrases_F.COLUMN_NAME_SUBCATEGORY_ID + " INTEGER, " +
                    Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE + " TEXT, " +
                    Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE + " TEXT, " +
                    Table_Phrases_F.COLUMN_NAME_ROMANISATION + " TEXT, " +
                    Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION + " TEXT, " +
                    Table_Phrases_F.COLUMN_NAME_NOTES + " TEXT, " +
                    Table_Phrases_F.COLUMN_NAME_FILE_NAME + " TEXT, " +
                    Table_Phrases_F.COLUMN_NAME_IS_FAVOURITE + " INTEGER DEFAULT 0)";

    public static String getSqlCreateTablePhrasesF()
    {
        return SQL_CREATE_TABLE_PHRASES_F;
    }

    private static final String SQL_DELETE_TABLE_PHRASES_F =
            "DROP TABLE IF EXISTS " + Table_Phrases_F.TABLE_NAME;

    public static String getSqlDeleteTablePhrasesF() {
        return SQL_DELETE_TABLE_PHRASES_F;
    }

    public static String getSqlSelectPhrasesF(int subCategoryId)
    {
        String sql = "SELECT " +
                Table_Phrases_F.COLUMN_NAME_PID + ", " +
                Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE + ", " +
                Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE + ", " +
                Table_Phrases_F.COLUMN_NAME_ROMANISATION + ", " +
                Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION + ", " +
                Table_Phrases_F.COLUMN_NAME_NOTES + ", " +
                Table_Phrases_F.COLUMN_NAME_FILE_NAME + ", " +
                Table_Phrases_F.COLUMN_NAME_IS_FAVOURITE + " " +
                "FROM " + Table_Phrases_F.TABLE_NAME + " " +
                "WHERE " + Table_Phrases_F.COLUMN_NAME_SUBCATEGORY_ID + " = " + String.valueOf(subCategoryId);

        return sql;
    }

    //PhrasesM Table
    public static abstract class Table_Phrases_M implements BaseColumns
    {
        public static final String TABLE_NAME = "phrasesM";
        public static final String COLUMN_NAME_PID = "pid";
        public static final String COLUMN_NAME_SUBCATEGORY_ID = "subCategoryid";
        public static final String COLUMN_NAME_FIRST_LANGUAGE = "firstLanguage";
        public static final String COLUMN_NAME_SECOND_LANGUAGE = "secondLanguage";
        public static final String COLUMN_NAME_ROMANISATION = "romanisation";
        public static final String COLUMN_NAME_LITERAL_TRANSLATION = "literalTranslation";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_FILE_NAME = "fileName";
        public static final String COLUMN_NAME_IS_FAVOURITE = "isFavourite";

    }

    private static final String SQL_CREATE_TABLE_PHRASES_M =
            "CREATE TABLE " + Table_Phrases_M.TABLE_NAME + " (" +
                    Table_Phrases_M.COLUMN_NAME_PID + " INTEGER PRIMARY KEY, " +
                    Table_Phrases_M.COLUMN_NAME_SUBCATEGORY_ID + " INTEGER, " +
                    Table_Phrases_M.COLUMN_NAME_FIRST_LANGUAGE + " TEXT, " +
                    Table_Phrases_M.COLUMN_NAME_SECOND_LANGUAGE + " TEXT, " +
                    Table_Phrases_M.COLUMN_NAME_ROMANISATION + " TEXT, " +
                    Table_Phrases_M.COLUMN_NAME_LITERAL_TRANSLATION + " TEXT, " +
                    Table_Phrases_M.COLUMN_NAME_NOTES + " TEXT, " +
                    Table_Phrases_M.COLUMN_NAME_FILE_NAME + " TEXT, " +
                    Table_Phrases_M.COLUMN_NAME_IS_FAVOURITE + " INTEGER DEFAULT 0)";

    public static String getSqlCreateTablePhrasesM()
    {
        return SQL_CREATE_TABLE_PHRASES_M;
    }

    private static final String SQL_DELETE_TABLE_PHRASES_M =
            "DROP TABLE IF EXISTS " + Table_Phrases_M.TABLE_NAME;

    public static String getSqlDeleteTablePhrasesM() {
        return SQL_DELETE_TABLE_PHRASES_M;
    }

    public static String getSqlSelectPhrasesM(int subCategoryId)
    {
        String sql = "SELECT " +
                Table_Phrases_M.COLUMN_NAME_PID + ", " +
                Table_Phrases_M.COLUMN_NAME_FIRST_LANGUAGE + ", " +
                Table_Phrases_M.COLUMN_NAME_SECOND_LANGUAGE + ", " +
                Table_Phrases_M.COLUMN_NAME_ROMANISATION + ", " +
                Table_Phrases_M.COLUMN_NAME_LITERAL_TRANSLATION + ", " +
                Table_Phrases_M.COLUMN_NAME_NOTES + ", " +
                Table_Phrases_M.COLUMN_NAME_FILE_NAME + ", " +
                Table_Phrases_M.COLUMN_NAME_IS_FAVOURITE + " " +
                "FROM " + Table_Phrases_M.TABLE_NAME + " " +
                "WHERE " + Table_Phrases_M.COLUMN_NAME_SUBCATEGORY_ID + " = " + String.valueOf(subCategoryId);

        return sql;
    }


    public static String getSqlSelectFavourites(int currentVoice)
    {
        String CurrentTable = Table_Phrases_F.TABLE_NAME;
        if (currentVoice == 0) {
            CurrentTable = Table_Phrases_M.TABLE_NAME;
        }
        String sql = "SELECT " +
                "p." + Table_Phrases_F.COLUMN_NAME_PID + ", " +
                "c." + Table_Categories.COLUMN_NAME_CATEGORY_NAME + ", " +
                "sc." + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + ", " +
                "sc." + Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_ROMANISATION + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_NOTES + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_FILE_NAME + " " +
                "FROM " + Table_Search.TABLE_NAME + " s " +
                "JOIN " + CurrentTable + " p " +
                "ON s." + Table_Search.COLUMN_NAME_PID + " = p." + Table_Phrases_F.COLUMN_NAME_PID + " " +
                "JOIN " + Table_SubCategories.TABLE_NAME + " sc " +
                "ON p." + Table_Phrases_F.COLUMN_NAME_SUBCATEGORY_ID + " = sc." + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " " +
                "JOIN " + Table_Categories.TABLE_NAME + " c " +
                "ON sc." + Table_SubCategories.COLUMN_NAME_CATEGORYID + " = c." + Table_Categories.COLUMN_NAME_CATEGORYID + " " +
                "WHERE p." + Table_Phrases_F.COLUMN_NAME_IS_FAVOURITE + " = 1 ";

        return sql;
    }

    public static String getSqlUpdateFavourite(int currentVoice, int isFavourite, int pid)
    {
        String TableName = Table_Phrases_M.TABLE_NAME;
        if (currentVoice == 1) {
            TableName = Table_Phrases_F.TABLE_NAME;
        }
        String sql = "UPDATE " + TableName + " " +
                "SET " + Table_Phrases_F.COLUMN_NAME_IS_FAVOURITE + " = " + String.valueOf(isFavourite) + " " +
                "WHERE " + Table_Phrases_F.COLUMN_NAME_PID  + " = " + String.valueOf(pid);

        return sql;
    }

    //Update favourite on non active table
    public static String getSqlUpdateIsFavouriteSecondary(int currentVoice, int isFavourite, String firstLanguage) {
        String TableName = Table_Phrases_M.TABLE_NAME;
        if (currentVoice == 0) {
            TableName = Table_Phrases_F.TABLE_NAME;
        }
        //escape single quotes
        firstLanguage = firstLanguage.replaceAll("'", "''");
        String sql = "UPDATE " + TableName + " " +
                "SET " + Table_Phrases_F.COLUMN_NAME_IS_FAVOURITE + " = " + String.valueOf(isFavourite) + " " +
                "WHERE " + Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE  + " = '" + firstLanguage + "'";

        return sql;
    }

    //Categories Table
    public  static abstract class Table_Categories implements BaseColumns
    {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME_CATEGORYID = "categoryId";
        public static final String COLUMN_NAME_CATEGORY_NAME = "categoryName";
    }

    private static String SQL_CREATE_TABLE_CATEGORIES =
            "CREATE TABLE " + Table_Categories.TABLE_NAME + " (" +
                    Table_Categories.COLUMN_NAME_CATEGORYID + " INTEGER PRIMARY KEY, " +
                    Table_Categories.COLUMN_NAME_CATEGORY_NAME + " TEXT)";

    public static String getSqlCreateTableCategories()
    {
        return SQL_CREATE_TABLE_CATEGORIES;
    }

    private static final String SQL_DELETE_TABLE_CATEGORIES =
            "DROP TABLE IF EXISTS " + Table_Categories.TABLE_NAME;

    public static String getSqlDeleteTableCategories() {
        return SQL_DELETE_TABLE_CATEGORIES;
    }

    private static String SQL_SELECT_CATEGORIES =
            "SELECT " + Table_Categories.COLUMN_NAME_CATEGORYID + ", " +
                    Table_Categories.COLUMN_NAME_CATEGORY_NAME + " " +
                    "FROM " + Table_Categories.TABLE_NAME;

    public static String getSqlSelectCategories()
    {
        return SQL_SELECT_CATEGORIES;
    }

    //SubCategories Table
    public static abstract class Table_SubCategories implements BaseColumns
    {
        public static final String TABLE_NAME = "subCategories";
        public static final String COLUMN_NAME_SUBCATEGORYID = "subCategoryId";
        public static final String COLUMN_NAME_CATEGORYID = "categoryId";
        public static final String COLUMN_NAME_SUBCATEGORY_NAME = "subCategoryName";
        public static final String COLUMN_NAME_LOCKED = "locked";

    }

    private static final String SQL_CREATE_TABLE_SUBCATEGORIES =
            "CREATE TABLE " + Table_SubCategories.TABLE_NAME + " (" +
                    Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " INTEGER PRIMARY KEY, " +
                    Table_SubCategories.COLUMN_NAME_CATEGORYID + " INTEGER, " +
                    Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME + " TEXT, " +
                    Table_SubCategories.COLUMN_NAME_LOCKED + " INTEGER)";

    public static String getSqlCreateTableSubcategories()
    {
        return SQL_CREATE_TABLE_SUBCATEGORIES;
    }

    private static final String SQL_DELETE_TABLE_SUBCATEGORIES =
            "DROP TABLE IF EXISTS " + Table_SubCategories.TABLE_NAME;

    public static String getSqlDeleteTableSubcategories()
    {
        return SQL_DELETE_TABLE_SUBCATEGORIES;
    }

    public static String sqlSelectSubCategories(int _catId)
    {

        String sql = "SELECT " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + ", " +
                Table_SubCategories.COLUMN_NAME_CATEGORYID + ", " +
                Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME + ", " +
                Table_SubCategories.COLUMN_NAME_LOCKED + " " +
                "FROM " +Table_SubCategories.TABLE_NAME + " " +
                "WHERE " +Table_SubCategories.COLUMN_NAME_CATEGORYID + " = " + String.valueOf(_catId);

        return sql;
    }

    public static String getSqlSelectNextSubCategoryId(int currentSubCatId)
    {
        String sql = "SELECT " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " " +
                "FROM " + Table_SubCategories.TABLE_NAME + " " +
                "WHERE " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " > " + String.valueOf(currentSubCatId) + " " +
                "ORDER BY " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " " +
                "LIMIT 1";

        return sql;
    }

    public static String getSqlSelectPreviousSubCategoryId(int currentSubCatId)
    {
        String sql = "SELECT " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " " +
                "FROM " + Table_SubCategories.TABLE_NAME + " " +
                "WHERE " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " < " + String.valueOf(currentSubCatId) + " " +
                "ORDER BY " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " DESC " +
                "LIMIT 1";

        return sql;
    }

    public static String getSqlSelectSubCategoryName(int subCatId)
    {
        String sql = "SELECT " + Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME + " " +
                "FROM " + Table_SubCategories.TABLE_NAME + " " +
                "WHERE " + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " = " + String.valueOf(subCatId);

        return sql;
    }

    public static String getSqlUnlockSubCategories()
    {
        String sql = "UPDATE " + Table_SubCategories.TABLE_NAME + " " +
                "SET locked = 0";

        return sql;
    }


    //Search table
    public static abstract class Table_Search implements BaseColumns {
        public static final String TABLE_NAME = "search";
        public static final String COLUMN_NAME_PID = "pid";
        public static final String COLUMN_NAME_FIRST_LANGUAGE = "firstLanguage";
        public static final String COLUMN_NAME_SECOND_LANGUAGE = "secondLanguage";
        public static final String COLUMN_NAME_ROMANISATION = "romanisation";
        public static final String COLUMN_NAME_LITERAL_TRANSLATION = "literalTranslation";
    }

    private static final String SQL_CREATE_TABLE_SEARCH =
            "CREATE VIRTUAL TABLE " + Table_Search.TABLE_NAME +
                    " USING fts3 (" +
                    Table_Search.COLUMN_NAME_PID + " INTEGER PRIMARY KEY, " +
                    Table_Search.COLUMN_NAME_FIRST_LANGUAGE + " TEXT, " +
                    Table_Search.COLUMN_NAME_SECOND_LANGUAGE + " TEXT, " +
                    Table_Search.COLUMN_NAME_ROMANISATION + " TEXT, " +
                    Table_Search.COLUMN_NAME_LITERAL_TRANSLATION + " TEXT)";

    public  static String getSqlCreateTableSearch() {return SQL_CREATE_TABLE_SEARCH;}

    private static final String SQL_DELETE_TABLE_SEARCH =
            "DROP TABLE IF EXISTS " + Table_Search.TABLE_NAME;

    public static String getSqlDeleteTableSearch() {
        return SQL_DELETE_TABLE_SEARCH;
    }

    private static final String SQL_CLEAR_TABLE_SEARCH =
            "DELETE FROM " + Table_Search.TABLE_NAME + " WHERE 1";

    public static String getSqlClearTableSearch() {
        return SQL_CLEAR_TABLE_SEARCH;
    }

    public static String getSqlInsertTableSearch(int currentVoice) {
        String currentTable = Table_Phrases_F.TABLE_NAME;
        if (currentVoice == 0) {
            currentTable = Table_Phrases_M.TABLE_NAME;
        }
        String sql =
                "INSERT INTO " + Table_Search.TABLE_NAME + " (  " +
                        Table_Search.COLUMN_NAME_PID + ", " +
                        Table_Search.COLUMN_NAME_FIRST_LANGUAGE + ", " +
                        Table_Search.COLUMN_NAME_SECOND_LANGUAGE + ", " +
                        Table_Search.COLUMN_NAME_ROMANISATION + ", " +
                        Table_Search.COLUMN_NAME_LITERAL_TRANSLATION + ") " +
                        "SELECT  " +
                        Table_Phrases_F.COLUMN_NAME_PID + ", " +
                        Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE + ", " +
                        Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE + ", " +
                        Table_Phrases_F.COLUMN_NAME_ROMANISATION + ", " +
                        Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION + " " +
                        "FROM " + currentTable;

        return sql;
    }

    public static String getSqlSelectSearchResults(String searchTerm, int currentVoice)
    {
        String currentTable = Table_Phrases_F.TABLE_NAME;
        if (currentVoice == 0) {
            currentTable = Table_Phrases_M.TABLE_NAME;
        }
        searchTerm = searchTerm.replaceAll("'", "''");
        String sql = "SELECT " +
                "p." + Table_Phrases_F.COLUMN_NAME_PID + ", " +
                "c." + Table_Categories.COLUMN_NAME_CATEGORY_NAME + ", " +
                "sc." + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + ", " +
                "sc." + Table_SubCategories.COLUMN_NAME_SUBCATEGORY_NAME + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_FIRST_LANGUAGE + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_SECOND_LANGUAGE + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_ROMANISATION + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_LITERAL_TRANSLATION + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_NOTES + ", " +
                "p." + Table_Phrases_F.COLUMN_NAME_FILE_NAME + " " +
                "FROM " + Table_Search.TABLE_NAME + " s " +
                "JOIN " + currentTable + " p " +
                "ON s." + Table_Search.COLUMN_NAME_PID + " = p." + Table_Phrases_F.COLUMN_NAME_PID + " " +
                "JOIN " + Table_SubCategories.TABLE_NAME + " sc " +
                "ON p." + Table_Phrases_F.COLUMN_NAME_SUBCATEGORY_ID + " = sc." + Table_SubCategories.COLUMN_NAME_SUBCATEGORYID + " " +
                "JOIN " + Table_Categories.TABLE_NAME + " c " +
                "ON sc." + Table_SubCategories.COLUMN_NAME_CATEGORYID + " = c." + Table_Categories.COLUMN_NAME_CATEGORYID + " " +
                "WHERE p." + Table_Phrases_F.COLUMN_NAME_PID + " IN " +
                    "(SELECT " + Table_Search.COLUMN_NAME_PID + " FROM " + Table_Search.TABLE_NAME + " " +
                        "WHERE " + Table_Search.COLUMN_NAME_FIRST_LANGUAGE + " MATCH '" + searchTerm + "' " +
                    "UNION " +
                    "SELECT " + Table_Search.COLUMN_NAME_PID + " FROM " + Table_Search.TABLE_NAME + " " +
                        "WHERE " + Table_Search.COLUMN_NAME_LITERAL_TRANSLATION + " MATCH '" + searchTerm + "' " +
                    "UNION " +
                    "SELECT " + Table_Search.COLUMN_NAME_PID + " FROM " + Table_Search.TABLE_NAME + " " +
                        "WHERE " + Table_Search.COLUMN_NAME_ROMANISATION + " MATCH '" + searchTerm + "' " +
                    "UNION " +
                    "SELECT " + Table_Search.COLUMN_NAME_PID + " FROM " + Table_Search.TABLE_NAME + " " +
                        "WHERE " + Table_Search.COLUMN_NAME_SECOND_LANGUAGE + " MATCH '" + searchTerm + "' )";


        return sql;
    }


}
