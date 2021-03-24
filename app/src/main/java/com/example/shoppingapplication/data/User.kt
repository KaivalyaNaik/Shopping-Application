package com.example.shoppingapplication.data

import android.util.Log
import com.google.firebase.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class User(private val uname:String, private val udate:Date, private val ucompany:String, private val uimageURL:String?){
    var  name:String = uname

    var dateOfBirth:Date = udate

    var company:String = ucompany

    var imageURL:String? = uimageURL

    fun toMap():HashMap<String,Any?> {
        return hashMapOf("name" to name,
            "dateOfBirth" to dateOfBirth,
            "company" to company,
            "imageURL" to imageURL)
    }

    fun fromMap(map:Map<String,Any>): User{
        val timestamp:Timestamp= map["dateOfBirth"] as Timestamp

        val date=Date(timestamp.seconds*1000)
        Log.d("Conversion",date.toString())
        val user :User=User(map["name"].toString(),date,map["company"].toString(),map["imageURL"].toString())
        return user
    }

}


