package com.bellminp.diet.ui.data

import com.bellminp.diet.domain.model.DietData

data class BodyImageData(
    val paging : Boolean,
    val list : ArrayList<DietData>
)