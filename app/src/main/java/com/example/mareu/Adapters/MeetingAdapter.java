package com.example.mareu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityListMeetingsBinding;
import com.example.mareu.events.MeetingAddedOrDeletedEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>
{
    ActivityListMeetingsBinding binding;
    private final ArrayList<Meeting> mMeetings;
    private static final String TAG = "MeetingAdapter";

    public MeetingAdapter(ArrayList meetings) {
this.mMeetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.fragment_meetings,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Meeting meeting = mMeetings.get(position);
    holder.displayMeeting(meeting);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DI.getMeetingApiService().deleteMeeting(meeting);
                              EventBus.getDefault().post(new MeetingAddedOrDeletedEvent());
            }
        });

    }


    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView informations;
    public final TextView users;
    public final ImageView delete;

    public ViewHolder(@NonNull View itemView) {

        super(itemView);
        informations = itemView.findViewById(R.id.textview_meetings_information);
        users = itemView.findViewById(R.id.textview_meetings_users);
        delete = itemView.findViewById(R.id.Imageview_delete_button);

    }
    public void displayMeeting(Meeting meeting) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        informations.setText(meeting.getSubject() + " - " +simpleDateFormat.format(meeting.getStartingDate()) + " - " + meeting.getLocation());
        users.setText(meeting.getUsers());
        }


}


}
