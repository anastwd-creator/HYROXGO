package com.hyroxgo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyroxgo.app.data.db.HyroxDatabase
import com.hyroxgo.app.data.repository.HyroxRepository
import com.hyroxgo.app.ui.HyroxApp
import com.hyroxgo.app.ui.theme.MyApplicationTheme
import com.hyroxgo.app.ui.timer.RaceTimerViewModel
import com.hyroxgo.app.ui.workout.WorkoutViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = HyroxDatabase.getDatabase(this)
        val repository = HyroxRepository(database.hyroxDao())

        val timerViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RaceTimerViewModel(repository) as T
                }
            }
        )[RaceTimerViewModel::class.java]

        val workoutViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return WorkoutViewModel(repository) as T
                }
            }
        )[WorkoutViewModel::class.java]

        setContent {
            MyApplicationTheme {
                HyroxApp(
                    raceTimerViewModel = timerViewModel,
                    workoutViewModel = workoutViewModel
                )
            }
        }
    }
}
