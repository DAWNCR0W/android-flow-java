package com.donghyeokseo.flow.network;

import android.os.AsyncTask;

import com.donghyeokseo.flow.api.school.School;
import com.donghyeokseo.flow.api.school.SchoolException;
import com.donghyeokseo.flow.api.school.SchoolMenu;
import com.donghyeokseo.flow.delegate.OnParseMeal;
import com.donghyeokseo.flow.delegate.ShowMeal;

import java.util.List;

/**
 * @author dawncrow
 * @date 2018. 3. 13.
 */

public final class GetMealInfo extends AsyncTask<Object, Void, List<SchoolMenu>> {

    private final School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");

    public ShowMeal showMeal = null;
    public OnParseMeal onParseMeal = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onParseMeal.onParseMeal();
    }

    @Override
    protected List<SchoolMenu> doInBackground(Object[] objects) {
        /**
         *         --- parameter explanation ---
         * @Params objects[0] = year
         * @Params objects[1] = month
         * @Params objects[2] = day
         * @Params objects[3] = true = return menus; false = return schedule
         */

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

        showMeal.processFinish(o);
    }
}
