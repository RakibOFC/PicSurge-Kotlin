package com.rakibofc.picsurge.views

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rakibofc.picsurge.R
import com.rakibofc.picsurge.databinding.ActivityMainBinding
import com.rakibofc.picsurge.receivers.ConnectionReceiver
import com.rakibofc.picsurge.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private var intentFilter: IntentFilter? = null
    private var receiver: ConnectionReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inflates the layout XML file for the main activity and creates an instance of the binding class
        val binding = ActivityMainBinding.inflate(layoutInflater)

        // Initializes the ViewModel for the main activity using the ViewModelProvider
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Set content view
        setContentView(binding.root)

        // Initialize connection status receivers
        intentFilter = IntentFilter()
        receiver = ConnectionReceiver()

        // Add action in intent filter
        intentFilter!!.addAction(CONNECTIVITY_ACTION)

        // Get and set image from view model
        mainViewModel.liveImage.observe(this, binding.imageView::setImageBitmap)

        binding.imageView.setOnClickListener {
            // Click listener to load data from ViewModel
            mainViewModel.loadData()
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    companion object {
        const val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }
}