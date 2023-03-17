package com.rijaldev.mygithub.presentation.main

import android.content.pm.ActivityInfo
import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.rijaldev.mygithub.R
import com.rijaldev.mygithub.util.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val validQuery = "dico"
    private val invalidQuery = "#63!_4%%70&"

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun whenSearchUsersSuccess() {
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(typeText(validQuery), pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun whenSearchUsersSuccessHoldDataInConfigurationChanges() {
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(typeText(validQuery), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))

        turnOffInternetConnection()

        activity.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        turnOnInternetConnection()
    }

    @Test
    fun whenSearchUsersEmpty() {
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(typeText(invalidQuery), pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.content_empty)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSearchUsersError() {
        turnOffInternetConnection()

        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(typeText(validQuery), pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.content_error)).check(matches(isDisplayed()))

        turnOnInternetConnection()
    }

    private fun turnOffInternetConnection() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data disable")
    }

    private fun turnOnInternetConnection() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data enable")
    }
}