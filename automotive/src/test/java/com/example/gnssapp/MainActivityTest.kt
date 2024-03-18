package com.example.gnssapp

import GNSS.GnssAntennaInfoProvider
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun gnssInfoDisplaysCorrectlyOnButtonClick() {
        // Mock the GnssInfoProvider

        val mockGnssInfoProvider = mock(GnssAntennaInfoProvider::class.java)
        `when`(mockGnssInfoProvider.registerGnssAntennaInfoListener { any() }).then{ invocation ->
            val callback = invocation.arguments[0] as (String) -> Unit
            callback("Mock GNSS Info")
        }

        // Launch MainActivity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            // Inject the mock provider into your MainActivity
            activity.gnssInfoProvider = mockGnssInfoProvider
        }

        // Perform a click on the button
        onView(withId(R.id.gnssInfoTextView)).perform(click());

        // Assert that the TextView displays the mocked GNSS info
        val resultingText = (onView(withId(R.id.gnssInfoTextView)) as TextView).text
        assertEquals("Mock GNSS Info", resultingText);

    }
}
