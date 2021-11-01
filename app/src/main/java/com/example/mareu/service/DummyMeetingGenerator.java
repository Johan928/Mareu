package com.example.mareu.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mareu.Model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DummyMeetingGenerator {


public static List<Meeting> FAKE_MEETINGS_LIST;

    static {
        try {
            FAKE_MEETINGS_LIST = Arrays.asList(
                    new Meeting(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("27/10/2021 08:30"), new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("27/10/2021 10:30"), "ROOM1", "Morning Briefing", "Bob,John,Henri"),
                    new Meeting(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("27/10/2021 11:00"), new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("27/10/2021 08:30"), "ROOM6", "Team Evaluation", "Michel,Claude,René")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

public static ArrayList<Meeting> generateMeetings() {
return new ArrayList<>(FAKE_MEETINGS_LIST);
}
}
