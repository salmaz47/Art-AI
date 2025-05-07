package com.salmee.artai.adapters // Corrected package name based on file structure

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salmee.artai.R
import com.salmee.artai.model.Image // Use the Image model

// Listener interfaces for interactions
interface OnImageItemClickListener {
    fun onImageClick(image: Image)
    fun onFavoriteClick(image: Image, position: Int)
}

class DrawingAdapter(
    var imageList: List<Image>,
    private val context: Context, // Need context for Glide and SharedPreferences
    private val listener: OnImageItemClickListener
) : RecyclerView.Adapter<DrawingAdapter.DrawingViewHolder>() {

    inner class DrawingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drawingImage: ImageView = itemView.findViewById(R.id.habitImage) // Keep ID or rename?
        // Removed drawingCard reference if the whole card click is handled differently

        fun bind(image: Image, position: Int) {
            Log.d("DrawingAdapter", "Binding image ID: ${image.id}, isFavorite: ${SharedPreferencesHelper.isFavorite(context, image.id)}")
            // Load image using Glide
            Glide.with(context)
                .load(image.imageUrl)
                .placeholder(R.drawable.loading_placeholder) // Add placeholder
                .error(R.drawable.error_placeholder) // Add error placeholder
                .centerCrop()
                .into(drawingImage)




            // Image click listener (whole item)
            itemView.setOnClickListener {
                listener.onImageClick(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paint, parent, false) // Use the updated layout
        return DrawingViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrawingViewHolder, position: Int) {
        val item = imageList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = imageList.size

    // Function to update the list data
    fun updateData(newImageList: List<Image>) {
        imageList = newImageList
        notifyDataSetChanged() // Consider using DiffUtil for better performance
    }

    // Function to update favorite status for a specific item
    fun updateFavoriteStatus(position: Int, isFavorite: Boolean) {
        if (position >= 0 && position < imageList.size) {
            // Update the specific item in the list if needed (though SharedPreferences is the source of truth)
            // imageList[position] = imageList[position].copy(isLoved = isFavorite) // Requires Image model update if needed
            notifyItemChanged(position) // Re-bind the view holder to update the icon
        }
    }
}

