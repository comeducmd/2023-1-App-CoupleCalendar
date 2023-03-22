package com.skku.cmdapp.couplecalendar.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skku.cmdapp.couplecalendar.R
import com.skku.cmdapp.couplecalendar.databinding.CalendarViewBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Date

class CalendarViewActivity : AppCompatActivity() {

    private lateinit var binding: CalendarViewBinding

    //년월 변수
    lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_view)

        //binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.calendar_view)

        //현재 날짜
        selectedDate = LocalDate.now()

        //화면 설정
        setMonthView()

        //이전달 버튼 누르는 이벤트
        binding.preBtn.setOnClickListener{
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        //다음달 버튼 누르는 이벤트
        binding.nextBtn.setOnClickListener{
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    //날짜 화면에 뿌리기
    private fun setMonthView() {
        binding.monthYearText.text = monthYearFromDate(selectedDate)
        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray(selectedDate)
        //어뎁터 초기화
        val adapter = CalendarAdapter(dayList)
        //레이아웃설정 7칸
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        //레이아웃적용
        binding.recyclerview.layoutManager = manager
        //어뎁터 끼우기
        binding.recyclerview.adapter = adapter
    }

    private fun monthYearFromDate(data: LocalDate): String{
        var formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        return data.format(formatter)
    }

    //날짜 생성
    private  fun dayInMonthArray(date: LocalDate): ArrayList<String>{
        var dayList = ArrayList<String>()
        var yearMonth = YearMonth.from(date)

        //해당 월 마지막 날짜 가져오기(28, 30, 31)
        var lastDay = yearMonth.lengthOfMonth()
        //해당 월 첫째 날짜 가져오기(4월 1일)
        var firstDay = selectedDate.withDayOfMonth(1)
        //첫번째 날 요일 가져오기
        var dayofweek = firstDay.dayOfWeek.value
        for(i in 1..41){
            if(i <= dayofweek || i > (lastDay + dayofweek)){
                dayList.add("")
            } else {
                dayList.add((i - dayofweek).toString())
            }
        }
        return dayList
    }
}