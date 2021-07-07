package com.example.view_pager;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file names
    private static final String PREF_NAME = "VANNET";

    private static final String IS_FIRST_LAUNCH = "isFirstLaunch";

    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirsTime){
        editor.putBoolean(IS_FIRST_LAUNCH, isFirsTime);
    }

    public boolean isFirstLaunch(){
        return preferences.getBoolean(IS_FIRST_LAUNCH,true);
    }
}
