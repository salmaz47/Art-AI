package com.salmee.artai.fragments

import android.content.Intent
import android.net.Uri // For opening URLs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // Use this for fragment viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.adapters.DrawingAdapter
import com.salmee.artai.adapters.OnImageItemClickListener // Import listener
import com.salmee.artai.data.repository.ImageFilter
import com.salmee.artai.databinding.FragmentFavBinding
import com.salmee.artai.model.Image // Import Image model
import com.salmee.artai.presentation.viewmodel.ImageViewModel
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity
import com.salmee.artai.utils.ViewModelFactory

class FavFragment : Fragment(), OnImageItemClickListener { // Implement listener
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DrawingAdapter
    private val imageViewModel: ImageViewModel by viewModels { // Use activityViewModels or fragment viewModels
        ViewModelFactory(context = requireActivity().applicationContext)
    }
    private var isGuest: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isGuest = SharedPreferencesHelper.isGuestMode(requireContext())

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()

        if (isGuest) {
            // Show guest message or empty state
            binding.recyclerViewHabits.visibility = View.GONE
            binding.guestMessage.visibility = View.VISIBLE // Add a TextView with id 'guestMessage'
            binding.guestMessage.text = getString(R.string.login_to_see_favorites)
        } else {
            binding.recyclerViewHabits.visibility = View.VISIBLE
            binding.guestMessage.visibility = View.GONE
            // Fetch all images and filter locally based on SharedPreferences
            // OR fetch only loved images if backend supports it well
            imageViewModel.fetchImages(ImageFilter.ALL) // Fetch all, filter locally
        }
    }

    private fun setupRecyclerView() {
        adapter = DrawingAdapter(emptyList(), requireContext(), this) // Pass listener
        binding.recyclerViewHabits.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewHabits.adapter = adapter
    }

    private fun observeViewModel() {
        imageViewModel.imagesResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { allImages ->
                    if (!isGuest) {
                        // Filter images based on local favorites
                        val favoriteIds = imageViewModel.getLocalFavoriteIds()
                        val favoriteImages = allImages.filter { favoriteIds.contains(it.id) }
                        if (favoriteImages.isEmpty()) {
                            binding.emptyFavoritesMessage.visibility = View.VISIBLE // Add TextView for empty state
                            binding.recyclerViewHabits.visibility = View.GONE
                        } else {
                            binding.emptyFavoritesMessage.visibility = View.GONE
                            binding.recyclerViewHabits.visibility = View.VISIBLE
                            adapter.updateData(favoriteImages)
                        }
                    }
                },
                onFailure = { exception ->
                    Toast.makeText(requireContext(), "Error loading images: ${exception.message}", Toast.LENGTH_SHORT).show()
                    binding.emptyFavoritesMessage.visibility = View.VISIBLE // Show empty/error state
                    binding.recyclerViewHabits.visibility = View.GONE
                }
            )
        }

        // Observe favorite status changes to update UI immediately
        imageViewModel.favoriteStatusResult.observe(viewLifecycleOwner) { (imageId, result) ->
             result.onSuccess { isNowFavorite ->
                 // Find the item in the current adapter list
                 val currentList = adapter.imageList // Assuming adapter has a public list or getter
                 val index = currentList.indexOfFirst { it.id == imageId }
                 if (index != -1) {
                     if (isNowFavorite) {
                         // Item is now favorite, ensure it's in the list (it should be already)
                         adapter.updateFavoriteStatus(index, true)
                     } else {
                         // Item is no longer favorite, remove it from this FavFragment list
                         val updatedList = currentList.toMutableList().apply { removeAt(index) }
                         adapter.updateData(updatedList)
                         if (updatedList.isEmpty()) {
                             binding.emptyFavoritesMessage.visibility = View.VISIBLE
                             binding.recyclerViewHabits.visibility = View.GONE
                         }
                     }
                 } else if (isNowFavorite) {
                     // If an item was favorited elsewhere and now appears, refetch or add manually
                     // For simplicity, could just refetch
                     imageViewModel.fetchImages(ImageFilter.ALL)
                 }
             }
             result.onFailure {
                 Toast.makeText(requireContext(), "Failed to update favorite status", Toast.LENGTH_SHORT).show()
             }
        }
    }

    private fun setupClickListeners() {
        // Navigation Bar
        binding.navigationBar.profileButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_elevation))
            startActivity(Intent(activity, ProfileActivity::class.java))
        }
        binding.navigationBar.homeButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_elevation))
            startActivity(Intent(activity, CategoryActivity::class.java))
        }
        binding.navigationBar.centerIcon.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_elevation))
            startActivity(Intent(activity, ModelActivity::class.java))
            // activity?.finish() // Decide if FavActivity should finish
        }
    }

    // --- OnImageItemClickListener Implementation --- 

    override fun onImageClick(image: Image) {
        // Handle image click - maybe open detail view or URL?
        // Example: Open image URL in browser (if applicable)
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(image.imageUrl))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Cannot open image URL", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFavoriteClick(image: Image, position: Int) {
        // Toggle favorite using ViewModel
        imageViewModel.toggleFavorite(image.id)
        // UI update will happen via observing favoriteStatusResult
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to avoid memory leaks
    }
}

