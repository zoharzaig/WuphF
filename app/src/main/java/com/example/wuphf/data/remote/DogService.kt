package com.example.wuphf.data.remote

import com.example.wuphf.data.local.model.Dog
import com.example.wuphf.data.remote.model.AllDogs
import com.example.wuphf.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DogService {

    @GET(Constants.BASE_URL)
    suspend fun getAllDogs() : Response<AllDogs>

    @GET(Constants.BASE_URL)
    suspend fun getDog(message : String) : Response<Dog>
}