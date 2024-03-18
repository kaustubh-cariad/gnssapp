package com.example.gnssapp

import GNSS.GnssAntennaInfoProvider
import GNSS.IGnssInfoProvider
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.gnssapp", appContext.packageName)
    }
    @Test
    fun checkButtonIsPresent() {
        val mockGnssInfoProvider: IGnssInfoProvider = mock(GnssAntennaInfoProvider::class.java)
        onView(withId(R.id.button))
            .check(matches(isDisplayed()))

        Mockito.`when`(mockGnssInfoProvider.registerGnssAntennaInfoListener { Mockito.any() })
            .then { invocation ->
                val callback = invocation.arguments[0] as (String) -> Unit
                callback("Mock GNSS Info")
            }
        activityRule.scenario.onActivity { activity ->
            // Inject the mock provider into your MainActivity
            activity.gnssInfoProvider = mockGnssInfoProvider
        }
        onView(withId(R.id.gnssInfoTextView)).perform(ViewActions.click());

        verify(mockGnssInfoProvider).registerGnssAntennaInfoListener(any())
        val resultingText =
            (Espresso.onView(ViewMatchers.withId(R.id.gnssInfoTextView)) as TextView).text
        TestCase.assertEquals("Mock GNSS Info", resultingText);
    }
}