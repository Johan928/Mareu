package com.example.mareu.service;

import android.util.Log;

import com.example.mareu.Model.Meeting;
import com.example.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

public static ArrayList<Meeting> ListMeetings = new ArrayList<>();

    @Override
    public void addMeeting(Meeting meeting) {
        ListMeetings.add(meeting);
    }

    @Override
    public void clearMeetings() {
        ListMeetings.clear();
    }

    @Override
    public ArrayList<Meeting> getMailsFilteredByDate(Date date) {
      ArrayList<Meeting> FilteredByDateListMeeting = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (int i = 0 ; i < ListMeetings.size();i++){
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(ListMeetings.get(i).getStartingDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) FilteredByDateListMeeting.add(ListMeetings.get(i));
        }
      return  FilteredByDateListMeeting;
    }

    @Override
    public ArrayList<Meeting> getMailsFilteredByLocation(String room) {
        ArrayList<Meeting> filteredByLocationListMeeting = new ArrayList<>();

        for (Meeting meeting : ListMeetings) {
            if (meeting.getLocation().equalsIgnoreCase(room)){
                filteredByLocationListMeeting.add(meeting);
            }
        }
        return filteredByLocationListMeeting;
    }

    @Override
    public int getMonthColorFromArray(Date date) {
        int color;
        int [] colors = new int[]{R.color.purple_200,R.color.teal_200,R.color.salmon,R.color.teal_700,
                R.color.orange,R.color.yellow,R.color.whitesmoke,R.color.lightgray,
                R.color.pink,R.color.blue,R.color.lightgreen,R.color.indianred};
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        color = colors[cal.get(GregorianCalendar.MONTH)];
        return color;
    }

    public ArrayList<Meeting> getMeetings(){
        return ListMeetings;
   }

    @Override
    public void deleteMeeting(Meeting meeting) {
        ListMeetings.remove(meeting);
    }



   @Override
    public void deleteFilteredMeeting(Meeting meeting,ArrayList<Meeting> meetingList) {
        meetingList.remove(meeting);
    }

    public static final List<String> ROOMS = Arrays.asList("ROOM1","ROOM2","ROOM3","ROOM4","ROOM5","ROOM6","ROOM7","ROOM8","ROOM9","ROOM10");

    public static final List<String> USERS = Arrays.asList("johan@lamzone.com","aurelie@lamzone.com","philippe@lamzone.com","henri@lamzone.com","nicolas@lamzone.com",
            "frederic@lamzone.com","fred@lamzone.com","marion@lamzone.com","yannick@lamzone.com","stephanie@lamzone.com");

}
