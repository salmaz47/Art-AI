package com.salmee.artai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.R
import com.salmee.artai.model.Habit

class ShapesAdapter(
    private val shapeList: List<Habit>,
    private val onItemClick: (Habit) -> Unit
) : RecyclerView.Adapter<ShapesAdapter.ShapeViewHolder>() {

    inner class ShapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shapeImage: ImageView = itemView.findViewById(R.id.habitImage)
        val shapeName: TextView = itemView.findViewById(R.id.habitName)

        fun bind(habit: Habit) {
            shapeImage.setImageResource(habit.imageResId)
            shapeName.text = habit.name
            itemView.setOnClickListener { onItemClick(habit) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShapeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false) // Use your existing item layout
        return ShapeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShapeViewHolder, position: Int) {
        holder.bind(shapeList[position])
    }

    override fun getItemCount(): Int = shapeList.size
}
