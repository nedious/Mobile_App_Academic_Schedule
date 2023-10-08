package com.example.termv30.helper;

import android.text.TextUtils;

import androidx.room.TypeConverter;

public class CourseStatusToString {

    @TypeConverter
    public static String fromCourseStatusToString(CourseStatus courseStatus) {
        if(courseStatus == null) {
            return null;
        }
        return courseStatus.name();
    }

    @TypeConverter
    public static CourseStatus fromStringToCourseStatus(String courseStatus) {
        if(TextUtils.isEmpty(courseStatus)) {
            return CourseStatus.IN_PROGRESS;
        }
        return CourseStatus.valueOf(courseStatus);
    }
}
