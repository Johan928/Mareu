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
import com.example.mareu.databinding.ActivityMainMeetingsBinding;
import com.example.mareu.events.MeetingAddedOrDeletedEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>
{
    ActivityMainMeetingsBinding binding;
    private final ArrayList<Meeting> mMeetings;
    private static final String TAG = "MeetingAdapter";

    public MeetingAdapter(ArrayList meetings) {
this.mMeetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.fragment_meetings_item,parent,false);
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

    private static String listUsers(ArrayList<String> listUsers) {
        String users = "";
        String seperator = " - ";
        int i = 0 ;
        for (i=0;i<listUsers.size();i++){
            if (i == listUsers.size()-1){
                users = users + listUsers.get(i);
            } else {
                users = users + listUsers.get(i) + seperator;
            }
        }
        return users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView informations;
    public final TextView users;
    public final ImageView delete;
    public final TextView dateincircle;

    public ViewHolder(@NonNull View itemView) {

        super(itemView);
        informations = itemView.findViewById(R.id.textview_meetings_information);
        users = itemView.findViewById(R.id.textview_meetings_users);
        delete = itemView.findViewById(R.id.Imageview_delete_button);
        dateincircle = itemView.findViewById(R.id.textView_date_in_circle);

    }

        public void displayMeeting(Meeting meeting) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String separator = " - ";
            String informationsString = simpleDateFormat.format(meeting.getStartingDate()) + separator + simpleDateFormat.format(meeting.getEndDate()) + separator + meeting.getSubject() + separator + meeting.getLocation();
            informations.setText(informationsString);
            users.setText(listUsers(meeting.getUsers()));
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM\nyyyy");
            dateincircle.setText(simpleDateFormat1.format(meeting.getStartingDate()));
        }
    }

}
