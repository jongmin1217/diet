package com.bellminp.diet.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.utils.Utils
import java.io.Serializable

@Entity(tableName = "profile_data")
data class ProfileData(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nickname: String,
    val birth: String,
    val height: Float,
    val gender: Int,
    val type: Int,
    val initWeight: Float,
    val goalWeight: Float
) : Serializable {
    fun getAge(): String {
        val year = birth.split(".")[0].toInt()
        val nowYear = Utils.getYear()
        return String.format("%d세", (nowYear - year) + 1)
    }

    fun getGenderText() =
        if (gender == 0) DietApplication.mInstance.resources.getString(R.string.female)
        else DietApplication.mInstance.resources.getString(R.string.male)


    fun getTypeText() =
        if (type == 0) DietApplication.mInstance.resources.getString(R.string.diet_text)
        else DietApplication.mInstance.resources.getString(R.string.bulk_up)


    fun getHeightText() = String.format("%.1fcm", height)

    fun getInitWeightText() = String.format("%.1fkg", initWeight)

    fun getGoalWeightText() = String.format("%.1fkg", goalWeight)

    fun getBmi(nowWeight : Float) : String{
        val heightM = height/100
        val bmi = nowWeight / (heightM * heightM)

        val obesity =  when{
            bmi < 20 -> DietApplication.mInstance.resources.getString(R.string.underweight)
            bmi >=20 && 25 > bmi -> DietApplication.mInstance.resources.getString(R.string.normal_weight)
            bmi >=25 && 30 > bmi -> DietApplication.mInstance.resources.getString(R.string.overweight)
            else -> DietApplication.mInstance.resources.getString(R.string.obesity)
        }

        val infoText =  when{
            bmi < 20 -> DietApplication.mInstance.resources.getString(R.string.underweight_info)
            bmi >=20 && 25 > bmi -> DietApplication.mInstance.resources.getString(R.string.normal_weight_info)
            bmi >=25 && 30 > bmi -> DietApplication.mInstance.resources.getString(R.string.overweight_info)
            else -> DietApplication.mInstance.resources.getString(R.string.obesity_info)
        }

        return String.format("비만도(BMI) : %.1f 으로 %s 입니다.\n\n%s",bmi,obesity,infoText)
    }
}