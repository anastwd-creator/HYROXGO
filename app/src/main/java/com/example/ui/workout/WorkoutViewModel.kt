package com.example.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.HyroxDivisionData
import com.example.data.model.WorkoutLog
import com.example.data.repository.HyroxRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class WeeklyRunningStats(
    val totalDistanceKm: Double = 0.0,
    val totalDurationSeconds: Long = 0L,
    val runsCount: Int = 0,
    val averagePaceSecondsPerKm: Long = 0L
)

data class MovementBestStat(
    val movementName: String,
    val bestDurationSeconds: Long = 0L,
    val lastWeightKg: Double = 0.0,
    val totalSessions: Int = 0
)

class WorkoutViewModel(
    private val repository: HyroxRepository
) : ViewModel() {

    val allLogs: StateFlow<List<WorkoutLog>> = repository.allWorkoutLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val runningLogs: StateFlow<List<WorkoutLog>> = repository.runningLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val weeklyRunningStats: StateFlow<WeeklyRunningStats> = repository.runningLogs
        .map { logs ->
            calculateWeeklyRunningStats(logs)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WeeklyRunningStats()
        )

    val movementStats: StateFlow<Map<String, MovementBestStat>> = repository.stationLogs
        .map { logs ->
            calculateMovementStats(logs)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    fun logRun(distanceKm: Double, minutes: Int, seconds: Int, notes: String = "") {
        viewModelScope.launch {
            val totalSeconds = (minutes * 60L) + seconds
            val distanceMeters = distanceKm * 1000.0
            val log = WorkoutLog(
                type = "RUNNING",
                movementName = "Running",
                distanceMeters = distanceMeters,
                durationSeconds = totalSeconds,
                notes = notes
            )
            repository.insertWorkoutLog(log)
        }
    }

    fun logStation(
        movementName: String,
        distanceMeters: Double,
        weightKg: Double,
        reps: Int,
        minutes: Int,
        seconds: Int,
        notes: String = ""
    ) {
        viewModelScope.launch {
            val totalSeconds = (minutes * 60L) + seconds
            val log = WorkoutLog(
                type = "STATION",
                movementName = movementName,
                distanceMeters = distanceMeters,
                durationSeconds = totalSeconds,
                weightKg = weightKg,
                reps = reps,
                notes = notes
            )
            repository.insertWorkoutLog(log)
        }
    }

    fun deleteLog(id: Int) {
        viewModelScope.launch {
            repository.deleteWorkoutLog(id)
        }
    }

    private fun calculateWeeklyRunningStats(logs: List<WorkoutLog>): WeeklyRunningStats {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val startOfWeekMs = cal.timeInMillis

        val thisWeekRuns = logs.filter { it.timestamp >= startOfWeekMs && it.type == "RUNNING" }
        val totalDistanceKm = thisWeekRuns.sumOf { it.distanceMeters } / 1000.0
        val totalDurationSeconds = thisWeekRuns.sumOf { it.durationSeconds }
        val count = thisWeekRuns.size
        val avgPace = if (totalDistanceKm > 0.0) {
            (totalDurationSeconds / totalDistanceKm).toLong()
        } else {
            0L
        }

        return WeeklyRunningStats(
            totalDistanceKm = totalDistanceKm,
            totalDurationSeconds = totalDurationSeconds,
            runsCount = count,
            averagePaceSecondsPerKm = avgPace
        )
    }

    private fun calculateMovementStats(logs: List<WorkoutLog>): Map<String, MovementBestStat> {
        val map = mutableMapOf<String, MovementBestStat>()
        HyroxDivisionData.MOVEMENTS.forEach { movement ->
            val movementLogs = logs.filter { it.movementName.equals(movement, ignoreCase = true) }
            if (movementLogs.isNotEmpty()) {
                val fastestWithTime = movementLogs.filter { it.durationSeconds > 0 }.minByOrNull { it.durationSeconds }
                val latest = movementLogs.first()
                map[movement] = MovementBestStat(
                    movementName = movement,
                    bestDurationSeconds = fastestWithTime?.durationSeconds ?: 0L,
                    lastWeightKg = latest.weightKg,
                    totalSessions = movementLogs.size
                )
            }
        }
        return map
    }
}
