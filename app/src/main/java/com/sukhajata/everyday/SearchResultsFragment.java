package com.sukhajata.everyday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on a11/2/2015.
 */
public class SearchResultsFragment extends Fragment {

    private ListView listSearchResults;
    private ArrayList<SearchResult> searchResults;

    public SearchResultsFragment(){}

    public static SearchResultsFragment newInstance(ArrayList<SearchResult> _searchResults) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SearchResultsActivity.ARG_NAME_SEARCH_RESULTS, _searchResults);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            searchResults = getArguments().getParcelableArrayList(SearchResultsActivity.ARG_NAME_SEARCH_RESULTS);
            //load audio in new thread
           // new Thread(new Runnable() {
             /*   public void run() {
                    SoundHelper
                            .getInstance(getActivity())
                            .LoadSearchAudioFiles(searchResults);
                }
            }).start();
            */
        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listSearchResults = (ListView)inflater.inflate(R.layout.fragment_main, container, false);
        listSearchResults.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final SearchResultsArrayAdapter searchArrayAdapter =
                new SearchResultsArrayAdapter(getActivity(), searchResults);
        listSearchResults.setAdapter(searchArrayAdapter);
        listSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult selectedResult = searchArrayAdapter.getItem(position);
                SoundHelper.getInstance(getActivity()).playSearchResult(selectedResult);

            }
        });
        return listSearchResults;
    }

    public void PlayAll()
    {
        //Log.d("action", "play all");
        for (int i = 0; i < listSearchResults.getCount(); i++) {
            listSearchResults.performItemClick(
                    listSearchResults.getAdapter().getView(i,null, null),
                    i,
                    listSearchResults.getAdapter().getItemId(i));
        }
    }
}
