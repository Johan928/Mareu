package com.example.mareu.events;

import com.example.mareu.Model.Meeting;

public class ShowMeetingDetailsInFragment {
    private final Meeting mMeeting;

    public ShowMeetingDetailsInFragment(Meeting meeting) {

        this.mMeeting = meeting;

    }

    public Meeting getCurrentMeeting() {
        return this.mMeeting;
    }

}
