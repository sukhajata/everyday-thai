package com.sukhajata.everyday;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;

/**
 * Created by Tim on 22/09/2016.
 */
public class ToneExplanationFragment extends Fragment {

    private IntroSlideCompletedListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.fragment_tones, container, false);

        final Button button = (Button) layout.findViewById(R.id.button_ok_tones);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mListener.onSlideCompleted(MainActivity.CODE_TONES);
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
