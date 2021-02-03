package com.itri.guesskotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itri.guesskotlin.data.Record
import kotlinx.android.synthetic.main.row_record_list.view.*

class RecordAdapter(var records : List<Record>) : RecyclerView.Adapter<RecordHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_record_list, parent, false)
        return RecordHolder(view)
    }
    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        holder.name.text = records.get(position).nickname
        holder.counter.text = records.get(position).counter.toString()
    }

    override fun getItemCount(): Int {
        return records.size
    }
}

class RecordHolder(view : View) : RecyclerView.ViewHolder(view){
    var name : TextView = view.record_nickname
    var counter : TextView = view.record_counter
}