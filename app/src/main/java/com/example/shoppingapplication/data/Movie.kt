package com.example.shoppingapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "movie_table")
data class Movie (

  @ColumnInfo
  val name:String,

  @PrimaryKey(autoGenerate = true)
  val id:Int=0,

  @ColumnInfo
  val popularity:Float,


)