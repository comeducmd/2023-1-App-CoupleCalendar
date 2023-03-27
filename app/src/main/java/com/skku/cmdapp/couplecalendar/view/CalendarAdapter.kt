package com.skku.cmdapp.couplecalendar.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.skku.cmdapp.couplecalendar.R
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(private val dayList: ArrayList<Date>)
    : RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dayText: TextView = itemView.findViewById(R.id.dayText)
    }

    //뷰 홀더 설정(화면 흩뿌리기)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_dayitems, parent, false)
        return ItemViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var monthDate = dayList[holder.bindingAdapterPosition]

        //초기화
        var dateCalendar = Calendar.getInstance()

        //날짜 캘린더에 담기
        dateCalendar.time = monthDate

        //캘린더값 날짜 변수에 담기
        var dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        holder.dayText.text = dayNo.toString()

        //넘어온 날짜
        var iYear = dateCalendar.get(Calendar.YEAR)
        var iMonth = dateCalendar.get(Calendar.MONTH) + 1
        var iDay = dateCalendar.get(Calendar.DAY_OF_MONTH)

        //현재 날짜
        var selectedYear = Calendar_Util.selectedDate.get(Calendar.YEAR)
        var selectedMonth = Calendar_Util.selectedDate.get(Calendar.MONTH) + 1
        var selectedDay = Calendar_Util.selectedDate.get(Calendar.DAY_OF_MONTH)

        //넘어온 날짜와 현재 날짜 비교
        if(iYear == selectedYear && iMonth == selectedMonth){
            holder.dayText.setTextColor(Color.parseColor("#000000"))

            //현재 일자 비교해서 배경색 변경
            if(selectedDay == dayNo){
                holder.itemView.setBackgroundColor(Color.LTGRAY)
            }

            //토,일 색상 변경
            if((position + 1) % 7 == 0){
                holder.dayText.setTextColor(Color.BLUE)
            } else if(position == 0 || position % 7 == 0){
                holder.dayText.setTextColor(Color.RED)
            }
        } else {
            holder.dayText.setTextColor(Color.parseColor("#B4B4B4"))
            //토,일 색상 변경
            if((position + 1) % 7 == 0){
                holder.dayText.setTextColor(Color.parseColor("#B4FFFF"))
            } else if(position == 0 || position % 7 == 0){
                holder.dayText.setTextColor(Color.parseColor("#FFB4B4"))
            }
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener{
            //인터페이스를 통해서 날짜 넘겨주기

            var yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"

            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}