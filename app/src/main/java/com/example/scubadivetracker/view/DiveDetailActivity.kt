package com.example.scubadivetracker.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scubadivetracker.R
import com.example.scubadivetracker.model.Dive.Dive
import com.example.scubadivetracker.viewmodel.DiveViewModel

/**
 * Activity that displays detailed information about a specific dive.
 *
 * Users can view, edit, and delete the selected dive entry.
 * Edits are performed by launching [AddDiveActivity] pre-filled with the dive’s data.
 * Deletions are handled directly within this screen.
 */

class DiveDetailActivity : AppCompatActivity() {
    /** ViewModel used to access and modify dive data from the Room database. */
    private val diveViewModel: DiveViewModel by viewModels()

    /** The currently selected dive being viewed or edited. */
    private var currentDive: Dive? = null

    /**
     * Launcher to handle results from [AddDiveActivity].
     * Updates the current dive in the database and refreshes the UI if edits are saved.
     */
    private val editDiveLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null && currentDive != null) {
                val updatedDive = currentDive!!.copy(
                    location = data.getStringExtra("location") ?: currentDive!!.location,
                    depth = data.getStringExtra("depth")?.toDoubleOrNull() ?: currentDive!!.depth,
                    duration = data.getStringExtra("duration")?.toIntOrNull() ?: currentDive!!.duration,
                    date = data.getStringExtra("date") ?: currentDive!!.date,
                    notes = data.getStringExtra("notes") ?: currentDive!!.notes
                )
                diveViewModel.update(updatedDive)
                Toast.makeText(this, "Dive updated!", Toast.LENGTH_SHORT).show()
                displayDiveDetails(updatedDive)
            }
        }
    }
    /**
     * Called when the activity is created.
     *
     * Initializes the layout, retrieves the selected dive by ID,
     * and sets up button listeners for editing and deleting the dive.
     *
     * @param savedInstanceState The saved state of the activity, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive_detail)

        val diveId = intent.getIntExtra("DIVE_ID", -1)
        val tvDetail = findViewById<TextView>(R.id.tvDiveDetail)
        val btnEdit = findViewById<Button>(R.id.btnEditDive)
        val btnDelete = findViewById<Button>(R.id.btnDeleteDive)

        // Observe database for dive data and display matching dive by IDS
        diveViewModel.allDives.observe(this) { dives ->
            currentDive = dives.find { it.id == diveId }
            currentDive?.let { displayDiveDetails(it) }
        }

        // Launch AddDiveActivity to edit existing dives
        btnEdit.setOnClickListener {
            currentDive?.let {
                val intent = Intent(this, AddDiveActivity::class.java).apply {
                    putExtra("location", it.location)
                    putExtra("depth", it.depth.toString())
                    putExtra("duration", it.duration.toString())
                    putExtra("date", it.date)
                    putExtra("notes", it.notes)
                }
                editDiveLauncher.launch(intent)
            }
        }

        // Delete dice and return to main screen
        btnDelete.setOnClickListener {
            currentDive?.let {
                diveViewModel.delete(it)
                Toast.makeText(this, "Dive deleted!", Toast.LENGTH_SHORT).show()
                finish() // go back to main screen
            }
        }
    }
    /**
     * Displays the details of a given dive on screen.
     *
     * @param dive The [Dive] object whose details are shown.
     */
    private fun displayDiveDetails(dive: Dive) {
        findViewById<TextView>(R.id.tvDiveDetail).text = """
            Location: ${dive.location}
            Depth: ${dive.depth} ft
            Duration: ${dive.duration} min
            Date: ${dive.date}
            Notes: ${dive.notes}
        """.trimIndent()
    }
}
