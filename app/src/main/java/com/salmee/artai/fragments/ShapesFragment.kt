package com.salmee.artai.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // Use this import
import androidx.recyclerview.widget.GridLayoutManager
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.adapters.DrawingAdapter
import com.salmee.artai.adapters.OnImageItemClickListener // Import listener
import com.salmee.artai.data.repository.ImageFilter
import com.salmee.artai.databinding.FragmentShapesBinding // Use correct binding
import com.salmee.artai.model.Image // Import Image model
import com.salmee.artai.presentation.viewmodel.ImageViewModel
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity
import com.salmee.artai.utils.ViewModelFactory

// This fragment now displays images fetched from the backend, using DrawingAdapter
class ShapesFragment : Fragment(), OnImageItemClickListener { // Implement listener
    private var _binding: FragmentShapesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DrawingAdapter
    private val imageViewModel: ImageViewModel by viewModels {
        ViewModelFactory(context = requireActivity().applicationContext)
    }
    private var isGuest: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShapesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isGuest = SharedPreferencesHelper.isGuestMode(requireContext())

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()

        // Fetch images (adjust filter if needed for this specific fragment)
        // Assuming ImageFilter.ALL for now, or create specific filters if backend supports
        imageViewModel.fetchImages(ImageFilter.ALL)
    }

    private fun setupRecyclerView() {
        // Initialize with empty list, pass context and listener
        adapter = DrawingAdapter(emptyList(), requireContext(), this)
        binding.recyclerViewHabits.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewHabits.adapter = adapter
    }

    private fun observeViewModel() {
        imageViewModel.imagesResult.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE // Hide progress bar
            result.fold(
                onSuccess = { images ->
                    // TODO: Filter images specifically for "Shapes" if needed, based on prompt or backend tag
                    // For now, displaying all images fetched
                    if (images.isEmpty()) {
                        binding.emptyListMessage.visibility = View.VISIBLE // Add a TextView for empty state
                        binding.recyclerViewHabits.visibility = View.GONE
                    } else {
                        binding.emptyListMessage.visibility = View.GONE
                        binding.recyclerViewHabits.visibility = View.VISIBLE
                        adapter.updateData(images)
                    }
                },
                onFailure = { exception ->
                    Toast.makeText(requireContext(), "Error loading images: ${exception.message}", Toast.LENGTH_SHORT).show()
                    binding.emptyListMessage.visibility = View.VISIBLE // Show empty/error state
                    binding.recyclerViewHabits.visibility = View.GONE
                }
            )
        }

        // Observe favorite status changes to update UI immediately
        imageViewModel.favoriteStatusResult.observe(viewLifecycleOwner) { (imageId, result) ->
            result.onSuccess { isNowFavorite ->
                val index = adapter.imageList.indexOfFirst { it.id == imageId }
                if (index != -1) {
                    adapter.updateFavoriteStatus(index, isNowFavorite)
                }
            }
            // Optionally handle failure
        }

        // Show progress bar while fetching
        binding.progressBar.visibility = View.VISIBLE // Add a ProgressBar with id 'progressBar'
        binding.emptyListMessage.visibility = View.GONE
        binding.recyclerViewHabits.visibility = View.GONE
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
            // activity?.finish() // Decide if this fragment's activity should finish
        }
    }

    // --- OnImageItemClickListener Implementation ---

    override fun onImageClick(image: Image) {
        // Handle image click - maybe open detail view or URL?
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
        _binding = null // Clear binding
    }
}

