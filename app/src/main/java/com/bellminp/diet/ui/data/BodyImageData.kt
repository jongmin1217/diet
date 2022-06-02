package com.bellminp.diet.ui.data

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import java.io.Serializable

data class BodyImageData(
    val paging : Boolean,
    val list : ArrayList<DietData>
)

data class BodySlideData(
    val list : ArrayList<FoodData>,
    val timer : Int
) : Serializable