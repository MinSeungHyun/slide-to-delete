package com.seunghyun.dragtodelete

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var firstX = 0f
    private var firstViewX = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val screenWidth = size.x

        deleteTV.setOnTouchListener { _, event ->
            val x = event.x

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    firstX = x
                    firstViewX = text.x
                }
                MotionEvent.ACTION_MOVE -> {
                    val distance = x - firstX
                    var viewX = firstViewX + distance

                    if (viewX > screenWidth) viewX = screenWidth.toFloat()
                    else if (viewX < 0) viewX = 0f
                    text.x = viewX
                }
            }
            return@setOnTouchListener true
        }
    }
}
