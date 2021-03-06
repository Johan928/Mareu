package com.example.mareu.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Adapters.MeetingAdapter;
import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.events.MeetingAddedEvent;
import com.example.mareu.events.MeetingFilteredList;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;


public class MeetingsFragment extends Fragment {

    RecyclerView mRecyclerView;
    ArrayList<Meeting> mMeetings;
    private final MeetingApiService service = DI.getMeetingApiService();


    public MeetingsFragment() {
        // Required empty public constructor
    }

    public static MeetingsFragment newInstance() {

        return new MeetingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initData();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        EventBus.getDefault().register(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Subscribe
    public void meetingAddedEvent(MeetingAddedEvent event) {
        mRecyclerView.setAdapter(new MeetingAdapter(mMeetings));
        Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
    }

    @Subscribe
    public void meetingFilteredListEvent(MeetingFilteredList event) {
        mRecyclerView.setAdapter(new MeetingAdapter(event.getFilteredMeetings()));
        Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
    }

    private void initData() {
        mMeetings = service.getMeetings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        MeetingAdapter meetingAdapter = new MeetingAdapter(mMeetings);
        mRecyclerView.setAdapter(meetingAdapter);
        return view;
    }

}