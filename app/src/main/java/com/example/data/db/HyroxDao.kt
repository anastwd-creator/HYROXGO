package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.RaceSimulation
import com.example.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow

@Dao
interface HyroxDao {
    @Query("SELECT * FROM workout_logs ORDER BY timestamp DESC")
    fun getAllWorkoutLogs(): Flow<List<WorkoutLog>>

    @Query("SELECT * FROM workout_logs WHERE type = 'RUNNING' ORDER BY timestamp DESC")
    fun getRunningLogs(): Flow<List<WorkoutLog>>

    @Query("SELECT * FROM workout_logs WHERE type = 'STATION' ORDER BY timestamp DESC")
    fun getStationLogs(): Flow<List<WorkoutLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLog): Long

    @Query("DELETE FROM workout_logs WHERE id = :id")
    suspend fun deleteWorkoutLog(id: Int)

    @Query("SELECT * FROM race_simulations ORDER BY timestamp DESC")
    fun getAllRaceSimulations(): Flow<List<RaceSimulation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaceSimulation(race: RaceSimulation): Long

    @Query("DELETE FROM race_simulations WHERE id = :id")
    suspend fun deleteRaceSimulation(id: Int)
}
