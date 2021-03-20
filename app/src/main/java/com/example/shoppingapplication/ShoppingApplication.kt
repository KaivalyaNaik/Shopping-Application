package com.example.shoppingapplication

import android.app.Application
import com.example.shoppingapplication.repository.FirebaseRepository
import com.example.shoppingapplication.repository.ItemDatabase
import com.example.shoppingapplication.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ShoppingApplication:Application() {


    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ItemDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { ItemRepository(database.itemDao()) }
    val firebaseRepository by lazy {
        FirebaseRepository(this)
    }

}