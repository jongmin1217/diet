package com.bellminp.diet.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bellminp.diet.R
import com.github.chrisbanes.photoview.PhotoView

class RadiusPhotoView : PhotoView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val height = measuredHeight
        background = resources.getDrawable(R.drawable.shape_8, null)
        clipToOutline = true
        setMeasuredDimension(width, height)
    }
}