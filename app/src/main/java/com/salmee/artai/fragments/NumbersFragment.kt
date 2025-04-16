package com.salmee.artai.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.R
import com.salmee.artai.adapters.HabitsAdapter
import com.salmee.artai.data.MockHabitsData


class NumbersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHabits)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = HabitsAdapter(MockHabitsData.getNumbers()) { habit->
            Toast.makeText(requireContext(), "${habit.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter


    }
}
