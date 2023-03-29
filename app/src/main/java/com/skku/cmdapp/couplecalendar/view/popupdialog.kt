package com.skku.cmdapp.couplecalendar.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.skku.cmdapp.couplecalendar.R

class popupdialog(context: Context): Dialog(context) {
    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.diary_popup)

        Log.d(TAG, "popupdialog - onCreate() called")
    }
}