package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.RaceSimulation
import com.example.data.model.WorkoutLog

@Database(
    entities = [WorkoutLog::class, RaceSimulation::class],
    version = 1,
    exportSchema = false
)
abstract class HyroxDatabase : RoomDatabase() {
    abstract fun hyroxDao(): HyroxDao

    companion object {
        @Volatile
        private var INSTANCE: HyroxDatabase? = null

        fun getDatabase(context: Context): HyroxDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HyroxDatabase::class.java,
                    "hyrox_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
