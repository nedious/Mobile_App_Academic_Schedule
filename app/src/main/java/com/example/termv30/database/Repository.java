package com.example.termv30.database;

import android.app.Application;

import com.example.termv30.DAO.AssessmentDAO;
import com.example.termv30.DAO.CourseDAO;
import com.example.termv30.DAO.TermDAO;
import com.example.termv30.entities.AssessmentEntity;
import com.example.termv30.entities.CourseEntity;
import com.example.termv30.entities.TermEntity;

import java.util.List;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;

    private List<TermEntity> mAllTerms;
    private List<CourseEntity> mAllCourses;
    private List<AssessmentEntity> mAllAssessments;

    private int termID;
    private int courseID;
    private int assessmentID;

    public Repository(Application application) {
        ScheduleDatabase db = ScheduleDatabase.getDatabase(application);
        mTermDAO = db.termDao();
        mCourseDAO = db.courseDao();
        mAssessmentDAO = db.assessmentDao();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<TermEntity> getAllTerms() {
        ScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

        public List<CourseEntity> getAllCourses() {
            ScheduleDatabase.databaseWriteExecutor.execute(() -> {
                mAllCourses = mCourseDAO.getAllCourses();
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllCourses;
        }

    public List<AssessmentEntity> getAllAssessments() {
        ScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert (TermEntity termEntity) {
        ScheduleDatabase.databaseWriteExecutor.execute(()->{
            mTermDAO.insert(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert (CourseEntity courseEntity) {
        ScheduleDatabase.databaseWriteExecutor.execute(()->{
            mCourseDAO.insert(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert (AssessmentEntity assessmentEntity) {
        ScheduleDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.insert(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (TermEntity termEntity) {
        ScheduleDatabase.databaseWriteExecutor.execute(()->{
            mTermDAO.delete(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete (CourseEntity courseEntity) {
        ScheduleDatabase.databaseWriteExecutor.execute(()->{
            mCourseDAO.delete(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete (AssessmentEntity assessmentEntity) {
        ScheduleDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.delete(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
