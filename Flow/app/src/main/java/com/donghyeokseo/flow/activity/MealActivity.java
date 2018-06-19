package com.donghyeokseo.flow.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.adapter.MealSectionsPagerAdapter;
import com.donghyeokseo.flow.api.school.SchoolMenu;
import com.donghyeokseo.flow.delegate.OnParseMeal;
import com.donghyeokseo.flow.delegate.ShowMeal;
import com.donghyeokseo.flow.fragment.PlaceholderFragment;
import com.donghyeokseo.flow.network.GetMealInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author dawncrow
 */
public final class MealActivity extends AppCompatActivity
        implements ShowMeal, DatePickerDialog.OnDateSetListener, OnParseMeal {

    private MealSectionsPagerAdapter mMealSectionsPagerAdapter;
    private List<SchoolMenu> mealInfo = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener dateSetListener = this;
    private ViewPager mViewPager;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setDateToToday();

        PlaceholderFragment.anotherMonth = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        setMealInfo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFragment();

        mViewPager.setCurrentItem(day - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_meal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_next_month) {

            showDatePicker();
            PlaceholderFragment.anotherMonth = true;
            return true;
        }

        if (id == R.id.action_previous_month) {

            PlaceholderFragment.anotherMonth = false;

            setDateToToday();

            setMealInfo();

            setFragment();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onParseMeal() {

        if (PlaceholderFragment.anotherMonth) {

            SchoolMenu schoolMenu = new SchoolMenu();

            schoolMenu.breakfast = "급식정보 받아오는중";
            schoolMenu.lunch = "급식정보 받아오는중";
            schoolMenu.dinner = "급식정보 받아오는중";

            ArrayList<SchoolMenu> schoolMenus = new ArrayList<>();

            for (int i = 0; i < 31; i++) {
                schoolMenus.add(schoolMenu);
            }

            mealInfo = schoolMenus;
            mMealSectionsPagerAdapter.mealInfo = mealInfo;
            mMealSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void processFinish(List<SchoolMenu> mealInfo) {

        this.mealInfo = mealInfo;
        mMealSectionsPagerAdapter.mealInfo = mealInfo;
        mMealSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        if (year == Calendar.getInstance().get(Calendar.YEAR) &&
                monthOfYear == Calendar.getInstance().get(Calendar.MONTH) &&
                dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            setDateToToday();
        } else {

            PlaceholderFragment.anotherMonth = true;

            this.year = year;
            this.month = monthOfYear + 1;
            this.day = dayOfMonth;
        }

        setMealInfo();

        setFragment();
    }

    private void setDateToToday() {

        //오늘 날짜로 설정
        PlaceholderFragment.anotherMonth = false;

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DATE);
    }

    private void showDatePicker() {

        //날짜 선택창 띄우기
        Context context = new ContextThemeWrapper(this,
                android.R.style.Theme_Holo_Light_Dialog);

        // API 24 이상일 경우 시스템 기본 테마 사용
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context = this;
        }

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(context, dateSetListener, year, month, day);

        datePickerDialog.show();
    }

    private void setMealInfo() {

        //급식 정보 파싱 받아오기
        int nowMonth = 0;

        Object[] asyncParameters = new Object[]{
                year,
                month + nowMonth,
                day
        };

        GetMealInfo getMealInfo = new GetMealInfo();
        getMealInfo.showMeal = this;
        getMealInfo.onParseMeal = this;
        getMealInfo.execute(asyncParameters);
    }

    private void setFragment() {

        //프래그먼트 설정하기
        mMealSectionsPagerAdapter =
                new MealSectionsPagerAdapter(getSupportFragmentManager(), year, month, day, mealInfo);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mMealSectionsPagerAdapter);
        mViewPager.setCurrentItem(day - 1);
    }
}
