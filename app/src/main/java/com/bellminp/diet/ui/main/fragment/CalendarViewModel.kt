package com.bellminp.diet.ui.main.fragment

import androidx.lifecycle.LiveData
import com.bellminp.diet.ui.adapter.CalendarAdapter
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.ViewPagerCalendar
import com.bellminp.diet.utils.SingleLiveEvent
import com.bellminp.diet.utils.Utils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class CalendarViewModel : BaseViewModel()  {

    private val _initScroll = SingleLiveEvent<Unit>()
    val initScroll : LiveData<Unit> get() = _initScroll

    private val _initCalendar = SingleLiveEvent<ArrayList<ViewPagerCalendar>>()
    val initCalendar : LiveData<ArrayList<ViewPagerCalendar>> get() = _initCalendar

    private val _beforeAddCalendar = SingleLiveEvent<ViewPagerCalendar>()
    val beforeAddCalendar : LiveData<ViewPagerCalendar> get() = _beforeAddCalendar

    private val _afterAddCalendar = SingleLiveEvent<ViewPagerCalendar>()
    val afterAddCalendar : LiveData<ViewPagerCalendar> get() = _afterAddCalendar

    fun initScroll(){
        _initScroll.value = Unit
    }

    @DelicateCoroutinesApi
    fun initCalendar(year : Int, month : Int, type : Int){

        GlobalScope.launch(Dispatchers.Default){
            val list = ArrayList<ViewPagerCalendar>()

            if(type == 0){
                val yearList = arrayListOf(Utils.beforeYear(year,month),year,Utils.afterYear(year, month))
                val monthList = arrayListOf(Utils.beforeMonth(month),month,Utils.afterMonth(month))

                for(i in 0..2){
                    if(yearList[i] == Utils.getYear() && monthList[i] > Utils.getMonth()) break

                    initCalendarItems(
                        Utils.lastDay(yearList[i], monthList[i]),
                        Utils.getDateDay(String.format("%d%s%s", yearList[i], Utils.dateText(monthList[i]), "01"), "yyyyMMdd")
                    ).apply {
                        list.add(ViewPagerCalendar(yearList[i],monthList[i],this,CalendarAdapter(this@CalendarViewModel)))
                    }
                }
                GlobalScope.launch(Dispatchers.Main) { _initCalendar.value = list }
            }else{
                if(year == Utils.getYear() && month > Utils.getMonth()) return@launch

                initCalendarItems(
                    Utils.lastDay(year, month),
                    Utils.getDateDay(String.format("%d%s%s", year, Utils.dateText(month), "01"), "yyyyMMdd")
                ).apply {
                    GlobalScope.launch(Dispatchers.Main) {
                        if(type == 1) _beforeAddCalendar.value = ViewPagerCalendar(year,month,this@apply,CalendarAdapter(this@CalendarViewModel))
                        else _afterAddCalendar.value = ViewPagerCalendar(year,month,this@apply,CalendarAdapter(this@CalendarViewModel))
                    }
                }
            }
        }
    }

    private fun initCalendarItems(lastDay: Int, startDay: Int) : ArrayList<CalendarData> {
        val list = ArrayList<CalendarData>()

        if (startDay != 1) {
            for (i in 0 until startDay - 1) {
                list.add(CalendarData(i, false, null, null))
            }
        }

        for (i in 0 until lastDay) {
            list.add(
                CalendarData(
                    if (list.isEmpty()) 0 else list[list.size - 1].id + 1,
                    true,
                    (i + 1).toString(),
                    null
                )
            )
        }

        return list
    }
}