package com.hyroxgo.app.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyroxgo.app.data.model.Category
import com.hyroxgo.app.data.model.Gender
import com.hyroxgo.app.data.model.HyroxDivisionData
import com.hyroxgo.app.data.model.RaceSimulation
import com.hyroxgo.app.data.model.RaceSplit
import com.hyroxgo.app.data.model.StationSpec
import com.hyroxgo.app.data.repository.HyroxRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class TimerStatus {
    IDLE,
    RUNNING,
    PAUSED,
    FINISHED
}

data class TimerUiState(
    val status: TimerStatus = TimerStatus.IDLE,
    val currentIntervalIndex: Int = 0,
    val totalTimeMillis: Long = 0L,
    val intervalTimeMillis: Long = 0L,
    val splits: List<RaceSplit> = emptyList(),
    val totalRunTimeMillis: Long = 0L,
    val totalStationTimeMillis: Long = 0L,
    val savedSuccessfully: Boolean = false
)

class RaceTimerViewModel(
    private val repository: HyroxRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    val raceHistory: StateFlow<List<RaceSimulation>> = repository.allRaceSimulations
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var timerJob: Job? = null
    private var lastTickTime: Long = 0L

    fun getIntervalNames(customStationSpecs: List<StationSpec>? = null): List<String> {
        return if (customStationSpecs != null && customStationSpecs.isNotEmpty()) {
            HyroxDivisionData.getRaceIntervalNames(customStationSpecs)
        } else {
            HyroxDivisionData.RACE_INTERVAL_NAMES
        }
    }

    val intervalNames = HyroxDivisionData.RACE_INTERVAL_NAMES

    fun startTimer() {
        if (_uiState.value.status == TimerStatus.RUNNING) return

        lastTickTime = System.currentTimeMillis()
        _uiState.update { it.copy(status = TimerStatus.RUNNING, savedSuccessfully = false) }

        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(50)
                val now = System.currentTimeMillis()
                val delta = now - lastTickTime
                lastTickTime = now

                _uiState.update { current ->
                    if (current.status == TimerStatus.RUNNING) {
                        current.copy(
                            totalTimeMillis = current.totalTimeMillis + delta,
                            intervalTimeMillis = current.intervalTimeMillis + delta
                        )
                    } else {
                        current
                    }
                }
            }
        }
    }

    fun pauseTimer() {
        _uiState.update { it.copy(status = TimerStatus.PAUSED) }
    }

    fun resumeTimer() {
        lastTickTime = System.currentTimeMillis()
        _uiState.update { it.copy(status = TimerStatus.RUNNING) }
    }

    fun logSplit(customIntervalNames: List<String>? = null) {
        val current = _uiState.value
        if (current.status != TimerStatus.RUNNING && current.status != TimerStatus.PAUSED) return

        val names = customIntervalNames ?: intervalNames
        val currentIndex = current.currentIntervalIndex
        val isRun = currentIndex % 2 == 0
        val intervalName = names.getOrElse(currentIndex) { "Interval ${currentIndex + 1}" }
        val splitDurationSec = current.intervalTimeMillis / 1000
        val cumulativeSec = current.totalTimeMillis / 1000

        val newSplit = RaceSplit(
            intervalIndex = currentIndex,
            name = intervalName,
            isRun = isRun,
            splitTimeSeconds = splitDurationSec,
            cumulativeTimeSeconds = cumulativeSec
        )

        val updatedSplits = current.splits + newSplit
        val newRunTotal = if (isRun) current.totalRunTimeMillis + current.intervalTimeMillis else current.totalRunTimeMillis
        val newStationTotal = if (!isRun) current.totalStationTimeMillis + current.intervalTimeMillis else current.totalStationTimeMillis

        val nextIndex = currentIndex + 1
        val isFinished = nextIndex >= names.size

        if (isFinished) {
            timerJob?.cancel()
            _uiState.update {
                it.copy(
                    status = TimerStatus.FINISHED,
                    currentIntervalIndex = nextIndex,
                    intervalTimeMillis = 0L,
                    splits = updatedSplits,
                    totalRunTimeMillis = newRunTotal,
                    totalStationTimeMillis = newStationTotal
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    currentIntervalIndex = nextIndex,
                    intervalTimeMillis = 0L,
                    splits = updatedSplits,
                    totalRunTimeMillis = newRunTotal,
                    totalStationTimeMillis = newStationTotal
                )
            }
        }
    }

    fun undoLastSplit() {
        val current = _uiState.value
        if (current.splits.isEmpty()) return

        val lastSplit = current.splits.last()
        val remainingSplits = current.splits.dropLast(1)
        val prevIndex = (current.currentIntervalIndex - 1).coerceAtLeast(0)

        val revertedRun = if (lastSplit.isRun) {
            (current.totalRunTimeMillis - lastSplit.splitTimeSeconds * 1000).coerceAtLeast(0L)
        } else current.totalRunTimeMillis

        val revertedStation = if (!lastSplit.isRun) {
            (current.totalStationTimeMillis - lastSplit.splitTimeSeconds * 1000).coerceAtLeast(0L)
        } else current.totalStationTimeMillis

        _uiState.update {
            it.copy(
                status = if (it.status == TimerStatus.FINISHED) TimerStatus.PAUSED else it.status,
                currentIntervalIndex = prevIndex,
                intervalTimeMillis = current.intervalTimeMillis + (lastSplit.splitTimeSeconds * 1000),
                splits = remainingSplits,
                totalRunTimeMillis = revertedRun,
                totalStationTimeMillis = revertedStation
            )
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _uiState.value = TimerUiState()
    }

    fun saveRaceSimulation(
        gender: Gender,
        category: Category,
        ageGroup: String,
        customDivisionName: String? = null
    ) {
        val current = _uiState.value
        if (current.splits.isEmpty()) return

        viewModelScope.launch {
            val totalSec = current.totalTimeMillis / 1000
            val runSec = current.totalRunTimeMillis / 1000
            val stationSec = current.totalStationTimeMillis / 1000
            val roxzoneSec = (totalSec - (runSec + stationSec)).coerceAtLeast(0L)

            val summaryLines = current.splits.joinToString("\n") { split ->
                val min = split.splitTimeSeconds / 60
                val sec = split.splitTimeSeconds % 60
                "${split.name}: ${String.format("%02d:%02d", min, sec)}"
            }

            val title = if (category == Category.CUSTOM) {
                val customName = if (!customDivisionName.isNullOrBlank()) customDivisionName else "Custom Division"
                "$customName (${gender.label} • $ageGroup)"
            } else {
                "${gender.label} ${category.label} ($ageGroup)"
            }

            val race = RaceSimulation(
                divisionTitle = title,
                totalTimeSeconds = totalSec,
                totalRunTimeSeconds = runSec,
                totalStationTimeSeconds = stationSec,
                totalRoxzoneSeconds = roxzoneSec,
                splitsSummary = summaryLines
            )

            repository.insertRaceSimulation(race)
            _uiState.update { it.copy(savedSuccessfully = true) }
        }
    }

    fun deleteRaceSimulation(id: Int) {
        viewModelScope.launch {
            repository.deleteRaceSimulation(id)
        }
    }
}
