package com.bellminp.diet.data.database

import androidx.room.TypeConverter
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.model.WorkOutData
import com.google.gson.Gson
import java.lang.Exception

class Converters {
    @TypeConverter
    fun foodListToJson(value: ArrayList<FoodData>?): String = Gson().toJson(value)

    @TypeConverter
    fun foodJsonToList(value: String) = try {
        val list = Gson().fromJson(value, Array<FoodData>::class.java).toList()
        ArrayList(list)
    }catch (e : Exception){
        null
    }

    @TypeConverter
    fun dailyListToJson(value: ArrayList<DailyData>?): String = Gson().toJson(value)

    @TypeConverter
    fun dailyJsonToList(value: String) = try {
        val list = Gson().fromJson(value, Array<DailyData>::class.java).toList()
        ArrayList(list)
    }catch (e : Exception){
        null
    }

    @TypeConverter
    fun workOutListToJson(value: ArrayList<WorkOutData>?): String = Gson().toJson(value)

    @TypeConverter
    fun workOutJsonToList(value: String) = try {
        val list = Gson().fromJson(value, Array<WorkOutData>::class.java).toList()
        ArrayList(list)
    }catch (e : Exception){
        null
    }
}