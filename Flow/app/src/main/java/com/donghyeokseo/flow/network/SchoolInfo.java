package com.donghyeokseo.flow.network;

import android.os.AsyncTask;

import com.donghyeokseo.flow.interfaces.MealResponse;
import com.donghyeokseo.flow.school.School;
import com.donghyeokseo.flow.school.SchoolException;
import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.List;

/**
 * Created by dawncrow on 2018. 3. 13..
 */

public class SchoolInfo extends AsyncTask {
    private static final School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");
    public static MealResponse mealResponse = null;

    @Override
    protected Object doInBackground(Object[] objects) {
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
    protected void onPostExecute(Object o) {
        mealResponse.processFinish(o);
    }
}
