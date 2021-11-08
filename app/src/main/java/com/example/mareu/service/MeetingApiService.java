package com.example.mareu.service;

import com.example.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    /**
     * Used to get all created meetings
     */
    ArrayList<Meeting> getMeetings();

    /**
     *Delete a meeting
     */

    void deleteMeeting(Meeting meeting);
    /**
     *Create a new meeting
     */
    void addMeeting(Meeting meeting);

    /**
     *Clear the meeting list
     */
    void clearMeetings();

    ArrayList<Meeting> getMailsFilteredByDate(Date date);

    ArrayList<Meeting> getMailsFilteredByLocation(String room);

}
