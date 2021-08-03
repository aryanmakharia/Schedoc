package com.example.drapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

        Calendar today = (Calendar) c.clone();
        today.add(Calendar.DATE, 1);
        Calendar lastDay = (Calendar) c.clone();
        lastDay.add(Calendar.DATE, 7);
        dialog.getDatePicker().setMinDate(today.getTimeInMillis());
        dialog.getDatePicker().setMaxDate(lastDay.getTimeInMillis());
        return dialog;
//        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}
