package com.skku.cmdapp.couplecalendar.view

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skku.cmdapp.couplecalendar.R
import com.skku.cmdapp.couplecalendar.databinding.CalendarViewBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class CalendarViewActivity : AppCompatActivity(){

    private lateinit var binding: CalendarViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_view)

        //binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.calendar_view)

        //화면 설정
        setMonthView()

        //이전달 버튼 누르는 이벤트
        binding.preBtn.setOnClickListener{
            Calendar_Util.selectedDate.add(Calendar.MONTH, -1)
            setMonthView()
        }

        //다음달 버튼 누르는 이벤트
        binding.nextBtn.setOnClickListener{
            Calendar_Util.selectedDate.add(Calendar.MONTH, 1)
            setMonthView()
        }
    }

    //날짜 화면에 뿌리기
    private fun setMonthView() {
        binding.monthYearText.text = monthYearFromDate(Calendar_Util.selectedDate)
        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray()
        //어뎁터 초기화
        val adapter = CalendarAdapter(dayList)
        //레이아웃설정 7칸
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        //레이아웃적용
        binding.recyclerview.layoutManager = manager
        //어뎁터 끼우기
        binding.recyclerview.adapter = adapter
    }

    private fun monthYearFromDate(calendar: Calendar): String{
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        return "$year 년 $month 월"
    }

    //날짜 생성
    private  fun dayInMonthArray(): ArrayList<Date>{
        var dayList = ArrayList<Date>()

        var monthCalendar = Calendar_Util.selectedDate.clone() as Calendar

        //1일로 세팅
        monthCalendar[Calendar.DAY_OF_MONTH] = 1

        //해당 달의 1일의 요일
        val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK] - 1

        //요일 숫자만큼 이전 날짜로 설정
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dayList.size < 42){
            dayList.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dayList
    }
}