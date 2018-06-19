package com.donghyeokseo.flow.delegate;

import com.donghyeokseo.flow.api.school.SchoolMenu;

import java.util.List;

/**
 * @author dawncrow
 * @date 2018. 3. 26.
 */

public interface ShowMeal {
    void processFinish(List<SchoolMenu> objects);
}
