package com.bellminp.diet.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bellminp.diet.utils.Utils
import java.io.Serializable

@Entity(tableName = "profile_data")
data class ProfileData(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val nickname : String,
    val birth : String,
    val height : Float,
    val gender : Int,
    val type : Int,
    val initWeight : Float,
    val goalWeight : Float
) : Serializable{
    fun getAge() : Int{
        val year = birth.split(".")[0].toInt()
        val nowYear = Utils.getYear()
        return (nowYear - year) + 1
    }
}