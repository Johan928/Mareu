package com.example.mareu.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.DatePicker;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityMainMeetingsBinding;
import com.example.mareu.databinding.FragmentMeetingsListBinding;
import com.example.mareu.events.MeetingAddedOrDeletedEvent;
import com.example.mareu.events.MeetingFilteredList;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.mareu.Adapters.ListMeetingsPagerAdapter;

public class ListMeetingsActivity extends AppCompatActivity {
    ActivityMainMeetingsBinding binding;
    FragmentMeetingsListBinding fragmentbinding;
    ListMeetingsPagerAdapter mListMeetingsPagerAdapter;
    private static final String TAG = "ListMeetingsActivity";
    ArrayList<Meeting> mMeetings = new ArrayList<>();
    ArrayList<Meeting> mDateFilteredMeetings = new ArrayList<>();
    ArrayList<Meeting> mLocationFilteredMeetings = new ArrayList<>();
    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

        setSupportActionBar(binding.toolbar);
        mMeetings = mMeetingApiService.getMeetings();
    }

    private void initUI() {
        binding = ActivityMainMeetingsBinding.inflate(getLayoutInflater());
        fragmentbinding = FragmentMeetingsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setButtonAddMeeting();
        mListMeetingsPagerAdapter = new ListMeetingsPagerAdapter(getSupportFragmentManager());
        binding.container.setAdapter(mListMeetingsPagerAdapter);

    }
    private static final int ITEMID = Menu.FIRST;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

//int i = 1;
String room;
       SubMenu subMenu = (SubMenu) menu.addSubMenu("Filter by location");
   //   for (i=1;i <= DummyMeetingApiService.ROOMS.size();i++) {
        //  room = (String) "ROOM" + i;
         //  subMenu.add(0, i, i, room);
           //     }
        subMenu.add(0,1,1,"ROOM1");
        subMenu.add(0,2,2,"ROOM2");
        subMenu.add(0,3,3,"ROOM3");
        subMenu.add(0,4,4,"ROOM4");
        subMenu.add(0,5,5,"ROOM5");
        subMenu.add(0,6,6,"ROOM6");
        subMenu.add(0,7,7,"ROOM7");
        subMenu.add(0,8,8,"ROOM8");
        subMenu.add(0,9,9,"ROOM9");
        subMenu.add(0,10,10,"ROOM10");

        menu.add(0,11,1,"Reset filter");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String room;
        switch (item.getItemId()) {
            case 1 :
            case 2 :
            case 3 :
            case 4 :
            case 5 :
            case 6 :
            case 7 :
            case 8 :
            case 9 :
            case 10 :
                room = item.getTitle().toString();
                locationDialog(room);
                return true;
            case (R.id.filter_date):
                dateDialog();
            return true;
            case 11:
                resetfilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void resetfilter() {
        EventBus.getDefault().post(new MeetingAddedOrDeletedEvent());
    }

    private void locationDialog(String selectedroom) {
    mLocationFilteredMeetings.clear();
    mLocationFilteredMeetings.addAll(mMeetingApiService.getMailsFilteredByLocation(selectedroom));
    EventBus.getDefault().post(new MeetingFilteredList(mLocationFilteredMeetings));
    }

    private void dateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 11;
        int selectedDay =1;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                Calendar cal = Calendar.getInstance();
                cal.set(i,i1,i2);

                mDateFilteredMeetings.clear();
                mDateFilteredMeetings.addAll(mMeetingApiService.getMailsFilteredByDate(cal.getTime()));
                Log.d(TAG, "onDateSet: " + mDateFilteredMeetings.size());
                EventBus.getDefault().post(new MeetingFilteredList(mDateFilteredMeetings));

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener,selectedYear,selectedMonth,selectedDay);
        datePickerDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
                super.onStop();

    }

    private void setButtonAddMeeting(){
        binding.ButtonStartAddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

}