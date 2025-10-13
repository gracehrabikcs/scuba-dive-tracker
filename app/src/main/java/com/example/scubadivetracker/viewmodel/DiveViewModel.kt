package com.example.scubadivetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.scubadivetracker.model.Dive.Dive
import com.example.scubadivetracker.model.Dive.DiveDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * ViewModel class for managing Dive data and business logic.
 *
 * This class serves as a bridge between the UI and the data layer.
 * It provides access to all dive records and performs insert, update,
 * and delete operations asynchronously using Kotlin coroutines.
 *
 * The ViewModel ensures that the UI always has up-to-date data from the database.
 *
 * @constructor Creates a new instance of [DiveViewModel] tied to the application lifecycle.
 * @param application The application context used to access the Room database.
 */
class DiveViewModel(application: Application) : AndroidViewModel(application) {
    /** Data Access Object (DAO) for interacting with the Dive database. */
    private val diveDao = DiveDatabase.getDatabase(application).diveDao()
    /** LiveData list of all dives, automatically updated when data changes. */
    val allDives: LiveData<List<Dive>> = diveDao.getAllDives()
    /**
     * Inserts a new dive into the database.
     *
     * This operation is executed on a background thread using the IO dispatcher
     * to prevent blocking the main thread.
     *
     * @param dive The [Dive] object to be inserted.
     */
    fun insert(dive: Dive) {
        viewModelScope.launch(Dispatchers.IO) {
            diveDao.insert(dive)
        }
    }
    /**
     * Deletes a dive from the database.
     *
     * Executed asynchronously on the IO thread to maintain UI responsiveness.
     *
     * @param dive The [Dive] object to be deleted.
     */
    fun delete(dive: Dive) {
        viewModelScope.launch(Dispatchers.IO) {
            diveDao.delete(dive)
        }
    }
    /**
     * Updates an existing dive in the database.
     *
     * Executed on a background thread to ensure smooth performance.
     *
     * @param dive The updated [Dive] object to be saved in the database.
     */
    fun update(dive: Dive) {
        viewModelScope.launch(Dispatchers.IO) {
            diveDao.update(dive)
        }
    }
}
