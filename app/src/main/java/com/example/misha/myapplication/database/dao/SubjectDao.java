package com.example.misha.myapplication.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.misha.myapplication.database.AbsDao;
import com.example.misha.myapplication.database.AppContentProvider;
import com.example.misha.myapplication.database.entity.Lesson;
import com.example.misha.myapplication.database.entity.Subject;

public class SubjectDao extends AbsDao<Subject> {

    private static volatile SubjectDao instance;

    private SubjectDao() {}

    public static SubjectDao getInstance() {
        if (null == instance){
            instance = new SubjectDao();
        }
        return instance;
    }

    public final static String ID = "idd_subject";
    public final static String SUBJECT = "subjects";


    public static final String[] ALL_SET_PROPERTIES = new String[] {ID, SUBJECT};

    @Override
    protected String[] getAllColumns() {
        return ALL_SET_PROPERTIES;
    }

    @Override
    protected Uri getTableUri() {
        return AppContentProvider.SCHEDULE_URI;
    }

    @Override
    protected Subject makeInstanceFromCursor(Cursor cursor) {
        Subject subject = new Subject();
        subject.setId(getString(cursor, ID));
        subject.setName(getString(cursor, SUBJECT ));
        return subject;
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Subject instance) {
        ContentValues set = new ContentValues();
        set.put(ID, instance.getId());
        set.put(SUBJECT, instance.getName());
        return set;
    }
    public boolean deleteItemById(long id) {
        return super.deleteItemById(id, ID);
    }
}
