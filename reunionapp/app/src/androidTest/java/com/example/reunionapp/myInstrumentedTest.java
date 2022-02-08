package com.example.reunionapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.reunionapp.utils.RecyclerViewItemCountAssertion.withItemCount;

import android.app.Activity;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import com.example.reunionapp.Activity.AddReunion;
import com.example.reunionapp.Activity.MainActivity;
import com.example.reunionapp.utils.AddViewAction;
import com.example.reunionapp.utils.DeleteViewAction;

import java.util.Calendar;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class myInstrumentedTest {

    private static final int ITEMS_COUNT = 12;
    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }


    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void reunionList_shouldNotBeEmpty() {

        onView(withId(R.id.myrecycleview))
                .check(matches(hasMinimumChildCount(1)));
    }
    @Test
    public void reunionList_deleteAction_shouldRemoveItem() {
        setUp();
        onView(withId(R.id.myrecycleview)).check(withItemCount(ITEMS_COUNT));
        //We remove the element at position 1
        onView(withId(R.id.myrecycleview))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        //  the number of element is 11
        onView(withId(R.id.myrecycleview)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void reunionList_FiltreByDateAndRoomwithsucess() throws InterruptedException {
        onView(withId(R.id.filterbydateandroom)).perform(click());
        onView(withId(R.id.date_filtre)).perform(doubleClick());
        Thread.sleep(500);
        Calendar calendar = Calendar.getInstance();
        onView(isAssignableFrom(DatePicker.class)).perform(
                PickerActions.setDate(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1,
                        calendar.get(Calendar.DAY_OF_MONTH)
                )
        );
     onView(withText("OK")).perform(click());
       onView(withId(R.id.salle_filtre)).perform(doubleClick());
        onView(withText("Salle A")).perform(ViewActions.click());
        onView(withText("OK")).perform(click());
        onView(withText("OK")).perform(click());
       onView(withId(R.id.myrecycleview)).check(withItemCount(3));
    }
    @Test
    public void reunionList_Filtrebydatewithsucces() {
        onView(withId(R.id.filterbydateandroom)).perform(click());
        onView(withId(R.id.date_filtre)).perform(click());
        onView(withText("OK")).perform(click());
        onView(allOf(withId(R.id.myrecycleview))).check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void reunionList_Filtrebyroomwithsucces() throws InterruptedException {
        onView(withId(R.id.filterbydateandroom)).perform(click());
        onView(withId(R.id.salle_filtre)).perform(doubleClick());
        Thread.sleep(500);
        onView(withText("Salle A")).perform(click());
        onView(withText("Salle B")).perform(click());
       onView(withText("OK")).perform(click());
        onView(withText("OK")).perform(click());
        onView(allOf(withId(R.id.myrecycleview))).check(withItemCount(6));
    }

    @Test
    public void disableAllfiltrewithsucess() {
        onView(withId(R.id.filterbydateandroom)).perform(click());
        onView(withId(R.id.salle_filtre)).perform(doubleClick());
        onView(withText("Salle A")).perform(doubleClick());
        onView(withText("Salle B")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("OK")).perform(click());
        onView(allOf(withId(R.id.myrecycleview))).check(withItemCount(6));
        onView(withId(R.id.filterbydateandroom)).perform(click());
        onView(withText("DISACTIVER TOUS LES FILTRES")).perform(click());
        onView(withId(R.id.myrecycleview)).check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void reunionList_addAction_shouldshowaddreunionactivity() {

       onView(withId(R.id.myrecycleview)).check(withItemCount(ITEMS_COUNT));
        onView(allOf(withId((R.id.addfloatingActionButton)))).perform(click());
        onView(allOf(withId((R.id.subject)))).check(matches(isDisplayed()));
        onView(allOf(withId((R.id.date_reunion)))).check(matches(isDisplayed()));
        onView(allOf(withId((R.id.hour)))).check(matches(isDisplayed()));
        onView(allOf(withId((R.id.listparticipants)))).check(matches(isDisplayed()));
        onView(allOf(withId((R.id.add_button)))).check(matches(isDisplayed()));
        pressBack();
    }
    @Test
    public void reunionList_addAction_shouldAddreunion() throws InterruptedException {
        onView(allOf(withId((R.id.addfloatingActionButton)))).perform(click());
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.subjectTextInputEditText),
                        isDisplayed()));
        textInputEditText.perform(replaceText("test sujet"), closeSoftKeyboard());
        onView(withId(R.id.salle)).perform(click());
        onData(Matchers.anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(5)
                .perform(ViewActions.click());
        onView(withId(R.id.date_reunion)).perform(doubleClick());
        Calendar calendar = Calendar.getInstance();
        onView(isAssignableFrom(DatePicker.class)).perform(
                PickerActions.setDate(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1,
                        22
                )
        );
        onView(withText("OK")).perform(click());
        onView(withId(R.id.hour)).perform(doubleClick());
        onView(isAssignableFrom(TimePicker.class)).perform(
                PickerActions.setTime(
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE) + 2
                )
        );
        onView(withText("OK")).perform(click());
        onView(withId(R.id.listparticipants)).perform(doubleClick());
        onView(withText("jam.yati@gmail.com")).perform(click());
        onView(withText("yoyo.elti@gmail.com")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.myrecycleview)).check(withItemCount(ITEMS_COUNT+1));

    }
}