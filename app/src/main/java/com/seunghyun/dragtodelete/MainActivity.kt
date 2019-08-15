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
        val screenWidth = size.x.toFloat()

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

                    if (viewX > screenWidth) viewX = screenWidth
                    else if (viewX < 0) viewX = 0f
                    text.x = viewX
                }
                MotionEvent.ACTION_UP -> {
                    if (text.x > screenWidth * AUTO_SLIDE_RATIO) {
                        text.x = screenWidth
                    } else if (text.x < screenWidth * AUTO_SLIDE_RATIO) {
                        text.x = 0f
                    }
                }
            }
            return@setOnTouchListener true
        }
    }

    companion object {
        private const val AUTO_SLIDE_RATIO = 0.5
    }
}
