package com.seunghyun.dragtodelete

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.seunghyun.slidetodelete.enableSlideToDelete
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deletedTV.enableSlideToDelete(container, text, 2000) {
            container.visibility = View.GONE
        }
        deletedTV2.enableSlideToDelete(container2, text2, 1000) {
            container2.visibility = View.GONE
        }
    }
}
