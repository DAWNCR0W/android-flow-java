package com.donghyeokseo.flow.network.interfaces;

import com.donghyeokseo.flow.school.SchoolMenu;

import java.util.List;

/**
 * Created by dawncrow on 2018. 3. 26..
 */

public interface MealDelegate {
    void processFinish(List<SchoolMenu> objects);
}
