package com.bellminp.diet.ui.data

import android.graphics.Color
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.DailyData

data class DialogData(
    val title : String = DietApplication.mInstance.resources.getString(R.string.try_delete),
    val leftText : String = DietApplication.mInstance.resources.getString(R.string.cancel),
    val rightText : String = DietApplication.mInstance.resources.getString(R.string.delete),
    val leftColor : Int = Color.parseColor("#545454"),
    val rightColor : Int = Color.parseColor("#e02020"),
    val deleteData : DeleteDietData? = null
)

data class DeleteDietData(
    val id : Long,
    val type : Int,
    val list : ArrayList<DailyData>? = null,
    val position : Int? = null
)