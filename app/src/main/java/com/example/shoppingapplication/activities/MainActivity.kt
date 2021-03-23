package com.example.shoppingapplication.activities

import android.app.ActionBar
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.activities.homeScreens.*
import com.example.shoppingapplication.ui.LoggedInViewModel
import com.example.shoppingapplication.ui.LoggedInViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {





    val loggedInViewModel:LoggedInViewModel by viewModels {
        LoggedInViewModelFactory((application as ShoppingApplication).firebaseRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val backButton:ImageButton =findViewById(R.id.backButton)
        backButton.visibility= View.GONE

        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val name=loggedInViewModel.getUserLiveData().value?.email

        val title:TextView=findViewById(R.id.toolBarTitle)
        title.setText("Hello ,"+name)

        val viewPager:ViewPager =findViewById(R.id.viewPagerHome)
        val adapter=HomePageAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment(application),  "Home")

        adapter.addFragment(Home2Fragment(),"Media Player")

        adapter.addFragment(Home3Fragment(),"Video Player")

        adapter.addFragment(WebViewFragment(),"WebView")

        viewPager.adapter=adapter
        val tabLayout:TabLayout =findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val alertDialog=AlertDialog.Builder(this).setTitle("Logout ?")
                    .setMessage("Are you sure you want to Logout").setPositiveButton("YES"
                    ) { dialog, which -> loggedInViewModel.logOut()
                        val intent=Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()}.setNegativeButton("NO",null).create()

            Log.i("On  Options Selected",item.itemId.toString())
            if(item.itemId ==R.id.signOut) {
                alertDialog.show()
            }
            return super.onContextItemSelected(item)
        }
    
}