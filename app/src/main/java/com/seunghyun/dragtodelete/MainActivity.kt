package com.seunghyun.dragtodelete

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seunghyun.slidetodelete.enableSlideToDelete
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deletedTV.enableSlideToDelete(container, text, 2000) {
            Toast.makeText(this@MainActivity, "First view deleted!", Toast.LENGTH_LONG).show()
        }
        deletedTV2.enableSlideToDelete(container2, text2, 1000) {
            Toast.makeText(this@MainActivity, "Second view deleted!", Toast.LENGTH_LONG).show()
        }
    }
}
