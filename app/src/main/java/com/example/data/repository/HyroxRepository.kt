package com.example.data.repository

import com.example.data.db.HyroxDao
import com.example.data.model.RaceSimulation
import com.example.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow

class HyroxRepository(private val dao: HyroxDao) {
    val allWorkoutLogs: Flow<List<WorkoutLog>> = dao.getAllWorkoutLogs()
    val runningLogs: Flow<List<WorkoutLog>> = dao.getRunningLogs()
    val stationLogs: Flow<List<WorkoutLog>> = dao.getStationLogs()
    val allRaceSimulations: Flow<List<RaceSimulation>> = dao.getAllRaceSimulations()

    suspend fun insertWorkoutLog(log: WorkoutLog): Long {
        return dao.insertWorkoutLog(log)
    }

    suspend fun deleteWorkoutLog(id: Int) {
        dao.deleteWorkoutLog(id)
    }

    suspend fun insertRaceSimulation(race: RaceSimulation): Long {
        return dao.insertRaceSimulation(race)
    }

    suspend fun deleteRaceSimulation(id: Int) {
        dao.deleteRaceSimulation(id)
    }
}
