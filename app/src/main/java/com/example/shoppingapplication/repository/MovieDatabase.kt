package com.example.shoppingapplication.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoppingapplication.data.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Movie::class),version = 1,exportSchema = false)
abstract class MovieDatabase :RoomDatabase(){
    abstract fun movieDao():MovieDao

    companion object{
        @Volatile
        private var INSTANCE :MovieDatabase ?=null

        fun getDatabase(context: Context,scope:CoroutineScope):MovieDatabase{
            return INSTANCE ?: synchronized(this){
                var instance =Room.databaseBuilder(context.applicationContext,
                        MovieDatabase::class.java,
                        "Movie_database"
                        ).addCallback(MovieDatabaseCallback(scope)).build()
                INSTANCE =instance
                return instance
            }
        }
    }
    private class MovieDatabaseCallback(private val scope: CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            ItemDatabase.INSTANCE?.let {
                database ->{
                scope.launch {

                }
            }
            }
        }
    }
}