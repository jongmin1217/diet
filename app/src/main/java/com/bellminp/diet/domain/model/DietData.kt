package com.bellminp.diet.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "diet_data")
data class DietData(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val year : Int,
    val month : Int,
    val day : Int,
    @ColumnInfo(name = "food")
    val food : ArrayList<FoodData>?,
    val body : String?,
    val weight : Float?,
    @ColumnInfo(name = "good_list")
    val goodList : ArrayList<DailyData>?,
    @ColumnInfo(name = "bad_list")
    val badList : ArrayList<DailyData>?,
    val content : String?
)

data class FoodData(
    val id : Long,
    val type : String,
    val image : String
) : Serializable

data class DailyData(
    val id : Long,
    val content : String
) : Serializable