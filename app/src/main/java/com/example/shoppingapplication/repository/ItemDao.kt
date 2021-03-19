package com.example.shoppingapplication.repository

import androidx.room.*
import com.example.shoppingapplication.data.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("Select * from Item_table ORDER BY id asc")
    fun getItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(model: Item)

    @Query("DELETE from item_table")
    suspend fun deleteAll()
}
