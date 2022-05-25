package com.bellminp.diet.domain.repository

import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.model.WorkOutData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DietDataRepository {
    fun getMonth(year : Int, month : Int): Observable<List<DietData>>
    fun getDay(year : Int, month : Int, day : Int): Observable<DietData?>
    fun addDietData(data : DietData) : Single<Long>
    fun editFood(id : Long, food : ArrayList<FoodData>?): Completable
    fun editBody(id : Long, body : String?): Completable
    fun editGood(id : Long, good : ArrayList<DailyData>?): Completable
    fun editBad(id : Long, bad : ArrayList<DailyData>?): Completable
    fun editWeight(id : Long, weight : Float?): Completable
    fun editContent(id : Long, content : String?): Completable
    fun editWorkOut(id : Long, workOut : ArrayList<WorkOutData>?): Completable
    fun getLastWeight(): Observable<List<Float>>
    fun getMonthWeight(year : Int, month : Int): Observable<List<Float>>
    fun getFoodImage(regDate : Long): Single<List<DietData>>
    fun getAllFoodImage(): Single<List<DietData>>
    fun getBodyImage(regDate : Long): Single<List<DietData>>
    fun getAllBodyImage(): Single<List<DietData>>
    fun getBodyImageRange(startDate : Long, endDate : Long): Single<List<DietData>>
}