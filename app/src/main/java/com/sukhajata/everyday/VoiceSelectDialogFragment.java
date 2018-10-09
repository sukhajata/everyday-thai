package com.sukhajata.everyday;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;

/**
 * Created by user on 3/a10/2016.
 */
public class VoiceSelectDialogFragment  extends DialogFragment{

    //instantiating activity must implement this interface to receive callbacks
    public interface VoiceSelectDialogListener {
        public void onDialogPositiveClick(VoiceSelectDialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    VoiceSelectDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (VoiceSelectDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement VoiceSelectDialogListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (VoiceSelectDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement VoiceSelectDialogListener");
        }
    }

    public int selectedVoice;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.select_voice)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // user clicked ok
                        mListener.onDialogPositiveClick(VoiceSelectDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.setSingleChoiceItems(R.array.voices, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedVoice = which;
            }
        });
                // Create the AlertDialog object and return it
        return builder.create();
    }
}
