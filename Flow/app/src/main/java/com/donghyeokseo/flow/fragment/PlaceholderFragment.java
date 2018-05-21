package com.donghyeokseo.flow.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.delegate.OnParseMealProgress;
import com.donghyeokseo.flow.api.school.SchoolMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindAnim;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class PlaceholderFragment extends Fragment implements OnParseMealProgress {

    public static boolean anotherMonth;

    /**
     * btnStatus 0 : breakfast
     * btnStatus 1 : lunch
     * btnStatus 2 : dinner
     */
    public int btnStatus = 0;
    //버튼
    @BindView(R.id.breakfast_button)
    Button breakfastBtn;
    @BindView(R.id.lunch_button)
    Button lunchBtn;
    @BindView(R.id.dinner_button)
    Button dinnerBtn;

    //버튼 색상
    @BindColor(R.color.buttonAfterClick)
    int afterClick;
    @BindColor(R.color.buttonBeforeClick)
    int beforeClick;

    @BindAnim(R.anim.fade_in3)
    Animation fadeIn;

    //스트링
    @BindString(R.string.breakfast_title)
    String breakfastTitle;
    @BindString(R.string.lunch_title)
    String lunchTitle;
    @BindString(R.string.dinner_title)
    String dinnerTitle;
    @BindString(R.string.no_data_title)
    String noDataTitle;
    @BindString(R.string.no_data_content)
    String noDataContent;

    //내용
    @BindView(R.id.meal_breakfast_textView)
    TextView breakfastContentTv;
    @BindView(R.id.meal_lunch_textView)
    TextView lunchContentTv;
    @BindView(R.id.meal_dinner_textView)
    TextView dinnerContentTv;

    //날짜
    @BindView(R.id.meal_date_textView)
    TextView dateTv;

    //제목
    @BindView(R.id.breakfast_title_textView)
    TextView breakfastTitleTv;
    @BindView(R.id.lunch_title_textView)
    TextView lunchTitleTv;
    @BindView(R.id.dinner_title_textView)
    TextView dinnerTitleTv;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");

    Date date = new Date();

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PlaceholderFragment newInstance(int sectionNumber, int year, int month, int day,
                                                  List<SchoolMenu> mealInfo) {

        PlaceholderFragment fragment = new PlaceholderFragment();

        Bundle args = new Bundle();

        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        args.putParcelableArrayList("mealInfo", (ArrayList<? extends Parcelable>) mealInfo);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume() {

        super.onResume();

        switch (btnStatus) {

            case 0:
                setBreakfastBtnClicked(breakfastBtn);
                stopAnimation();
                breakfastContentTv.startAnimation(fadeIn);
                breakfastTitleTv.startAnimation(fadeIn);
                break;
            case 1:
                setLunchBtnClicked(lunchBtn);
                stopAnimation();
                lunchContentTv.startAnimation(fadeIn);
                lunchTitleTv.startAnimation(fadeIn);
                break;
            case 2:
                setDinnerBtnClicked(dinnerBtn);
                stopAnimation();
                dinnerContentTv.startAnimation(fadeIn);
                dinnerTitleTv.startAnimation(fadeIn);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_meal, container, false);

        rootView.setPadding(0, 0, 0, Util.deviceNavbarHeight);

        ButterKnife.bind(this, rootView);

        final List<SchoolMenu> mealInfo = getArguments().getParcelableArrayList("mealInfo");

        int year, month, day;

        year = getArguments().getInt("year");
        month = getArguments().getInt("month");
        day = getArguments().getInt("day");

        try {

            if (!mealInfo.isEmpty()) {

                breakfastContentTv.setText(
                        mealInfo.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).breakfast);
                lunchContentTv.setText(
                        mealInfo.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).lunch);
                dinnerContentTv.setText(
                        mealInfo.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).dinner);
                dateTv.setText(year + "년 " + month + "월 " +
                        getArguments().getInt(ARG_SECTION_NUMBER) + "일");
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), "서버에서 정보를 불러올 수 없습니다!", Toast.LENGTH_SHORT).show();
        }

        setBreakfastBtnClicked(breakfastBtn);
        if (!anotherMonth) {

            //오늘
            date.setTime(System.currentTimeMillis());

            int curHour = Integer.parseInt(simpleDateFormat.format(date));

            if (getArguments().getInt(ARG_SECTION_NUMBER) == day) {

                if (!anotherMonth && curHour < 7) {

                    //아침 먹기 전
                    setBreakfastBtnClicked(breakfastBtn);
                } else if (!anotherMonth && curHour < 13) {

                    //점심 먹기 전
                    setLunchBtnClicked(lunchBtn);
                } else if (!anotherMonth && curHour < 19) {

                    //저녁 먹기 전
                    setDinnerBtnClicked(dinnerBtn);
                } else {

                    //밥 다 먹었을때
                    breakfastContentTv.setVisibility(View.GONE);
                    lunchContentTv.setVisibility(View.GONE);
                    breakfastTitleTv.setVisibility(View.GONE);
                    lunchTitleTv.setVisibility(View.GONE);

                    dinnerContentTv.setText("오늘 밥 다 묵읏넹");
                    dinnerTitleTv.setText("밥 다먹음");
                }
            }
        }

        return rootView;
    }

    public void onParseMeal() {

        breakfastContentTv.setText("급식정보 받아오는중");
        lunchContentTv.setText("급식정보 받아오는중");
        dinnerContentTv.setText("급식정보 받아오는중");
    }

    private void stopAnimation() {

        breakfastContentTv.clearAnimation();
        lunchContentTv.clearAnimation();
        dinnerContentTv.clearAnimation();
        breakfastTitleTv.clearAnimation();
        lunchTitleTv.clearAnimation();
        dinnerTitleTv.clearAnimation();
    }

    @OnClick(R.id.breakfast_button)
    public void setBreakfastBtnClicked(View view) {

        stopAnimation();

        view.setBackgroundColor(afterClick);

        lunchBtn.setBackgroundColor(beforeClick);
        dinnerBtn.setBackgroundColor(beforeClick);

        btnStatus = 0;

        breakfastContentTv.setVisibility(View.VISIBLE);
        lunchContentTv.setVisibility(View.GONE);
        dinnerContentTv.setVisibility(View.GONE);
        breakfastTitleTv.setVisibility(View.VISIBLE);
        lunchTitleTv.setVisibility(View.GONE);
        dinnerTitleTv.setVisibility(View.GONE);

        breakfastContentTv.startAnimation(fadeIn);
        breakfastTitleTv.startAnimation(fadeIn);
    }

    @OnClick(R.id.lunch_button)
    public void setLunchBtnClicked(View view) {

        stopAnimation();

        view.setBackgroundColor(afterClick);

        breakfastBtn.setBackgroundColor(beforeClick);
        dinnerBtn.setBackgroundColor(beforeClick);

        btnStatus = 1;

        breakfastContentTv.setVisibility(View.GONE);
        lunchContentTv.setVisibility(View.VISIBLE);
        dinnerContentTv.setVisibility(View.GONE);
        breakfastTitleTv.setVisibility(View.GONE);
        lunchTitleTv.setVisibility(View.VISIBLE);
        dinnerTitleTv.setVisibility(View.GONE);

        lunchContentTv.startAnimation(fadeIn);
        lunchTitleTv.startAnimation(fadeIn);
    }

    @OnClick(R.id.dinner_button)
    public void setDinnerBtnClicked(View view) {

        stopAnimation();

        view.setBackgroundColor(afterClick);

        breakfastBtn.setBackgroundColor(beforeClick);
        lunchBtn.setBackgroundColor(beforeClick);

        btnStatus = 2;

        breakfastContentTv.setVisibility(View.GONE);
        lunchContentTv.setVisibility(View.GONE);
        dinnerContentTv.setVisibility(View.VISIBLE);
        breakfastTitleTv.setVisibility(View.GONE);
        lunchTitleTv.setVisibility(View.GONE);
        dinnerTitleTv.setVisibility(View.VISIBLE);

        dinnerContentTv.startAnimation(fadeIn);
        dinnerTitleTv.startAnimation(fadeIn);
    }
}