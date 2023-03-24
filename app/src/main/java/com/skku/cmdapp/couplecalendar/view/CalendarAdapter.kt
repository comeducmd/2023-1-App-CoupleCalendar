package com.skku.cmdapp.couplecalendar.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skku.cmdapp.couplecalendar.R

class CalendarAdapter(private val dayList: ArrayList<String>): RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

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
        holder.dayText.text = dayList[holder.bindingAdapterPosition]
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}