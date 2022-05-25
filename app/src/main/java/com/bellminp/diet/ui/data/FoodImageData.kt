package com.bellminp.diet.ui.data

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import java.io.Serializable

data class FoodImageData(
    val id : Long,
    val list : ArrayList<FoodData>,
    val position : Int
) : Serializable

data class FoodImageListData(
    val paging : Boolean,
    val list : ArrayList<DietData>
)