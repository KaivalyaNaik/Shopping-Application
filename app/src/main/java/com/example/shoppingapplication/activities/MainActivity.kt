package com.example.shoppingapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.example.shoppingapplication.R
import com.example.shoppingapplication.activities.homeScreens.Home2Fragment
import com.example.shoppingapplication.activities.homeScreens.Home3Fragment
import com.example.shoppingapplication.activities.homeScreens.HomeFragment
import com.example.shoppingapplication.activities.homeScreens.HomePageAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backButton:ImageButton =findViewById(R.id.backButton)
        backButton.visibility= View.GONE

        val viewPager:ViewPager =findViewById(R.id.viewPagerHome)
        val adapter=HomePageAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment(application),"Home")

        adapter.addFragment(Home2Fragment(),"View 2")

        adapter.addFragment(Home3Fragment(),"View 3")
        viewPager.adapter=adapter
        val tabLayout:TabLayout =findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

    }




}