import android.content.Context
import android.provider.ContactsContract.Intents
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.gnssapp.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GnssAntennaInfoProviderTest {

    @get: Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity:: class.java)
    private lateinit var gnssAntennaInfoProvider: GnssAntennaInfoProvider
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
    }

}