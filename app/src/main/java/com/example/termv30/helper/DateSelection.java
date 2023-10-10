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
    final Calendar calendar = Calendar.getInstance();
    public DateSelection(TextView datePickerView) {
        this.datePickerView = datePickerView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // New Instancce of  DatePickerDialog.
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        ++month;
        String currentDateString = month + "/" + day + "/" + year;
        datePickerView.setText(currentDateString);
    }
}
