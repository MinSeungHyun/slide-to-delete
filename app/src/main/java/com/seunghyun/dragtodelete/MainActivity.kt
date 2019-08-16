package com.seunghyun.dragtodelete

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var firstX = 0f
    private var firstViewX = 0f
    private var velocity = 0f
    private var isSlidedToRight = false

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
                    if (firstViewX != 0f && firstViewX != parentWidth && firstViewX != parentWidth * -1) return@setOnTouchListener true
                    val distance = x - firstX
                    var viewX = firstViewX + distance

                    isSlidedToRight = (distance > 0 || viewX > 0) && firstViewX >= 0

                    if (viewX > parentWidth) viewX = parentWidth

                    velocity += viewX - text.x
                    velocity /= 2
                    text.x = viewX
                    onViewMove()
                }
                MotionEvent.ACTION_UP -> {
                    var textX = text.x
                    val autoSlideVelocity = AUTO_SLIDE_VELOCITY_RATIO * parentWidth
                    val autoSlideWidth = parentWidth * AUTO_SLIDE_RATIO
                    isSlidedToRight = textX > 0

                    if (isSlidedToRight || firstViewX == parentWidth) {
                        when {
                            velocity > autoSlideVelocity -> slideTextToRight()
                            velocity < autoSlideVelocity * -1 -> resetText()
                            textX > autoSlideWidth -> slideTextToRight()
                            textX < autoSlideWidth -> resetText()
                        }
                    } else {
                        velocity *= -1
                        textX *= -1
                        when {
                            velocity > autoSlideVelocity -> slideTextToLeft()
                            velocity < autoSlideVelocity * -1 -> resetText()
                            textX > autoSlideWidth -> slideTextToLeft()
                            textX < autoSlideWidth -> resetText()
                        }
                    }
                }
            }
            return@setOnTouchListener true
        }
    }

    private fun onViewMove() {
        var x = text.x
        val parentWidth = container.width

        if (!isSlidedToRight) x *= -1
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

    private fun slideTextToRight() {
        val animator = ValueAnimator.ofFloat(text.x, container.width.toFloat()).apply {
            duration = 200
        }
        animator.addUpdateListener {
            text.x = it.animatedValue.toString().toFloat()
            onViewMove()
        }
        animator.start()
    }

    private fun slideTextToLeft() {
        val animator = ValueAnimator.ofFloat(text.x, container.width.toFloat() * -1).apply {
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
        private const val AUTO_SLIDE_VELOCITY_RATIO = 0.02
    }
}
