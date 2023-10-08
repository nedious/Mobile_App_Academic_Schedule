package com.example.termv30.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.termv30.helper.AssessmentType;

import java.time.LocalDate;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey
    private int assessmentID;

    private String assessmentTitle;
    private AssessmentType assessmentType;
    private LocalDate startDate;
    private LocalDate endDate;

    private int courseID;

    public AssessmentEntity(int assessmentID, String assessmentTitle, AssessmentType assessmentType, LocalDate startDate, LocalDate endDate, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
