package com.example.termv30.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termv30.database.Repository;
import com.example.termv30.entities.CourseEntity;
import com.example.termv30.R;
import com.google.android.material.snackbar.Snackbar;

public class ActivityTermHome extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCourseDetail.termID = -1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        repository = new Repository(getApplication());
        repository.getAllTerms();
        repository.getAllCourses();
        repository.getAllAssessments();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_term);

        final AdapterTerm adapter = new AdapterTerm(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(repository.getAllTerms());
        adapter.setCourses(repository.getAllCourses());


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // deletes term if no courses are assigned to it
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                for (CourseEntity course: repository.getAllCourses()){
                    if (course.getTermID() == adapter.getTermAt(viewHolder.getAdapterPosition()).getTermID()){
                        Toast.makeText(getApplicationContext(), "Cannot delete term with assigned courses.  Please remove all courses.", Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
                repository.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
                adapter.mTerms.remove((viewHolder.getAdapterPosition()));
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar snackbar = Snackbar.make(findViewById(R.id.term_list_layout), "Term deleted", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }).attachToRecyclerView(recyclerView);

        if (getIntent().getBooleanExtra("termSaved", false))
            Toast.makeText(this,"Term Saved",Toast.LENGTH_LONG).show();
    }

    public void addNewTermButton(View view) {
        Intent intent = new Intent(ActivityTermHome.this, ActivityTermDetail.class);
        startActivity(intent);
    }

}