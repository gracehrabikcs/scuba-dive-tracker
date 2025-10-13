package com.example.scubadivetracker.model.Dive

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
/**
 * The Room database for storing dive information.
 *
 * This abstract class defines the database configuration and serves
 * as the main access point to the persisted data.
 *
 * It uses the Dive entity and provides an instance of DiveDao
 * to perform database operations.
 */
@Database(entities = [Dive::class], version = 1, exportSchema = false)
abstract class DiveDatabase : RoomDatabase() {
    /**
     * Provides access to the Data Access Object (DAO) for Dive entities.
     *
     * @return The DiveDao instance used to perform database operations.
     */
    abstract fun diveDao(): DiveDao

    companion object {
        /**
         * A singleton instance of the database to prevent multiple instances
         * from being created at the same time.
         */
        @Volatile
        private var INSTANCE: DiveDatabase? = null

        /**
         * Returns the singleton instance of the database.
         * If it does not exist, it builds a new database instance.
         *
         * @param context The application context used to build the database.
         * @return The DiveDatabase instance.
         *
         * This method ensures thread safety by using synchronization
         * to prevent concurrent initialization.
         */
        fun getDatabase(context: Context): DiveDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiveDatabase::class.java,
                    "dive_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

