package com.donghyeokseo.flow.api.school;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * School API
 * 전국 교육청 소속 교육기관의 학사일정, 메뉴를 간단히 불러올 수 있습니다.
 *
 * @author HyunJun Kim
 * @version 3.0
 */
public class SchoolMenu implements Parcelable {
    private SchoolMenu(Parcel in) {
        breakfast = in.readString();
        lunch = in.readString();
        dinner = in.readString();
    }

    public static final Creator<SchoolMenu> CREATOR = new Creator<SchoolMenu>() {
        @Override
        public SchoolMenu createFromParcel(Parcel in) {
            return new SchoolMenu(in);
        }

        @Override
        public SchoolMenu[] newArray(int size) {
            return new SchoolMenu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(breakfast);
        dest.writeString(lunch);
        dest.writeString(dinner);
    }

    /**
     * 조식
     */
    public String breakfast;

    /**
     * 중식
     */
    public String lunch;

    /**
     * 석식
     */
    public String dinner;

    public SchoolMenu() {
        breakfast = lunch = dinner = "급식이 없습니다";
    }

    @Override
    public String toString() {
        return "[아침]\n" + breakfast + "\n" + "[점심]\n" + lunch + "\n" + "[저녁]\n" + dinner;
    }
}