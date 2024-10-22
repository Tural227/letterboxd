package com.example.movieapp.presentation.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomRating(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val numberOfRectangles = 5
    private val rectangleWidth = 30f
    private var rectangleHeight = 0f
    private val spacing = 5


    var data = listOf<Float>()


    init {
        paint.setColor(Color.parseColor("#808080"))

        val valueAnimator = ValueAnimator.ofFloat(0f, 150f)
        valueAnimator.duration = 1000
        valueAnimator.addUpdateListener {
            rectangleHeight = it.animatedValue as Float
            invalidate()
        }
        valueAnimator.start()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        //baslangic noqte
        canvas.translate(0f, height.toFloat())


        //koordinat oxlari deyismek ucun
        canvas.scale(1f, -1f)

        var x = 0f

        if(data.isNotEmpty()){
            repeat(numberOfRectangles) {

                val height = rectangleHeight * data[it]
                canvas.drawRect(
                    x,
                    0f,
                    x + rectangleWidth,
                    height,
                    paint
                )


                x += (rectangleWidth + spacing)
            }

        }


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = (numberOfRectangles * (spacing + rectangleWidth) - spacing).toInt()
        val desiredHeight = 150
        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

}