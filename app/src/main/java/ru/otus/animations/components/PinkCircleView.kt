package ru.otus.animations.components

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.withStyledAttributes
import ru.otus.animations.R

class PinkCircleView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tiktok_style
) : View(context, attrs, defStyleAttr) {
    private var stroke = DEFAULT_STROKE_WIDTH
    private lateinit var pink_circle_color: Paint
    private var circleX = (width/2).toFloat()

    init {
        initCircles(attrs, defStyleAttr)
    }

    private fun initCircles(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(attrs, R.styleable.Tiktok_loading_animation, defStyleAttr, R.style.tiktok_loading_animation) {
            pink_circle_color = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = getColorOrThrow(R.styleable.Tiktok_loading_animation_pink_circle_color)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
            canvas.drawCircle(
                circleX,
                (height / 2).toFloat(),
                firstCircleRadius,
                pink_circle_color
            )
            canvas.translate(dpToPx(SIZE * 2).toFloat(), 0f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = dpToPx(SIZE)
        val desiredHeight = dpToPx(SIZE)
        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animatePinkCircle()


    }

    private fun animatePinkCircle() {
        val xHolder = PropertyValuesHolder.ofFloat("x", circleX, circleX - DISTANCE/2, circleX - DISTANCE/2, circleX + DISTANCE/2, circleX + DISTANCE/2) // Анимация изменения позиции по оси x

        val radiusHolder = PropertyValuesHolder.ofFloat("radius", firstCircleRadius, 120f, firstCircleRadius,firstCircleRadius,firstCircleRadius)
        val Animator = ValueAnimator.ofPropertyValuesHolder(radiusHolder, xHolder).apply {
            duration = 2000
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                firstCircleRadius = it.getAnimatedValue("radius") as Float
                circleX = it.getAnimatedValue("x") as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animatePinkCircle() // Запуск новой анимации после завершения текущей
                }
            })
            start()
        }
    }

    companion object {
        private const val DEFAULT_STROKE_WIDTH = 1
        private const val DISTANCE = 100
        private const val SIZE = 150
        private const val TAG = "Rating"
        private var firstCircleX = SIZE * 1f
        private var firstCircleRadius = 150f
        private var secondCircleX = 200f
        private var secondCircleRadius = 50f
    }




}