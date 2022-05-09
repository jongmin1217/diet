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
    val food : ArrayList<FoodData>? = null,
    val body : String? = null,
    val weight : Float? = null,
    @ColumnInfo(name = "good_list")
    val goodList : ArrayList<DailyData>? = null,
    @ColumnInfo(name = "bad_list")
    val badList : ArrayList<DailyData>? = null,
    val content : String? = null,
    @ColumnInfo(name = "work_out_list")
    val workOutList : ArrayList<WorkOutData>? = null
) : Serializable{
    fun weightText() : String?{
        weight?.let {
            return String.format("%.1fKg",it)
        }
        return null
    }
}

data class FoodData(
    val id : Long,
    val type : String,
    val image : String
) : Serializable

data class DailyData(
    val id : Long,
    val content : String
) : Serializable

data class WorkOutData(
    val id : Long,
    val type : String,
    val time : String,
    val startRegDate : Long,
    val endRegDate : Long
) : Serializable{
    fun getTimeText() : String{
        val differenceTime = endRegDate - startRegDate
        val min = differenceTime / (1000*60)

        val hourText = if((min/60).toInt() == 0) "" else "${(min/60).toInt()}시간 "
        val minText = if((min%60).toInt()== 0) "" else "${(min%60).toInt()}분 "

        return String.format("%s (%s%s 운동)",time.replace("|"," ~ "),hourText,minText)
    }
}