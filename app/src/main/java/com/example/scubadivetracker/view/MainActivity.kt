package com.example.scubadivetracker.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDives)
        val fabAddDive = findViewById<FloatingActionButton>(R.id.fabAddDive)

        diveAdapter = DiveAdapter(emptyList(),
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

        diveViewModel.allDives.observe(this) { dives ->
            diveAdapter.updateDives(dives)
        }

        fabAddDive.setOnClickListener {
            startActivity(Intent(this, AddDiveActivity::class.java))
        }
    }

    private fun openDiveDetail(dive: Dive) {
        val intent = Intent(this, DiveDetailActivity::class.java)
        intent.putExtra("DIVE_ID", dive.id)
        startActivity(intent)
    }
}
