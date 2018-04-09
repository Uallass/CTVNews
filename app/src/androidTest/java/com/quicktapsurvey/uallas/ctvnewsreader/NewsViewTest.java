package com.quicktapsurvey.uallas.ctvnewsreader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.quicktapsurvey.uallas.ctvnewsreader.news.NewsActivity;
import com.quicktapsurvey.uallas.ctvnewsreader.news.NewsDetailsActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Uallas on 30/03/2018.
 */

@RunWith(AndroidJUnit4.class)
public class NewsViewTest {

    @Rule
    public ActivityTestRule<NewsActivity> myActivityRule = new ActivityTestRule<>(NewsActivity.class);

    @Test
    public void refreshNews() {

        // swipes the list to refresh and verify if the list is populated
        onView(withId(R.id.swiperefresh)).perform(swipeDown());
        onView(withId(R.id.news_list)).check(matches(isDisplayed()));

        RecyclerView newsList = myActivityRule.getActivity().findViewById(R.id.news_list);

        assertThat(newsList.getChildCount(), Matchers.greaterThan(0));
    }

    @Test
    public void openNewsDetails() {

        // click on the list and verify if it went to the details screen
        onView(withId(R.id.news_list)).perform(click());
        onView(withId(R.id.wb_news_details)).check(matches(isDisplayed()));
    }
}
