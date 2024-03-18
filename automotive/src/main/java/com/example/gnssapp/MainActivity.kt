package com.example.gnssapp

import GNSS.GnssAntennaInfoProvider
import GNSS.GnssInfoReceiver
import GNSS.IGnssInfoProvider
import android.location.GnssAntennaInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var ERROR = "No Gnss Antena info received. Please try again"
    var gnssInfoProvider: IGnssInfoProvider? = null
    private lateinit var gnssInfoReceiver: GnssInfoReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        gnssInfoProvider = GnssAntennaInfoProvider(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            Log.i(TAG, "Button clicked")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && gnssInfoProvider != null) {
                (gnssInfoProvider as GnssAntennaInfoProvider).registerGnssAntennaInfoListener { gnssAntennaInfo ->
                    updateTextField(gnssAntennaInfo)
                }
            }
        }
    }

    private fun updateTextField(gnssAntennaInfo: GnssAntennaInfo?) {
        runOnUiThread {
            val textView = findViewById<TextView>(R.id.gnssInfoTextView)
            if (gnssAntennaInfo != null) {
                Log.i(TAG, gnssAntennaInfo.phaseCenterOffset.toString())
                textView.text =
                    "X Offset: ${gnssAntennaInfo.phaseCenterOffset.xOffsetMm} mm\nY Offset: ${gnssAntennaInfo.phaseCenterOffset.yOffsetMm} mm\nZ Offset: ${gnssAntennaInfo.phaseCenterOffset.zOffsetMm} mm"
            }else {
                Log.e(TAG, ERROR)
                textView.text = ERROR
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gnssInfoProvider!!.unregisterGnssAntennaInfoListener()
    }

}