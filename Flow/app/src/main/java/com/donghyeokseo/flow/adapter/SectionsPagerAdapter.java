package com.donghyeokseo.flow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.donghyeokseo.flow.fragment.PlaceholderFragment;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.Calendar;
import java.util.List;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private int year;
    private int month;
    private int day;
    public List<SchoolMenu> mealInfo;

    public SectionsPagerAdapter(FragmentManager fm, int year, int month, int day, List<SchoolMenu> mealInfo) {
        super(fm);
        this.year = year;
        this.month = month;
        this.day  = day;
        this.mealInfo = mealInfo;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position + 1, year, month, day, mealInfo);
    }

    @Override
    public int getCount() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}