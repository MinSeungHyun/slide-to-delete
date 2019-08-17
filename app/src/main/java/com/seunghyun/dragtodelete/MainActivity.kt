package com.seunghyun.dragtodelete

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deletedTV.setOnTouchListener(SlideToDeleteTouchListener(container, text))
        deletedTV2.setOnTouchListener(SlideToDeleteTouchListener(container2, text2))
    }
}
