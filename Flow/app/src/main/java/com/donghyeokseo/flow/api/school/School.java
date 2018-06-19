package com.donghyeokseo.flow.api.school;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * School API
 * 전국 교육청 소속 교육기관의 학사일정, 메뉴를 간단히 불러올 수 있습니다.
 *
 * @author HyunJun Kim
 * @version 3.0
 */
public class School {
    private static final String MONTHLY_MENU_URL = "sts_sci_md00_001.do";
    private static final String SCHEDULE_URL = "sts_sci_sf01_001.do";
    private School.Type schoolType;
    private School.Region schoolRegion;
    private String schoolCode;

    public School(School.Type schoolType, School.Region schoolRegion, String schoolCode) {
        this.schoolType = schoolType;
        this.schoolRegion = schoolRegion;
        this.schoolCode = schoolCode;
    }

    public List<SchoolMenu> getMonthlyMenu(int year, int month) throws SchoolException {
        StringBuilder targetUrl = new StringBuilder("https://" + this.schoolRegion.url + "/" + "sts_sci_md00_001.do");
        targetUrl.append("?");
        targetUrl.append("schulCode=").append(this.schoolCode).append("&");
        targetUrl.append("schulCrseScCode=").append(this.schoolType.id).append("&");
        targetUrl.append("schulKndScCode=0").append(this.schoolType.id).append("&");
        targetUrl.append("schYm=").append(year).append(String.format("%02d", month)).append("&");

        try {
            String content = this.getContentFromUrl(new URL(targetUrl.toString()), "<tbody>", "</tbody>");
            return SchoolMenuParser.parse(content);
        } catch (MalformedURLException var5) {
            throw new SchoolException("교육청 접속 주소가 올바르지 않습니다.");
        }
    }

    public List<SchoolSchedule> getMonthlySchedule(int year, int month) throws SchoolException {
        StringBuilder targetUrl = new StringBuilder("https://" + this.schoolRegion.url + "/" + "sts_sci_sf01_001.do");
        targetUrl.append("?");
        targetUrl.append("schulCode=").append(this.schoolCode).append("&");
        targetUrl.append("schulCrseScCode=").append(this.schoolType.id).append("&");
        targetUrl.append("schulKndScCode=0").append(this.schoolType.id).append("&");
        targetUrl.append("ay=").append(year).append("&");
        targetUrl.append("mm=").append(String.format("%02d", month)).append("&");

        try {
            String content = this.getContentFromUrl(new URL(targetUrl.toString()), "<tbody>", "</tbody>");
            return SchoolScheduleParser.parse(content);
        } catch (MalformedURLException var5) {
            throw new SchoolException("교육청 접속 주소가 올바르지 않습니다.");
        }
    }

    private String getContentFromUrl(URL url, String readAfter, String readBefore) throws SchoolException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            boolean reading = false;

            String inputLine;
            while((inputLine = reader.readLine()) != null) {
                if(reading) {
                    if(inputLine.contains(readBefore)) {
                        break;
                    }

                    buffer.append(inputLine);
                } else if(inputLine.contains(readAfter)) {
                    reading = true;
                }
            }

            reader.close();
            return buffer.toString();
        } catch (IOException var8) {
            throw new SchoolException("교육청 서버에 접속하지 못하였습니다.");
        }
    }

    public static enum Region {
        SEOUL("stu.sen.go.kr"),
        INCHEON("stu.ice.go.kr"),
        BUSAN("stu.pen.go.kr"),
        GWANGJU("stu.gen.go.kr"),
        DAEJEON("stu.dje.go.kr"),
        DAEGU("stu.dge.go.kr"),
        SEJONG("stu.sje.go.kr"),
        ULSAN("stu.use.go.kr"),
        GYEONGGI("stu.goe.go.kr"),
        KANGWON("stu.kwe.go.kr"),
        CHUNGBUK("stu.cbe.go.kr"),
        CHUNGNAM("stu.cne.go.kr"),
        GYEONGBUK("stu.gbe.go.kr"),
        GYEONGNAM("stu.gne.go.kr"),
        JEONBUK("stu.jbe.go.kr"),
        JEONNAM("stu.jne.go.kr"),
        JEJU("stu.jje.go.kr");

        private String url;

        private Region(String url) {
            this.url = url;
        }
    }

    public static enum Type {
        KINDERGARTEN("1"),
        ELEMENTARY("2"),
        MIDDLE("3"),
        HIGH("4");

        private String id;

        private Type(String id) {
            this.id = id;
        }
    }
}