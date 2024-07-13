package ru.otus.animations.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getIntOrThrow
import androidx.core.content.withStyledAttributes
import ru.otus.animations.R

class TiktokView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tiktok_style
) : View(context, attrs, defStyleAttr) {
    private var stroke = DEFAULT_STROKE_WIDTH
    private lateinit var violet_circle_color: Paint
    private lateinit var pink_circle_color: Paint

    init {
        initCircles(attrs, defStyleAttr)
    }

    private fun initCircles(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(attrs, R.styleable.Tiktok_loading_animation, defStyleAttr, R.style.tiktok_loading_animation) {
            // stroke = getDimensionPixelSize(R.styleable.Rating_rating_stroke_width, DEFAULT_STROKE_WIDTH)
            violet_circle_color = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = getColorOrThrow(R.styleable.Tiktok_loading_animation_violet_circle_color)
            }
            pink_circle_color = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = getColorOrThrow(R.styleable.Tiktok_loading_animation_pink_circle_color)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {

        repeat(2) {
            canvas.drawCircle(
                dpToPx(SIZE + stroke).toFloat(),
                (height / 2).toFloat(),
                dpToPx(SIZE - stroke / 2).toFloat(),
                if (it == 1) pink_circle_color else violet_circle_color
            )
            canvas.translate(dpToPx(SIZE * 2 + DISTANCE).toFloat(), 0f)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.i(TAG, "onMeasure")
        Log.i(TAG, "Width.size = ${printMeasureSize(widthMeasureSpec)}")
        Log.i(TAG, "Width.mode = ${printMeasureMode(widthMeasureSpec)}")
        Log.i(TAG, "Height.size = ${printMeasureSize(heightMeasureSpec)}")
        Log.i(TAG, "Height.mode = ${printMeasureMode(heightMeasureSpec)}")

        val desiredWidth = 2 * (dpToPx(SIZE) * 2 + dpToPx(DISTANCE)) - dpToPx(DISTANCE)
        val desiredHeight = dpToPx(SIZE) * 2
        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }
    companion object {
        private const val DEFAULT_COUNT = 5
        private const val DEFAULT_VALUE = 0
        private const val DEFAULT_STROKE_WIDTH = 1
        private const val DISTANCE = 20
        private const val SIZE = 50
        private const val TAG = "Rating"
    }


}