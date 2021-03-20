package com.example.shoppingapplication.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class FirebaseRepository(val application: Application){

    private lateinit var  firebaseAuth:FirebaseAuth

    private lateinit var userLiveData: MutableLiveData<FirebaseUser>

    private lateinit var loggedOut :MutableLiveData<Boolean>

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        this.userLiveData= MutableLiveData(firebaseAuth.currentUser)
        if(userLiveData.value !=null)
            this.loggedOut=MutableLiveData(false)
    }

    fun login(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                userLiveData.postValue(firebaseAuth.currentUser)
            }
            else{
                Toast.makeText(
                    application.applicationContext, "Login Failed! " + (it.exception?.message
                        ?: ""), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun register(email: String, password: String,name:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                userLiveData.postValue(firebaseAuth.currentUser)

                val profileChangeRequest=UserProfileChangeRequest.Builder()
                profileChangeRequest.displayName=name
                userLiveData.value?.updateProfile(profileChangeRequest.build())
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
}