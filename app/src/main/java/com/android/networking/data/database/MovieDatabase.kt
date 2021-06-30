package com.android.networking.data.database

import android.content.Context
import androidx.room.*
import com.android.networking.data.dao.MovieDetailDao
import com.android.networking.data.dao.MovieListDao
import com.android.networking.data.model.Movie
import com.android.networking.data.typeConverter.DbTypeConverters


@TypeConverters(DbTypeConverters::class)
@Database(entities = [Movie :: class], version = 3)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieListDao() : MovieListDao
    abstract fun getMovieDetailDao() : MovieDetailDao

    //creating singleton pattern
    companion object{

        private var instance : MovieDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(this){

            Room.databaseBuilder(
                context.applicationContext, MovieDatabase :: class.java, "MovieDatabase"
            ).fallbackToDestructiveMigration()
                .build()
                .also {
                    instance = it
                }
            }


        }
    }
