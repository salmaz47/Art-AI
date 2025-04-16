package com.salmee.artai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.ui.StoryDetailActivity
import com.salmee.artai.adapters.HabitsAdapter
import com.salmee.artai.data.MockHabitsData
import com.salmee.artai.databinding.FragmentShapesBinding
import com.salmee.artai.databinding.FragmentStoryBinding
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity

class StorysFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =binding.recyclerViewHabits
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = HabitsAdapter(MockHabitsData.getStory()) { habit ->
            val intent = Intent(requireContext(), StoryDetailActivity::class.java).apply {
                putExtra("habit_name", habit.name)
                putExtra("habit_description", habit.description)
                putExtra("habit_image", habit.imageResId)
                putExtra("habit_color", getColorForStory(habit.id))
                putExtra("show_buttons", true) // Pass this only if Save & Share are needed
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        binding.navigationBar.profileButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_elevation))
            val intent = Intent(activity, ProfileActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.navigationBar.homeButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_elevation))
            val intent = Intent(activity, CategoryActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.navigationBar.centerIcon.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_elevation))
            val intent = Intent(activity, ModelActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    private fun getColorForStory(id: Int): Int {
        return when (id) {
            21 -> 0xFF729ea7.toInt() // Orange
            22 -> 0xFFf1a5a5.toInt() // Blue
            23 -> 0xFFd95ea6.toInt() // Green
            24 -> 0xFFa5eaf2.toInt() // Green
            25 -> 0xFF729ea7.toInt() // Green
            else ->  0xFFf1a5a5.toInt() // Default Grey
        }
    }
}
