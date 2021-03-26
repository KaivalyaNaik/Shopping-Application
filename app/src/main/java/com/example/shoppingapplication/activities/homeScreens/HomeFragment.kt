package com.example.shoppingapplication.activities.homeScreens

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.activities.ItemList
import com.example.shoppingapplication.ui.LoggedInViewModel
import com.example.shoppingapplication.ui.LoggedInViewModelFactory
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.lang.RuntimeException


class HomeFragment(val application: Application): Fragment() {

    private val loggedInViewModel: LoggedInViewModel by viewModels {
        LoggedInViewModelFactory((application as ShoppingApplication).firebaseRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_home, container, false)
        val button=view.findViewById<Button>(R.id.nextPage)

        button.text="Crash!!"
        button.setOnClickListener {
           throw RuntimeException("Test Crash!")
        }

        val root=view
        var dX:Float=0F
        var dY:Float=0F
        root.setOnDragListener { v, event ->

            when(event.action){
                DragEvent.ACTION_DRAG_LOCATION->{
                    dX=event.x
                    dY=event.y
                }
                DragEvent.ACTION_DRAG_ENDED->{
                    button.x=(dX-button.width/2)
                    button.y=dY-button.width/2
                }
            }
            true
        }

        button.setOnLongClickListener {
            val myShadow= View.DragShadowBuilder(button)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                it.startDragAndDrop(null,myShadow,null,View.DRAG_FLAG_GLOBAL)
            else
                it.startDrag(null,myShadow,null,View.DRAG_FLAG_GLOBAL)

            true
        }

        val adView =view.findViewById<AdView>(R.id.adView)
        val adRequest=AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener=object :AdListener(){
            override fun onAdOpened() {
                Log.d("AdMob","Ad Opened")
                super.onAdOpened()
            }

            override fun onAdClicked() {
                Log.d("AdMob","Ad Clicked")
                super.onAdClicked()
            }
        }


        return view
    }

}