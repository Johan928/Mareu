package com.example.mareu.events;

import com.example.mareu.Model.Meeting;

import java.util.ArrayList;

public class MeetingFilteredList {

    private ArrayList<Meeting> mMeetings;


public MeetingFilteredList(ArrayList<Meeting> meetings) {
    this.mMeetings = meetings;
}

    public ArrayList<Meeting> getMeetings() {
        return mMeetings;
    }
}
