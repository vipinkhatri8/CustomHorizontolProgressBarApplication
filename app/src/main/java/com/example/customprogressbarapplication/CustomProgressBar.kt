package com.example.customprogressbarapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Shader.TileMode


class CustomProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var progress = 0
    private val backgroundColor = Color.parseColor("#055B6E")
    private val progressColorFirst = Color.parseColor("#99C817")
    private val progressColorSecond = Color.parseColor("#FDEB48")
    private val progressColorThird = Color.parseColor("#FED137")
    private val progressColorForth = Color.parseColor("#F7B11E")
    private val progressColorFifth = Color.parseColor("#D72626")
    private val dividerColor = Color.BLACK

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColor
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = dividerColor
        strokeWidth = dpToPx(1) // Adjust as needed
    }

    private val rectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2
        val centerY = height / 2

        // Draw background
        canvas.drawRoundRect(0f, 0f, width, height, dpToPx(10).toFloat(), dpToPx(10).toFloat(), backgroundPaint)

        // Draw progress
        val progressWidth = (progress.toFloat() / 100) * width
        rectF.set(0f, 0f, progressWidth, height)
        val gradient = LinearGradient(
            0f, 0f, width, 0f,
            intArrayOf(progressColorFirst,progressColorSecond, progressColorThird,progressColorForth, progressColorFifth),
            null,
            Shader.TileMode.CLAMP
        )


        progressPaint.shader = gradient
        canvas.drawRoundRect(rectF, dpToPx(10).toFloat(), dpToPx(10).toFloat(), progressPaint)

        // Draw dividers
        val numDividers = 20 // Adjust as needed
        val dividerSpacing = width / numDividers
        for (i in 1 until numDividers) {
            val x = i * dividerSpacing
            canvas.drawLine(x, 0f, x, height, dividerPaint)
        }
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    private fun dpToPx(dp: Int): Float {
        return (dp * resources.displayMetrics.density)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = dpToPx(30).toInt() // Convert 30dp to pixels
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // Match parent or fixed size
            MeasureSpec.AT_MOST -> minOf(desiredHeight, heightSize) // Wrap content
            else -> desiredHeight // Fallback to desired height
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
    }
}
