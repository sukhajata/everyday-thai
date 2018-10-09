
package com.sukhajata.everyday;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Administrator on a11/2/2015.
 */
public class SearchResultsActivity extends Activity {

    public static String ARG_NAME_SEARCH_QUERY = "SearchQuery";
    public static String ARG_NAME_SEARCH_RESULTS = "SearchResults";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Intent outIntent = new Intent();
            outIntent.putExtra(ARG_NAME_SEARCH_QUERY, query);
            //outIntent.putParcelableArrayListExtra(ARG_NAME_SEARCH_RESULTS, searchResults);
            setResult(Activity.RESULT_OK, outIntent);

            //GlobalData.getInstance(this).mainActivity.showSearchResults();

        }


        this.finish();
    }



}
