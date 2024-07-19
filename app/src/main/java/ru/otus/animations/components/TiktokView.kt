package ru.otus.animations.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
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
                if (it == 0) firstCircleX else secondCircleX,
                (height / 2).toFloat(),
                if (it == 0) firstCircleRadius else secondCircleRadius,
                if (it == 0) violet_circle_color else pink_circle_color
            )
            canvas.translate(dpToPx(SIZE * 2 + DISTANCE).toFloat(), 0f)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 2 * (dpToPx(SIZE) * 2 + dpToPx(DISTANCE)) - dpToPx(DISTANCE)
        val desiredHeight = dpToPx(SIZE) * 2
        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }



    private fun startAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 3000 // Продолжительность анимации в миллисекундах

        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            val radius = dpToPx(SIZE - stroke / 2).toFloat()

            // Перемещение кружков
            val translationX = dpToPx(SIZE * 2 + DISTANCE).toFloat() * value
            firstCircleX = dpToPx(SIZE + stroke).toFloat() + translationX
            secondCircleX = dpToPx(SIZE + stroke).toFloat() - translationX

            // Изменение размера и прозрачности кружков
            firstCircleRadius = radius + (radius * value)
            secondCircleRadius = radius - (radius * value)
            val alpha = (255 * (1 - value)).toInt()

            pink_circle_color.alpha = alpha

            invalidate()
        }

        animator.start()
    }

    companion object {
        private const val DEFAULT_COUNT = 5
        private const val DEFAULT_VALUE = 0
        private const val DEFAULT_STROKE_WIDTH = 1
        private const val DISTANCE = 20
        private const val SIZE = 50
        private const val TAG = "Rating"
        private var firstCircleX = 100f
        private var secondCircleX = 200f
        private var firstCircleRadius = 50f
        private var secondCircleRadius = 50f
    }




}