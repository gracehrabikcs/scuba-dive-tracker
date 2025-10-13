package com.example.scubadivetracker.model.Dive
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import android.content.Context


@Database(entities = [Dive::class], version = 1, exportSchema = false)
abstract class DiveDatabase : RoomDatabase() {
    abstract fun diveDao(): DiveDao
    companion object {
        @Volatile private var INSTANCE: DiveDatabase? = null

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