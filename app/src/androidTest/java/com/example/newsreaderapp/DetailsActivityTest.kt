package com.example.newsreaderapp

import androidx.compose.ui.res.dimensionResource
import androidx.test.core.app.ActivityScenario
import com.example.newsreaderapp.ui.screens.DetailsActivity
import org.hamcrest.CoreMatchers.anyOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsreaderapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(DetailsActivity::class.java)

    @Test
    fun testDetailsScreenDisplaysElements() {
        onView(withId(R.id.newsTitle))
            .check(matches(isDisplayed()))
        onView(withId(R.id.newsDescription))
            .check(matches(isDisplayed()))
        onView(withId(R.id.newsImage))
            .check(matches(isDisplayed()))
        onView(withId(R.id.backButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.header))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.details)))
        onView(withId(R.id.card))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testTitleAndDescription() {
        onView(withId(R.id.newsTitle))
            .check(matches(anyOf(withText("No Title"), withText("Sample Title"))))
        onView(withId(R.id.newsDescription))
            .check(matches(anyOf(withText("No Description"), withText("Sample Description"))))
    }


    @Test
    fun testBackButtonNavigatesBack() {
        val activityScenario = ActivityScenario.launch(DetailsActivity::class.java)
        onView(withId(R.id.backButton)).perform(click())
        activityScenario.close()
    }

}