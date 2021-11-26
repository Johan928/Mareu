package com.example.mareu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.DI.DI;
import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.View.ListMeetingsActivity;
import com.example.mareu.events.ShowMeetingDetailsInFragment;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>
{

    private final ArrayList<Meeting> mMeetings;
    private MeetingApiService mApiService;

    public MeetingAdapter(ArrayList<Meeting> meetings) {
    this.mMeetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.fragment_meetings_item,parent,false);
       mApiService = DI.getMeetingApiService();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.displayMeeting(meeting);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ListMeetingsActivity.selectedList.equals("filtered")) {
                    mApiService.deleteFilteredMeeting(meeting,mMeetings);
                }
                    mApiService.deleteMeeting(meeting);
                    notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.informations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new ShowMeetingDetailsInFragment(meeting));

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
        int i;
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
    public final ImageView dateincirclebackground;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            informations = itemView.findViewById(R.id.textview_meetings_information);
            users = itemView.findViewById(R.id.textview_meetings_users);
            delete = itemView.findViewById(R.id.Imageview_delete_button);
            dateincircle = itemView.findViewById(R.id.textView_date_in_circle);
            dateincirclebackground = itemView.findViewById(R.id.circle);

        }

        public void displayMeeting(Meeting meeting) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
            String separator = " - ";
            String informationsString = simpleDateFormat.format(meeting.getStartingDate()) + separator + meeting.getSubject() + separator + meeting.getLocation();
            informations.setText(informationsString);
            users.setText(listUsers(meeting.getUsers()));
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM\nyyyy",Locale.FRENCH);
            dateincircle.setText(simpleDateFormat1.format(meeting.getStartingDate()));
            dateincirclebackground.setColorFilter(ContextCompat.getColor(itemView.getContext(), DI.getMeetingApiService().getMonthColorFromArray(meeting.getStartingDate())), android.graphics.PorterDuff.Mode.SRC_IN);
        }



    }

}
