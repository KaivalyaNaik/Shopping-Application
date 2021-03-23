package com.example.shoppingapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppingapplication.util.Date.Companion.getCurrentSystemTime

@Entity(tableName = "Item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id:Long =0L,

    @ColumnInfo val name:String,

    @ColumnInfo val url:String?,

    @ColumnInfo val quantity:Int,

    @ColumnInfo
    var dateItemCreated: String = getCurrentSystemTime(System.currentTimeMillis())


)

