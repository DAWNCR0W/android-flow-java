package com.donghyeokseo.flow.api.school;

import java.util.ArrayList;
import java.util.List;

/**
 * School API
 * 전국 교육청 소속 교육기관의 학사일정, 메뉴를 간단히 불러올 수 있습니다.
 *
 * @author HyunJun Kim
 * @version 3.0
 */
public class SchoolScheduleParser {
    public SchoolScheduleParser() {
    }

    public static List<SchoolSchedule> parse(String rawData) throws SchoolException {
        if(rawData.length() < 1) {
            throw new SchoolException("불러온 데이터가 올바르지 않습니다.");
        } else {
            List<SchoolSchedule> monthlySchedule = new ArrayList();
            rawData = rawData.replaceAll("\\s+", "");
            String[] chunk = rawData.split("textL\">");

            try {
                for(int i = 1; i < chunk.length; ++i) {
                    String trimmed = before(chunk[i], "</div>");
                    String date = before(after(trimmed, ">"), "</em>");
                    if(date.length() >= 1) {
                        if(trimmed.indexOf("<strong>") > 0) {
                            String name = before(after(trimmed, "<strong>"), "</strong>");
                            monthlySchedule.add(new SchoolSchedule(name));
                        } else {
                            monthlySchedule.add(new SchoolSchedule());
                        }
                    }
                }

                return monthlySchedule;
            } catch (Exception var7) {
                throw new SchoolException("학사일정 정보 파싱에 실패했습니다. 최신 버전으로 업데이트 해 주세요.");
            }
        }
    }

    private static String before(String string, String delimiter) {
        int index = string.indexOf(delimiter);
        return string.substring(0, index);
    }

    private static String after(String string, String delimiter) {
        int index = string.indexOf(delimiter);
        return string.substring(index + delimiter.length(), string.length());
    }
}