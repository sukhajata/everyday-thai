package com.sukhajata.everyday;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on a11/2/2015.
 */
public class SearchResult implements Parcelable{
    public  int pid;
    public int subCategoryId;
    public String categoryName;
    public String subCategoryName;
    public String firstLanguage;
    public String secondLanguage;
    public String romanisation;
    public String literalTranslation;
    public String notes;
    public String fileName;

    public SearchResult(){}

    public SearchResult(int _pid,
                        String _categoryName,
                        int _subCategoryId,
                        String _subCategoryName,
                        String _firstLanguage,
                        String _secondLanguage,
                        String _romanisation,
                        String _literalTranslation,
                        String _notes,
                        String _fileName)
    {
        pid = _pid;
        categoryName = _categoryName;
        subCategoryId = _subCategoryId;
        subCategoryName = _subCategoryName;
        firstLanguage = _firstLanguage;
        secondLanguage = _secondLanguage;
        romanisation = _romanisation;
        literalTranslation = _literalTranslation;
        notes = _notes;
        fileName = _fileName;
    }

    private SearchResult(Parcel in) {
        pid = in.readInt();
        categoryName = in.readString();
        subCategoryId = in.readInt();
        subCategoryName = in.readString();
        firstLanguage = in.readString();
        secondLanguage = in.readString();
        romanisation = in.readString();
        literalTranslation = in.readString();
        notes = in.readString();
        fileName = in.readString();
    }

    public int getLayoutResId() {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(pid);
        out.writeString(categoryName);
        out.writeInt(subCategoryId);
        out.writeString(subCategoryName);
        out.writeString(firstLanguage);
        out.writeString(secondLanguage);
        out.writeString(romanisation);
        out.writeString(literalTranslation);
        out.writeString(notes);
        out.writeString(fileName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SearchResult> CREATOR
            = new Parcelable.Creator<SearchResult>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}
