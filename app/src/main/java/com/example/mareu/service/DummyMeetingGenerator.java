package com.example.mareu.service;

import com.example.mareu.Model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DummyMeetingGenerator {


public static List<Meeting> FAKE_MEETINGS_LIST;

    static {
        try {
            final ArrayList<String> FAKEUSERLIST = new ArrayList<>();
            FAKEUSERLIST.add("bob@lamzone.com");
            FAKEUSERLIST.add("bene@lamzone.com");
            FAKEUSERLIST.add("georges@lamzone.com");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);

                FAKE_MEETINGS_LIST = Arrays.asList(
                    new Meeting(sdf.parse("01/11/2021 08:30"),sdf.parse("01/11/2021 10:30"),
                            "ROOM1", "Morning Briefing", FAKEUSERLIST),
                    new Meeting(sdf.parse("02/12/2021 11:00"),sdf.parse("02/12/2021 12:30"),
                            "ROOM6", "Team Evaluation", FAKEUSERLIST),
                    new Meeting(sdf.parse("02/01/2022 07:00"),sdf.parse("02/01/2022 08:00"),
                            "ROOM2","Happy New Year Meeting",FAKEUSERLIST),
                    new Meeting(sdf.parse("20/01/2022 17:15"),sdf.parse("20/01/2022 19:30"),
                            "ROOM1","Security training",FAKEUSERLIST),
                    new Meeting(sdf.parse("28/01/2022 15:00"),sdf.parse("28/01/2022 16:00"),
                            "ROOM6","Monthly Meeting",FAKEUSERLIST)

                );


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

public static ArrayList<Meeting> generateMeetings() {
return new ArrayList<>(FAKE_MEETINGS_LIST);
}
}
