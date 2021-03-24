package com.example.shoppingapplication.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.shoppingapplication.data.User
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import java.text.SimpleDateFormat

class FirebaseRepository(val application: Application){

    private lateinit var  firebaseAuth:FirebaseAuth

    private lateinit var userLiveData: MutableLiveData<FirebaseUser>

    private lateinit var loggedOut :MutableLiveData<Boolean>


    val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    private lateinit var firestore: FirebaseFirestore

    private lateinit var user:MutableLiveData<User>



    init {
        firebaseAuth = FirebaseAuth.getInstance()
        this.userLiveData= MutableLiveData(firebaseAuth.currentUser)
        if(userLiveData.value !=null)
            this.loggedOut=MutableLiveData(false)
        else
            this.loggedOut=MutableLiveData(true)

        firestore= FirebaseFirestore.getInstance()
        user= MutableLiveData(User("",SimpleDateFormat("dd-MM-yyyy").parse("20-10-2020"),
                "","")
        )

    }

    fun login(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                userLiveData.postValue(firebaseAuth.currentUser)
                getUser()
            }
            else{
                Toast.makeText(
                    application.applicationContext, "Login Failed! " + (it.exception?.message
                        ?: ""), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun register(email: String, password: String,user: User){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                userLiveData.value=(firebaseAuth.currentUser)

                val profileChangeRequest=UserProfileChangeRequest.Builder().setDisplayName(user.name).build()
            it.result?.user?.updateProfile(profileChangeRequest)

                addUser(user)
                getUser()
           }else{

                Toast.makeText(
                    application.applicationContext, "Registration Failed! " + (it.exception?.message
                        ?: ""), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun logout(){
        firebaseAuth.signOut()
        loggedOut.postValue(true)
        userLiveData.postValue(null)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOut
    }

    private fun addUser(user: User){

        firestore.collection("users").document(firebaseAuth.currentUser.uid).set(user.toMap()).addOnCompleteListener {
            Log.d("Register","User Added Successfully")
        }
    }

    fun getUser():MutableLiveData<User>{
        firestore.collection("users").document(firebaseAuth.currentUser.uid).get()
                .addOnCompleteListener() {

            val map:MutableMap<String,Any>? = it.result?.data
                    Log.d("User Info",map.toString())
            val tuser= map?.let { it1 -> User("",SimpleDateFormat("dd-MM-yyyy").parse("20-10-2020"),
                    "","").fromMap(it1) }
            user.postValue(tuser)

        }
        return user
    }
}