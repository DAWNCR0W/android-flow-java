package com.donghyeokseo.flow.network;

import android.os.AsyncTask;

import com.donghyeokseo.flow.delegate.OnParseMealProgress;
import com.donghyeokseo.flow.network.interfaces.MealDelegate;
import com.donghyeokseo.flow.school.School;
import com.donghyeokseo.flow.school.SchoolException;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.List;

/**
 * Created by dawncrow on 2018. 3. 13..
 */

public final class GetMealInfo extends AsyncTask<Object, Void, List<SchoolMenu>> {
    private final School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");
    public MealDelegate mealDelegate = null;
    public OnParseMealProgress onParseMealProgress = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onParseMealProgress.onParseMeal();
    }

    @Override
    protected List<SchoolMenu> doInBackground(Object[] objects) {
//        --- parameter explanation ---
//        objects[0] = year
//        objects[1] = month
//        objects[2] = day
//        objects[3] = true = return menus; false = return schedule
        List<SchoolMenu> menu = null;
        try {
            menu = api.getMonthlyMenu((int) objects[0], (int) objects[1]);
        } catch (SchoolException e) {
            e.printStackTrace();
        }
        return menu;
    }

    @Override
    protected void onPostExecute(List<SchoolMenu> o) {
        mealDelegate.processFinish(o);
    }
}
