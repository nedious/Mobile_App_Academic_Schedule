package com.example.termv30.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateSelection extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView datePickerView;
    final Calendar c = Calendar.getInstance();
    public DateSelection(TextView datePickerView) {
        this.datePickerView = datePickerView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        ++month;
        // Do something with the date chosen by the user
        String currentDateString = month + "/" + day + "/" + year;
        datePickerView.setText(currentDateString);
    }
}
