package com.example.misha.myapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

public final class Preferences {

    private static volatile Preferences instance;
    private final SharedPreferences mPrefs;
    Calendar calendar = Calendar.getInstance();

    private static final String PREF_KEY_FIRST_OPEN_HINT = "PREF_KEY_FIRST_OPEN_HINT";
    private static final String PREF_KEY_SEMESTER_START = "PREF_KEY_SEMESTER_START";
    private static final String PREF_KEY_SELECT_WEEK = "PREF_KEY_SELECT_WEEK";
    private static final String PREF_KEY_SELECT_DAY = "PREF_KEY_SELECT_DAY";

    public static Preferences getInstance() {
        if (instance != null) return instance;
        instance = new Preferences();
        return instance;
    }

    private Preferences() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ScheduleApp.getAppContext());
    }


    public boolean isHintsOpened() {
        return mPrefs.getBoolean(PREF_KEY_FIRST_OPEN_HINT, false);
    }


    public void setHintsOpened() {
        mPrefs.edit().putBoolean(PREF_KEY_FIRST_OPEN_HINT, true).apply();
    }

    public void setSemesterStart(long date) {
        mPrefs.edit().putLong(PREF_KEY_SEMESTER_START, date).apply();
    }

    public long getSemestStart() {
        return mPrefs.getLong(PREF_KEY_SEMESTER_START, calendar.getTimeInMillis());
    }

    public void setSelectedWeekEditSchedule(int position) {
        mPrefs.edit().putInt(PREF_KEY_SELECT_WEEK, position).apply();
    }

    public int getSelectedWeekEditSchedule() {
        return mPrefs.getInt(PREF_KEY_SELECT_WEEK, 0);
    }


}
