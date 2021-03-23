package com.example.shoppingapplication.activities.homeScreens

import android.icu.text.CaseMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.ListFragment

class HomePageAdapter (val fm:FragmentManager): FragmentPagerAdapter(fm) {

    private val mFragment:MutableList<Fragment> = mutableListOf()
    private val mTitle:MutableList<String> = mutableListOf()

    fun addFragment (fragment: Fragment,title:String){
        mFragment.add(fragment)
        mTitle.add(title)
    }

    override fun getCount(): Int {
    return 4
    }

    override fun getItem(position: Int): Fragment {
        return mFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }

}