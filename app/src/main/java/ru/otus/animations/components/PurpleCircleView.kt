package ru.otus.animations.components

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.withStyledAttributes
import ru.otus.animations.R

class PurpleCircleView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tiktok_style
) : View(context, attrs, defStyleAttr) {
    private lateinit var violet_circle_color: Paint

    init {
        initCircles(attrs, defStyleAttr)
    }

    private fun initCircles(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(attrs, R.styleable.Tiktok_loading_animation, defStyleAttr, R.style.tiktok_loading_animation) {
            violet_circle_color = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = getColorOrThrow(R.styleable.Tiktok_loading_animation_violet_circle_color)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
            canvas.drawCircle(
                (width/2).toFloat(),
                (height / 2).toFloat(),
                firstCircleRadius,
                violet_circle_color
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
    fun animatePurpleCircle() {
        val xHolder_1 = PropertyValuesHolder.ofFloat("x", 0f, 182.5f, 365f) // Анимация изменения позиции по оси x
        val Animator_1 = ValueAnimator.ofPropertyValuesHolder(xHolder_1).apply {
            duration = 1000
            val myInterpolator = MyInterpolator.myInterpolator
            interpolator = myInterpolator
            addUpdateListener {
                this@PurpleCircleView.translationX = it.getAnimatedValue("x") as Float
                invalidate()
            }
            start()
        }
//        val xHolder_2 = PropertyValuesHolder.ofFloat("x", 0f, 182.5f, 365f, 365f, 182.5f, 0f) // Анимация изменения позиции по оси x
//        val Animator_2 = ValueAnimator.ofPropertyValuesHolder(xHolder_2).apply {
//            duration = 3000
//            val myInterpolator = MyInterpolator.myInterpolator
//            interpolator = myInterpolator
//            addUpdateListener {
//                this@PurpleCircleView.translationX = it.getAnimatedValue("x") as Float
//                invalidate()
//            }
//            start()
//        }
    }

    companion object {
        private const val SIZE = 150
        private var firstCircleRadius = 150f
    }




}