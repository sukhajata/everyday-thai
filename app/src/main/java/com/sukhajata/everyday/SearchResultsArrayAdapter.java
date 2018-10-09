package com.sukhajata.everyday;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on a11/2/2015.
 */
public class SearchResultsArrayAdapter extends ArrayAdapter<SearchResult> {

    private ArrayList<SearchResult> searchResults;
    private Context mContext;

    public SearchResultsArrayAdapter(Context con, ArrayList<SearchResult> _searchResults)
    {
        super(con, R.layout.searchresult_item, _searchResults);
        mContext = con;
        searchResults = _searchResults;
        //SoundHelper.getInstance(con).LoadSearchAudioFiles(searchResults);

    }

    @Override
    public SearchResult getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.searchresult_item, parent, false);

        SearchResult searchResult = searchResults.get(position);

        if (searchResult != null)
        {
            TextView txtSCN = (TextView)rowView.findViewById(R.id.subcategoryName);
            txtSCN.setText(searchResult.categoryName + " > " + searchResult.subCategoryName);

            TextView txtFirstRow = (TextView)rowView.findViewById(R.id.secondLanguage);
            txtFirstRow.setText(searchResult.secondLanguage + "\n" + searchResult.firstLanguage);

            TextView txtSecondRow = (TextView)rowView.findViewById(R.id.firstLanguage);
            String secondTier = searchResult.romanisation;
            
            SpannableString formattedTranslation;
            if (searchResult.literalTranslation != null && !searchResult.literalTranslation.equals(""))
            {
                int idxItalicsStart = secondTier.length() + 2; //for " ("
                int idxItalicsEnd = idxItalicsStart + searchResult.literalTranslation.length();
                secondTier = secondTier + "\n(" + searchResult.literalTranslation + ")";
                formattedTranslation = new SpannableString(secondTier);
                formattedTranslation.setSpan(new StyleSpan(Typeface.ITALIC), idxItalicsStart,
                        idxItalicsEnd, 0);
            }
            else
            {
                formattedTranslation = new SpannableString(secondTier);
            }
            txtSecondRow.setText(formattedTranslation);


            TextView txtN = (TextView)rowView.findViewById(R.id.notes);
            if (searchResult.notes != null && !searchResult.notes.equals("")) {
                android.text.Spanned notes = Html.fromHtml("<i>" + searchResult.notes + "</i>");
                txtN.setText(notes);
            }
            else {
                txtN.setVisibility(View.GONE);
            }


        }

        return rowView;
    }

}
