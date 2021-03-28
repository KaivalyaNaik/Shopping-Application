package com.example.shoppingapplication.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapplication.data.Item
import com.example.shoppingapplication.data.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("Select * from MOVIE_TABLE ORDER BY id asc")
    fun getMovies(): Flow<List<Movie>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)


}