package com.sukhajata.everyday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * Created by Administrator on 3/08/2015.
 */
public class MainFragment extends Fragment {

    //private PhraseArrayAdapter phraseArrayAdapter;
    //private ListView listPhrases;
    private ArrayList<Phrase> phrases;
    private  int position;
    public static final String ARG_NAME_SUBCAT_ID = "subCatId";


    public MainFragment() {
    }

    public static MainFragment newInstance(int subCatId) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NAME_SUBCAT_ID, subCatId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int id = getArguments().getInt(ARG_NAME_SUBCAT_ID);
            phrases = EverydayLanguageDbHelper
                    .getInstance(getActivity().getApplicationContext())
                    .getPhrases(id);


            //load audio in new thread
          /*  new Thread(new Runnable() {
                public void run() {
                    SoundHelper
                            .getInstance(getActivity())
                            .LoadAudioFiles(phrases, this);
                }
            }).start(); */
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = (View) inflater.inflate(R.layout.fragment_main, container, false);

        //ListView listPhrases = (ListView)inflater.inflate(R.layout.fragment_main, container, false);
        ListView listPhrases = (ListView)layout.findViewById(R.id.phrase_list);
        listPhrases.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        final PhraseArrayAdapter phraseArrayAdapter = new PhraseArrayAdapter(getActivity(), phrases);
        listPhrases.setAdapter(phraseArrayAdapter);

        listPhrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phrase selectedPhrase = phraseArrayAdapter.getItem(position);
                SoundHelper.getInstance(getActivity()).playPhrase(selectedPhrase);

            }
        });

        /*
        RecyclerView layout = (RecyclerView) inflater.inflate(R.layout.fragment_main2, container, false);
        layout.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        layout.setAdapter(new PhraseRecyclerViewAdapter(layout.getContext(), phrases));
        */

        return layout;
    }

    /*public ListView getListView() {
        return listPhrases;
    }*/

    /*
    public void LoadNewContent()
    {
        phraseArrayAdapter = new PhraseArrayAdapter(getActivity());
        listPhrases.setAdapter(phraseArrayAdapter);

        if (! this.isDetached()) {
            getFragmentManager().beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
    */

    /*
    public void PlayAll()
    {
        //Log.d("action", "play all");

        position = 0;
        if (listPhrases.getSelectedItemPosition() > 0) {
            position = listPhrases.getSelectedItemPosition();
        }

        GlobalData.getInstance().playingAll = true;

        selectItem();
    }
*/

/*
    //Select items in turn, waiting for 'pause' between selectio
    // ns
    public void selectItem() {
        int pause = 3000;

        if (GlobalData.getInstance().playingAll) {
            listPhrases.performItemClick(phraseArrayAdapter.getView(position, null, null),
                    position,
                    listPhrases.getItemIdAtPosition(position));

            listPhrases.setItemChecked(position,true );

            if (position == listPhrases.getCount() - 1) {
                position = 0;
            } else {
                position = position + 1;
            }

            //delay next item selection
                this.getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selectItem();
                    }
                }, pause);
        }

    }
*/


}
