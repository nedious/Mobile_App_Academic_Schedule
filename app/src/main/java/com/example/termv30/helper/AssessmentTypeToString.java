package com.example.termv30.helper;

import android.text.TextUtils;

import androidx.room.TypeConverter;

public class AssessmentTypeToString {
    @TypeConverter
    public static String fromAssessmentTypeToString(AssessmentType assessmentType) {
        if(assessmentType == null) {
            return null;
        }
        return assessmentType.name();
    }

    @TypeConverter
    public static AssessmentType fromStringToAssessmentType(String assessmentType) {
        if(TextUtils.isEmpty(assessmentType)) {
            return AssessmentType.ObjectiveAssessment;
        }
        return AssessmentType.valueOf(assessmentType);
    }
}
