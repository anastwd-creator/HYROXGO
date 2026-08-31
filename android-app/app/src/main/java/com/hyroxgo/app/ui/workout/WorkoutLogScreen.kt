package com.hyroxgo.app.ui.workout

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyroxgo.app.data.model.Category
import com.hyroxgo.app.data.model.Gender
import com.hyroxgo.app.data.model.HyroxDivisionData
import com.hyroxgo.app.data.model.StationSpec
import com.hyroxgo.app.data.model.WorkoutLog
import com.hyroxgo.app.ui.components.HyroxBigButton
import com.hyroxgo.app.ui.components.HyroxDropdownField
import com.hyroxgo.app.ui.theme.Charcoal500
import com.hyroxgo.app.ui.theme.Charcoal600
import com.hyroxgo.app.ui.theme.Charcoal700
import com.hyroxgo.app.ui.theme.Charcoal800
import com.hyroxgo.app.ui.theme.Charcoal900
import com.hyroxgo.app.ui.theme.ElectricBlue
import com.hyroxgo.app.ui.theme.NeonYellow
import com.hyroxgo.app.ui.theme.NeonYellowContainer
import com.hyroxgo.app.ui.theme.RunBlue
import com.hyroxgo.app.ui.theme.TextPrimary
import com.hyroxgo.app.ui.theme.TextSecondary
import com.hyroxgo.app.ui.theme.TextTertiary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.hyroxgo.app.ui.i18n.AppLanguage
import com.hyroxgo.app.ui.i18n.HyroxStrings
import com.hyroxgo.app.ui.i18n.LocalAppLanguage

