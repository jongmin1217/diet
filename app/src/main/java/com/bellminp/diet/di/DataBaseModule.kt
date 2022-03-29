package com.bellminp.diet.di

import android.app.Application
import androidx.room.Room
import com.bellminp.diet.data.database.DietDatabase
import com.bellminp.diet.data.database.dao.DietDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDietDB(application : Application) : DietDatabase{
        return Room.databaseBuilder(application,DietDatabase::class.java,"Diet").build()
    }

    @Provides
    @Singleton
    fun provideDietDataDao(database: DietDatabase) : DietDataDao{
        return database.dietDataDao()
    }
}