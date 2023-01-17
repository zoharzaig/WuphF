package com.example.wuphf.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wuphf.data.local.model.Dog
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Dog::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun dogDao() : DogDao

    class Callback @Inject constructor(
        private val database: Provider<AppDataBase>
    ) : RoomDatabase.Callback()

}