package com.skku.cmdapp.couplecalendar.view

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.skku.cmdapp.couplecalendar.R
import com.skku.cmdapp.couplecalendar.databinding.DiaryWritingBinding

class DiaryWritingActivity : AppCompatActivity() {

    companion object{
        lateinit var prefs: PreferenceUtil
    }
    private lateinit var binding: DiaryWritingBinding

    override fun onCreate(savedInstanceState: Bundle?){
        prefs = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)

        binding=DiaryWritingBinding.inflate(layoutInflater,null, false)

        //선택된 날짜 정보 화면에 보여주기
        val selectedDate=intent.getStringExtra("SelectedDate")
        binding.tvDateInfo.text=selectedDate


        //취소하기 버튼 누르면 뒤로가기
        binding.cancelButton.setOnClickListener {
            finish()
        }

        //binding = DataBindingUtil.setContentView(this, R.layout.diary_writing)
        setContentView(binding.root)
    }

    private fun onClickTitle(binding: DiaryWritingBinding){
        binding.titleText
    }
    private fun onClickDiary(binding: DiaryWritingBinding){
        binding.textField
    }
    private fun onClickSave(binding: DiaryWritingBinding){
        binding.titleText.text.toString()
        binding.textField.text.toString()
    }
    private fun onClickCancle(binding: DiaryWritingBinding){
        binding.cancelButton
    }
}
