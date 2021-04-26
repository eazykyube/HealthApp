package com.example.healthapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.habit.Habit
import kotlinx.android.synthetic.main.list_habit_item.view.*

class Adapter(): RecyclerView.Adapter<Adapter.ListHabitViewHolder>() {

    var data = listOf<Habit>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ListHabitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.habit_title

        fun bind (item: Habit) {
            title.text = item.name
            itemView.setOnClickListener {
                // Navigate to the habit page
            }
        }

        companion object {
            fun from(parent: ViewGroup): ListHabitViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_habit_item, parent, false)

                return ListHabitViewHolder(
                        view
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHabitViewHolder {
        return ListHabitViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListHabitViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }

    override fun getItemCount() = data.size
}