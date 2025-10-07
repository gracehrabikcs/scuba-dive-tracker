package com.example.scubadivetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var diveListView: ListView
    private lateinit var fabAddDive: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diveListView = findViewById(R.id.diveListView)
        fabAddDive = findViewById(R.id.fabAddDive)

        //diveListView = findViewById<ListView>(R.id.diveListView)
        //fabAddDive = findViewById<FloatingActionButton>(R.id.fabAddDive)

        val dives = listOf(
            "Cozumel Reef - 60ft - 45min",
            "Blue Hole - 100ft - 35min",
            "Maldives Drift - 70ft - 50min"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dives)
        diveListView.adapter = adapter

        fabAddDive.setOnClickListener {
            Toast.makeText(this, "Add Dive clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}
