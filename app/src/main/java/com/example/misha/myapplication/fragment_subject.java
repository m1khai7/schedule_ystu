package com.example.misha.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.misha.myapplication.data.ScheduleClass;
import com.example.misha.myapplication.data.ScheduleDB;

import java.util.ArrayList;


public class fragment_subject extends android.support.v4.app.Fragment {


    EditText input_subject;
    ListView list_subjects;
    private ScheduleDB ScheduleDB;
    final ArrayList<String> subject_list = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    String select_item="";
    ViewPager vp;
    Button clear_subjects;
    Integer abc=0;
    public fragment_subject() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_subject, container, false);

        ScheduleDB = new ScheduleDB(getActivity());

        input_subject = view.findViewById(R.id.input_subject);
        list_subjects = view.findViewById(R.id.list_subjects);


        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, subject_list);
        list_subjects.setAdapter(adapter);

        list_subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                select_item = textView.getText().toString();
                onCreateDialogDeleteItem().show();
            }
        });

        start();

        vp = getActivity().findViewById(R.id.viewPager); //добавить подсказку

        input_subject.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                    input_subject.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    String subject = input_subject.getText().toString();
                    subject = subject.trim().replaceAll(" +", " ");
                    if(TextUtils.isEmpty(subject)||subject==" ") {
                        input_subject.setError("Введите предмет");
                        return true;
                    }
                    SQLiteDatabase db = ScheduleDB.getWritableDatabase();
                    db.execSQL("INSERT INTO " + ScheduleClass.subjects.TABLE_NAME + " (" + ScheduleClass.subjects.subject + ") VALUES ('" + subject + "');");
                    input_subject.getText().clear();
                    start();
                    adapter.notifyDataSetChanged();
                    return true;
                }
                else{
                    return false;
                }
            }
        });


        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        abc=tabLayout.getSelectedTabPosition();

        return view;
    }
    public Dialog onCreateDialogDeleteItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setCancelable(false).setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SQLiteDatabase db = ScheduleDB.getWritableDatabase();
                db.execSQL("DELETE FROM " + ScheduleClass.subjects.TABLE_NAME + " WHERE "+ ScheduleClass.subjects.subject + "='"+ select_item+"'");
                start();
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).setTitle("Удалить предмет «"+select_item+"»?");
        return builder.create();
    }



    public void start(){

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, subject_list);
        list_subjects.setAdapter(adapter);

        SQLiteDatabase db = ScheduleDB.getReadableDatabase();
        String searchQuery = "SELECT "+ ScheduleClass.subjects.subject +" FROM " + ScheduleClass.subjects.TABLE_NAME + " WHERE "+ ScheduleClass.subjects.idd_subject +">1;";
        subject_list.clear();
        Cursor cursor = db.rawQuery(searchQuery, null);
        while(cursor.moveToNext()) {
            subject_list.add(cursor.getString(0));
        }
        cursor.close();
    }



}
