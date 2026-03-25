package com.example.newsreaderapp

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.auth.LoginActivity
import com.example.newsreaderapp.ui.auth.RegisterActivity
import com.example.newsreaderapp.ui.screens.HomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {
    private lateinit var scenario: ActivityScenario<HomeActivity>

    @get:Rule
    val intentsTestRule = IntentsTestRule(HomeActivity::class.java)

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(HomeActivity::class.java)
    }

    @After
    fun teardown() {
        scenario.close()
    }

    @Test
    fun testToolbarAndBottomNavigationDisplayed() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.bottomNavigationView)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToDifferentFragments() {
        onView(withId(R.id.nav_business)).perform(click())
        onView(withId(R.id.businessFragmentLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.nav_sports)).perform(click())
        onView(withId(R.id.sportsFragmentLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.nav_saved)).perform(click())
        onView(withId(R.id.savedFragmentLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.nav_tech)).perform(click())
        onView(withId(R.id.techFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun testLogoutFunctionality() {
        onView(withId(R.id.logoutButton)).perform(click())
        intended(hasComponent(LoginActivity::class.java.name))
        //onView(withId(R.id.loginLayout)).check(matches(isDisplayed()))
    }
}
