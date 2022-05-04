package com.bellminp.diet.data.data_source.local

import androidx.room.Insert
import androidx.room.Query
import com.bellminp.diet.data.database.dao.DietDataDao
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DietDataLocalDataSource @Inject constructor(
    private val local: DietDataDao
) {
    fun getMonth(year: Int, month: Int): Observable<List<DietData>> {
        return local.getMonth(year, month)
    }

    fun getDay(year: Int, month: Int, day : Int) : Observable<DietData?>{
        return local.getDay(year, month, day)
    }

    fun addDietData(data : DietData) : Single<Long>{
        return local.addDietData(data)
    }

    fun editFood(id : Long, food : ArrayList<FoodData>?): Completable{
        return local.editFood(id,food)
    }

    fun editBody(id : Long, body : String?): Completable{
        return local.editBody(id,body)
    }

    fun editGood(id : Long, good : ArrayList<DailyData>?): Completable{
        return local.editGood(id,good)
    }

    fun editBad(id : Long, bad : ArrayList<DailyData>?): Completable{
        return local.editBad(id,bad)
    }

    fun editWeight(id : Long, weight : Float?): Completable{
        return local.editWeight(id,weight)
    }

    fun editContent(id : Long, content : String?): Completable{
        return local.editContent(id,content)
    }
}