package com.donghyeokseo.flow.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.model.MealDate;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PlaceholderFragment extends Fragment {
    public static boolean anotherMonth;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
    Date date = new Date();
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PlaceholderFragment newInstance(int sectionNumber, MealDate mealDate,
                                                  List<SchoolMenu> mealInfo) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putSerializable("mealDate", mealDate);
        args.putParcelableArrayList("mealInfo", (ArrayList<? extends Parcelable>) mealInfo);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_meal, container, false);
        //내용
        final TextView morningTextView = rootView.findViewById(R.id.meal_moring_textview);
        final TextView lunchTextView = rootView.findViewById(R.id.meal_lunch_textview);
        final TextView dinnerTextView = rootView.findViewById(R.id.meal_dinner_textview);
        final TextView dateTextView = rootView.findViewById(R.id.meal_date);
        //제목
        final TextView morning = rootView.findViewById(R.id.morning);
        final TextView lunch = rootView.findViewById(R.id.lunch);
        final TextView dinner = rootView.findViewById(R.id.dinner);

        final MealDate mealDate = (MealDate) getArguments().getSerializable("mealDate");
        final List<SchoolMenu> mealInfo = getArguments().getParcelableArrayList("mealInfo");

        morningTextView.setText("급식정보 받아오는중");
        lunchTextView.setText("급식정보 받아오는중");
        dinnerTextView.setText("급식정보 받아오는중");

        if (!Objects.requireNonNull(mealInfo).isEmpty()) {
            morningTextView.setText(
                    mealInfo.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).breakfast);
            lunchTextView.setText(
                    mealInfo.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).lunch);
            dinnerTextView.setText(
                    mealInfo.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).dinner);
            dateTextView.setText(mealDate.getYear() + "년 " + mealDate.getMonth() + "월 " +
                    getArguments().getInt(ARG_SECTION_NUMBER) + "일");
        }
        if (anotherMonth) {
            //다른 날짜
            morningTextView.setVisibility(View.VISIBLE);
            lunchTextView.setVisibility(View.VISIBLE);
            dinnerTextView.setVisibility(View.VISIBLE);
            morning.setVisibility(View.VISIBLE);
            lunch.setVisibility(View.VISIBLE);
            dinner.setVisibility(View.VISIBLE);
        } else {
            //오늘
            date.setTime(System.currentTimeMillis());
            int curHour = Integer.parseInt(simpleDateFormat.format(date));
            if (getArguments().getInt(ARG_SECTION_NUMBER) == mealDate.getDay()) {
                if (!anotherMonth && curHour < 7) {
                    //아침 먹기 전
                    lunchTextView.setVisibility(View.GONE);
                    dinnerTextView.setVisibility(View.GONE);
                    lunch.setVisibility(View.GONE);
                    dinner.setVisibility(View.GONE);
                } else if (!anotherMonth && curHour < 13) {
                    //점심 먹기 전
                    morningTextView.setVisibility(View.GONE);
                    dinnerTextView.setVisibility(View.GONE);
                    morning.setVisibility(View.GONE);
                    dinner.setVisibility(View.GONE);
                } else if (!anotherMonth && curHour < 19) {
                    //저녁 먹기 전
                    morningTextView.setVisibility(View.GONE);
                    lunchTextView.setVisibility(View.GONE);
                    morning.setVisibility(View.GONE);
                    lunch.setVisibility(View.GONE);
                } else {
                    //밥 다 먹었을때
                    morningTextView.setVisibility(View.GONE);
                    lunchTextView.setVisibility(View.GONE);
                    morning.setVisibility(View.GONE);
                    lunch.setVisibility(View.GONE);
                    dinnerTextView.setText("오늘 밥 다 묵읏넹");
                    dinner.setText("밥 다먹음");
                }
            } else {
                morningTextView.setVisibility(View.VISIBLE);
                lunchTextView.setVisibility(View.VISIBLE);
                dinnerTextView.setVisibility(View.VISIBLE);
                morning.setVisibility(View.VISIBLE);
                lunch.setVisibility(View.VISIBLE);
                dinner.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }
}