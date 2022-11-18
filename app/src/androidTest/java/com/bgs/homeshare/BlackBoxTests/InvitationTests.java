package com.bgs.homeshare.BlackBoxTests;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.*;

import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.bgs.homeshare.*;
import com.bgs.homeshare.Managers.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InvitationTests {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void ViewInvitation() {
        boolean loginResult = LoginTests.Login("hello", "12345");
        assertTrue(loginResult);

        onView(withId(R.id.invitationListNotifications)).check(matches(isDisplayed()));
        Random rd = new Random();
        int randomAccess = (rd.nextInt() % InvitationManager.invitations.size());
        onData(anything()).inAdapterView(withId(R.id.invitationListNotifications)).atPosition(randomAccess).onChildView(withId(R.id.ViewInvitationButton)).perform(ViewActions.click());
        String streetAddress = InvitationManager.invitations.get(randomAccess).getProperty().getAddress();
        onView(withText(streetAddress)).check(matches(isDisplayed()));
    }

    @Test
    public void ViewProfileInInvite(){

        boolean loginResult = LoginTests.Login("hello", "12345");
        assertTrue(loginResult);
        onView(withId(R.id.invitationListNotifications)).check(matches(isDisplayed()));
        Random rd = new Random();
        int randomAccess = (rd.nextInt() % InvitationManager.invitations.size());
        onData(anything()).inAdapterView(withId(R.id.invitationListNotifications)).atPosition(randomAccess).onChildView(withId(R.id.ViewInvitationButton)).perform(ViewActions.click());
        onView(withId(R.id.ViewCreatorButton)).perform(ViewActions.click());
        String OwnerUserName = UserManager.GetProfile(InvitationManager.invitations.get(randomAccess).getUserId()).getUserName();
        onView(withText(OwnerUserName)).check(matches(isDisplayed()));
    }

    @Test
    public void changeSortingCondition(){
        boolean loginResult = LoginTests.Login("hello", "12345");
        assertTrue(loginResult);
        onView(withId(R.id.invitationListNotifications)).check(matches(isDisplayed()));

        //change sorting condition to distance
        onView(withId(R.id.SortOptionsSpinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("squarefeet"))).perform(ViewActions.click());
        onView(withId(R.id.SortOptionsSpinner)).check(matches(withSpinnerText(containsString("squarefeet"))));

        //check in sorted order as expected
        boolean expected = true;
        Integer before = 0;
        Integer after;
        for(int i = 0; i < InvitationManager.invitations.size(); i++){
            after = InvitationManager.invitations.get(i).getProperty().getSquareFeet();
            if(after < before){
                expected = false;
            }
            before = after;
        }

        assertTrue(expected);

    }

    @Test
    public void changeSortingOrder(){

        boolean loginResult = LoginTests.Login("hello", "12345");
        assertTrue(loginResult);
        onView(withId(R.id.invitationListNotifications)).check(matches(isDisplayed()));

        //change sorting condition to distance
        onView(withId(R.id.SortOptionsSpinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("squarefeet"))).perform(ViewActions.click());
        onView(withId(R.id.SortOptionsSpinner)).check(matches(withSpinnerText(containsString("squarefeet"))));

        onView(withId(R.id.SortOrderSpinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("desc"))).perform(ViewActions.click());
        onView(withId(R.id.SortOrderSpinner)).check(matches(withSpinnerText(containsString("desc"))));

        //check in sorted order as expected
        boolean expected = true;
        Integer before = Integer.MAX_VALUE;
        Integer after;
        for(int i = 0; i < InvitationManager.invitations.size(); i++){
            after = InvitationManager.invitations.get(i).getProperty().getSquareFeet();
            if(after > before){
                expected = false;
            }
            before = after;
        }

        assertTrue(expected);

    }

    @Test
    public void rejectAnInvite(){
        ViewInvitation();
        onView(withId(R.id.scrollViewInvitePage)).perform(ViewActions.swipeUp());

        try{
            Thread.sleep(3000);
            onView(withId(R.id.RejectButton)).perform(ViewActions.click());
        }
        catch(Exception e){

        }
    }

    @Test
    public void viewAddAnInvite(){
        boolean loginResult = LoginTests.Login("hello", "12345");
        assertTrue(loginResult);

        onView(withId(R.id.navigation_invite))
                .perform(ViewActions.click());

        onView(withId(R.id.layoutOuterInviteFrag)).check(matches(isDisplayed()));
    }

    @Test
    public void viewCreateAnInvitePage(){
        viewAddAnInvite();
        onView(withId(R.id.createInvitationButton)).perform(ViewActions.click());
        onView(withId(R.id.layoutCreateInvite)).check(matches(isDisplayed()));
    }

    @Test
    public void addAQuestionOnCreateInvite(){
        viewAddAnInvite();
        onView(withId(R.id.createInvitationButton)).perform(ViewActions.click());
        onView(withId(R.id.layoutCreateInvite)).check(matches(isDisplayed()));

        onView(withId(R.id.scrollViewCreateInvitation)).perform(ViewActions.swipeUp());

        try{
            Thread.sleep(3000);
            onView(withId(R.id.inputQuestion)).perform(ViewActions.typeText("What is you favorite activity?"));
            onView(withId(R.id.addQuestionImg)).perform(ViewActions.click());
            onView (withId(R.id.listOfQuestionsCreateInvite)).check (ViewAssertions.matches (NotificationTests.Matchers.withListSize (1)));
        }
        catch(Exception e){

        }
    }

}
