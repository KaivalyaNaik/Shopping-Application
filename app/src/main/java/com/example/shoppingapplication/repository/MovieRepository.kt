package com.example.shoppingapplication.repository

import com.example.shoppingapplication.data.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: Flow<List<Movie>> =movieDao.getMovies()

    suspend fun insert(movie: Movie){
        movieDao.insert(movie)
    }
}