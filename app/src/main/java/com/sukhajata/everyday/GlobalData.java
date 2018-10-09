package com.sukhajata.everyday;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 9/26/2015.
 */
public class GlobalData {

    public int currentSubCatId;
    public int currentVoice;

    private Context mContext;

    private static GlobalData instance;

    private GlobalData(Context context) {
        mContext = context;
    }

    public static GlobalData getInstance(Context context) {
        if (instance == null) {
            instance = new GlobalData(context);
        }
        return instance;
    }

    public void handleError(String tag, String message) {

        Toast.makeText(mContext, tag + ", " + message, Toast.LENGTH_LONG).show();

    }

}
