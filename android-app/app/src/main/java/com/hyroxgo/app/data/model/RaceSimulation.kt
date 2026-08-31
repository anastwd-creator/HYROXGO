package com.hyroxgo.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_simulations")
data class RaceSimulation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val divisionTitle: String,
    val totalTimeSeconds: Long,
    val totalRunTimeSeconds: Long,
    val totalStationTimeSeconds: Long,
    val totalRoxzoneSeconds: Long,
    val splitsSummary: String // Comma or newline separated summary of 16 splits
)

data class RaceSplit(
    val intervalIndex: Int,
    val name: String,
    val isRun: Boolean,
    val splitTimeSeconds: Long,
    val cumulativeTimeSeconds: Long,
    val roxzoneTimeSeconds: Long = 0L
)
