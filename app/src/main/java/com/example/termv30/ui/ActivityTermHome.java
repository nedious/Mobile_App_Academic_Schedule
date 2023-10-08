package com.example.termv30.ui;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.termv30.database.ScheduleRepository;
//import com.example.termv30.entities.CourseEntity;


public class ActivityTermHome extends AppCompatActivity {

//    private ScheduleRepository scheduleRepository;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        CourseEditAssessmentListActivity.termID = -1;
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_term_list);
//
//        scheduleRepository = new ScheduleRepository(getApplication());
//        scheduleRepository.getAllTerms();
//        scheduleRepository.getAllCourses();
//        scheduleRepository.getAllAssessments();
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerview_term);
//
//        final TermAdapter adapter = new TermAdapter(this);
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setTerms(scheduleRepository.getAllTerms());
//        adapter.setCourses(scheduleRepository.getAllCourses());
//
//
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                for (CourseEntity course: scheduleRepository.getAllCourses()){
//                    if (course.getTermID() == adapter.getTermAt(viewHolder.getAdapterPosition()).getTermID()){
//                        Toast.makeText(getApplicationContext(), "Cannot delete term with assigned courses.  Please remove all courses.", Toast.LENGTH_LONG).show();
//                        adapter.notifyDataSetChanged();
//                        return;
//                    }
//                }
//                scheduleRepository.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
//                adapter.mTerms.remove((viewHolder.getAdapterPosition()));
//                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.term_list_layout), "Term deleted", Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//        }).attachToRecyclerView(recyclerView);
//
//        if (getIntent().getBooleanExtra("termSaved", false))
//            Toast.makeText(this,"Term Saved",Toast.LENGTH_LONG).show();
//    }
//
//    public void goToTermEditCourseList(View view) {
//        Intent intent = new Intent(ActivityTermList.this, TermEditCourseListActivity.class);
//        startActivity(intent);
//    }

}