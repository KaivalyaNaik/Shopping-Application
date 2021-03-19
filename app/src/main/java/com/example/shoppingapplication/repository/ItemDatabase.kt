package com.example.shoppingapplication.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoppingapplication.data.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Item::class),version = 1,exportSchema = false)
abstract class ItemDatabase :RoomDatabase(){
    abstract fun itemDao ():ItemDao

    companion object{
        @Volatile
        private var INSTANCE :ItemDatabase ?=null

        fun getDatabase(context: Context, scope: CoroutineScope):ItemDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "word_database"
                ).addCallback(ItemDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
        }
        }
    }
    private class ItemDatabaseCallback(private val scope: CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                database ->{
                    scope.launch {
                        populateDatabase(database.itemDao())
                    }
            }
            }
        }
        suspend fun populateDatabase(itemDao: ItemDao){
            itemDao.deleteAll()
            val item =Item(1,"Shoes",url = "",quantity = 2)
            val item2 =Item(2,"Laptop",url = "",quantity = 3)

            itemDao.insert(item)
            itemDao.insert(item2)
        }
    }

}