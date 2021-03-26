package com.example.shoppingapplication.util

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener

class MyAdMob(application: Application) {
    init {
        MobileAds.initialize(application, OnInitializationCompleteListener {
            Log.d("Admob",it.adapterStatusMap.toString())
        })
    }
}