package com.bellminp.diet.domain.model

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bellminp.diet.utils.Utils
import java.io.Serializable
import java.text.SimpleDateFormat

@Entity(tableName = "diet_data")
data class DietData(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val year : Int,
    val month : Int,
    val day : Int,
    @ColumnInfo(name = "food_list")
    val food : ArrayList<FoodData>? = null,
    val body : String? = null,
    val weight : Float? = null,
    @ColumnInfo(name = "good_list")
    val goodList : ArrayList<DailyData>? = null,
    @ColumnInfo(name = "bad_list")
    val badList : ArrayList<DailyData>? = null,
    val content : String? = null,
    @ColumnInfo(name = "work_out_list")
    val workOutList : ArrayList<WorkOutData>? = null,
    var regDate : Long? = null,
    var foodHave : Boolean = false
) : Serializable{
    fun weightText() : String?{
        weight?.let {
            return String.format("%.1fKg",it)
        }
        return null
    }

    fun totalWorkOutTime() : Int?{
        workOutList?.let {
            var time = 0
            for(i in it){
                time += i.getWorkOutMin()
            }
            return time
        }
        return null
    }

    @SuppressLint("SimpleDateFormat")
    fun settingDate(){
        val dateFormat = SimpleDateFormat("yyyy:MM:dd")
        val date = dateFormat.parse("${year}:${Utils.dateText(month)}:${Utils.dateText(day)}")
        regDate = date!!.time
    }

    fun foodCheck(){
        foodHave = food != null
    }

    fun getDateText() = String.format("%d.%s.%s",year,Utils.dateText(month),Utils.dateText(day))
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

    fun getWorkOutMin() : Int{
        val differenceTime = endRegDate - startRegDate
        return (differenceTime / (1000*60)).toInt()
    }
}