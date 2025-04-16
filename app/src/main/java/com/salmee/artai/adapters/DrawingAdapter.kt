package com.salmee.artai.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.R
import com.salmee.artai.model.DrawingItem

class DrawingAdapter(private val drawingList: List<DrawingItem>) :
    RecyclerView.Adapter<DrawingAdapter.DrawingViewHolder>() {

    inner class DrawingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drawingImage: ImageView = itemView.findViewById(R.id.habitImage)
        val drawingCard: CardView = itemView.findViewById(R.id.drawing_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawing_card, parent, false)
        return DrawingViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrawingViewHolder, position: Int) {
        val item = drawingList[position]
        holder.drawingImage.setImageResource(item.imageResId)

        holder.drawingCard.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.youtubeUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = drawingList.size
}
