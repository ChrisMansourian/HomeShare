package com.bgs.homeshare.BlackBoxTests;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.*;

import android.view.*;
import android.widget.*;

import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.bgs.homeshare.*;
import com.bgs.homeshare.Managers.NotificationManager;

import org.hamcrest.TypeSafeMatcher;
import org.junit.*;
import org.hamcrest.Description;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotificationTests {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void NotificationPageAppearing() {
        String username = "hello";
        boolean loginResult = LoginTests.Login(username, "12345");
        assertTrue(loginResult);

        onView(withId(R.id.navigation_notifications))
                .perform(ViewActions.click());

    }

    @Test
    public void NotificationPageProperLength() {
        String username = "hello";
        boolean loginResult = LoginTests.Login(username, "12345");
        assertTrue(loginResult);

        onView(withId(R.id.navigation_notifications))
                .perform(ViewActions.click());

        onView (withId(R.id.notificationListNotifications)).check (ViewAssertions.matches (Matchers.withListSize (NotificationManager.notifications.size())));
    }

    static class Matchers {
        public static TypeSafeMatcher<View> withListSize (final int size) {
            return new TypeSafeMatcher<View>() {
                @Override public boolean matchesSafely (final View view) {
                    return ((ListView) view).getCount () == size;
                }

                @Override public void describeTo (final Description description) {
                    description.appendText("ListView should have " + size + " items");
                }
            };
        }
    }

}
