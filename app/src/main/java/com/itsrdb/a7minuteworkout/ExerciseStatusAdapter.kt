package com.itsrdb.a7minuteworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        p0.tvItem.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}