@Composable
fun WorkoutLogScreen(
    viewModel: WorkoutViewModel,
    gender: Gender,
    category: Category,
    customStationSpecs: List<StationSpec>? = null,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val allLogs by viewModel.allLogs.collectAsStateWithLifecycle()
    val weeklyStats by viewModel.weeklyRunningStats.collectAsStateWithLifecycle()
    val movementStats by viewModel.movementStats.collectAsStateWithLifecycle()

    var isAddingLog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Weekly Running Distance Hero Card (Elegant Dark 24dp rounded card)
            WeeklyRunningCard(stats = weeklyStats)
        }

        item {
            // Big Button for Quick Logging
            if (!isAddingLog) {
                HyroxBigButton(
                    text = HyroxStrings.logWorkoutSessionButton(lang),
                    subText = HyroxStrings.logWorkoutSubText(lang),
                    icon = Icons.Default.Add,
                    isPrimary = true,
                    onClick = { isAddingLog = true },
                    testTag = "open_log_form_button"
                )
            }
        }

        // Expanded Quick Logger Form
        if (isAddingLog) {
            item {
                QuickLogForm(
                    gender = gender,
                    category = category,
                    customStationSpecs = customStationSpecs,
                    onSaveRun = { distance, mins, secs, notes ->
                        viewModel.logRun(distance, mins, secs, notes)
                        isAddingLog = false
                    },
                    onSaveStation = { movement, dist, weight, reps, mins, secs, notes ->
                        viewModel.logStation(movement, dist, weight, reps, mins, secs, notes)
                        isAddingLog = false
                    },
                    onCancel = { isAddingLog = false }
                )
            }
        }

        // 8 HYROX Movements Tracker
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = HyroxStrings.movementsProgressHeader(lang),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.4.sp,
                        fontWeight = FontWeight.Black,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(HyroxDivisionData.MOVEMENTS) { movement ->
                        val stat = movementStats[movement]
                        MovementStatChip(
                            name = movement,
                            stat = stat
                        )
                    }
                }
            }
        }

        // Activity Log History
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = HyroxStrings.recentActivityHeader(allLogs.size, lang),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.4.sp,
                        fontWeight = FontWeight.Black,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                )
            }
        }

        if (allLogs.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Charcoal800)
                        .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = HyroxStrings.noWorkoutsLoggedTitle(lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = HyroxStrings.noWorkoutsLoggedSub(lang),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        } else {
            items(allLogs) { log ->
                WorkoutLogItem(
                    log = log,
                    onDelete = { viewModel.deleteLog(log.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun WeeklyRunningCard(
    stats: WeeklyRunningStats,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val mins = stats.totalDurationSeconds / 60
    val avgPaceMin = stats.averagePaceSecondsPerKm / 60
    val avgPaceSec = stats.averagePaceSecondsPerKm % 60
    val avgPaceFormatted = if (stats.averagePaceSecondsPerKm > 0) {
        String.format(Locale.US, "%d:%02d /km", avgPaceMin, avgPaceSec)
    } else {
        "--:-- /km"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("weekly_running_card"),
        colors = CardDefaults.cardColors(containerColor = Charcoal800),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Charcoal600)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = null,
                        tint = RunBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = HyroxStrings.weeklyRunningDistance(lang),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = RunBlue,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.4.sp,
                            fontSize = 11.sp
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Charcoal700)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = HyroxStrings.runsCountLabel(stats.runsCount, lang),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }

                    val context = LocalContext.current
                    IconButton(
                        onClick = { shareWeeklySummary(context, stats, lang) },
                        modifier = Modifier
                            .size(28.dp)
                            .testTag("share_weekly_stats_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Weekly Summary",
                            tint = ElectricBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Big Total Distance Display
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = String.format(Locale.US, "%.1f", stats.totalDistanceKm),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = TextPrimary
                    )
                )
                Text(
                    text = "KM",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = NeonYellow,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            // Metric Tiles
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Charcoal700)
                    .border(BorderStroke(1.dp, Charcoal500), RoundedCornerShape(12.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(HyroxStrings.totalTimeLabel(lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, letterSpacing = 0.8.sp))
                    Text(
                        text = "${mins}m",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(HyroxStrings.avgPaceLabel(lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, letterSpacing = 0.8.sp))
                    Text(
                        text = avgPaceFormatted,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = RunBlue,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MovementStatChip(
    name: String,
    stat: MovementBestStat?,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val translatedName = HyroxStrings.translateStationName(name, lang)

    Box(
        modifier = modifier
            .width(136.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Charcoal800)
            .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = translatedName.uppercase(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = TextPrimary,
                    fontSize = 11.sp,
                    letterSpacing = 0.3.sp
                ),
                maxLines = 1
            )

            if (stat != null && stat.totalSessions > 0) {
                val bestMin = stat.bestDurationSeconds / 60
                val bestSec = stat.bestDurationSeconds % 60
                if (stat.bestDurationSeconds > 0) {
                    Text(
                        text = "PR: ${String.format(Locale.US, "%02d:%02d", bestMin, bestSec)}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NeonYellow,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                } else {
                    Text(
                        text = HyroxStrings.sessionsCount(stat.totalSessions, lang),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = TextSecondary
                        )
                    )
                }
            } else {
                Text(
                    text = HyroxStrings.noLogsYet(lang),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextTertiary,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
fun QuickLogForm(
    gender: Gender,
    category: Category,
    customStationSpecs: List<StationSpec>? = null,
    onSaveRun: (Double, Int, Int, String) -> Unit,
    onSaveStation: (String, Double, Double, Int, Int, Int, String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    var isRunMode by remember { mutableStateOf(true) }

    // Run inputs
    var runDistanceKm by remember { mutableDoubleStateOf(5.0) }
    var runMinutesText by remember { mutableStateOf("24") }
    var runSecondsText by remember { mutableStateOf("30") }

    val availableMovementOptions = remember(category, customStationSpecs) {
        if (category == Category.CUSTOM && customStationSpecs != null) {
            (customStationSpecs.map { it.name } + HyroxDivisionData.MOVEMENTS).distinct()
        } else {
            HyroxDivisionData.MOVEMENTS
        }
    }

    // Station inputs
    var selectedMovement by remember(availableMovementOptions) { mutableStateOf(availableMovementOptions.first()) }
    var stationWeightKg by remember(gender, category, selectedMovement, customStationSpecs) {
        val defaultSpec = if (category == Category.CUSTOM && customStationSpecs != null) {
            customStationSpecs.firstOrNull { it.name == selectedMovement }
                ?: HyroxDivisionData.getStationSpecs(gender, category).firstOrNull { it.name == selectedMovement }
        } else {
            HyroxDivisionData.getStationSpecs(gender, category).firstOrNull { it.name == selectedMovement }
        }
        mutableDoubleStateOf(defaultSpec?.weightKg ?: 0.0)
    }
    var stationReps by remember { mutableIntStateOf(100) }
    var stationMinutesText by remember { mutableStateOf("3") }
    var stationSecondsText by remember { mutableStateOf("45") }
    var notesText by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("quick_log_form_card"),
        colors = CardDefaults.cardColors(containerColor = Charcoal800),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, NeonYellow)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isRunMode) HyroxStrings.logRunningSessionTitle(lang) else HyroxStrings.logHyroxStationTitle(lang),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        color = NeonYellow,
                        letterSpacing = 1.4.sp,
                        fontSize = 11.sp
                    )
                )
                IconButton(onClick = onCancel, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            // Mode Selector (Run vs Station)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Charcoal700)
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isRunMode) RunBlue else Color.Transparent)
                        .clickable { isRunMode = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = HyroxStrings.runningTab(lang),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isRunMode) Charcoal900 else TextPrimary
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isRunMode) NeonYellow else Color.Transparent)
                        .clickable { isRunMode = false },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = HyroxStrings.stationsTab(lang),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (!isRunMode) Charcoal900 else TextPrimary
                        )
                    )
                }
            }

            if (isRunMode) {
                // Quick Distance Chips
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(HyroxStrings.distanceLabel(lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, letterSpacing = 1.sp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(1.0, 3.0, 5.0, 8.0, 10.0).forEach { dist ->
                            val isSelected = runDistanceKm == dist
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) RunBlue else Charcoal700)
                                    .clickable { runDistanceKm = dist },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${dist.toInt()}km",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Charcoal900 else TextPrimary
                                    )
                                )
                            }
                        }
                    }
                }

                // Duration Inputs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = runMinutesText,
                        onValueChange = { runMinutesText = it },
                        label = { Text(HyroxStrings.minutesLabel(lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RunBlue,
                            unfocusedBorderColor = Charcoal600
                        )
                    )

                    OutlinedTextField(
                        value = runSecondsText,
                        onValueChange = { runSecondsText = it },
                        label = { Text(HyroxStrings.secondsLabel(lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RunBlue,
                            unfocusedBorderColor = Charcoal600
                        )
                    )
                }
            } else {
                // Station Movement Selector
                val translatedOptions = availableMovementOptions.map { HyroxStrings.translateStationName(it, lang) }
                HyroxDropdownField(
                    label = HyroxStrings.stationNameLabel(lang),
                    selectedValue = HyroxStrings.translateStationName(selectedMovement, lang),
                    options = translatedOptions,
                    onSelect = { movTranslated ->
                        val origIdx = translatedOptions.indexOf(movTranslated)
                        val mov = if (origIdx != -1) availableMovementOptions[origIdx] else movTranslated
                        selectedMovement = mov
                        val spec = if (category == Category.CUSTOM && customStationSpecs != null) {
                            customStationSpecs.firstOrNull { it.name == mov }
                                ?: HyroxDivisionData.getStationSpecs(gender, category).firstOrNull { it.name == mov }
                        } else {
                            HyroxDivisionData.getStationSpecs(gender, category).firstOrNull { it.name == mov }
                        }
                        if (spec != null) {
                            stationWeightKg = spec.weightKg
                        }
                    },
                    testTag = "movement_dropdown"
                )

                // Station Duration Inputs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = stationMinutesText,
                        onValueChange = { stationMinutesText = it },
                        label = { Text(HyroxStrings.minutesLabel(lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonYellow,
                            unfocusedBorderColor = Charcoal600
                        )
                    )

                    OutlinedTextField(
                        value = stationSecondsText,
                        onValueChange = { stationSecondsText = it },
                        label = { Text(HyroxStrings.secondsLabel(lang)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonYellow,
                            unfocusedBorderColor = Charcoal600
                        )
                    )
                }
            }

            // Save Big Button
            HyroxBigButton(
                text = HyroxStrings.saveWorkoutEntryButton(lang),
                icon = Icons.Default.Add,
                isPrimary = true,
                onClick = {
                    if (isRunMode) {
                        val mins = runMinutesText.toIntOrNull() ?: 0
                        val secs = runSecondsText.toIntOrNull() ?: 0
                        onSaveRun(runDistanceKm, mins, secs, notesText)
                    } else {
                        val mins = stationMinutesText.toIntOrNull() ?: 0
                        val secs = stationSecondsText.toIntOrNull() ?: 0
                        onSaveStation(selectedMovement, 0.0, stationWeightKg, stationReps, mins, secs, notesText)
                    }
                },
                testTag = "confirm_save_workout_button"
            )
        }
    }
}

