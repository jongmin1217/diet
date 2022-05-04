package com.bellminp.diet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface DietDataDao {
    @Query("SELECT * FROM diet_data WHERE year = :year AND month = :month ORDER BY day DESC")
    fun getMonth(year : Int, month : Int): Observable<List<DietData>>

    @Query("SELECT * FROM diet_data WHERE year = :year AND month = :month AND day = :day")
    fun getDay(year : Int, month : Int, day : Int): Observable<DietData?>

    @Insert
    fun addDietData(data : DietData) : Single<Long>

    @Query("UPDATE diet_data SET food = :food WHERE id = :id")
    fun editFood(id : Long, food : ArrayList<FoodData>?): Completable

    @Query("UPDATE diet_data SET body = :body WHERE id = :id")
    fun editBody(id : Long, body : String?): Completable

    @Query("UPDATE diet_data SET good_list = :good WHERE id = :id")
    fun editGood(id : Long, good : ArrayList<DailyData>?): Completable

    @Query("UPDATE diet_data SET bad_list = :bad WHERE id = :id")
    fun editBad(id : Long, bad : ArrayList<DailyData>?): Completable

    @Query("UPDATE diet_data SET weight = :weight WHERE id = :id")
    fun editWeight(id : Long, weight : Float?): Completable

    @Query("UPDATE diet_data SET content = :content WHERE id = :id")
    fun editContent(id : Long, content : String?): Completable
}