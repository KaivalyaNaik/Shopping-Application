package com.example.shoppingapplication.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.activities.homeScreens.*
import com.example.shoppingapplication.ui.LoggedInViewModel
import com.example.shoppingapplication.ui.LoggedInViewModelFactory
import com.example.shoppingapplication.util.MyAnalytics
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_drawer.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    val loggedInViewModel:LoggedInViewModel by viewModels {
        LoggedInViewModelFactory((application as ShoppingApplication).firebaseRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)




        val backButton:ImageButton =findViewById(R.id.backButton)
        backButton.visibility= View.GONE

        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val name=loggedInViewModel.getUserLiveData().value?.displayName



        val toggle=ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.nav_app_bar_open_drawer_description  ,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)



        val viewPager:ViewPager =findViewById(R.id.viewPagerHome)
        val adapter=HomePageAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment(application),  "Home")

        adapter.addFragment(MediaPlayer(application),"Media Player")

        adapter.addFragment(VideoView(),"Video Player")

        adapter.addFragment(WebView(),"WebView")


        viewPager.adapter=adapter
        val tabLayout:TabLayout =findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if(!it.isSuccessful){
                Log.w("Notification",it.exception)
            }
            val token=it.result
        })


    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val alertDialog=AlertDialog.Builder(this).setTitle("Logout ?")
            .setMessage("Are you sure you want to Logout").setPositiveButton("YES"
            ) { dialog, which -> loggedInViewModel.logOut()
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()}.setNegativeButton("NO",null).create()
        when(item.itemId){
            R.id.listingPage->{
                val intent=Intent(this,ItemList::class.java)
                startActivity(intent)
            }
            R.id.profile->{
                val intent=Intent(this,ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.logout->{
                Log.d("Navigation","Logout")
                alertDialog.show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}