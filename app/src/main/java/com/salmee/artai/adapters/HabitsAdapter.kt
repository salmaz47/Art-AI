package com.salmee.artai.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.R
import com.salmee.artai.model.Habit

class HabitsAdapter(
    private val habitList: List<Habit>,
    private val onHabitClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val habitImage: ImageView = itemView.findViewById(R.id.habitImage)
        val habitName: TextView = itemView.findViewById(R.id.habitName)
        val habitDescription: TextView = itemView.findViewById(R.id.habitDescription)
        val habitCard: CardView = itemView.findViewById(R.id.habit_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        // Set the image, name, and description
        holder.habitImage.setImageResource(habit.imageResId)
        holder.habitName.text = habit.name
        holder.habitDescription.text = habit.description

        // Click listener for navigating to another activity
        holder.habitCard.setOnClickListener {
            onHabitClick(habit)
        }
    }

    override fun getItemCount(): Int = habitList.size
}
