package com.skku.cmdapp.couplecalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.skku.cmdapp.couplecalendar.databinding.ActivityMainBinding
import com.skku.cmdapp.couplecalendar.view.CalendarViewActivity
import com.skku.cmdapp.couplecalendar.view.popupdialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClick(binding)
    }

    private fun handleClick(binding: ActivityMainBinding){
        binding.openCalendarBtn.setOnClickListener{
            startActivity(Intent(this, CalendarViewActivity::class.java))
        }
    }

    fun diarybuttonclicked(view: View) {

        val diarypopup = popupdialog(this)

        diarypopup.show()
    }

}