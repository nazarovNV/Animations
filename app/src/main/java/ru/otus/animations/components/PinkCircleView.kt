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

class PinkCircleView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tiktok_style
) : View(context, attrs, defStyleAttr) {
    private lateinit var pink_circle_color: Paint
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
                (width / 2).toFloat(),
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

    fun animatePinkCircle() {
        val xHolder_1 = PropertyValuesHolder.ofFloat("x", 0f, -182.5f, -365f) // Анимация изменения позиции по оси x
        val yHolder_1 = PropertyValuesHolder.ofFloat("y", 0f, -30f, 0f) // Анимация изменения позиции по оси y
        val alphaHolder_1 = PropertyValuesHolder.ofInt("alpha", 255, 0, 255)
        val radiusHolder_1 = PropertyValuesHolder.ofFloat("radius", firstCircleRadius, 120f, firstCircleRadius)
        val Animator_1 = ValueAnimator.ofPropertyValuesHolder(radiusHolder_1, xHolder_1, yHolder_1, alphaHolder_1).apply {
            duration = 1000
            val myInterpolator = MyInterpolator.myInterpolator
            interpolator = myInterpolator
            addUpdateListener {
                firstCircleRadius = it.getAnimatedValue("radius") as Float
                this@PinkCircleView.translationX = it.getAnimatedValue("x") as Float
                this@PinkCircleView.translationY = it.getAnimatedValue("y") as Float
                pink_circle_color.alpha = it.getAnimatedValue("alpha") as Int
                invalidate()
            }
            start()
        }
//        val xHolder_2 = PropertyValuesHolder.ofFloat("x", 0f, -182.5f, -365f, -365f, -182.5f, 0f) // Анимация изменения позиции по оси x
//        val yHolder_2 = PropertyValuesHolder.ofFloat("y", 0f, -30f, 0f, 0f, 0f, 0f) // Анимация изменения позиции по оси y
//        val alphaHolder_2 = PropertyValuesHolder.ofInt("alpha", 255, 0, 255, 255, 255, 255, 255)
//        val radiusHolder_2 = PropertyValuesHolder.ofFloat("radius", firstCircleRadius, 120f, firstCircleRadius,firstCircleRadius,firstCircleRadius+15f,150f)
//        val Animator_2 = ValueAnimator.ofPropertyValuesHolder(radiusHolder_2, xHolder_2, yHolder_2, alphaHolder_2).apply {
//            duration = 3000
//            val myInterpolator = MyInterpolator.myInterpolator
//            interpolator = myInterpolator
//            addUpdateListener {
//                firstCircleRadius = it.getAnimatedValue("radius") as Float
//                this@PinkCircleView.translationX = it.getAnimatedValue("x") as Float
//                this@PinkCircleView.translationY = it.getAnimatedValue("y") as Float
//                pink_circle_color.alpha = it.getAnimatedValue("alpha") as Int
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