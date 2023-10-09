package com.example.termv30.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.termv30.R;
import com.example.termv30.database.Repository;
import com.example.termv30.entities.AssessmentEntity;
import com.example.termv30.helper.AssessmentType;
import com.example.termv30.helper.DateHelper;
import com.example.termv30.helper.DateSelection;
import com.example.termv30.helper.MyReceiver;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityAssessmentDetail extends AppCompatActivity {
    private Repository repository;

    public static int courseIdAssessmentDetail = -1;
    public static int termIdAssessmentDetail = -1;

    int assessmentID;
    EditText assessmentTitle;
    RadioButton objectiveAssessment;
    RadioButton performanceAssessment;
    EditText endDate;
    EditText startDate;
    int courseID;

    AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        //Set Variables
//        System.out.println(courseIdAssessmentDetail);

        courseIdAssessmentDetail = getIntent().getIntExtra("courseID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        termIdAssessmentDetail = getIntent().getIntExtra("termID", -1);

//        System.out.println(courseIdAssessmentDetail);

        // fill in assessment details if not making new assessment
        repository = new Repository((getApplication()));
        List<AssessmentEntity> allAssessments = repository.getAllAssessments();

        for(AssessmentEntity assessment:allAssessments){
            if (assessment.getAssessmentID() == assessmentID)
                currentAssessment = assessment;
        }

        assessmentTitle = findViewById(R.id.assessment_title_editText);
        objectiveAssessment = findViewById(R.id.objective_assessment);
        performanceAssessment = findViewById(R.id.performance_assessment);
        endDate = findViewById(R.id.assessment_end_date);
        startDate = findViewById(R.id.assessment_start_date);

        if (currentAssessment != null){
            assessmentTitle.setText(currentAssessment.getAssessmentTitle());
            switch (currentAssessment.getAssessmentType()){
                case ObjectiveAssessment:
                    objectiveAssessment.setChecked(true);
                    break;
                case PerformanceAssessment:
                    performanceAssessment.setChecked(true);
                    break;
            }
            endDate.setText(currentAssessment.getEndDate().format(DateHelper.dtf));
            startDate.setText(currentAssessment.getStartDate().format(DateHelper.dtf));
        }
        else
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // menu bar
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == R.id.assessment_notification_start) {
            String dateFromScreen = startDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ActivityAssessmentDetail.this, MyReceiver.class);
            intent.putExtra("courseAlert", " The Assessment: '" + assessmentTitle.getText().toString() + "' start date notification has been set.");
            PendingIntent sender=PendingIntent.getBroadcast(ActivityAssessmentDetail.this,++ActivityCourseDetail.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            return true;
        }

        if (item.getItemId() == R.id.assessment_notification_end) {
            String dateFromScreen = endDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ActivityAssessmentDetail.this, MyReceiver.class);
            intent.putExtra("courseAlert", " The Assessment: '" + assessmentTitle.getText().toString() + "' end date notification has been set.");
            PendingIntent sender=PendingIntent.getBroadcast(ActivityAssessmentDetail.this,++ActivityCourseDetail.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDateClickerDialog(View view) {
        int viewID = view.getId();
        TextView datePickerView = findViewById(viewID);
        DialogFragment newFragment = new DateSelection(datePickerView);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void saveAssessmentButton(View view) {
        if (assessmentTitle.getText().toString().trim().isEmpty() || startDate.getText().toString().trim().isEmpty() || endDate.getText().toString().trim().isEmpty() || (!objectiveAssessment.isChecked() && !performanceAssessment.isChecked())){
            Toast.makeText(this, "Error: Empty Fields", Toast.LENGTH_LONG).show();
            return;
        }

        AssessmentEntity assessment = null;

        if (assessmentID == -1) {
            List<AssessmentEntity> allAssessments = repository.getAllAssessments();
            assessmentID = allAssessments.get(allAssessments.size() - 1).getAssessmentID();
            ++assessmentID;
        }
        if (objectiveAssessment.isChecked()) {
            assessment = new AssessmentEntity(assessmentID, assessmentTitle.getText().toString(), AssessmentType.ObjectiveAssessment, DateHelper.parseDate(startDate.getText().toString()), DateHelper.parseDate(endDate.getText().toString()),courseID);
        }
        else if (performanceAssessment.isChecked()){
            assessment = new AssessmentEntity(assessmentID, assessmentTitle.getText().toString(), AssessmentType.PerformanceAssessment, DateHelper.parseDate(startDate.getText().toString()), DateHelper.parseDate(endDate.getText().toString()),courseID);
        }
        repository.insert(assessment);

        Intent intent = new Intent(ActivityAssessmentDetail.this, ActivityCourseDetail.class);
        intent.putExtra("assessmentSaved",true);
        startActivity(intent);
    }
}