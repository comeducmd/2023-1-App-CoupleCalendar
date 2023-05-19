package com.skku.cmdapp.couplecalendar.view

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.skku.cmdapp.couplecalendar.R
import com.skku.cmdapp.couplecalendar.databinding.ActivityMainBinding
import com.skku.cmdapp.couplecalendar.databinding.DiaryWritingBinding
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context){
    private val prefs: SharedPreferences = context.getSharedPreferences("pref", 0)

    fun getString(key: String, defValue: String):String{
        return prefs.getString(key, defValue).toString()
    }
    fun setString(key:String, str:String){
        prefs.edit().putString(key, str).apply()
    }
}

class DiaryWriting : AppCompatActivity() {

    companion object{
        lateinit var prefs: PreferenceUtil
    }
    private lateinit var binding: DiaryWritingBinding

    override fun onCreate(savedInstanceState: Bundle?){
        prefs = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diary_writing)

        binding = DataBindingUtil.setContentView(this, R.layout.diary_writing)
        setContentView(binding.root)
    }

    private fun onClickTitle(binding: DiaryWritingBinding){
        binding.titleText
    }
    private fun onClickDiary(binding: DiaryWritingBinding){
        binding.textField
    }
    private fun onClickSave(binding:DiaryWritingBinding){
        binding.titleText.text.toString()
        binding.textField.text.toString()
    }
    private fun onClickCancle(binding: DiaryWritingBinding){
        binding.cancelButton
    }

}