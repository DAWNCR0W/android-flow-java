package com.donghyeokseo.flow.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.DatePicker;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.adapter.SectionsPagerAdapter;
import com.donghyeokseo.flow.fragment.PlaceholderFragment;
import com.donghyeokseo.flow.model.MealDate;
import com.donghyeokseo.flow.network.SchoolInfo;
import com.donghyeokseo.flow.interfaces.MealResponse;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealActivity extends AppCompatActivity implements MealResponse,
        DatePickerDialog.OnDateSetListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<SchoolMenu> mealInfo = new ArrayList<>();
    private MealDate mealDate = new MealDate();
    private DatePickerDialog.OnDateSetListener dateSetListener = this;

    private void setDateToToday() {
        //오늘 날짜로 설정
        PlaceholderFragment.anotherMonth = false;
        mealDate.setYear(Calendar.getInstance().get(Calendar.YEAR));
        mealDate.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
        mealDate.setDay(Calendar.getInstance().get(Calendar.DATE));
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
                new DatePickerDialog(context, dateSetListener, mealDate.getYear(),
                        mealDate.getMonth(), mealDate.getDay());
        datePickerDialog.show();
    }

    private void setMealInfo() {
        //급식 정보 파싱 받아오기
        int nowMonth = 0;
        Object[] asyncParameters = new Object[]{
                mealDate.getYear(),
                mealDate.getMonth() + nowMonth,
                mealDate.getDay()
        };
        SchoolInfo schoolInfo = new SchoolInfo();
        SchoolInfo.mealResponse = this;
        schoolInfo.execute(asyncParameters);
    }

    private void setFragment() {
        //프래그먼트 설정하기
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mealDate,
                mealInfo);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mealDate.getDay() - 1);
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
        mViewPager.setCurrentItem(mealDate.getDay() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
    public void processFinish(Object objects) {
        mealInfo = (List<SchoolMenu>) objects;
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
            mealDate.setYear(year);
            mealDate.setMonth(monthOfYear + 1);
            mealDate.setDay(dayOfMonth);
        }
        setMealInfo();
        setFragment();

        Log.e("asdf", String.valueOf(monthOfYear));
    }
}
