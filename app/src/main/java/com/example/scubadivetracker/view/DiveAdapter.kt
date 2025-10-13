package com.example.scubadivetracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scubadivetracker.R
import com.example.scubadivetracker.model.Dive.Dive

class DiveAdapter(
    private var dives: List<Dive>,
    private val onClick: (Dive) -> Unit,
    private val onLongClick: (Dive) -> Unit
) : RecyclerView.Adapter<DiveAdapter.DiveViewHolder>() {

    inner class DiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLocation: TextView = view.findViewById(R.id.tvDiveLocation)
        val tvSummary: TextView = view.findViewById(R.id.tvDiveSummary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dive, parent, false)
        return DiveViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiveViewHolder, position: Int) {
        val dive = dives[position]
        holder.tvLocation.text = dive.location
        holder.tvSummary.text = "${dive.depth} ft, ${dive.duration} min"

        holder.itemView.setOnClickListener { onClick(dive) }
        holder.itemView.setOnLongClickListener {
            onLongClick(dive)
            true
        }
    }

    override fun getItemCount() = dives.size

    fun updateDives(newDives: List<Dive>) {
        dives = newDives
        notifyDataSetChanged()
    }
}
