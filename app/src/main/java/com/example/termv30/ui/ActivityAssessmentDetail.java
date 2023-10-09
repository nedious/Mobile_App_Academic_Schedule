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

import java.time.ZoneId;
import java.util.List;

public class ActivityAssessmentDetail extends AppCompatActivity {
    private Repository repository;

    public static int courseIdAssessmentDetail = -1;
    public static int termIdAssEditPage = -1;

    int assessmentID;
    EditText assessmentTitle;
    RadioButton OARadio;
    RadioButton PARadio;
    EditText endDate;
    EditText startDate;
    int courseID;

    AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        //Set Variables
        System.out.println(courseIdAssessmentDetail);
        courseIdAssessmentDetail = getIntent().getIntExtra("courseID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        termIdAssEditPage = getIntent().getIntExtra("termID", -1);

        System.out.println(courseIdAssessmentDetail);

        //If not creating a new Entity, fills out current fields
        repository = new Repository((getApplication()));
        List<AssessmentEntity> allAssessments = repository.getAllAssessments();

        for(AssessmentEntity assessment:allAssessments){
            if (assessment.getAssessmentID() == assessmentID)
                currentAssessment = assessment;
        }

        assessmentTitle = findViewById(R.id.assessment_title_editText);
        OARadio = findViewById(R.id.objective_assessment);
        PARadio = findViewById(R.id.performance_assessment);
        endDate = findViewById(R.id.assessment_end_date);
        startDate = findViewById(R.id.assessment_start_date);

        if (currentAssessment != null){
            assessmentTitle.setText(currentAssessment.getAssessmentTitle());
            switch (currentAssessment.getAssessmentType()){
                case ObjectiveAssessment:
                    OARadio.setChecked(true);
                    break;
                case PerformanceAssessment:
                    PARadio.setChecked(true);
                    break;
            }
            endDate.setText(currentAssessment.getEndDate().format(DateHelper.dtf));
            startDate.setText(currentAssessment.getStartDate().format(DateHelper.dtf));
        }
        else
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.assessment_notification_start) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.assessment_details), "Notification for Start date Set", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            Intent intentStart = new Intent(ActivityAssessmentDetail.this, MyReceiver.class);
            intentStart.putExtra("courseAlert","The Assessment: '" + assessmentTitle.getText().toString() + "' start date notification has been set..");
            PendingIntent senderStart = PendingIntent.getBroadcast(ActivityAssessmentDetail.this,++ActivityCourseDetail.numAlert,intentStart, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            long startDateMillis = DateHelper.parseDate(startDate.getText().toString()).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startDateMillis, senderStart);
            return true;
        }
        if (id == R.id.assessment_notification_end) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.assessment_details), "Notification for End date Set", Snackbar.LENGTH_LONG);
            mySnackbar.show();
            Intent intentEnd = new Intent(ActivityAssessmentDetail.this, MyReceiver.class);
            intentEnd.putExtra("courseAlert","The Assessment: '" + assessmentTitle.getText().toString() + "' end date notification has been set..");
            PendingIntent senderEnd = PendingIntent.getBroadcast(ActivityAssessmentDetail.this,++ActivityCourseDetail.numAlert,intentEnd, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            long endDateMillis = DateHelper.parseDate(endDate.getText().toString()).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, endDateMillis, senderEnd);
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

    public void addAssessmentFromScreen(View view) {
        if (assessmentTitle.getText().toString().trim().isEmpty() || startDate.getText().toString().trim().isEmpty() || endDate.getText().toString().trim().isEmpty() || (!OARadio.isChecked() && !PARadio.isChecked())){
            Toast.makeText(this, "All fields must be filled prior to saving Assessment", Toast.LENGTH_LONG).show();
            return;
        }

        AssessmentEntity assessment = null;

        if (assessmentID == -1) {
            List<AssessmentEntity> allAssessments = repository.getAllAssessments();
            assessmentID = allAssessments.get(allAssessments.size() - 1).getAssessmentID();
            ++assessmentID;
        }
        if (OARadio.isChecked()) {
            assessment = new AssessmentEntity(assessmentID, assessmentTitle.getText().toString(), AssessmentType.ObjectiveAssessment, DateHelper.parseDate(startDate.getText().toString()), DateHelper.parseDate(endDate.getText().toString()),courseID);
        }
        else if (PARadio.isChecked()){
            assessment = new AssessmentEntity(assessmentID, assessmentTitle.getText().toString(), AssessmentType.PerformanceAssessment, DateHelper.parseDate(startDate.getText().toString()), DateHelper.parseDate(endDate.getText().toString()),courseID);
        }
        repository.insert(assessment);

        Intent intent = new Intent(ActivityAssessmentDetail.this, ActivityCourseDetail.class);
        intent.putExtra("assessmentSaved",true);
        startActivity(intent);
    }
}