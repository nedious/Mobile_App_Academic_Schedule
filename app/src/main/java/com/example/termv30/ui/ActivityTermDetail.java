package com.example.termv30.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termv30.R;
import com.example.termv30.database.Repository;
import com.example.termv30.entities.CourseEntity;
import com.example.termv30.entities.TermEntity;
import com.example.termv30.helper.DateHelper;
import com.example.termv30.helper.DateSelection;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivityTermDetail extends AppCompatActivity {
    private Repository repository;

    int id;
    String title;
    LocalDate startDate;
    LocalDate endDate;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;

    TermEntity currentTerm;

    public static int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        Button addCourseBtn = (Button) findViewById(R.id.add_course_button);

//------Fill Term Edit Fields if editing a Term-----------------//
        id=getIntent().getIntExtra("termID", -1);
        if(id == -1)
            id = ActivityCourseDetail.termID;
        repository = new Repository(getApplication());
        List<TermEntity> allTerms = repository.getAllTerms();

        for(TermEntity term:allTerms){
            if(term.getTermID() == id)
                currentTerm = term;
        }

        editTitle = findViewById(R.id.term_title_editText);
        editStartDate = findViewById(R.id.term_start_date_editText);
        editEndDate = findViewById(R.id.term_end_date_editText);

        if(currentTerm != null){
            title = currentTerm.getTermTitle();
            startDate = currentTerm.getStartDate();
            endDate = currentTerm.getEndDate();
        }
        else {
            addCourseBtn.setVisibility(View.GONE);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        }
        if(id != -1){

            editTitle.setText(title);
            editStartDate.setText(startDate.format(DateHelper.dtf));
            editEndDate.setText(endDate.format(DateHelper.dtf));
        }

//------Set and show associated Courses-----------------//
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recyclerview_course);
        final AdapterCourse adapter = new AdapterCourse(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseEntity> filteredCourseEntityList = new ArrayList<>();
        for(CourseEntity course: repository.getAllCourses()){
            if (course.getTermID() == id)
                filteredCourseEntityList.add(course);
        }
        numCourses = filteredCourseEntityList.size();
        adapter.setCourses(filteredCourseEntityList);
        adapter.setAssessments(repository.getAllAssessments());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                repository.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                adapter.mCourses.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar snackbar = Snackbar.make(findViewById(R.id.term_details), "Course deleted", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }).attachToRecyclerView(recyclerView);

        if (getIntent().getBooleanExtra("courseSaved", false))
            Toast.makeText(this,"Course Saved",Toast.LENGTH_LONG).show();
    }


    public void addNewCourseButton(View view) {
        Intent intent = new Intent(ActivityTermDetail.this, ActivityCourseDetail.class);
        intent.putExtra("termID", id);
        ActivityAssessmentDetail.courseIdAssessmentDetail = -1;
        startActivity(intent);
    }

    public void saveTermButton(View view) {
        if (editTitle.getText().toString().trim().isEmpty() || editStartDate.getText().toString().trim().isEmpty() || editEndDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled prior to saving Term", Toast.LENGTH_LONG).show();
            return;
        }

        TermEntity term;

        if (id != -1)
            term = new TermEntity(id, editTitle.getText().toString(), DateHelper.parseDate(editStartDate.getText().toString()), DateHelper.parseDate(editEndDate.getText().toString()));
        else {
            List<TermEntity> allTerms = repository.getAllTerms();
            id = allTerms.get(allTerms.size() - 1).getTermID();
            term = new TermEntity(++id, editTitle.getText().toString(), DateHelper.parseDate(editStartDate.getText().toString()), DateHelper.parseDate(editEndDate.getText().toString()));
        }
        repository.insert(term);

        Intent intent = new Intent(ActivityTermDetail.this, ActivityTermHome.class);
        intent.putExtra("termSaved",true);
        startActivity(intent);
    }

    public void showDateClickerDialog(View view) {
        int viewID = view.getId();
        TextView datePickerView = findViewById(viewID);
        DialogFragment newFragment = new DateSelection(datePickerView);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}