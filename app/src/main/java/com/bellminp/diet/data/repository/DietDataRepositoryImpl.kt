package com.bellminp.diet.data.repository

import com.bellminp.diet.data.data_source.local.DietDataLocalDataSource
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.repository.DietDataRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DietDataRepositoryImpl @Inject constructor(
    private val dataSource: DietDataLocalDataSource
) : DietDataRepository{
    override fun getMonth(year: Int, month: Int): Observable<List<DietData>> {
        return dataSource.getMonth(year, month)
    }

    override fun getDay(year: Int, month: Int, day: Int): Observable<DietData?> {
        return dataSource.getDay(year, month, day)
    }

    override fun addDietData(data : DietData) : Single<Long>{
        return dataSource.addDietData(data)
    }

    override fun editFood(id : Long, food : ArrayList<FoodData>?): Completable{
        return dataSource.editFood(id,food)
    }

    override fun editBody(id : Long, body : String?): Completable{
        return dataSource.editBody(id,body)
    }

    override fun editGood(id : Long, good : ArrayList<DailyData>?): Completable{
        return dataSource.editGood(id,good)
    }

    override fun editBad(id : Long, bad : ArrayList<DailyData>?): Completable{
        return dataSource.editBad(id,bad)
    }

    override fun editWeight(id : Long, weight : Float?): Completable{
        return dataSource.editWeight(id,weight)
    }

    override fun editContent(id : Long, content : String?): Completable{
        return dataSource.editContent(id,content)
    }

}