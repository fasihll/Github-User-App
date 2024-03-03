package com.example.githubuserapp.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.githubuserapp.R
import com.example.githubuserapp.core.presentation.MainActivity
import org.junit.Test

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private val dummyUsername = "fasihll"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun DarkModeTesting() {
        onView(withId(R.id.menu1)).check(matches(isDisplayed()))
        onView(withId(R.id.menu1)).perform(click())

        onView(withId(R.id.darkmode_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.swtich_theme)).check(matches(isDisplayed()))
        onView(withId(R.id.swtich_theme)).perform(click())
        onView(withId(R.id.swtich_theme)).perform(click())
        onView(withId(R.id.swtich_theme)).perform(click())
    }

}