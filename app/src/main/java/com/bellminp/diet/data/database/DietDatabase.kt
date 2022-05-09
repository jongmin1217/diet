package com.bellminp.diet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bellminp.diet.data.database.dao.DietDataDao
import com.bellminp.diet.data.database.dao.ProfileDao
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.ProfileData

@Database(entities = [DietData::class, ProfileData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDataDao() : DietDataDao
    abstract fun profileDao() : ProfileDao
}