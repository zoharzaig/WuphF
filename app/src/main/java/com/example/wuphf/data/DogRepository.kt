package com.example.wuphf.data

import com.example.wuphf.data.local.DogDao
import com.example.wuphf.data.local.model.Dog
import com.example.wuphf.data.remote.DogService
import com.example.wuphf.data.remote.model.AllDogs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val dogService: DogService, //This is for API
    private val dogDao: DogDao //This is for database
){

    //From API
    suspend fun getAllDogs() : Response<AllDogs> = withContext(Dispatchers.IO){
        dogService.getAllDogs()
    }

    //From DB
    suspend fun getAllFavourites(): List<Dog> = withContext(Dispatchers.IO){
        dogDao.getAllDogs()
    }

    suspend fun insertIntoFav(dog: Dog) = withContext(Dispatchers.IO){
        dogDao.insertDog(dog)
    }

    suspend fun removeFromFav(dog: Dog) = withContext(Dispatchers.IO){
        dogDao.removeFromFavourite(dog)
    }
}
