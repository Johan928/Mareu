package com.example.mareu.View;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.events.MeetingAddedEvent;
import com.example.mareu.service.DataValidation;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;
    TextInputEditText textInputDate;
    TextInputLayout textInputDateLayout;
    TextInputEditText textInputStartingHour;
    TextInputLayout textInputStartingHourLayout;
    TextInputEditText textInputEndingHour;
    TextInputLayout textInputEnddingHourLayout;
    TextInputEditText textInputSubject;
    TextInputLayout textInputSubjectLayout;
    AutoCompleteTextView dropDownListRooms;
    TextInputLayout dropDownListRoomsLayout;
    TextInputLayout textInputUsersLayout;
    Button registerNewMeetingButton;
    List<String> users = new ArrayList<>();
    List<String> mRooms = new ArrayList<>();
    List<String> occupiedRooms = new ArrayList<>();
    SimpleDateFormat simpleDateFormatWithHour = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
    MeetingApiService mMeetingApiService = DI.getMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initWidgets();
        initOnClickListeners();
        initTextFieldsListeners();
        initusers();
    }

    private void initTextFieldsListeners() {
        textInputDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                initRooms();
            }
        });
        textInputStartingHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                initRooms();
            }
        });
        textInputEndingHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                initRooms();
            }
        });
    }

    private void initOnClickListeners() {
        textInputDateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {selectDateDialog();}
        });
        textInputDateLayout.setErrorIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {selectDateDialog();}
        });
        textInputStartingHourLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {selectTimeDialog("startinghour");}
        });
        textInputStartingHourLayout.setErrorIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {selectTimeDialog("startinghour");}
        });
        textInputEnddingHourLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {selectTimeDialog("endinghour");}
        });
        textInputEnddingHourLayout.setErrorIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {selectTimeDialog("endinghour");}
        });
        registerNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {createMeeting();}
        });
    }

    private void initWidgets() {

        textInputDate = binding.textinputEditTextDateAddmeetingactivity;
        textInputStartingHour = binding.textinputEditTextStarthourAddmeetingactivity;
        textInputEndingHour = binding.textinputEditTextEndhourAddmeetingactivity;
        dropDownListRooms = binding.autoCompleteRoomsAddmeetingactivity;
        registerNewMeetingButton = binding.outlinedButtonCreateAddmeetingactivity;
        textInputSubject = binding.textinputEditTextSubjectAddmeetingactivity;
        textInputDateLayout = binding.outlinedTextFieldDateAddmeetingactivity;
        textInputStartingHourLayout = binding.outlinedTextFieldStarthourAddmeetingactivity;
        textInputEnddingHourLayout = binding.outlinedTextFieldEndhourAddmeetingactivity;
        textInputSubjectLayout = binding.outlinedTextFieldSubjectAddmeetingactivity;
        dropDownListRoomsLayout = binding.dropdownMenuRoomsAddmeetingactivity;
        textInputUsersLayout = binding.outlinedTextFieldUsersAddmeetingactivity;
    }

    private void initusers() {
        int id = 1000;
        for (String user : DummyMeetingApiService.USERS) {
            MaterialCheckBox checkBox = new MaterialCheckBox(this);
            checkBox.setText(user);
            checkBox.setWidth(WRAP_CONTENT);
            checkBox.setHeight(WRAP_CONTENT);
            checkBox.setId(id);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox chk = findViewById(view.getId());
                    String chkTitle = (String) chk.getText();
                    if (chk.isChecked()) {
                        users.add(chkTitle);
                    } else {
                        users.remove(chkTitle);
                    }
                }
            });
            textInputUsersLayout.addView(checkBox, textInputUsersLayout.getChildCount());
            id += 1;
        }
    }

    private void initRooms() {
        if (checkfordatevalidity()) {
            mRooms.clear();
            mRooms.addAll(DummyMeetingApiService.ROOMS);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mRooms);
            dropDownListRooms.setAdapter(adapter);
            checkForOccupiedRooms();
        } else {
            dropDownListRoomsLayout.setHelperText("");
            dropDownListRooms.setText("");
            dropDownListRooms.setAdapter(null);
        }
    }

    private void selectDateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 10;
        int selectedDay = 1;
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                textInputDate.setText(simpleDateFormat.format(cal.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }

    private void selectTimeDialog(String id) {

        MaterialTimePicker timepicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Select an hour :")
                .build();
        timepicker.show(getSupportFragmentManager(), "tag");
        timepicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heure = String.format("%02d", timepicker.getHour()).concat(":").concat(String.format("%02d", timepicker.getMinute()));
                if (id.equals("startinghour")) {
                    textInputStartingHour.setText(heure);
                } else {
                    textInputEndingHour.setText(heure);
                }
            }
        });
    }

    private void checkForOccupiedRooms() {
        if (!(mMeetingApiService.checkForOccupiedRooms(textInputDate, textInputStartingHour, textInputEndingHour) == null)) {
            occupiedRooms = mMeetingApiService.checkForOccupiedRooms(textInputDate, textInputStartingHour, textInputEndingHour);
            String listOccupiedRooms = "";
            for (String occupiedRoom : occupiedRooms) {
                mRooms.remove(occupiedRoom);
                listOccupiedRooms = listOccupiedRooms.concat(occupiedRoom + " ");
            }
            if (!(listOccupiedRooms.equals(""))) {
                dropDownListRoomsLayout.setHelperText(getString(R.string.unavailable_rooms) + listOccupiedRooms);
            } else {
                dropDownListRoomsLayout.setHelperText("");
            }
        }
    }

    private boolean checkfordatevalidity() {
        DataValidation dataValidation = new DataValidation(textInputDate, textInputDateLayout, textInputStartingHour, textInputStartingHourLayout, textInputEndingHour,
                textInputEnddingHourLayout, dropDownListRooms, dropDownListRoomsLayout);
        return dataValidation.validate();
    }

    private void createMeeting() {
        textInputDateLayout.setError("");
        textInputStartingHourLayout.setError("");
        textInputEnddingHourLayout.setError("");
        textInputSubjectLayout.setError("");
        dropDownListRoomsLayout.setError("");
        textInputUsersLayout.setError("");
        simpleDateFormatWithHour.setLenient(false);
        Date startingDate = null;
        Date endingDate = null;
        if (checkfordatevalidity()) {
            try {
                startingDate = simpleDateFormatWithHour.parse(Objects.requireNonNull(textInputDate.getText()).toString() + " " + Objects.requireNonNull(textInputStartingHour.getText()).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                endingDate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + Objects.requireNonNull(textInputEndingHour.getText()).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else return;

        String subject = Objects.requireNonNull(textInputSubject.getText()).toString();
        if (subject.isEmpty()) {
            textInputSubjectLayout.setError(getString(R.string.subject_is_empty));
            return;
        }
        String room = dropDownListRooms.getText().toString();
        if (room.isEmpty()) {
            dropDownListRoomsLayout.setError(getString(R.string.no_rooms_selected));
            return;
        }
        ArrayList<String> usersToRegister = new ArrayList<>();
        usersToRegister.addAll(users);
        if (usersToRegister.size() == 0) {
            textInputUsersLayout.setError(getString(R.string.no_participant_selected));
            return;
        }
        Meeting meeting = new Meeting(startingDate, endingDate, room, subject, usersToRegister);
        DI.getMeetingApiService().addMeeting(meeting);
        EventBus.getDefault().post(new MeetingAddedEvent());
        finish();
    }
}