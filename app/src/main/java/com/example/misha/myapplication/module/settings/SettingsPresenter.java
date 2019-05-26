package com.example.misha.myapplication.module.settings;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;

import com.example.misha.myapplication.common.core.BaseActivity;
import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SettingsPresenter extends BaseMainPresenter<SettingsFragmentView> implements SettingsPresenterInterface {

    private Context context;

    public SettingsPresenter(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void init() {
    }

    @Override
    public void onSearchAudience() {
            getView().openFragmentSearchAudience();
    }

    @Override
    public void onOpenFragmentGroups() {
        getView().openFragmentGroups();
    }

    @Override
    public void onCreateDialogSelectTheme() {
        getView().showDialogSelectTheme();
    }

    @Override
    public void onCreateDialogAbout() {
        getView().onCreateDialogAbout().show();
    }

   /* @Override
    public void load() {
        getView().showProgressDialog();
        getCompositeDisposable().add(getRepositoryManager()
                .getSubjects()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::load, throwable -> {
                    getView().hideProgressDialog();
                    processSimpleError(throwable);
                })
        );
    }

    @Override
    public void load(ArrayList<Subject> subjects) {
        getCompositeDisposable().add(getRepositoryManager()
                .getSubjects()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getView().hideProgressDialog();
                    SubjectDao.getInstance().deleteAll();
                    SubjectDao.getInstance().insertAll(response);
                }, throwable -> {
                    getView().hideProgressDialog();
                    processSimpleError(throwable);
                })
        );
    }*/

    public void getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        final Calendar selectedDate = Calendar.getInstance();
        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Preferences.getInstance().setSemesterStart(selectedDate.getTimeInMillis());

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(Preferences.getInstance().getSemestStart());
            mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
            mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            ArrayList<String> allDays = new ArrayList<>();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat mFormat = new SimpleDateFormat("dd.MM");
            for (int week = 0; week < 17; week++) {
                for (int day = 0; day < 7; day++) {
                    String startWeek = mFormat.format(mCalendar.getTime());

                    mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                    mCalendar.add(Calendar.DAY_OF_YEAR, -1);
                    allDays.add(startWeek + " - " + mFormat.format(mCalendar.getTime()));
                    mCalendar.add(Calendar.DAY_OF_YEAR, 1);
                    break;
                }
            }

        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
