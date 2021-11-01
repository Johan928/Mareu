package com.example.mareu.View;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.events.MeetingFilteredList;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.DummyMeetingGenerator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;
    TextInputEditText textInputDate;
    TextInputEditText textInputStartingHour;
    TextInputEditText textInputEndingHour;
    TextInputEditText textInputSubject;
    AutoCompleteTextView dropDownListRooms;
    TextInputEditText textInputUsers;
    Button createNewMeetingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       initUI();
initspinner();
    }



    private void initUI() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        textInputDate = binding.textinputEditTextDateAddmeetingactivity;
        textInputStartingHour = binding.textinputEditTextStarthourAddmeetingactivity;
        textInputEndingHour = binding.textinputEditTextEndhourAddmeetingactivity;
        dropDownListRooms = binding.autoCompleteRoomsAddmeetingactivity;
        createNewMeetingButton = binding.outlinedButtonCreateAddmeetingactivity;
        textInputSubject = binding.textinputEditTextSubjectAddmeetingactivity;
        textInputUsers = binding.textinputEditTextUsersAddmeetingactivity;
        View view = binding.getRoot();
        setContentView(view);

        textInputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dateDialog();
            }
        });
        textInputStartingHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeDialog("startinghour");
            }
        });
        textInputEndingHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeDialog("endinghour");

            }
        });
        createNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   try {
                  //  Meeting meeting = new Meeting(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(textInputDate.getText().toString() + " " + textInputStartingHour.getText().toString())
                  //  ,new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(textInputDate.getText().toString() + " "  + textInputEndingHour.getText().toString())
                  //          ,dropDownListRooms.getText().toString(),textInputSubject.getText().toString(),textInputUsers.getText().toString());
                 //  DI.getMeetingApiService().addMeeting(meeting);
              //  } catch (ParseException e) {
              //      e.printStackTrace();
              //  }

DI.getMeetingApiService().addMeeting(DummyMeetingGenerator.FAKE_MEETINGS_LIST.get(0));
            }
        });
    }
    private void dateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 10;
        int selectedDay =1;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.set(i,i1,i2);
            textInputDate.setText(simpleDateFormat.format(cal.getTime()));
                Log.d(TAG, "onDateSet: " + simpleDateFormat.format(cal.getTime()));

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener,selectedYear,selectedMonth,selectedDay);
        datePickerDialog.show();

    }

    private void timeDialog(String id) {

        MaterialTimePicker timepicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)

                        .setTitleText("Select Starting time")
                        .build();

                timepicker.show(getSupportFragmentManager(),"tag");

        timepicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == "startinghour") {
                    textInputStartingHour.setText(timepicker.getHour() + ":" + timepicker.getMinute());
                } else {
                    textInputEndingHour.setText(timepicker.getHour() + ":" + timepicker.getMinute());
                }

            }
        });
    }

private void initspinner() {


    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, DummyMeetingApiService.ROOMS);
//set the spinners adapter to the previously created one.
    dropDownListRooms.setAdapter(adapter);


}
}