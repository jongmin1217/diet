package com.bellminp.diet.data.database

import androidx.room.TypeConverter
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.FoodData
import com.google.gson.Gson
import java.lang.Exception

class Converters {
    @TypeConverter
    fun foodListToJson(value: ArrayList<FoodData>?): String = Gson().toJson(value)

    @TypeConverter
    fun foodJsonToList(value: String) = try {
        val list = Gson().fromJson(value, Array<FoodData>::class.java).toList()
        val arrayList = ArrayList<FoodData>()
        for(i in list){
            arrayList.add(i)
        }
        arrayList
    }catch (e : Exception){
        null
    }

    @TypeConverter
    fun dailyListToJson(value: ArrayList<DailyData>?): String = Gson().toJson(value)

    @TypeConverter
    fun dailyJsonToList(value: String) = try {
        val list = Gson().fromJson(value, Array<DailyData>::class.java).toList()
        val arrayList = ArrayList<DailyData>()
        for(i in list){
            arrayList.add(i)
        }
        arrayList
    }catch (e : Exception){
        null
    }
}