package com.example.movieapp.presentation.components


import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movieapp.R


class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val path = Path()
    private var bitmap: Bitmap? = null

    init {
        // Optional: Load a default image from resources
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
        drawable?.let {
            bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap!!)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            // Define the clipping path
            path.moveTo(0f, 0f)
            path.lineTo(width.toFloat(), 0f)
            path.lineTo(width.toFloat(), height * 0.65f)
            path.quadTo(width * 0.6f, height.toFloat(), 0f, height.toFloat())
            path.close()

            canvas.save()
            // Clip the canvas
            canvas.clipPath(path)

            // Draw the image within the clipped region
            canvas.drawBitmap(it, null, Rect(0, 0, width, height), paint)

            canvas.restore()
        }
    }

    fun setImageWithGlide(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                    invalidate()  // Redraw the view with the new bitmap
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle bitmap cleanup if needed
                }
            })
    }
}