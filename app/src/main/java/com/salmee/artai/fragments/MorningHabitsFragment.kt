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
import com.salmee.artai.ui.HabitDetailActivity
import com.salmee.artai.R
import com.salmee.artai.adapters.HabitsAdapter
import com.salmee.artai.data.MockHabitsData
import com.salmee.artai.databinding.FragmentMorningHabitsBinding
import com.salmee.artai.databinding.FragmentShapesBinding
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity


class MorningHabitsFragment : Fragment() {
    private var _binding: FragmentMorningHabitsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMorningHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHabits)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = HabitsAdapter(MockHabitsData.getMorningHabits()) { habit ->
            val intent = Intent(requireContext(), HabitDetailActivity::class.java).apply {
                putExtra("habit_name", habit.name)
                putExtra("habit_description", habit.description)
                putExtra("habit_image", habit.imageResId) // Ensure image is passed as an integer
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
}
