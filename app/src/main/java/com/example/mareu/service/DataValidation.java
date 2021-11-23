package com.example.mareu.service;

import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataValidation {
    TextInputEditText textInputDate;
    TextInputLayout textInputDateLayout;
    TextInputEditText textInputStartingHour;
    TextInputLayout textInputStartingHourLayout;
    TextInputEditText textInputEndingHour;
    TextInputLayout textInputEnddingHourLayout;
    AutoCompleteTextView dropDownListRooms;
    TextInputLayout dropDownListRoomsLayout;

    SimpleDateFormat simpleDateFormatWithHour =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);

    public DataValidation(TextInputEditText textInputDate,
                          TextInputLayout textInputDateLayout,
                          TextInputEditText textInputStartingHour,
                          TextInputLayout textInputStartingHourLayout,
                          TextInputEditText textInputEndingHour,
                          TextInputLayout textInputEnddingHourLayout,
                          AutoCompleteTextView dropDownListRooms,
                          TextInputLayout dropDownListRoomsLayout) {
        this.textInputDate = textInputDate;
        this.textInputDateLayout = textInputDateLayout;
        this.textInputStartingHour = textInputStartingHour;
        this.textInputStartingHourLayout = textInputStartingHourLayout;
        this.textInputEndingHour = textInputEndingHour;
        this.textInputEnddingHourLayout = textInputEnddingHourLayout;
        this.dropDownListRooms = dropDownListRooms;
        this.dropDownListRoomsLayout = dropDownListRoomsLayout;
    }

    public boolean validate() {
        Date startingDate;
        Date endingDate;
        simpleDateFormatWithHour.setLenient(false);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(textInputDate.getText().toString());
            textInputDateLayout.setError("");
        } catch (ParseException e) {
            textInputDateLayout.setError("invalid Date");
            return false;
        }

        try {
            startingDate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + textInputStartingHour.getText().toString());
            textInputStartingHourLayout.setError("");
        } catch (ParseException e) {
            textInputStartingHourLayout.setError("select a starting time");
            return false;
        }

        try {
            endingDate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + textInputEndingHour.getText().toString());
            textInputEnddingHourLayout.setError("");
        } catch (ParseException e) {
            e.printStackTrace();
            textInputEnddingHourLayout.setError("select an ending time");
            return false;
        }

        if (startingDate.after(endingDate)) {
            textInputEnddingHourLayout.setError("invalid ending before starting");
            resetDropDownList();
            return false;
        } else if (startingDate.equals(endingDate)) {
            textInputEnddingHourLayout.setError("ending can't equals starting");
            resetDropDownList();
            return false;
        }
        return true;
    }

    private void resetDropDownList() {
        dropDownListRoomsLayout.setHelperText("");
        dropDownListRooms.setText("");
        dropDownListRooms.setAdapter(null);
    }

}
