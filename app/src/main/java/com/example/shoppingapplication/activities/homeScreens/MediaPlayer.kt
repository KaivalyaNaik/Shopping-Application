package com.example.shoppingapplication.activities.homeScreens

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.util.MediaService
import com.example.shoppingapplication.util.MyAnalytics


class MediaPlayer(application: Application) : Fragment() {


    private var myAnalytics: MyAnalytics =(application as ShoppingApplication).myAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_home_2, container, false)

        val play=view.findViewById<Button>(R.id.playButton)
        val stop=view.findViewById<Button>(R.id.stopButton)

        play.setOnClickListener {
            myAnalytics.logRingtone(R.id.playButton,"Play Button")
            activity?.startService(Intent(activity,MediaService::class.java))
        }
        stop.setOnClickListener {
            myAnalytics.logRingtone(R.id.stopButton,"Stop Button")
            activity?.stopService(Intent(activity,MediaService::class.java))
        }

        return view
    }


}