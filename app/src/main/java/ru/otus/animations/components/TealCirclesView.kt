package ru.otus.animations.components

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.os.Handler
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.withStyledAttributes
import ru.otus.animations.R

data class Ripple(var radius: Float, var alpha: Int)

class TealCirclesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tiktok_style
) : View(context, attrs, defStyleAttr) {

    private lateinit var tealCircleColor: Paint
    private val ripples = mutableListOf<Ripple>()
    private val handler = Handler()
    private var currentRippleRadius = 0f

    init {
        initCircles(attrs, defStyleAttr)
        animateTealCircle()
    }

    private fun initCircles(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(
            attrs,
            R.styleable.Tiktok_loading_animation,
            defStyleAttr,
            R.style.tiktok_loading_animation
        ) {
            tealCircleColor = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = getColorOrThrow(R.styleable.Tiktok_loading_animation_teal_circle_color)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        ripples.forEach { ripple ->
            tealCircleColor.alpha = ripple.alpha
            canvas.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                ripple.radius,
                tealCircleColor
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = dpToPx(SIZE)
        val desiredHeight = dpToPx(SIZE)
        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    companion object {
        private const val SIZE = 300
        private var firstCircleRadius = 60f
    }

    fun animateTealCircle() {
        // Добавляем первый круг в список
        ripples.add(Ripple(firstCircleRadius, 255))

        // Запускаем анимацию для каждого круга
        animateRipple(ripples.last())

        // Запускаем Handler для добавления нового круга каждую секунду
        handler.postDelayed(object : Runnable {
            override fun run() {
                val newRipple = Ripple(firstCircleRadius, 255)
                ripples.add(newRipple)
                animateRipple(newRipple)

                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    private fun animateRipple(ripple: Ripple) {
        val alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0)
        val radiusHolder = PropertyValuesHolder.ofFloat("radius", ripple.radius, 300f)

        ValueAnimator.ofPropertyValuesHolder(radiusHolder, alphaHolder).apply {
            duration = 3000
            interpolator = MyInterpolator.myInterpolator
            addUpdateListener {
                ripple.radius = it.getAnimatedValue("radius") as Float
                ripple.alpha = it.getAnimatedValue("alpha") as Int
                invalidate()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        handler.removeCallbacksAndMessages(null)
    }
}