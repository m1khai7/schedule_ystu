package com.example.misha.myapplication.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.misha.myapplication.activity.ActivityEditData;
import com.example.misha.myapplication.R;
import com.example.misha.myapplication.adapter.EditScheduleListAdapters.ListTypelessonAdapter;
import com.example.misha.myapplication.SimpleItemClickListener;
import com.example.misha.myapplication.database.entity.Typelesson;

import java.util.ArrayList;


//Todo прочитать про наследование инкапсуляцию интерфейсы абстрактные классы и generic.
public class TypelessonList extends DialogFragment {

    public static final int TYPELESSON_CODE = 3433;

    public static final String TYPELESSONS = "TYPELESSONS";
    public static final String TYPELESSON_LIST = "TYPELESSON_LIST";
    public static final String POSITION = "POSITION";

    private int clickedPosition;
    private ListTypelessonAdapter listTypelessonAdapter;
    private ArrayList<Typelesson> listTypelesson;
    private RecyclerView rvTypelesson;

    public static TypelessonList newInstance(int position, ArrayList<Typelesson> typelessons) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(TYPELESSONS, typelessons);
        args.putInt(POSITION, position);
        TypelessonList fragment = new TypelessonList();
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        clickedPosition = getArguments().getInt(POSITION);
        listTypelesson = getArguments().getParcelableArrayList(TYPELESSONS);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View view = layoutInflater.inflate(R.layout.rv_list, null);
        View layoutTitleDialog = layoutInflater.inflate(R.layout.title_dialog, null);
        TextView title_dialog = layoutTitleDialog.findViewById(R.id.textViewDialog);
        title_dialog.setText("Тип занятия");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
        builder.setView(view);
        builder.setCustomTitle(layoutTitleDialog);
        rvTypelesson =  view.findViewById(R.id.rv);
        listTypelessonAdapter =  new ListTypelessonAdapter(listTypelesson, new SimpleItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent();
                intent.putExtra(POSITION, clickedPosition);
                intent.putExtra(TYPELESSON_LIST, listTypelesson.get(position));
                getParentFragment().onActivityResult(TYPELESSON_CODE, Activity.RESULT_OK, intent);
                dismiss();
            }
        });
        rvTypelesson.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        rvTypelesson.setAdapter(listTypelessonAdapter);

        Button button_add = view.findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityEditData.class);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
        });
        Button button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

}
