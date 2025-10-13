package com.example.scubadivetracker.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.scubadivetracker.R

class AddDiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dive)

        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etDepth = findViewById<EditText>(R.id.etDepth)
        val etDuration = findViewById<EditText>(R.id.etDuration)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnSave = findViewById<Button>(R.id.btnSaveDive)

        btnSave.setOnClickListener {
            val data = Intent().apply {
                putExtra("location", etLocation.text.toString())
                putExtra("depth", etDepth.text.toString())
                putExtra("duration", etDuration.text.toString())
                putExtra("date", etDate.text.toString())
                putExtra("notes", etNotes.text.toString())
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
