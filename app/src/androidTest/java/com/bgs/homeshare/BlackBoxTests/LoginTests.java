package com.bgs.homeshare.BlackBoxTests;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.bgs.homeshare.HomeActivity;
import com.bgs.homeshare.LoginActivity;
import com.bgs.homeshare.MainActivity;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTests {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void LoginMissingFields()
    {
        onView(withId(R.id.UserNameTextBox)).check(matches(isDisplayed()));
        onView(withId(R.id.PasswordTextBox)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(isDisplayed()));
        onView(withId(R.id.UserNameTextBox))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.PasswordTextBox))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button2)).perform(ViewActions.click());

        onView(withText("You must enter a username"))
                .check(matches(isDisplayed()));
        onView(withText("OK"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());

        onView(withId(R.id.UserNameTextBox))
                .perform(ViewActions.typeText("asdf"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button2)).perform(ViewActions.click());

        onView(withText("You must enter a password"))
                .check(matches(isDisplayed()));
        onView(withText("OK"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
                .perform(ViewActions.click());

    }

    @Test
    public void LoginFail()
    {
        boolean loginResult = Login("thisusernamewillfail", "thispasswordwillfail");
        assertFalse(loginResult);
    }

    @Test
    public void LoginSuccess()
    {
        boolean loginResult = Login("hello", "12345");
        assertTrue(loginResult);
    }

    @Test
    public void Logout()
    {
        String username = "hello";
        boolean loginResult = Login(username, "12345");
        assertTrue(loginResult);

        assertEquals(username, UserManager.LoggedInUser.getUserName());

        onView(withId(R.id.navigation_profile))
                .perform(ViewActions.click());

        onView(withText(username))
                .check(matches(isDisplayed()));
        
        //onView(withId(R.id.logOutButtonProfilePage)).perform(ViewActions.scrollTo(), ViewActions.click());

        onView(withId(R.id.logOutButtonProfilePage))
                .perform(ViewActions.click());

        assertEquals(null, UserManager.LoggedInUser);

        intended(hasComponent(MainActivity.class.getName()));
    }

    public static boolean Login(String userName, String password) {
        try {
            onView(withId(R.id.UserNameTextBox)).check(matches(isDisplayed()));
            onView(withId(R.id.PasswordTextBox)).check(matches(isDisplayed()));
            onView(withId(R.id.button2)).check(matches(isDisplayed()));
            onView(withId(R.id.UserNameTextBox))
                    .perform(ViewActions.typeText(userName))
                    .perform(ViewActions.closeSoftKeyboard());
            onView(withId(R.id.PasswordTextBox))
                    .perform(ViewActions.typeText(password))
                    .perform(ViewActions.closeSoftKeyboard());

            boolean loginDone = false;
            while (!loginDone) {
                try {
                    onView(withId(R.id.button2)).perform(ViewActions.click());
                    loginDone = true;
                } catch (Exception e) {
                }
            }

            try { // Will throw an error if the credentials were valid
                onView(withText("Incorrect Username/Password Entered"))
                        .check(matches(isDisplayed()));
                onView(withText("OK"))
                        .inRoot(RootMatchers.isDialog())
                        .check(matches(isDisplayed()))
                        .perform(ViewActions.click());
                return false;
            }
            catch (NoMatchingViewException e) {

            }

            intended(hasComponent(HomeActivity.class.getName()));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
