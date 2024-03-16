import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


interface GnssInfoListener {
    fun onGnssInfoReceived(xOffsetMm: Float, yOffsetMm: Float, zOffsetMm: Float)
}
class GnssInfoReceiver(private val listener: GnssInfoListener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.broadcast.GNSS_ANTENNA_INFO") {
            val xOffsetMm = intent.getFloatExtra("xOffsetMm", 0f)
            val yOffsetMm = intent.getFloatExtra("yOffsetMm", 0f)
            val zOffsetMm = intent.getFloatExtra("zOffsetMm", 0f)
            listener.onGnssInfoReceived(xOffsetMm, yOffsetMm, zOffsetMm)
        }
    }
}