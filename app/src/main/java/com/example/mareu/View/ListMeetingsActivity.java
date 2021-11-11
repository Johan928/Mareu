package com.example.mareu.View;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityMainMeetingsBinding;
import com.example.mareu.databinding.FragmentMeetingsListBinding;
import com.example.mareu.events.MeetingAddedOrDeletedEvent;
import com.example.mareu.events.MeetingFilteredList;
import com.example.mareu.events.ShowMeetingDetailsInFragment;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListMeetingsActivity extends AppCompatActivity {
    ActivityMainMeetingsBinding binding;
    FragmentMeetingsListBinding fragmentbinding;
    private static final String TAG = "ListMeetingsActivity";
    ArrayList<Meeting> mMeetings = new ArrayList<>();
    ArrayList<Meeting> mDateFilteredMeetings = new ArrayList<>();
    ArrayList<Meeting> mLocationFilteredMeetings = new ArrayList<>();
    private final MeetingApiService mMeetingApiService = DI.getMeetingApiService();


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        try {
            initUI(savedInstanceState);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        setSupportActionBar(binding.toolbar);
        mMeetings = mMeetingApiService.getMeetings();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initUI(Bundle savedInstanceState) throws InstantiationException, IllegalAccessException {
        mMeetingApiService.clearMeetings();
        binding = ActivityMainMeetingsBinding.inflate(getLayoutInflater());
        fragmentbinding = FragmentMeetingsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setButtonAddMeeting();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                .add(R.id.fragment_container_list,MeetingsFragment.class.newInstance(),null)
                .commit();
        }
        initFragmentDetails();

    }

    private void initFragmentDetails() throws InstantiationException, IllegalAccessException {
        if (!(findViewById(R.id.fragment_container_details) == null)){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_details,DetailsFragment.class.newInstance(),null)
                    .commit();
        }
    }


    private static final int ITEMID = Menu.FIRST;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        int i = 1;
        List<String> rooms = DummyMeetingApiService.ROOMS;
        SubMenu subMenu = (SubMenu) menu.addSubMenu("Filter by location");
        for (String room : rooms) {
           subMenu.add(0, i, i, room);
           i+=1;
                }
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
                try {
                    initFragmentDetails();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            case (R.id.filter_date):
                dateDialog();
                try {
                    initFragmentDetails();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            return true;
            case 11:
                resetfilter();
                try {
                    initFragmentDetails();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void resetfilter() {
        EventBus.getDefault().post(new MeetingAddedOrDeletedEvent());
    }

    @Subscribe
    public void ShowMeetingDetailsInFragment(ShowMeetingDetailsInFragment event) {

        if (!(findViewById(R.id.fragment_container_details) == null)) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_details, DetailsFragment.newInstance(event.getCurrentMeeting()), null)
                    .commit();
        }
    }

    private void locationDialog(String selectedroom) {
        mLocationFilteredMeetings.clear();
        mLocationFilteredMeetings.addAll(mMeetingApiService.getMailsFilteredByLocation(selectedroom));
        EventBus.getDefault().post(new MeetingFilteredList(mLocationFilteredMeetings));
    }

    private void dateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 10;
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