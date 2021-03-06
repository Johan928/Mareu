package com.example.mareu.service;

import com.example.mareu.Model.Meeting;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    /**
     * Used to get all created meetings
     */
    ArrayList<Meeting> getMeetings();

    void setMeetings(List<Meeting> meetingList);

    /**
     *Delete a meeting
     */

    void deleteMeeting(Meeting meeting);
    /**
     *Create a new meeting
     */
    void deleteFilteredMeeting(Meeting meeting,ArrayList<Meeting> meetingsList);

    void addMeeting(Meeting meeting);

    /**
     *Clear the meeting list
     */
    void clearMeetings();

    ArrayList<Meeting> getMailsFilteredByDate(Date date);

    ArrayList<Meeting> getMailsFilteredByLocation(String room);

    int getMonthColorFromArray(Date date);

    List<String> checkForOccupiedRooms(TextInputEditText textInputDate, TextInputEditText textInputStartingHour, TextInputEditText textInputEndingHour);

    List<String> checkForOccupiedRoomsTests(String date,String startingHour, String endingHour);

}
