package com.example.gnssapp

import GnssAntennaInfoProvider
import GnssInfoListener
import GnssInfoReceiver
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity(), GnssInfoListener {
    val TAG = "MainActivity"
    private lateinit var gnssAntennaInfoProvider: GnssAntennaInfoProvider
    private lateinit var gnssInfoReceiver: GnssInfoReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Initialise GnSSAntenInfo provider
        gnssAntennaInfoProvider = GnssAntennaInfoProvider(this)

        //Initialse GnSSInfo receiver
        gnssInfoReceiver = GnssInfoReceiver(this)
        val filter = IntentFilter("com.example.broadcast.GNSS_ANTENNA_INFO")
        registerReceiver(gnssInfoReceiver, filter)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            Log.i(TAG, "Button clicked")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                gnssAntennaInfoProvider.startListening()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        gnssAntennaInfoProvider.stopListening()
    }

    override fun onGnssInfoReceived(xOffsetMm: Float, yOffsetMm: Float, zOffsetMm: Float) {
        runOnUiThread {
            val textView = findViewById<TextView>(R.id.gnssInfoTextView)
            textView.text = "X Offset: $xOffsetMm mm\nY Offset: $yOffsetMm mm\nZ Offset: $zOffsetMm mm"
        }
    }
}