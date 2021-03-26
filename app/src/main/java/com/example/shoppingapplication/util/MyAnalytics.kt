package com.example.shoppingapplication.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MyAnalytics {

    private var firebaseAnalytics:FirebaseAnalytics =Firebase.analytics

    fun logRingtone(id:Int, name:String){
        val bundle=Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"MediaPlayer")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,bundle)
    }
}