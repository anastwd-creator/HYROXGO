package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_logs")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val type: String, // "RUNNING" or "STATION"
    val movementName: String, // "Running", "SkiErg", "Sled Push", "Sled Pull", "Burpee Broad Jumps", "Rowing", "Farmers Carry", "Sandbag Lunges", "Wall Balls"
    val distanceMeters: Double = 0.0,
    val durationSeconds: Long = 0L,
    val weightKg: Double = 0.0,
    val reps: Int = 0,
    val notes: String = ""
)
