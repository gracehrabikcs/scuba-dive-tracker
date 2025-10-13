package com.example.scubadivetracker.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.scubadivetracker.R
/**
 * The AddDiveActivity class allows users to add a new dive entry
 * or edit an existing dive in the Scuba Dive Tracker app.
 *
 * This activity includes input fields for location, depth, duration,
 * date, and notes, along with a save button that returns the entered data
 * to the the main dive list screen.
 *
 * If the activity receives extras via an Intent, the fields are pre-filled
 * with existing data, allowing the user to update a dive entry instead of
 * creating a new one.
 */
class AddDiveActivity : AppCompatActivity() {
    /**
     * Called when the activity is created.
     *
     * This method initializes the UI components, checks if an existing dive
     * is being edited, pre-fills fields if needed, and sets up the save button
     * to return the entered data to the calling activity.
     *
     * @param savedInstanceState the previously saved state of the activity, if any
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dive)

        // Initialize UI components
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etDepth = findViewById<EditText>(R.id.etDepth)
        val etDuration = findViewById<EditText>(R.id.etDuration)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnSave = findViewById<Button>(R.id.btnSaveDive)

        // Check if we're editing an existing dive (intent contains extras)
        val existingLocation = intent.getStringExtra("location")
        val existingDepth = intent.getStringExtra("depth")
        val existingDuration = intent.getStringExtra("duration")
        val existingDate = intent.getStringExtra("date")
        val existingNotes = intent.getStringExtra("notes")

        // If extras exist, pre-fill the fields
        etLocation.setText(existingLocation ?: "")
        etDepth.setText(existingDepth ?: "")
        etDuration.setText(existingDuration ?: "")
        etDate.setText(existingDate ?: "")
        etNotes.setText(existingNotes ?: "")

        // Change button text if editing
        if (existingLocation != null) {
            btnSave.text = "Update Dive"
        }

        // When user saves, return data back to calling activity
        // Set event listener for the save button
        // When clicked, collects all entered data, packages it into an Intent,
        // and returns it to the calling activity with a RESULT_OK code
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
