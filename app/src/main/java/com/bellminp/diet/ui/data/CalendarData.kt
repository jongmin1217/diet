package com.bellminp.diet.ui.data

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.ui.adapter.CalendarAdapter
import com.bellminp.diet.utils.Utils

data class CalendarData(
    val id : Int,
    val visible : Boolean,
    val text : String?,
    val dietData: DietData?
)

data class ViewPagerCalendar(
    val year : Int,
    val month : Int,
    val data : ArrayList<CalendarData>,
    val adapter : CalendarAdapter
){
    fun getId() : Long{
        return String.format("%d%s",year, Utils.dateText(month)).toLong()
    }
}