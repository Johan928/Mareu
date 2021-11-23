package com.example.mareu.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mareu.Model.Meeting;
import com.example.mareu.R;
import com.example.mareu.databinding.FragmentDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class DetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    FragmentDetailsBinding binding;
    TextView textViewMeetingDateAndSubject;
    TextView textViewStartingDate;
    TextView textViewParticipants;
    TextView textViewLocation;
    SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);

    // TODO: Rename and change types of parameters
    private Meeting mMeeting;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    public static DetailsFragment newInstance(Meeting meeting) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, meeting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMeeting = (Meeting) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());
        textViewMeetingDateAndSubject = binding.textviewDateAndSubjectDetails;
        textViewStartingDate = binding.textviewStartingAndEndingHourDetails;
        textViewParticipants = binding.textviewListOfParticipantsDetails;
        textViewLocation = binding.textviewLocationDetails;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (!(mMeeting == null)) {

            textViewMeetingDateAndSubject.setText(simpleDateFormat.format(mMeeting.getStartingDate()) + getString(R.string.separator) + mMeeting.getSubject());
            textViewLocation.setText(getString(R.string.select_room) + mMeeting.getLocation());
            textViewStartingDate.setText(getString(R.string.from) + simpleHourFormat.format(mMeeting.getStartingDate()) + getString(R.string.to) + simpleHourFormat.format(mMeeting.getEndDate()));
            textViewParticipants.setText(getString(R.string.participants) + listParticipants());

        } else {
            textViewMeetingDateAndSubject.setText(getResources().getString(R.string.click_on_a_meeting));
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private String listParticipants() {
        String users = "";
        String seperator = getString(R.string.separator);
        int i = 0;
        for (i = 0; i < mMeeting.getUsers().size(); i++) {
            if (i == mMeeting.getUsers().size() - 1) {
                users = users + mMeeting.getUsers().get(i);
            } else {
                users = users + mMeeting.getUsers().get(i) + seperator;
            }
        }
        return users;
    }
}