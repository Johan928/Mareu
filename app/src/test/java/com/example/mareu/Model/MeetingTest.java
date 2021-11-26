package com.example.mareu.Model;


import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.test.espresso.ViewFinder;

import com.example.mareu.DI.DI;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.textfield.TextInputEditText;
import androidx.test.InstrumentationRegistry.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    /**
     * Get a meeting from generator and add it to the Meeting list using the ApiService
     */
    public void addMeeting(){
        Meeting meeting = DummyMeetingGenerator.generateMeetings().get(0);
        service.addMeeting(meeting);
        Assert.assertEquals(1,service.getMeetings().size());
    }
    @Test
    /**
     * Delete previously added meeting et check if the list size is decreased
     */
    public void deleteMeeting() {
        service.getMeetings().clear();
        service.getMeetings().add(DummyMeetingGenerator.FAKE_MEETINGS_LIST.get(0));
        Meeting meeting = service.getMeetings().get(0);
        service.deleteMeeting(meeting);
        Assert.assertFalse(service.getMeetings().contains(meeting));
    }
    @Test
    /**
     * Feed the meetings list with the FAKE_MEETING_LIST and verify if getmeetings returns the meetings.
     */
    public void getMeeting() {

        for (int i = 0; i < DummyMeetingGenerator.FAKE_MEETINGS_LIST.size(); i++) {
            service.addMeeting(DummyMeetingGenerator.FAKE_MEETINGS_LIST.get(i));
        }
        List<Meeting> meetings = service.getMeetings();
        Assert.assertEquals(5,meetings.size());
    }
    @Test
    /**
     * Feed the meeting list with FAKE MEETINGS LIST, then clear all and check if list size is equals to 0.
     */
    public void clearmeetings() {
        service.getMeetings().addAll(DummyMeetingGenerator.FAKE_MEETINGS_LIST);
        service.clearMeetings();
        Assert.assertEquals(0,service.getMeetings().size());
    }

    @Test
    public void getMeetingFilteredByDate() throws ParseException {
        service.clearMeetings();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = simpleDateFormat.parse("02/12/2021");
        service.getMeetings().addAll(DummyMeetingGenerator.FAKE_MEETINGS_LIST);
        List<Meeting> filteredByDateList = service.getMailsFilteredByDate(date);
        Assert.assertEquals(1,filteredByDateList.size());
    }

    @Test
    public void getMeetingFilteredByLocation() {
        service.clearMeetings();
        service.getMeetings().addAll(DummyMeetingGenerator.FAKE_MEETINGS_LIST);
        String room = "ROOM6";
        List<Meeting> filteredByRoomList = service.getMailsFilteredByLocation(room);
        Assert.assertEquals(2,filteredByRoomList.size());
    }

    @Test
    public void checkForOccupiedRooms() {
        service.clearMeetings();
        service.getMeetings().addAll(DummyMeetingGenerator.FAKE_MEETINGS_LIST);
        TextInputEditText textInputDate = new TextInputEditText(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getContext());
        TextInputEditText textInputStartingHour = new TextInputEditText(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getContext());
        TextInputEditText textInputEndingHour= new TextInputEditText(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getContext());
        textInputDate.setText("01/11/2021");
        textInputStartingHour.setText("08:30");
        textInputEndingHour.setText("10:30");
        List<String> occupiedRoomsList = service.checkForOccupiedRooms(textInputDate,textInputStartingHour,textInputEndingHour);
        Assert.assertEquals(1,occupiedRoomsList.size());

    }


}