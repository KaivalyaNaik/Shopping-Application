package com.example.shoppingapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import com.example.shoppingapplication.R
import com.example.shoppingapplication.ShoppingApplication
import com.example.shoppingapplication.data.User
import com.example.shoppingapplication.ui.LoggedInViewModel
import com.example.shoppingapplication.ui.LoggedInViewModelFactory
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat

class ProfileActivity : AppCompatActivity() {

    val loggedInViewModel:LoggedInViewModel by viewModels {
        LoggedInViewModelFactory((application as ShoppingApplication).firebaseRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name:TextView=findViewById(R.id.profileName)

        val email:TextView=findViewById(R.id.editProfileEmail)

        val company:TextView=findViewById(R.id.editProfileCompany)

        val dob:TextView=findViewById(R.id.editProfileDOB)

        val user: User? = loggedInViewModel.getUser().value

        val transitionName =findViewById<TextView>(R.id.editNameEnd)

        val firebaseUser: FirebaseUser? =loggedInViewModel.getUserLiveData().value
        if(user!=null){
            transitionName.text=user.name
            name.text= user.name
            email.text=firebaseUser?.email.toString()
            company.text=user.company
            val date=SimpleDateFormat("dd/MM/yyyy").format(user.dateOfBirth)
            dob.text=date.toString()
        }



        val adRequest= AdRequest.Builder().build()

        var interstitialAd : InterstitialAd? = null
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712",adRequest,
            object : InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d("AdMob","Failed to Load")
                    interstitialAd = null
                    super.onAdFailedToLoad(p0)
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    Log.d("AdMob","Ad Loaded Successfully!")
                    interstitialAd = p0
                    p0.show(this@ProfileActivity)
                }
            }
        )
        Log.d("Admob",interstitialAd.toString())
        if(interstitialAd!=null) {
            Log.d("AdMob","Show CAlled")
            interstitialAd?.show(this)
        }
        else{
            Log.d("AdMob","Ad isn't ready yet")
        }

        interstitialAd?.fullScreenContentCallback=object : FullScreenContentCallback(){
            override fun onAdDismissedFullScreenContent() {
                Log.d("AdMob","Ad was Dismissed")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("AdMob","Ad showed on FullScreen ")
                interstitialAd=null
            }


            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("AdMob", "Ad failed to show.")
            }
        }





    }
}