@Composable
fun WorkoutLogItem(
    log: WorkoutLog,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val isRun = log.type == "RUNNING"
    val durationMin = log.durationSeconds / 60
    val durationSec = log.durationSeconds % 60
    val durationFormatted = String.format(Locale.US, "%02d:%02d", durationMin, durationSec)

    val dateFormat = remember(lang) {
        SimpleDateFormat("MMM dd, yyyy", if (lang == AppLanguage.FR) Locale.FRANCE else Locale.US)
    }
    val dateString = dateFormat.format(Date(log.timestamp))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("workout_log_${log.id}"),
        colors = CardDefaults.cardColors(containerColor = Charcoal800),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Charcoal600)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isRun) RunBlue.copy(alpha = 0.18f) else NeonYellowContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isRun) Icons.AutoMirrored.Filled.DirectionsRun else Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = if (isRun) RunBlue else NeonYellow,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    val titleText = if (isRun) {
                        val distStr = String.format(Locale.US, "%.1f", log.distanceMeters / 1000.0)
                        if (lang == AppLanguage.FR) "Course $distStr km" else "$distStr km Run"
                    } else {
                        HyroxStrings.translateStationName(log.movementName, lang).uppercase()
                    }

                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = dateString,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = durationFormatted,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = if (isRun) RunBlue else NeonYellow
                    )
                )

                val context = LocalContext.current
                IconButton(
                    onClick = { shareWorkoutLog(context, log, lang) },
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("share_workout_log_${log.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share on Social Media",
                        tint = ElectricBlue,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("delete_workout_log_${log.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

fun shareWorkoutLog(context: Context, log: WorkoutLog, lang: AppLanguage = AppLanguage.EN) {
    val isRun = log.type == "RUNNING"
    val durationMin = log.durationSeconds / 60
    val durationSec = log.durationSeconds % 60
    val durationFormatted = String.format(Locale.US, "%02d:%02d", durationMin, durationSec)
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", if (lang == AppLanguage.FR) Locale.FRANCE else Locale.US)
    val dateString = dateFormat.format(Date(log.timestamp))

    val shareText = buildString {
        if (lang == AppLanguage.FR) {
            appendLine("⚡ Entraînement HYROXGO Terminé !")
            if (isRun) {
                val distKm = log.distanceMeters / 1000.0
                val paceSec = if (distKm > 0) (log.durationSeconds / distKm).toInt() else 0
                val paceMin = paceSec / 60
                val paceRemSec = paceSec % 60
                appendLine("🏃 Activité : Course ${String.format(Locale.FRANCE, "%.1f", distKm)} km")
                appendLine("⏱️ Temps : $durationFormatted")
                if (paceSec > 0) {
                    appendLine("⚡ Allure : ${String.format(Locale.FRANCE, "%d:%02d /km", paceMin, paceRemSec)}")
                }
            } else {
                appendLine("🏋️ Station : ${HyroxStrings.translateStationName(log.movementName, lang)}")
                appendLine("⏱️ Temps : $durationFormatted")
                if (log.weightKg > 0) appendLine("⚖️ Poids : ${log.weightKg} kg")
                if (log.reps > 0) appendLine("🔢 Répétitions : ${log.reps}")
                if (log.distanceMeters > 0) appendLine("📏 Distance : ${log.distanceMeters.toInt()} m")
            }
            if (!log.notes.isNullOrBlank()) {
                appendLine("📝 Remarques : ${log.notes}")
            }
            appendLine("📅 Date : $dateString")
            appendLine()
            append("Entraîné avec HYROXGO • #HYROX #HYROXGO #ROXGO #Fitness #Entrainement")
        } else {
            appendLine("⚡ HYROXGO Workout Completed!")
            if (isRun) {
                val distKm = log.distanceMeters / 1000.0
                val paceSec = if (distKm > 0) (log.durationSeconds / distKm).toInt() else 0
                val paceMin = paceSec / 60
                val paceRemSec = paceSec % 60
                appendLine("🏃 Activity: ${String.format(Locale.US, "%.1f", distKm)} km Run")
                appendLine("⏱️ Time: $durationFormatted")
                if (paceSec > 0) {
                    appendLine("⚡ Pace: ${String.format(Locale.US, "%d:%02d /km", paceMin, paceRemSec)}")
                }
            } else {
                appendLine("🏋️ Station: ${log.movementName}")
                appendLine("⏱️ Time: $durationFormatted")
                if (log.weightKg > 0) appendLine("⚖️ Weight: ${log.weightKg} kg")
                if (log.reps > 0) appendLine("🔢 Reps: ${log.reps}")
                if (log.distanceMeters > 0) appendLine("📏 Distance: ${log.distanceMeters.toInt()} m")
            }
            if (!log.notes.isNullOrBlank()) {
                appendLine("📝 Notes: ${log.notes}")
            }
            appendLine("📅 Date: $dateString")
            appendLine()
            append("Trained with HYROXGO • #HYROX #HYROXGO #ROXGO #Fitness #Workout")
        }
    }

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, if (lang == AppLanguage.FR) "Partager l'entraînement" else "Share Workout Log")
    context.startActivity(shareIntent)
}

fun shareWeeklySummary(context: Context, stats: WeeklyRunningStats, lang: AppLanguage = AppLanguage.EN) {
    val mins = stats.totalDurationSeconds / 60
    val hours = mins / 60
    val remMins = mins % 60
    val durationText = if (hours > 0) "${hours}h ${remMins}m" else "${mins}m"
    val avgPaceMin = stats.averagePaceSecondsPerKm / 60
    val avgPaceSec = stats.averagePaceSecondsPerKm % 60
    val avgPaceFormatted = if (stats.averagePaceSecondsPerKm > 0) {
        String.format(Locale.US, "%d:%02d /km", avgPaceMin, avgPaceSec)
    } else {
        "--:-- /km"
    }

    val shareText = buildString {
        if (lang == AppLanguage.FR) {
            appendLine("⚡ Rapport de Course Hebdomadaire HYROXGO")
            appendLine("🏃 Distance Totale : ${String.format(Locale.FRANCE, "%.1f", stats.totalDistanceKm)} km")
            appendLine("🔥 Nombre de Courses : ${stats.runsCount}")
            appendLine("⏱️ Temps Total : $durationText")
            appendLine("⚡ Allure Moyenne : $avgPaceFormatted")
            appendLine()
            append("Suivi avec HYROXGO • #HYROX #HYROXGO #ROXGO #CourseAPied #EntrainementHyrox")
        } else {
            appendLine("⚡ HYROXGO Weekly Running Report")
            appendLine("🏃 Total Distance: ${String.format(Locale.US, "%.1f", stats.totalDistanceKm)} km")
            appendLine("🔥 Total Runs: ${stats.runsCount}")
            appendLine("⏱️ Total Time: $durationText")
            appendLine("⚡ Avg Pace: $avgPaceFormatted")
            appendLine()
            append("Tracked with HYROXGO • #HYROX #HYROXGO #ROXGO #Running #HyroxTraining")
        }
    }

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, if (lang == AppLanguage.FR) "Partager le rapport de course" else "Share Weekly Running Report")
    context.startActivity(shareIntent)
}
