package com.skku.cmdapp.couplecalendar.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skku.cmdapp.couplecalendar.databinding.ChartListItemBinding

class PieChartDataAdapter(val items:ArrayList<PieChartData>): RecyclerView.Adapter<PieChartDataAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:ChartListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =ChartListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.chartListIcon.setImageResource(items[position].pieIcon)
        holder.binding.chartListIcon.background=items[position].pieBackground
        holder.binding.chartListCategory.text=items[position].pieCategory
        holder.binding.chartListValue.text= items[position].pieValue.toString()



    }
}