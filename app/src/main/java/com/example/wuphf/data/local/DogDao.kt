package com.example.wuphf.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wuphf.data.local.model.Dog
import retrofit2.Response

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs")
    fun getAllDogs() :  List<Dog>

    @Query("SELECT * FROM dogs WHERE message = :message")
    fun getDog(message : String) : Dog

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog : Dog): Long

    @Delete
    suspend fun removeFromFavourite(dog: Dog)
}