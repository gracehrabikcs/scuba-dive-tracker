package com.example.scubadivetracker.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scubadivetracker.R
import com.example.scubadivetracker.viewmodel.DiveViewModel
import android.widget.TextView

class DiveDetailActivity : AppCompatActivity() {
    private val diveViewModel: DiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive_detail)

        val diveId = intent.getIntExtra("DIVE_ID", -1)
        val tvDetail = findViewById<TextView>(R.id.tvDiveDetail)

        diveViewModel.allDives.observe(this) { dives ->
            val dive = dives.find { it.id == diveId }
            dive?.let {
                tvDetail.text = """
                    Location: ${it.location}
                    Depth: ${it.depth} ft
                    Duration: ${it.duration} min
                    Date: ${it.date}
                    Notes: ${it.notes}
                """.trimIndent()
            }
        }
    }
}
