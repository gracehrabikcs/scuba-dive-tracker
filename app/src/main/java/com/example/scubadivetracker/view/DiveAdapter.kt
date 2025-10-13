package com.example.scubadivetracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scubadivetracker.R
import com.example.scubadivetracker.model.Dive.Dive
/**
 * Adapter class for displaying a list of [Dive] objects in a RecyclerView.
 *
 * The adapter binds dive data (location, depth, duration) to individual list item views.
 * It also provides click and long-click listeners for handling user interactions.
 *
 * @property dives The current list of dive entries to display.
 * @property onClick Callback function invoked when a dive item is clicked.
 * @property onLongClick Callback function invoked when a dive item is long-clicked.
 */
class DiveAdapter(
    private var dives: List<Dive>,
    private val onClick: (Dive) -> Unit,
    private val onLongClick: (Dive) -> Unit
) : RecyclerView.Adapter<DiveAdapter.DiveViewHolder>() {

    /**
     * ViewHolder class that holds references to the views for each dive item.
     *
     * Each item view contains a location TextView and a summary TextView displaying
     * dive details such as depth and duration.
     *
     * @param view The item view for a single dive entry.
     */
    inner class DiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLocation: TextView = view.findViewById(R.id.tvDiveLocation)
        val tvSummary: TextView = view.findViewById(R.id.tvDiveSummary)
    }

    /**
     * Creates a new [DiveViewHolder] instance by inflating the item layout.
     *
     * @param parent The parent view group into which the new view will be added.
     * @param viewType The view type of the new view (unused here since there's only one type).
     * @return A new [DiveViewHolder] instance holding the inflated view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dive, parent, false)
        return DiveViewHolder(view)
    }

    /**
     * Binds a [Dive] object to the views in a [DiveViewHolder].
     *
     * This method sets the location and summary text for each dive and assigns
     * click and long-click listeners for user interaction.
     *
     * @param holder The [DiveViewHolder] that holds the views for this item.
     * @param position The position of the dive in the list.
     */
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

    /**
     * Returns the total number of dive items in the list.
     *
     * @return The number of dives currently displayed.
     */
    override fun getItemCount() = dives.size

    /**
     * Updates the adapter with a new list of dives and refreshes the UI.
     *
     * This method replaces the current list of dives and calls [notifyDataSetChanged]
     * to re-render the RecyclerView with the new data.
     *
     * @param newDives The updated list of [Dive] objects to display.
     */
    fun updateDives(newDives: List<Dive>) {
        dives = newDives
        notifyDataSetChanged()
    }
}
