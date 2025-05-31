package com.salmee.artai.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.R

import com.salmee.artai.model.PaintItem

/**
 * Adapter for displaying paint items in a grid layout within a RecyclerView.
 * When an item is clicked, it opens the associated YouTube URL.
 */
class PaintAdapter(private var paintItems: List<PaintItem>) :
    RecyclerView.Adapter<PaintAdapter.PaintViewHolder>() {

    /**
     * ViewHolder for the paint item views
     */
    inner class PaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.paint_image)

        init {
            // Set click listener for each item
            itemView.setOnClickListener {
                it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.button_elevation))
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Get the URL for the clicked position
                    val youtubeUrl = paintItems[position].youtubeUrl

                    // Create an intent to open the URL
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paint, parent, false)
        return PaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaintViewHolder, position: Int) {
        val currentItem = paintItems[position]
        holder.imageView.setImageResource(currentItem.imageResId)
    }

    override fun getItemCount(): Int = paintItems.size

    /**
     * Updates the adapter with a new list of paint items
     */
    fun updatePaints(newPaintItems: List<PaintItem>) {
        this.paintItems = newPaintItems
        notifyDataSetChanged()
    }
}