package com.bellminp.diet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bellminp.diet.data.database.dao.DietDataDao
import com.bellminp.diet.domain.model.DietData

@Database(entities = [DietData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DietDatabase : RoomDatabase() {
    abstract fun dietDataDao() : DietDataDao
}