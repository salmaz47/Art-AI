package com.salmee.artai.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salmee.artai.R
import com.salmee.artai.data.MockHabitsData
import com.salmee.artai.adapters.PaintAdapter

/**
 * Fragment for displaying a grid of paint items
 */
class PaintFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var paintAdapter: PaintAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyListMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerViewHabits)
        progressBar = view.findViewById(R.id.progressBar)
        emptyListMessage = view.findViewById(R.id.emptyListMessage)

        // Set up RecyclerView
        setupRecyclerView()

        // Load data
        loadPaintItems()
    }

    private fun setupRecyclerView() {
        // Create adapter with empty list (will be updated)
        paintAdapter = PaintAdapter(emptyList())

        // Set layout manager (despite the layoutManager attribute in XML, we need to set it programmatically as well)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Set adapter
        recyclerView.adapter = paintAdapter
    }

    private fun loadPaintItems() {
        // Show progress while loading
        progressBar.visibility = View.VISIBLE

        // Get drawing items from mock data
        val paintItems = MockHabitsData.getDrawings()

        // Hide progress
        progressBar.visibility = View.GONE

        // Check if list is empty
        if (paintItems.isEmpty()) {
            emptyListMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyListMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            // Update adapter with data
            paintAdapter.updatePaints(paintItems)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PaintFragment()
    }
}