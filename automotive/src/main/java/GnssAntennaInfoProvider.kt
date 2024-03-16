import android.content.Context
import android.content.Intent
import android.location.GnssAntennaInfo
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class GnssAntennaInfoProvider(private val context: Context) {
    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val gnssInfoListener = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        GnssAntennaInfo.Listener { gnssAntennaInfos ->
            for (info in gnssAntennaInfos) {
                val phaseCenterOffset = info.phaseCenterOffset ?: continue
                broadCastGnssAntennaInfo(phaseCenterOffset)
            }
        }
    } else null

    @RequiresApi(Build.VERSION_CODES.R)
    fun startListening() {
        locationManager.registerAntennaInfoListener(
            Executors.newSingleThreadExecutor(),
            gnssInfoListener!!
        )
    }

    fun stopListening () {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            locationManager.unregisterAntennaInfoListener(gnssInfoListener!!)
        }
    }

    private fun broadCastGnssAntennaInfo(offset: GnssAntennaInfo.PhaseCenterOffset) {
        val intent = Intent().apply {
            action = "com.example.broadcast.GNSS_ANTENNA_INFO"
            putExtra("xOffsetMm", offset.xOffsetMm)
            putExtra("yOffsetMm", offset.yOffsetMm)
            putExtra("zOffsetMm", offset.zOffsetMm)
        }
        context.sendBroadcast(intent)
    }
}