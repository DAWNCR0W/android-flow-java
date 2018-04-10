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

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.adapter.SectionsPagerAdapter;
import com.donghyeokseo.flow.fragment.PlaceholderFragment;
import com.donghyeokseo.flow.network.interfaces.MealDelegate;
import com.donghyeokseo.flow.network.GetMealInfo;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealActivity extends AppCompatActivity implements MealDelegate,
        DatePickerDialog.OnDateSetListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<SchoolMenu> mealInfo = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener dateSetListener = this;
    private ViewPager mViewPager;
    private int year;
    private int month;
    private int day;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // API 24 이상일 경우 시스템 기본 테마 사용
            context = this;
        }
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(context, dateSetListener, year,
                        month, day);
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
        getMealInfo.mealDelegate = this;
        getMealInfo.execute(asyncParameters);
    }

    private void setFragment() {
        //프래그먼트 설정하기
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), year, month, day,
                mealInfo);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(day - 1);
    }

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
    public void processFinish(List<SchoolMenu> mealInfo) {
        this.mealInfo = mealInfo;
        mSectionsPagerAdapter.mealInfo = mealInfo;
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (year == Calendar.getInstance().get(Calendar.YEAR) &&
                monthOfYear == Calendar.getInstance().get(Calendar.MONTH) &&
                dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            setDateToToday();
        else {
            PlaceholderFragment.anotherMonth = true;
            this.year = year;
            this.month = monthOfYear + 1;
            this.day = dayOfMonth;
        }
        setMealInfo();
        setFragment();
    }
}
