package com.example.scubadivetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.scubadivetracker.model.Dive.Dive
import com.example.scubadivetracker.model.Dive.DiveDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiveViewModel(application: Application) : AndroidViewModel(application) {
    private val diveDao = DiveDatabase.getDatabase(application).diveDao()
    val allDives: LiveData<List<Dive>> = diveDao.getAllDives()

    fun insert(dive: Dive) {
        viewModelScope.launch(Dispatchers.IO) {
            diveDao.insert(dive)
        }
    }

    fun delete(dive: Dive) {
        viewModelScope.launch(Dispatchers.IO) {
            diveDao.delete(dive)
        }
    }

    fun update(dive: Dive) {
        viewModelScope.launch(Dispatchers.IO) {
            diveDao.update(dive)
        }
    }
}
