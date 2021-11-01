package com.example.mareu.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mareu.View.MeetingsFragment;

public class ListMeetingsPagerAdapter extends FragmentPagerAdapter {


    public ListMeetingsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MeetingsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
