package com.seunghyun.dragtodelete

import android.animation.ValueAnimator
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

        deleteTV.setOnTouchListener { _, event ->
            val x = event.x
            val parentWidth = container.width.toFloat()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    firstX = x
                    firstViewX = text.x
                }
                MotionEvent.ACTION_MOVE -> {
                    if (firstViewX != 0f && firstViewX != parentWidth) return@setOnTouchListener true
                    val distance = x - firstX
                    var viewX = firstViewX + distance

                    if (viewX > parentWidth) viewX = parentWidth
                    else if (viewX < 0) viewX = 0f
                    text.x = viewX
                    onViewMove()
                }
                MotionEvent.ACTION_UP -> {
                    if (text.x > parentWidth * AUTO_SLIDE_RATIO) {
                        slideText()
                    } else if (text.x < parentWidth * AUTO_SLIDE_RATIO) {
                        resetText()
                    }
                }
            }
            return@setOnTouchListener true
        }
    }

    private fun onViewMove() {
        val x = text.x
        val parentWidth = container.width

        if (firstViewX == 0f) {
            val alpha = 1 - x / (parentWidth / 2)
            text.alpha = alpha
        } else {
            val alpha = 2 - 2 * x / parentWidth
            text.alpha = alpha
        }
    }

    private fun resetText() {
        val animator = ValueAnimator.ofFloat(text.x, 0f).apply {
            duration = 200
        }
        animator.addUpdateListener {
            text.x = it.animatedValue.toString().toFloat()
            onViewMove()
        }
        animator.start()
    }

    private fun slideText() {
        val animator = ValueAnimator.ofFloat(text.x, container.width.toFloat()).apply {
            duration = 200
        }
        animator.addUpdateListener {
            text.x = it.animatedValue.toString().toFloat()
            onViewMove()
        }
        animator.start()
    }

    companion object {
        private const val AUTO_SLIDE_RATIO = 0.5
    }
}
