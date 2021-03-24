package com.example.shoppingapplication.util

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class MyFirebaseMessagingService:FirebaseMessagingService() {

    private val TAG="FirebaseMessaging"
    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if(!it.isSuccessful){
                Log.w("FirebaseMessaging",it.exception)
                return@OnCompleteListener
            }

            val token =it.result

            Toast.makeText(baseContext,"Notification", Toast.LENGTH_LONG,).show()
        })
    }


    override fun onNewToken(token: String) {
        Log.d(TAG,"RefreshedToken:$token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remote: RemoteMessage) {

        if(remote.data.isNotEmpty()){
            Log.d(TAG,"Message :${remote.data}")

                remote.notification?.let {
                    Log.d(TAG,"Message Notification Body:${it.body}")
                    Toast.makeText(baseContext,it.body,Toast.LENGTH_SHORT)
                }

        }
        super.onMessageReceived(remote)
    }
}