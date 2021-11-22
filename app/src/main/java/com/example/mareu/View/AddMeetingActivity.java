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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.events.MeetingAddedEvent;
import com.example.mareu.service.DummyMeetingApiService;
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

    private void initTextFieldsListeners(){
      textInputDate.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void afterTextChanged(Editable editable) {
              initRooms();
          }
      });
      textInputStartingHour.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void afterTextChanged(Editable editable) {
              initRooms();
          }
      });
      textInputEndingHour.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          }
          @Override
          public void afterTextChanged(Editable editable) {
              initRooms();
          }
      });
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
                    String chkTitle =(String) chk.getText();
                    if(chk.isChecked()) {
                        users.add(chkTitle);
                    } else {
                        if (users.contains(chkTitle)){
                            users.remove(chkTitle);
                        }
                    }
                }
            });
            textInputUsersLayout.addView(checkBox,textInputUsersLayout.getChildCount() );
            id +=1;
        }

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

    private void initOnClickListeners() {
        textInputDateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDateDialog();
            }
        });
        textInputDateLayout.setErrorIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDateDialog();
            }
        });
        textInputStartingHourLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTimeDialog("startinghour");
            }
        });
        textInputStartingHourLayout.setErrorIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTimeDialog("startinghour");
            }
        });
        textInputEnddingHourLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTimeDialog("endinghour");
            }
        });
        textInputEnddingHourLayout.setErrorIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTimeDialog("endinghour");
            }
        });
        registerNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {createMeeting();}
        });
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
                startingDate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + textInputStartingHour.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                endingDate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + textInputEndingHour.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else return;

        String subject = textInputSubject.getText().toString();
        if (subject.isEmpty()) {
            textInputSubjectLayout.setError(getString(R.string.subject_is_empty));
            return;
        }
        String room = dropDownListRooms.getText().toString();
        if (room.isEmpty()) {
            dropDownListRoomsLayout.setError("You must choose a meeting room");
            return;
        }

        ArrayList<String> usersToRegister = new ArrayList<>();
        usersToRegister.addAll(users);
        if (usersToRegister.size() == 0) {
            textInputUsersLayout.setError("You must select at least one participant");
            return;
        }

        Meeting meeting = new Meeting(startingDate, endingDate, room, subject, usersToRegister);
        DI.getMeetingApiService().addMeeting(meeting);
        EventBus.getDefault().post(new MeetingAddedEvent());
        finish();

    }

    private void selectDateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 10;
        int selectedDay =1;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                Calendar cal = Calendar.getInstance();
                cal.set(i,i1,i2);
            textInputDate.setText(simpleDateFormat.format(cal.getTime()));
                 }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener,selectedYear,selectedMonth,selectedDay);

        datePickerDialog.show();

    }

    private void selectTimeDialog(String id) {

        MaterialTimePicker timepicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTitleText("Select an hour :")
                        .build();

                timepicker.show(getSupportFragmentManager(),"tag");

        timepicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String heure = String.format("%02d",timepicker.getHour()).concat(":").concat(String.format("%02d",timepicker.getMinute()));
                    if (id == "startinghour") {
                        textInputStartingHour.setText(heure);
                    } else {
                        textInputEndingHour.setText(heure);
                    }
            }
        });
    }

    private void initRooms() {

        if (checkfordatevalidity()) {
            mRooms.clear();
            mRooms.addAll(DummyMeetingApiService.ROOMS);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mRooms);
            dropDownListRooms.setAdapter(adapter);
            checkForOccupiedRooms();
        } else {
            resetDropDownList();
        }
    }

    private void resetDropDownList() {
        dropDownListRoomsLayout.setHelperText("");
        dropDownListRooms.setText("");
        dropDownListRooms.setAdapter(null);
    }

    private void checkForOccupiedRooms() {

        Date currentStartingdate;
        Date currentEndingDate;
        try {
            currentStartingdate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + textInputStartingHour.getText().toString());
        } catch (ParseException e) {
            return;
        }
        try {
            currentEndingDate = simpleDateFormatWithHour.parse(textInputDate.getText().toString() + " " + textInputEndingHour.getText().toString());
        } catch (ParseException e) {
            return;
        }

        String currentRoom;
        occupiedRooms.clear();
        List<Meeting> meetings = DI.getMeetingApiService().getMeetings();
        for (Meeting meeting : meetings) {
            currentRoom = meeting.getLocation();

            if ( (((currentStartingdate.after(meeting.getStartingDate()) || currentStartingdate.equals(meeting.getStartingDate())) && (currentStartingdate.before(meeting.getEndDate())))) ||
                    (((currentEndingDate.after(meeting.getStartingDate())) && ((currentEndingDate.equals(meeting.getEndDate())) || (currentEndingDate.before(meeting.getEndDate()))))) ||
                    (((currentStartingdate.before(meeting.getStartingDate())) && ((currentEndingDate.after(meeting.getEndDate()))))) ) {

                mRooms.remove(currentRoom);
                if (!(occupiedRooms.contains(currentRoom))) {
                    occupiedRooms.add(currentRoom);
                }
            }
        }
        String listOccupiedRooms = "";
        for (String occupiedRoom : occupiedRooms) {
            listOccupiedRooms = listOccupiedRooms.concat(occupiedRoom + " ");
        }
        if (!(listOccupiedRooms == "")) {
            dropDownListRoomsLayout.setHelperText(getString(R.string.unavailable_rooms) + listOccupiedRooms);
        } else {
            dropDownListRoomsLayout.setHelperText("");
        }

    }

    private boolean checkfordatevalidity() {

        Date startingDate;
        Date endingDate;
        simpleDateFormatWithHour.setLenient(false);
        simpleDateFormat.setLenient(false);

        /**
        * checking for a valid date
        */

        try {
            simpleDateFormat.parse(textInputDate.getText().toString());
            textInputDateLayout.setError("");
        } catch (ParseException e) {
            textInputDateLayout.setError("invalid Date");
            return false;
        }
        /**
        * checking for starting and ending hour --- ending hour can not be < starting hour
        **/

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
            textInputEnddingHourLayout.setError("Invalid ending before starting");
            resetDropDownList();
            return false;
        } else if (startingDate.equals(endingDate)) {
            textInputEnddingHourLayout.setError("ending can't equals starting");
            resetDropDownList();
            return false;
        }
        return true;
    }


}