package com.example.misha.myapplication.module.schedule.explore.page;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.ScheduleApp;
import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;
import com.example.misha.myapplication.entity.Audience;
import com.example.misha.myapplication.entity.Calls;
import com.example.misha.myapplication.entity.Educator;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.entity.Subject;
import com.example.misha.myapplication.entity.Typelesson;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragmentPagerAdapter extends RecyclerView.Adapter {

    private List<Lesson> lessonList = new ArrayList<>();

    public class ViewHolderLesson extends RecyclerView.ViewHolder {
        private final TextView number;
        private final TextView timeEdit;
        private final TextView subjectEdit;
        private final TextView audienceEdit;
        private final TextView educatorEdit;
        private final TextView typelessonEdit;
        private final TextInputLayout subjectHint;
        private final TextInputLayout audienceHint;
        private final TextInputLayout educatorHint;
        private final TextInputLayout typelessonHint;

        public ViewHolderLesson(View view) {
            super(view);
            number = view.findViewById(R.id.number);
            timeEdit = view.findViewById(R.id.time);
            subjectEdit = view.findViewById(R.id.subject);
            audienceEdit = view.findViewById(R.id.audience);
            educatorEdit = view.findViewById(R.id.educator);
            typelessonEdit = view.findViewById(R.id.typelesson);
            subjectHint = view.findViewById(R.id.subject_hint);
            audienceHint = view.findViewById(R.id.audience_hint);
            educatorHint = view.findViewById(R.id.educator_hint);
            typelessonHint = view.findViewById(R.id.typelesson_hint);
        }
    }


    public static class ViewHolderEmptyLesson extends RecyclerView.ViewHolder {

        private final TextView number;
        private final TextView timeEdit;

        public ViewHolderEmptyLesson(View view) {
            super(view);
            number = view.findViewById(R.id.number);
            timeEdit = view.findViewById(R.id.time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int code = 0;

        if (lessonList.get(position).getSubject().equals("0")) {
            code = 1;
        }
        if (lessonList.get(position).getAudience().equals("0")) {
            code = 1;
        }
        if (lessonList.get(position).getEducator().equals("0")) {
            code = 1;
        }
        if (lessonList.get(position).getTypeLesson().equals("0")) {
            code = 1;
        }
        return code;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
                return new ViewHolderLesson(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_lesson, parent, false);
                return new ViewHolderEmptyLesson(view);
        }
        return null;
    }


    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                Lesson lesson = lessonList.get(position);
                Calls calls = CallDao.getInstance().getItemByID(Long.parseLong(lesson.getTimeLesson()));
                ((ViewHolderLesson) holder).number.setText(lesson.getTimeLesson());
                ((ViewHolderLesson) holder).timeEdit.setText(calls.getName());

                Subject subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getSubject()));
                Audience audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getAudience()));
                Educator educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getEducator()));
                Typelesson typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getTypeLesson()));

                ((ViewHolderLesson) holder).subjectHint.setHint("Предмет");
                ((ViewHolderLesson) holder).subjectEdit.setText(subject.getName());
                ((ViewHolderLesson) holder).audienceHint.setHint("Аудитория");
                ((ViewHolderLesson) holder).audienceEdit.setText(audience.getName());
                ((ViewHolderLesson) holder).educatorHint.setHint("Преподаватель");
                ((ViewHolderLesson) holder).educatorEdit.setText(educator.getName());
                ((ViewHolderLesson) holder).typelessonHint.setHint("Тип занятия");
                ((ViewHolderLesson) holder).typelessonEdit.setText(typelesson.getName());

                break;
            case 1:
                lesson = lessonList.get(position);
                calls = CallDao.getInstance().getItemByID(Long.parseLong(lesson.getTimeLesson()));
                ((ViewHolderEmptyLesson) holder).number.setText(lesson.getTimeLesson());
                ((ViewHolderEmptyLesson) holder).timeEdit.setText(calls.getName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lessonList == null ? 0 : lessonList.size();
    }
}
