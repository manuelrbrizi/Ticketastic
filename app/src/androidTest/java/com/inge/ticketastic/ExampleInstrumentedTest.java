package com.inge.ticketastic;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.util.Log;
import android.widget.AdapterView;

import androidx.test.rule.ActivityTestRule;

import com.inge.ticketastic.activities.LoginActivity;
import com.inge.ticketastic.activities.RegisterActivity;
import com.inge.ticketastic.activities.TabbedActivity;
import com.inge.ticketastic.utils.PreferenceUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    //Test para el refactoreo de packages
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.inge.ticketastic", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
    @Rule
    public ActivityTestRule<RegisterActivity> registerActivity = new ActivityTestRule<>(RegisterActivity.class);
    @Rule
    public ActivityTestRule<TabbedActivity> tabbedActivity = new ActivityTestRule<>(TabbedActivity.class);

    public IntentsTestRule<TabbedActivity> tabbed = new IntentsTestRule<>(TabbedActivity.class, true, false);


    //Test de login
    @Test
    public void testLogin(){
        onView(withId(R.id.emailLI)).perform(typeText("fede.bergagna@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.passLI)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

    }

    @Test
    public void testFailLogin(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Check username and password!")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }


    @Test
    public void testSignUp() {
        onView(withId(R.id.deleteDB));

        onView(withId(R.id.signupButton)).perform(click());

        onView(withId(R.id.sendButton)).perform(click());
        onView(withText("Please fill all the fields")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

        onView(withId(R.id.emailSU)).perform(typeText("fede.bergagna@gmail.com"));
        onView(withId(R.id.passSU)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.confirmSU)).perform(typeText("123"),closeSoftKeyboard());
        onView(withId(R.id.sendButton)).perform(click());

        onView(withText("Given passwords don't match!")).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

        onView(withId(R.id.confirmSU)).perform(clearText(),typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.sendButton)).perform(click());

        onView(withId(R.id.tabs)).check(matches(isDisplayed()));

    }


}
