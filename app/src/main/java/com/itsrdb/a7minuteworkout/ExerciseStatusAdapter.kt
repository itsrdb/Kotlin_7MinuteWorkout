package com.itsrdb.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*


class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvItem = view.tvItem
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_exercise_status, p0, false))
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val model: ExerciseModel = items[p1]

        p0.tvItem.text = model.getId().toString()   //displays 1, 2, 3

        if(model.getIsSelected()){
            p0.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.item_circular_thin_color_accent_border)
            p0.tvItem.setTextColor(Color.parseColor("#212121"))
        }else if(model.getIsCompleted()){
            p0.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.item_circular_color_accent_background)
            p0.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            p0.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.item_circular_color_gray_background)
            p0.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}