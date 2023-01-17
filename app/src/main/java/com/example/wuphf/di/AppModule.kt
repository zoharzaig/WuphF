package com.example.wuphf.di

import com.example.wuphf.data.remote.DogService
import com.example.wuphf.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides // comes with @Module annotation
    @Singleton
    fun provideRetrofit(gson: Gson) : Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().create()
    //converting String to json and vice versa

    @Provides
    @Singleton
    fun provideDogService(retrofit: Retrofit): DogService {
        return retrofit.create(DogService::class.java)
    }
}