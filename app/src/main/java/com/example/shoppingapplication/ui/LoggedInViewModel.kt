package com.example.shoppingapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import java.lang.IllegalArgumentException

class LoggedInViewModel(private val firebaseRepository: FirebaseRepository):ViewModel(){

    private var userLiveData: MutableLiveData<FirebaseUser> = firebaseRepository.getUserLiveData()

    private var loggedOut:MutableLiveData<Boolean> = firebaseRepository.getLoggedOutLiveData()

    fun logOut(){
        firebaseRepository.logout()
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOut
    }
}


class LoggedInViewModelFactory(private  val firebaseRepository: FirebaseRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoggedInViewModel::class.java))
            return LoggedInViewModel(firebaseRepository) as T
        throw IllegalArgumentException("Unknown View Model Class")
    }

}