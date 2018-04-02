package com.donghyeokseo.flow.school;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * School API
 * 전국 교육청 소속 교육기관의 학사일정, 메뉴를 간단히 불러올 수 있습니다.
 *
 * @author HyunJun Kim
 * @version 3.0
 */
public class SchoolMenuParser {
    public SchoolMenuParser() {
    }

    public static List<SchoolMenu> parse(String rawData) throws SchoolException {
        if (rawData.length() < 1) {
            throw new SchoolException("불러온 데이터가 올바르지 않습니다.");
        } else {
            List<SchoolMenu> monthlyMenu = new ArrayList();
            rawData = rawData.replaceAll("\\s+", "");
            StringBuilder buffer = new StringBuilder();
            boolean inDiv = false;

            try {
                for (int i = 0; i < rawData.length(); ++i) {
                    if (rawData.charAt(i) == 118) {
                        if (inDiv) {
                            buffer.delete(buffer.length() - 4, buffer.length());
                            if (buffer.length() > 0) {
                                monthlyMenu.add(parseDay(buffer.toString()));
                            }

                            buffer.setLength(0);
                        } else {
                            ++i;
                        }

                        inDiv = !inDiv;
                    } else if (inDiv) {
                        buffer.append(rawData.charAt(i));
                    }
                }
                return monthlyMenu;
            } catch (Exception var5) {
                throw new SchoolException("급식 정보 파싱에 실패했습니다. 최신 버전으로 업데이트 해 주세요.");
            }
        }
    }

    private static SchoolMenu parseDay(String rawData) {
        SchoolMenu menu = new SchoolMenu();
        rawData = rawData.replace("(석식)", "");
        rawData = rawData.replace("(선)", "");
        String[] chunk = rawData.split("<br/>");
        int parsingMode = 0;

        for (int i = 1; i < chunk.length; ++i) {
            if (chunk[i].trim().length() >= 1) {
                switch (chunk[i]) {
                    case "[조식]":
                        parsingMode = 0;
                        menu.breakfast = "";
                        break;
                    case "[중식]":
                        parsingMode = 1;
                        menu.lunch = "";
                        break;
                    case "[석식]":
                        parsingMode = 2;
                        menu.dinner = "";
                        break;
                    default:
                        final String mealText = chunk[i];
                        switch (parsingMode) {
                            case 0:
                                if (menu.breakfast.length() > 1) {
                                    menu.breakfast = menu.breakfast + "\n" + mealText;
                                } else {
                                    menu.breakfast = menu.breakfast + mealText;
                                }
                                break;
                            case 1:
                                if (menu.lunch.length() > 1) {
                                    menu.lunch = menu.lunch + "\n" + mealText;
                                } else {
                                    menu.lunch = menu.lunch + mealText;
                                }
                                break;
                            case 2:
                                if (menu.dinner.length() > 1) {
                                    menu.dinner = menu.dinner + "\n" + mealText;
                                } else {
                                    menu.dinner = menu.dinner + mealText;
                                }
                        }
                        break;
                }
            }
        }

        return menu;
    }
}