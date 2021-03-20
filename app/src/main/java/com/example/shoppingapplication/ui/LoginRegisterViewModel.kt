package com.example.shoppingapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import java.lang.IllegalArgumentException

class LoginRegisterViewModel(private val firebaseRepository: FirebaseRepository):ViewModel() {

    private val userLiveData:MutableLiveData<FirebaseUser> =firebaseRepository.getUserLiveData()

    fun login(email:String,password:String){
        firebaseRepository.login(email,password)
    }

    fun register(email: String,password: String,name:String){
        firebaseRepository.register(email,password,name)
    }

    fun getUserLiveData():MutableLiveData<FirebaseUser>{
        return userLiveData
    }
}

class LoginRegisterViewModelFactory(private val firebaseRepository: FirebaseRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginRegisterViewModel::class.java))
        {
            return LoginRegisterViewModel(firebaseRepository) as T
        }
        throw IllegalArgumentException("Unkown ViewModel Class")
    }

}