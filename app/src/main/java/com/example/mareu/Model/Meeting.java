package com.example.mareu.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Meeting implements Serializable {

    private final Date mStartingDate;
    private final Date mEndDate;
    private final String mLocation;
    private final String mSubject;
    private final ArrayList<String> mUsers;

    public Meeting(Date startingDate, Date endDate, String location, String subject, ArrayList<String> users) {

        this.mStartingDate = startingDate;
        this.mEndDate = endDate;
        this.mLocation = location;
        this.mSubject = subject;
        this.mUsers = users;
    }

    public Date getStartingDate() {
        return mStartingDate;
    }
    public Date getEndDate() {return mEndDate;}
    public String getLocation() {
        return mLocation;
    }
    public String getSubject() {
        return  mSubject;
    }
    public ArrayList<String> getUsers() {
        return mUsers;
    }


}
