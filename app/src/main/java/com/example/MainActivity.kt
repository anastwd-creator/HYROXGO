package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.db.HyroxDatabase
import com.example.data.repository.HyroxRepository
import com.example.ui.HyroxApp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.timer.RaceTimerViewModel
import com.example.ui.workout.WorkoutViewModel

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

