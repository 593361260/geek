package com.mingo.geek.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TempCanvas @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var can: Canvas? = null
    private var path = Path()
    private val matrix1 = Matrix()

    private val paint = Paint().apply {
        isDither = true
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 8f
        isFilterBitmap = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        can = Canvas(bitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        bitmap?.let {
            canvas?.drawBitmap(it, matrix1, paint)
        }
    }

    private var downX = 0f
    private var downY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                downX = event.x
                downY = event.y
                path.moveTo(downX, downY)
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x
                val moveY = event.y
                path.lineTo(moveX, moveY)
//                can!!.drawColor(Color.WHITE)
                can!!.drawPath(path, paint)
            }
            MotionEvent.ACTION_UP -> {
                invalidate()
            }

        }
        return true
    }
}

