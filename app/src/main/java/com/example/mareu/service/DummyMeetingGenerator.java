package com.example.mareu.service;

import com.example.mareu.Model.Meeting;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {


public static List<Meeting> FAKE_MEETINGS_LIST;

    static {
        try {
            final ArrayList<String> FAKEUSERLIST = new ArrayList<>();
            FAKEUSERLIST.add("Bob");
            FAKEUSERLIST.add("René");
            FAKEUSERLIST.add("Georges");
            FAKE_MEETINGS_LIST = Arrays.asList(
                    new Meeting(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("01/11/2021 08:30"),
                            new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("01/11/2021 10:30"),
                            "ROOM1", "Morning Briefing", FAKEUSERLIST),
                    new Meeting(new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("02/12/2021 11:00"),
                            new SimpleDateFormat("dd/MM/yyyy hh:mm").parse("02/12/2021 12:30"),
                            "ROOM6", "Team Evaluation", FAKEUSERLIST)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

public static ArrayList<Meeting> generateMeetings() {
return new ArrayList<>(FAKE_MEETINGS_LIST);
}
}
