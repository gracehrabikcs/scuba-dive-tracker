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

class MainActivity : AppCompatActivity() {

    private val diveViewModel: DiveViewModel by viewModels()
    private lateinit var diveAdapter: DiveAdapter

    // Register launcher to handle AddDiveActivity result
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDives)
        val fabAddDive = findViewById<FloatingActionButton>(R.id.fabAddDive)

        diveAdapter = DiveAdapter(
            emptyList(),
            onClick = { dive -> openDiveDetail(dive) },
            onLongClick = { dive ->
                diveViewModel.delete(dive)
                Toast.makeText(this, "Deleted dive at ${dive.location}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = diveAdapter
        }

        // Observe database changes and update RecyclerView
        diveViewModel.allDives.observe(this) { dives ->
            diveAdapter.updateDives(dives)
        }

        // Launch AddDiveActivity to add a new dive
        fabAddDive.setOnClickListener {
            val intent = Intent(this, AddDiveActivity::class.java)
            addDiveLauncher.launch(intent)
        }
    }

    private fun openDiveDetail(dive: Dive) {
        val intent = Intent(this, DiveDetailActivity::class.java)
        intent.putExtra("DIVE_ID", dive.id)
        startActivity(intent)
    }
}
