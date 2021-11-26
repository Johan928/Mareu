package com.example.mareu.View;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.View.utils.DeleteViewAction;
import com.example.mareu.View.utils.RecyclerViewItemCount;
import com.example.mareu.service.DummyMeetingGenerator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ListMeetingsActivityTest {

    private static final int ITEMS_COUNT = 2;
    @Rule
    public ActivityTestRule<ListMeetingsActivity> mActivityRule =
            new ActivityTestRule(ListMeetingsActivity.class);

    @Before
    public void setUp() {
        ListMeetingsActivity activity = mActivityRule.getActivity();
        Assert.assertNotNull(activity);
        DI.getMeetingApiService().addMeeting(DummyMeetingGenerator.FAKE_MEETINGS_LIST.get(0));
        DI.getMeetingApiService().addMeeting(DummyMeetingGenerator.FAKE_MEETINGS_LIST.get(1));

    }

    @Test
    public void CheckIfAddMeetingIsLaunchedOnButtonClick() {
    onView(withId(R.id.ButtonStartAddMeeting))
            .perform(click());
    onView(withId(R.id.add_meeting_activity)).check(matches(isDisplayed()));

    }

    @Test
    public void checkIfAddMeetingWorksAndCountRecyclerViewItems() {
    onView(withId(R.id.ButtonStartAddMeeting))
            .perform(click());
    onView(withId(R.id.textinputEditText_date_addmeetingactivity)).perform(replaceText("01/01/2022"));
    onView(withId(R.id.textinputEditText_starthour_addmeetingactivity)).perform(replaceText("08:00"));
    onView(withId(R.id.textinputEditText_endhour_addmeetingactivity)).perform(replaceText("12:00"));
    onView(withId(R.id.textinputEditText_subject_addmeetingactivity)).perform(replaceText("TEST"));
    onView(withId(R.id.dropdown_menu_rooms_addmeetingactivity)).perform(click());
    onView(withId(1000)).perform(click());
    onView(withText("johan@lamzone.com")).perform(click());
    onView(withId(R.id.outlinedButton_create_addmeetingactivity)).perform(click());

    onView(withId(R.id.layout_list_meetings)).check(matches(isDisplayed()));
    onView(ViewMatchers.withId(R.id.recyclerview2)).check(matches(hasChildCount(3)));
}

    @Test
    public void clickOnTrashDeleteMeeting(){


    onView(ViewMatchers.withId(R.id.recyclerview2)).check(RecyclerViewItemCount.withItemCount(ITEMS_COUNT));

    onView(ViewMatchers.withId(R.id.recyclerview2))
            .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

    onView(ViewMatchers.withId(R.id.recyclerview2)).check(RecyclerViewItemCount.withItemCount(ITEMS_COUNT-1));
}

    @Test
    public void checkIfOptionMenuFilterByDateWorks(){
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
    onView(withText("Filter by date"))
            .perform(click());

   onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021,12,2));
    onView(withText("OK")).perform(click());
    onView(ViewMatchers.withId(R.id.recyclerview2)).check(RecyclerViewItemCount.withItemCount(1));
}

    @Test
    public void checkIfOptionMenuFilterByRoomWorks(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filter by location"))
                .perform(click());
        onView(withText("ROOM6")).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview2)).check(RecyclerViewItemCount.withItemCount(1));
    }

    @Test
    public void checkIfOptionMenuResetFilterWorks(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filter by location"))
                .perform(click());
        onView(withText("ROOM6")).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview2)).check(RecyclerViewItemCount.withItemCount(ITEMS_COUNT -1));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Reset filter"))
                .perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview2)).check(RecyclerViewItemCount.withItemCount(ITEMS_COUNT));
    }

}