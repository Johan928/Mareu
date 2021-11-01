package com.example.mareu.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;

import com.example.mareu.events.MeetingAddedOrDeletedEvent;
import com.example.mareu.events.MeetingFilteredList;

import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import com.example.mareu.Adapters.MeetingAdapter;


public class MeetingsFragment extends Fragment {

    RecyclerView mRecyclerView;
    ArrayList<Meeting> mMeetings;
    private MeetingApiService service = DI.getMeetingApiService();


    public MeetingsFragment() {
        // Required empty public constructor
    }
    public  static MeetingsFragment newInstance() {

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
    public void MeetingAddedOrDeletedEvent(MeetingAddedOrDeletedEvent event) {
        mRecyclerView.setAdapter(new MeetingAdapter(mMeetings));
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
@Subscribe
public void MeetingFilteredList(MeetingFilteredList event){
        mRecyclerView.setAdapter(new MeetingAdapter(event.getMeetings()));
        mRecyclerView.getAdapter().notifyDataSetChanged();
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