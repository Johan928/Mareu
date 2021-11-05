package com.example.mareu.service;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import com.example.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

public static ArrayList<Meeting> ListMeetings = new ArrayList<>();

    @Override
    public void addMeeting(Meeting meeting) {
        ListMeetings.add(meeting);
    }

    @Override
    public ArrayList<Meeting> getMailsFilteredByDate(Date date) {
      ArrayList<Meeting> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (int i = 0 ; i < ListMeetings.size();i++){
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(ListMeetings.get(i).getStartingDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(ListMeetings.get(i));


        }
      return  result;
    }

    @Override
    public ArrayList<Meeting> getMailsFilteredByLocation(String room) {
        ArrayList<Meeting> filteredListMeeting = new ArrayList<>();

        for (Meeting meeting : ListMeetings) {
            if (meeting.getLocation().equalsIgnoreCase(room)){
                filteredListMeeting.add(meeting);
            }
        }

        return filteredListMeeting;
    }

    public ArrayList<Meeting> getMeetings(){
        return ListMeetings;
   }

    @Override    public void deleteMeeting(Meeting meeting) {
        ListMeetings.remove(meeting);
    }

   // public static final String[] ROOMS = {"ROOM1","ROOM2","ROOM3","ROOM4","ROOM5","ROOM6","ROOM7","ROOM8","ROOM9","ROOM10"};

    public static final List<String> ROOMS = Arrays.asList("ROOM1","ROOM2","ROOM3","ROOM4","ROOM5","ROOM6","ROOM7","ROOM8","ROOM9","ROOM10");

    public static final List<String> USERS = Arrays.asList("Johan","Aurélie","Philippe","Henri","Nicolas","Frédéric","Frédéric","Marion","Yannick","Stéphanie");

}
