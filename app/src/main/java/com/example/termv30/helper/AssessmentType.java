package com.example.termv30.helper;

public enum AssessmentType {

    PerformanceAssessment {
        @Override
        public String toString() {
            return "Performance Assessment";
        }
    },

    ObjectiveAssessment {
        @Override
        public String toString() {
            return "Objective Assessment";
        }
    }

}
