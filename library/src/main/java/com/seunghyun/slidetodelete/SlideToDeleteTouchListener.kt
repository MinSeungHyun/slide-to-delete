package com.seunghyun.slidetodelete

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

private class SlideToDeleteTouchListener(
    private val container: ViewGroup,
    private val content: View,
    private val waitingTime: Long,
    private val doOnDelete: (container: ViewGroup) -> Unit
) : View.OnTouchListener {

    private var firstX = 0f
    private var firstY = 0f
    private var firstViewX = 0f
    private var velocity = 0f
    private var isSlidedToRight = false
    private var runningThread: WaitingThread? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val parentWidth = container.width.toFloat()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                firstX = x
                firstY = y
                firstViewX = content.x
            }
            MotionEvent.ACTION_MOVE -> {
                if (firstViewX != 0f && firstViewX != parentWidth && firstViewX != parentWidth * -1) return true
                val distanceX = x - firstX
                val distanceY = y - firstY
                var viewX = firstViewX + distanceX

                if (abs(distanceY) > abs(distanceX) && abs(distanceX) < parentWidth / 20) {
                    //Vertical scrolling
                    requestDisallowInterceptTouchEvent(false)
                    resetView()
                } else {
                    requestDisallowInterceptTouchEvent(true)
                }

                isSlidedToRight = (distanceX > 0 || viewX > 0) && firstViewX >= 0

                if (viewX > parentWidth) viewX = parentWidth

                velocity += viewX - content.x
                velocity /= 2
                content.x = viewX
                onViewMove()
            }
            MotionEvent.ACTION_UP -> {
                requestDisallowInterceptTouchEvent(false)
                var textX = content.x
                val autoSlideVelocity = AUTO_SLIDE_VELOCITY_RATIO * parentWidth
                val autoSlideWidth = parentWidth * AUTO_SLIDE_RATIO
                isSlidedToRight = textX > 0

                if (isSlidedToRight || firstViewX == parentWidth) {
                    when {
                        velocity > autoSlideVelocity -> autoSlide(FLAG_SLIDE_TO_RIGHT)
                        velocity < autoSlideVelocity * -1 -> autoSlide(FLAG_RESET)
                        textX > autoSlideWidth -> autoSlide(FLAG_SLIDE_TO_RIGHT)
                        textX < autoSlideWidth -> autoSlide(FLAG_RESET)
                    }
                } else {
                    velocity *= -1
                    textX *= -1
                    when {
                        velocity > autoSlideVelocity -> autoSlide(FLAG_SLIDE_TO_LEFT)
                        velocity < autoSlideVelocity * -1 -> autoSlide(FLAG_RESET)
                        textX > autoSlideWidth -> autoSlide(FLAG_SLIDE_TO_LEFT)
                        textX < autoSlideWidth -> autoSlide(FLAG_RESET)
                    }
                }
            }
        }
        return true
    }

    private fun resetView() {
        autoSlide(FLAG_RESET)
        content.alpha = 1f
    }

    private fun onViewMove() {
        var x = content.x
        val parentWidth = container.width

        if (x.toInt() == parentWidth || x.toInt() == parentWidth * -1) {
            if (runningThread != null) runningThread!!.interrupt()
            runningThread = WaitingThread(waitingTime, doOnDelete, container)
            runningThread!!.start()
        } else if (runningThread?.isAlive == true) {
            runningThread!!.interrupt()
        }

        if (!isSlidedToRight) x *= -1
        val alpha =
            if (firstViewX == 0f) 1 - x / (parentWidth / 2)
            else 2 - 2 * x / parentWidth

        content.alpha = alpha
    }

    private fun autoSlide(flag: Int) {
        val end = when (flag) {
            FLAG_RESET -> 0f
            FLAG_SLIDE_TO_LEFT -> container.width.toFloat() * -1
            FLAG_SLIDE_TO_RIGHT -> container.width.toFloat()
            else -> return
        }

        val animator = ValueAnimator.ofFloat(content.x, end).apply {
            duration = AUTO_SLIDE_MS
        }
        animator.addUpdateListener {
            content.x = it.animatedValue.toString().toFloat()
            onViewMove()
        }
        animator.start()
    }

    private fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        val scrollableView = when {
            container.parent is RecyclerView -> container.parent as RecyclerView
            container.parent is ScrollView -> container.parent as ScrollView
            container.parent.parent is ScrollView -> container.parent.parent as ScrollView
            else -> return
        }
        scrollableView.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    class WaitingThread(
        private val waitingTime: Long,
        private val doOnDelete: (container: ViewGroup) -> Unit,
        private val container: ViewGroup
    ) : Thread() {
        override fun run() {
            try {
                sleep(waitingTime)
                Handler(Looper.getMainLooper()).post {
                    doOnDelete.invoke(container)
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    companion object {
        private const val AUTO_SLIDE_RATIO = 0.5
        private const val AUTO_SLIDE_VELOCITY_RATIO = 0.02
        private const val AUTO_SLIDE_MS = 200L
        private const val FLAG_RESET = 0
        private const val FLAG_SLIDE_TO_RIGHT = 1
        private const val FLAG_SLIDE_TO_LEFT = 2
    }
}

fun View.enableSlideToDelete(
    container: ViewGroup,
    content: View,
    waitingTime: Long,
    doOnDelete: (container: ViewGroup) -> Unit
) {
    this.setOnTouchListener(SlideToDeleteTouchListener(container, content, waitingTime, doOnDelete))
}