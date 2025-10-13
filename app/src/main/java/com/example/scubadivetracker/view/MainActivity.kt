package com.example.scubadivetracker.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scubadivetracker.R
import com.example.scubadivetracker.model.Dive.Dive
import com.example.scubadivetracker.viewmodel.DiveViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * The main activity of the Scuba Dive Tracker app.
 *
 * This activity displays a list of all logged dives using a RecyclerView.
 * It observes data from the DiveViewModel to update the UI whenever the database changes.
 * Users can view, select, and add dives from this screen.
 */
class MainActivity : AppCompatActivity() {
    /** ViewModel that manages data between the UI and the repository layer. */
    private val diveViewModel: DiveViewModel by viewModels()
    /** Adapter responsible for displaying the list of dives in the RecyclerView. */
    private lateinit var diveAdapter: DiveAdapter

    /**
     * Activity result launcher for handling results returned from {@link AddDiveActivity}.
     *
     * When a user saves a new or edited dive, the result is captured here
     * and inserted into the database through the ViewModel.
     */
    private val addDiveLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val location = data.getStringExtra("location") ?: ""
                val depth = data.getStringExtra("depth")?.toDoubleOrNull() ?: 0.0
                val duration = data.getStringExtra("duration")?.toIntOrNull() ?: 0
                val date = data.getStringExtra("date") ?: ""
                val notes = data.getStringExtra("notes") ?: ""

                // Create a new Dive object and insert it
                val newDive = Dive(
                    id = 0, // Room auto-generates this
                    location = location,
                    depth = depth,
                    duration = duration,
                    date = date,
                    notes = notes
                )
                diveViewModel.insert(newDive)
                Toast.makeText(this, "Dive added at $location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Called when the activity is first created.
         *
         * Initializes the RecyclerView, connects it to the adapter, observes
         * the dive list LiveData from the ViewModel, and sets up the Floating Action Button
         * to open the AddDiveActivity when clicked.
         *
         * @param savedInstanceState The previously saved instance state, if available.
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDives)
        val fabAddDive = findViewById<FloatingActionButton>(R.id.fabAddDive)

        // Initialize the  adapter with click and long-click actions
        diveAdapter = DiveAdapter(
            emptyList(),
            onClick = { dive -> openDiveDetail(dive) },
            onLongClick = { _ -> } // because DiveAdapter constructor doesn't allow optional params
        )

        // Configure the RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = diveAdapter
        }

        // Observe database changes and update RecyclerView
        diveViewModel.allDives.observe(this) { dives ->
            diveAdapter.updateDives(dives)
        }

        // Launch AddDiveActivity to add a new dive when floating action button is clicked
        fabAddDive.setOnClickListener {
            val intent = Intent(this, AddDiveActivity::class.java)
            addDiveLauncher.launch(intent)
        }
    }
    /**
     * Opens the DiveDetailActivity for the selected dive.
     *
     * @param dive The {@link Dive} object that was selected from the list.
     */
    private fun openDiveDetail(dive: Dive) {
        val intent = Intent(this, DiveDetailActivity::class.java)
        intent.putExtra("DIVE_ID", dive.id)
        startActivity(intent)
    }
}
