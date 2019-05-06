package com.example.misha.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseActivity;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.data.database.DatabaseHelper;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.module.callSchedule.CallsSchedule;
import com.example.misha.myapplication.module.schedule.explore.ScheduleFragment;
import com.example.misha.myapplication.module.settings.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Preferences.getInstance().isHintsOpened()) {
            Intent intent = new Intent(MainActivity.this, ActivityStart.class);
            startActivity(intent);
            Preferences.getInstance().setHintsOpened();
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.what:
                           Fragment fragment = new CallsSchedule();
                            replaceFragment(fragment);
                            return true;
                        case R.id.rasp_day:
                            fragment = new ScheduleFragment();
                            replaceFragment(fragment);
                            return true;
                        case R.id.settings:
                            fragment = new Settings();
                            replaceFragment(fragment);
                            return true;
                    }
                    return false;
                });
        bottomNavigationView.setSelectedItemId(R.id.rasp_day);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new DatabaseHelper(this).getWritableDatabase();
        CallDao.getInstance().initTable();
        LessonDao.getInstance().initTable();
    }


}