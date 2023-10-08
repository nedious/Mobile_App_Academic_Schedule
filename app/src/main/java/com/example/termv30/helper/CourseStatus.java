package com.example.termv30.helper;

public enum CourseStatus {
    IN_PROGRESS {
        @Override
        public String toString() {
            return "In Progress";
        }
    },

    COMPLETED {
        @Override
        public String toString() {
            return "Completed";
        }
    },

    DROPPED {
        @Override
        public String toString() {
            return "Dropped";
        }
    },

    PLAN_TO_TAKE {
        @Override
        public String toString() {
            return "Plan to take";
        }
    };

    public static CourseStatus fromString(String string){
        for (CourseStatus status: CourseStatus.values()){
            if (status.toString().equalsIgnoreCase(string))
                return status;
        }
        return null;
    }

}
