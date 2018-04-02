package com.donghyeokseo.flow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.donghyeokseo.flow.fragment.PlaceholderFragment;
import com.donghyeokseo.flow.model.MealDate;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.Calendar;
import java.util.List;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private MealDate mealDate;
    public List<SchoolMenu> mealInfo;

    public SectionsPagerAdapter(FragmentManager fm, MealDate mealDate, List<SchoolMenu> mealInfo) {
        super(fm);
        this.mealDate = mealDate;
        this.mealInfo = mealInfo;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return PlaceholderFragment.newInstance(position + 1, mealDate, mealInfo);
    }

    @Override
    public int getCount() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mealDate.getYear(), mealDate.getMonth() - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}