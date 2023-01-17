package com.example.wuphf.di

import android.app.Application
import androidx.room.Room
import com.example.wuphf.data.local.AppDataBase
import com.example.wuphf.data.local.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDataBase.Callback): AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java, "dogs_db")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideDogsDbDao(db: AppDataBase): DogDao {
        return db.dogDao()
    }
}