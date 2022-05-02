package com.bellminp.diet.ui.data

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.ui.adapter.CalendarAdapter
import com.bellminp.diet.utils.Utils
import java.io.Serializable

data class CalendarData(
    val id : Int,
    val visible : Boolean,
    val year : Int,
    val month : Int,
    val text : String?,
    val dietData: DietData?
) : Serializable

data class DateData(
    val year : Int,
    val month : Int,
    val day : Int
) : Serializable