package com.example.newsreaderapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsreaderapp.ui.auth.LoginActivity
import com.example.newsreaderapp.ui.auth.RegisterActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(LoginActivity::class.java)

    private lateinit var activity: LoginActivity

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            activity = it
        }
    }

    @Test
    fun checkAllTextViewsAreRenderedCorrectly() {
        onView(withId(R.id.loginTitle))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.login)))

        onView(withId(R.id.tv_signup))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.don_have_account_signup)))
    }

    @Test
    fun testSignUpNavigation() {
        onView(withId(R.id.tv_signup)).perform(click())
        intended(hasComponent(RegisterActivity::class.java.name))
    }

    @Test
    fun checkAllInputFieldsAndButtonsAreDisplayed() {
        onView(withId(R.id.et_email))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.enter_mail)))

        onView(withId(R.id.et_password))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.enter_password)))

        onView(withId(R.id.btn_login))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.login)))

        onView(withId(R.id.tv_signup))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.don_have_account_signup)))
    }


    @Test
    fun validateEmailAndPasswordInputFields() {
        onView(withId(R.id.et_email))
            .perform(typeText("test@example.com"))
            .check(matches(withText("test@example.com")))

        onView(withId(R.id.et_password))
            .perform(typeText("password123"))
            .check(matches(withText("password123")))
    }


}
