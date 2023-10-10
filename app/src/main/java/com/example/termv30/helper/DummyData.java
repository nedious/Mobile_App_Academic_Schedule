package com.example.termv30.helper;

import com.example.termv30.entities.AssessmentEntity;
import com.example.termv30.entities.CourseEntity;
import com.example.termv30.entities.TermEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DummyData {

    private static final LocalDate DATE = LocalDate.of(2023,10,1);

        public static List<TermEntity> getDummyTerms() {
            List<TermEntity> terms = new ArrayList<>();
            terms.add(new TermEntity(0, "Fall 2023" , DATE, DATE.plusMonths(6)));
            terms.add(new TermEntity(1, "Spring 2024" , DATE.plusMonths(6), DATE.plusMonths(12)));
            terms.add(new TermEntity(2, "Fall 2024" , DATE.plusMonths(12), DATE.plusMonths(18)));
            terms.add(new TermEntity(3, "Spring 2025" , DATE.plusMonths(18), DATE.plusMonths(24)));

            return terms;
        }

        public static List<CourseEntity> getDummyCourses() {
            List<CourseEntity> courses = new ArrayList<>();
            courses.add(new CourseEntity(0, "American History", DATE, DATE.plusMonths(2),  CourseStatus.IN_PROGRESS, "Dummy Notes for American History", "Mr. US History","123-456-7890", "UShistory@email.com", 0));
            courses.add(new CourseEntity(1, "Art History", DATE.plusMonths(2), DATE.plusMonths(2),  CourseStatus.IN_PROGRESS, "Dummy Notes for Art History", "Mr. Art History","123-456-7890", "artHistory@email.com", 0));
            courses.add(new CourseEntity(2, "Statistics", DATE.plusMonths(3), DATE.plusMonths(3),  CourseStatus.IN_PROGRESS, "Dummy Notes for Statistics", "Mr. Stats","123-456-7890", "stats@email.com", 1));
            courses.add(new CourseEntity(3, "Psychology", DATE, DATE.plusMonths(2),  CourseStatus.PLAN_TO_TAKE, "Dummy Notes for Psychology", "Mr. Psychology","123-456-7890", "psych@email.com", 1));
            courses.add(new CourseEntity(4, "Foreign Language", DATE.plusMonths(2), DATE.plusMonths(3),  CourseStatus.COMPLETED, "Dummy Notes for Foreign Language", "Ms. Language","123-456-7890", "language@email.com", 1));
            courses.add(new CourseEntity(5, "Creative Writing", DATE, DATE.plusMonths(4),  CourseStatus.DROPPED, "Dummy Notes for Creative Writing", "Ms. Writing","123-456-7890", "writing@email.com", 2));

            return courses;
        }

        public static List<AssessmentEntity> getDummyAssessments() {
            List<AssessmentEntity> assessments = new ArrayList<>();
            assessments.add(new AssessmentEntity(0, "Dummy Assessment 1", AssessmentType.PerformanceAssessment, DATE, DATE.plusMonths(2),0));
            assessments.add(new AssessmentEntity(1, "Dummy Assessment 2", AssessmentType.PerformanceAssessment, DATE, DATE.plusMonths(2),1));
            assessments.add(new AssessmentEntity(2, "Dummy Assessment 3", AssessmentType.ObjectiveAssessment, DATE, DATE.plusMonths(2),2));
            assessments.add(new AssessmentEntity(3, "Dummy Assessment 4", AssessmentType.ObjectiveAssessment, DATE, DATE.plusMonths(2),2));
            assessments.add(new AssessmentEntity(4, "Dummy Assessment 5", AssessmentType.PerformanceAssessment, DATE, DATE.plusMonths(2),3));
            assessments.add(new AssessmentEntity(5, "Dummy Assessment 6", AssessmentType.PerformanceAssessment, DATE, DATE.plusMonths(2),4));

            return assessments;
        }
}
