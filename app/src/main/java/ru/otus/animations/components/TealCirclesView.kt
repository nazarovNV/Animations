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

class TealCirclesView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tiktok_style
) : View(context, attrs, defStyleAttr) {
    private lateinit var teal_circle_color: Paint

    init {
        initCircles(attrs, defStyleAttr)
    }

    private fun initCircles(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(
            attrs,
            R.styleable.Tiktok_loading_animation,
            defStyleAttr,
            R.style.tiktok_loading_animation
        ) {
            teal_circle_color = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = getColorOrThrow(R.styleable.Tiktok_loading_animation_teal_circle_color)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            firstCircleRadius,
            teal_circle_color
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
    companion object {
        private const val SIZE = 300
        private var firstCircleRadius = 60f
    }

    fun animateTealCircle() {
        val alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0)
        val radiusHolder = PropertyValuesHolder.ofFloat("radius",
            firstCircleRadius, 300f)
        val Animator = ValueAnimator.ofPropertyValuesHolder(radiusHolder, alphaHolder).apply {
            duration = 3000
            val myInterpolator = MyInterpolator.myInterpolator
            interpolator = myInterpolator
            addUpdateListener {
                firstCircleRadius = it.getAnimatedValue("radius") as Float
                teal_circle_color.alpha = it.getAnimatedValue("alpha") as Int
                invalidate()
            }
            start()
        }
    }

}
