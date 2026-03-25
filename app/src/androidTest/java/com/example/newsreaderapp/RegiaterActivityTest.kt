



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
import com.example.newsreaderapp.utils.ToastMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegiaterActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegisterActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(RegisterActivity::class.java)

    private lateinit var activity: RegisterActivity

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            activity = it
        }
    }

    @Test
    fun checkToRenderScreenAndCard(){
        onView(withId(R.id.main))
            .check(matches(isDisplayed()))
        onView(withId(R.id.card_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkAllTextViewsAreRenderedCorrectly() {
        onView(withId(R.id.registerTitle))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.register)))

        onView(withId(R.id.tv_login))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.have_account_login)))
        onView(withId(R.id.btn_register))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.register)))
    }

    @Test
    fun testSignUpNavigation() {
        onView(withId(R.id.tv_login)).perform(click())
        intended(hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun checkAllInputFieldsAndButtonsAreDisplayed() {
        onView(withId(R.id.et_email))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.enter_mail)))

        onView(withId(R.id.et_password))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.enter_password)))

        onView(withId(R.id.et_name))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.enter_name)))

        onView(withId(R.id.et_contact))
            .check(matches(isDisplayed()))
            .check(matches(withHint(R.string.enter_contact)))

        onView(withId(R.id.btn_register))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.register)))

        onView(withId(R.id.tv_login))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.have_account_login)))
    }

    @Test
    fun validateInputFields() {
        onView(withId(R.id.et_email))
            .perform(typeText("test@example.com"))
            .check(matches(withText("test@example.com")))

        onView(withId(R.id.et_password))
            .perform(typeText("password123"))
            .check(matches(withText("password123")))
    }

}




