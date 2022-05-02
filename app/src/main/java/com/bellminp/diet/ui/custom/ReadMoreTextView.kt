package com.bellminp.diet.ui.custom

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.doOnLayout
import androidx.core.view.isInvisible
import com.bellminp.diet.R

class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var readMoreMaxLine = DEFAULT_MAX_LINE
    private var readMoreText = ""
    private var readMoreColor = 0

    var state: State = State.COLLAPSED
        private set(value) {
            field = value
            text = collapseText
            changeListener?.onStateChange(value)
        }


    var changeListener: ChangeListener? = null

    private var originalText: CharSequence = ""
    private var collapseText: CharSequence = ""

    init {
        setupAttributes(context, attrs, defStyleAttr)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView, defStyleAttr, 0)

        readMoreMaxLine =
            typedArray.getInt(R.styleable.ReadMoreTextView_readMoreMaxLine, readMoreMaxLine)
        readMoreText =
            typedArray.getString(R.styleable.ReadMoreTextView_readMoreText) ?: readMoreText
        readMoreColor =
            typedArray.getColor(R.styleable.ReadMoreTextView_readMoreColor, readMoreColor)
        typedArray.recycle()
    }


    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        setupReadMore()
    }

    private fun setupReadMore() {
        if (needSkipSetupReadMore()) {
            return
        }
        originalText = text

        val adjustCutCount = getAdjustCutCount(readMoreMaxLine, readMoreText)
        val maxTextIndex = layout.getLineVisibleEnd(readMoreMaxLine - 1)
        val originalSubText = originalText.substring(0, maxTextIndex - 1 - adjustCutCount)

        collapseText = buildSpannedString {
            append(originalSubText)
            color(readMoreColor) { append(readMoreText) }
        }

        text = collapseText

    }

    private fun needSkipSetupReadMore(): Boolean =
        isInvisible || lineCount <= readMoreMaxLine || text == null || text == collapseText

    private fun getAdjustCutCount(maxLine: Int, readMoreText: String): Int {

        val lastLineStartIndex = layout.getLineVisibleEnd(maxLine - 2) + 1
        val lastLineEndIndex = layout.getLineVisibleEnd(maxLine - 1)
        val lastLineText = text.substring(lastLineStartIndex, lastLineEndIndex)

        val bounds = Rect()
        paint.getTextBounds(lastLineText, 0, lastLineText.length, bounds)

        var adjustCutCount = -1
        do {
            adjustCutCount++
            val subText = lastLineText.substring(0, lastLineText.length - adjustCutCount)
            val replacedText = subText + readMoreText
            paint.getTextBounds(replacedText, 0, replacedText.length, bounds)
            val replacedTextWidth = bounds.width()
        } while (replacedTextWidth > width)

        return adjustCutCount
    }

    enum class State {
        COLLAPSED
    }

    interface ChangeListener {
        fun onStateChange(state: State)
    }

    companion object {
        private const val DEFAULT_MAX_LINE = 4
    }

}