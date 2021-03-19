package com.example.shoppingapplication.repository

import androidx.annotation.WorkerThread
import com.example.shoppingapplication.data.Item
import kotlinx.coroutines.flow.Flow

class ItemRepository (private val itemDao: ItemDao){
    val allItems: Flow<List<Item>> =itemDao.getItems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }


    suspend fun delete(item: Item){
        itemDao.delete(item)
    }
}