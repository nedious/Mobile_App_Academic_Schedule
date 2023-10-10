package com.example.termv30.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.termv30.DAO.AssessmentDAO;
import com.example.termv30.DAO.CourseDAO;
import com.example.termv30.DAO.TermDAO;
import com.example.termv30.entities.AssessmentEntity;
import com.example.termv30.entities.CourseEntity;
import com.example.termv30.entities.TermEntity;
import com.example.termv30.helper.AssessmentTypeToString;
import com.example.termv30.helper.CourseStatusToString;
import com.example.termv30.helper.DateToString;
import com.example.termv30.helper.DummyData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 2)
@TypeConverters({DateToString.class, CourseStatusToString.class, AssessmentTypeToString.class})
public abstract class ScheduleDatabase extends RoomDatabase {
    public abstract AssessmentDAO assessmentDao();
    public abstract CourseDAO courseDao();
    public abstract TermDAO termDao();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile ScheduleDatabase INSTANCE;

    static ScheduleDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ScheduleDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabase.class, "schedule_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            TermDAO mTermDAO = INSTANCE.termDao();
            CourseDAO mCourseDAO = INSTANCE.courseDao();
            AssessmentDAO mAssessmentDAO = INSTANCE.assessmentDao();

            databaseWriteExecutor.execute(() -> {

                mTermDAO.deleteAllTerms();
                mCourseDAO.deleteAllCourses();
                mAssessmentDAO.deleteAllAssessments();

                mTermDAO.insertAll(DummyData.getDummyTerms());
                mCourseDAO.insertAll(DummyData.getDummyCourses());
                mAssessmentDAO.insertAll(DummyData.getDummyAssessments());

            });
        }
    };
}

