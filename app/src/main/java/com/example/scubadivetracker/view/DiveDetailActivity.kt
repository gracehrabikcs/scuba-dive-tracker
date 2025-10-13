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

class DiveDetailActivity : AppCompatActivity() {

    private val diveViewModel: DiveViewModel by viewModels()
    private var currentDive: Dive? = null

    // launcher to get edited dive data
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive_detail)

        val diveId = intent.getIntExtra("DIVE_ID", -1)
        val tvDetail = findViewById<TextView>(R.id.tvDiveDetail)
        val btnEdit = findViewById<Button>(R.id.btnEditDive)
        val btnDelete = findViewById<Button>(R.id.btnDeleteDive)

        diveViewModel.allDives.observe(this) { dives ->
            currentDive = dives.find { it.id == diveId }
            currentDive?.let { displayDiveDetails(it) }
        }

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

        btnDelete.setOnClickListener {
            currentDive?.let {
                diveViewModel.delete(it)
                Toast.makeText(this, "Dive deleted!", Toast.LENGTH_SHORT).show()
                finish() // go back to main screen
            }
        }
    }

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
