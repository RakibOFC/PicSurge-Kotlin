package com.rakibofc.picsurge.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import java.net.URL

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _liveImage = MutableLiveData<Bitmap>()
    val liveImage: LiveData<Bitmap> get() = _liveImage

    init {
        loadData()
    }

    public fun loadData() {

        val imageUrl = "https://loremflickr.com/320/240"

        Thread {

            try {
                val url = URL(imageUrl)
                _liveImage.postValue(
                    BitmapFactory.decodeStream(
                        url.openConnection().getInputStream()
                    )
                )
                Log.e("Error", url.toString())
            } catch (e: IOException) {
                Log.e("Error", e.message.toString())
            }

        }.start()
    }
}