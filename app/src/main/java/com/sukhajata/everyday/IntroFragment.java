package com.sukhajata.everyday;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;

/**
 * Created by Tim on 10/09/2016.
 */
public class IntroFragment extends Fragment {

    private IntroSlideCompletedListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.fragment_intro, container, false);

        final Button button = (Button) layout.findViewById(R.id.button_select_voice);

        final RadioButton male = (RadioButton) layout.findViewById(R.id.radio_male);
        male.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                button.setEnabled(true);
            }

        });

        RadioButton female = (RadioButton)layout.findViewById(R.id.radio_female);
        female.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                button.setEnabled(true);
            }

        });


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //RadioButton male = (RadioButton) GlobalData.getInstance().mainActivity.findViewById(R.id.radio_male);
                //RadioButton female = (RadioButton)GlobalData.getInstance().mainActivity.findViewById(R.id.radio_female);

                if (male.isChecked()) {
                    GlobalData.getInstance(getActivity()).currentVoice = 0;
                } else {
                    GlobalData.getInstance(getActivity()).currentVoice = 1;
                }

                mListener.onSlideCompleted(MainActivity.CODE_INTRO);
            }
        });
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IntroSlideCompletedListener) {
            mListener = (IntroSlideCompletedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IntroSlideCompletedListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (IntroSlideCompletedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
            + " must implement IntroSlideCompletedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
