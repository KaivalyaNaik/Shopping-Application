package com.example.shoppingapplication

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.shoppingapplication.repository.FirebaseRepository
import com.example.shoppingapplication.repository.ItemDatabase
import com.example.shoppingapplication.repository.ItemRepository
import com.example.shoppingapplication.util.MyAdMob
import com.example.shoppingapplication.util.MyAnalytics
import com.example.shoppingapplication.util.MyFirebaseMessagingService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ShoppingApplication:Application() {


    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ItemDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { ItemRepository(database.itemDao()) }
    val firebaseRepository by lazy {
        FirebaseRepository(this)
    }
    val myAnalytics by lazy {
        MyAnalytics()
    }
    val myAdMob by lazy {
        MyAdMob(this)
    }
    val myFirebaseMessagingService  by lazy {
        MyFirebaseMessagingService()
    }


}