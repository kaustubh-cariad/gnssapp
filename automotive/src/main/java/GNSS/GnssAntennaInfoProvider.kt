package GNSS
import android.content.Context
import android.location.GnssAntennaInfo
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.Executors


interface IGnssInfoProvider {
    fun registerGnssAntennaInfoListener(callback: (GnssAntennaInfo?) -> Unit)
    fun unregisterGnssAntennaInfoListener()
}


class GnssAntennaInfoProvider(private val context: Context): IGnssInfoProvider {
    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var callback: ((GnssAntennaInfo?) -> Unit)? = null

    private val gnssAntennaInfoListener = GnssAntennaInfo.Listener { antennaInfos ->
        val info = antennaInfos.firstOrNull()
        callback?.invoke(info)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun registerGnssAntennaInfoListener(callback: (GnssAntennaInfo?) -> Unit) {
        this.callback = callback
        locationManager.registerAntennaInfoListener(
            Executors.newSingleThreadExecutor(),
            gnssAntennaInfoListener
        )
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun unregisterGnssAntennaInfoListener() {
        locationManager.unregisterAntennaInfoListener(gnssAntennaInfoListener)
        this.callback = null
    }
}