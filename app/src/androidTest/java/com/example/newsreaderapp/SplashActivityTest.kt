package com.example.newsreaderapp

import android.content.Intent
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.auth.LoginActivity
import com.example.newsreaderapp.ui.screens.SplashActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.*



@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun testSplashScreenIsDisplayed() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun testLogoIsDisplayed() {
        onView(withId(R.id.splash_logo)).check(matches(isDisplayed()))
    }


    @Test
    fun testNavigationToLoginScreen() {
        ActivityScenario.launch(SplashActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.btn_login))
            .check(matches(isDisplayed()))
    }




}
