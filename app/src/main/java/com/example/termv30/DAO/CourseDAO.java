package com.example.termv30.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.termv30.entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> course);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM course_Table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_Table ORDER BY courseID ASC")
    List<CourseEntity> getAllCourses();
}
