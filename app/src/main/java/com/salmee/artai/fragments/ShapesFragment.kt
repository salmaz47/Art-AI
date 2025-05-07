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
import com.salmee.artai.adapter.ShapesAdapter
import com.salmee.artai.data.MockHabitsData

class ShapesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shapes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHabits)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val shapes = MockHabitsData.getShapes()
        val adapter = ShapesAdapter(shapes) { selectedHabit ->
            Toast.makeText(requireContext(), selectedHabit.name, Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
    }
}
