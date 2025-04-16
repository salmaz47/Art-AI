package com.salmee.artai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.adapter.DrawingAdapter
import com.salmee.artai.data.MockHabitsData
import com.salmee.artai.databinding.FragmentDrawingBinding
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity

class DrawingsFragment : Fragment() {
    private var _binding: FragmentDrawingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerViewHabits.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewHabits.adapter = DrawingAdapter(MockHabitsData.getDrawings())

        // Navigation Bar buttons
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
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
