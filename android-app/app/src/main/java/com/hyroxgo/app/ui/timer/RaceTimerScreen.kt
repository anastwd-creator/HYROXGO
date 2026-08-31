package com.hyroxgo.app.ui.timer

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyroxgo.app.data.model.Category
import com.hyroxgo.app.data.model.Gender
import com.hyroxgo.app.data.model.RaceSimulation
import com.hyroxgo.app.data.model.RaceSplit
import com.hyroxgo.app.data.model.StationSpec
import com.hyroxgo.app.ui.components.HyroxBigButton
import com.hyroxgo.app.ui.theme.Charcoal500
import com.hyroxgo.app.ui.theme.Charcoal600
import com.hyroxgo.app.ui.theme.Charcoal700
import com.hyroxgo.app.ui.theme.Charcoal800
import com.hyroxgo.app.ui.theme.Charcoal900
import com.hyroxgo.app.ui.theme.ErrorRed
import com.hyroxgo.app.ui.theme.NeonYellow
import com.hyroxgo.app.ui.theme.NeonYellowContainer
import com.hyroxgo.app.ui.theme.RunBlue
import com.hyroxgo.app.ui.theme.SuccessGreen
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
fun RaceTimerScreen(
    viewModel: RaceTimerViewModel,
    gender: Gender,
    category: Category,
    ageGroup: String,
    customDivisionName: String = "Custom Division",
    customStationSpecs: List<StationSpec>? = null,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pastRaces by viewModel.raceHistory.collectAsStateWithLifecycle()
    var showPastRaces by remember { mutableStateOf(false) }

    val activeIntervalNames = remember(category, customStationSpecs) {
        viewModel.getIntervalNames(if (category == Category.CUSTOM) customStationSpecs else null)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Central Timer Display Card (Elegant Dark 24dp rounded card)
            TimerDisplayCard(
                state = state,
                intervalNames = activeIntervalNames
            )
        }

        item {
            // Oversized Big Button
            BigActionControl(
                state = state,
                intervalNames = activeIntervalNames,
                onStart = { viewModel.startTimer() },
                onSplit = { viewModel.logSplit(activeIntervalNames) },
                onSave = { viewModel.saveRaceSimulation(gender, category, ageGroup, customDivisionName) }
            )
        }

        item {
            // Secondary Quick Actions (Pause / Resume, Reset, Undo)
            SecondaryTimerControls(
                state = state,
                onPause = { viewModel.pauseTimer() },
                onResume = { viewModel.resumeTimer() },
                onReset = { viewModel.resetTimer() },
                onUndo = { viewModel.undoLastSplit() }
            )
        }

        // Live Splits List or Race Summary
        if (state.status == TimerStatus.FINISHED) {
            item {
                RaceFinishedSummaryCard(
                    state = state,
                    gender = gender,
                    category = category,
                    ageGroup = ageGroup,
                    customDivisionName = customDivisionName,
                    onSave = { viewModel.saveRaceSimulation(gender, category, ageGroup, customDivisionName) }
                )
            }
        }

        if (state.splits.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = HyroxStrings.recordedSplitsHeader(state.splits.size, activeIntervalNames.size, lang),
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 1.4.sp,
                            fontWeight = FontWeight.Black,
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    )
                    Text(
                        text = if (lang == AppLanguage.FR) "CUMULATIF" else "CUMULATIVE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NeonYellow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            items(state.splits.reversed()) { split ->
                SplitRowItem(split = split)
            }
        }

        // Past Saved Races Toggle
        if (pastRaces.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Charcoal800)
                        .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(16.dp))
                        .clickable { showPastRaces = !showPastRaces }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = NeonYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (lang == AppLanguage.FR) "SIMULATIONS PRÉCÉDENTES (${pastRaces.size})" else "PAST SIMULATIONS (${pastRaces.size})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )
                    }
                    Text(
                        text = if (showPastRaces) (if (lang == AppLanguage.FR) "MASQUER" else "HIDE") else (if (lang == AppLanguage.FR) "AFFICHER" else "SHOW"),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NeonYellow,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }

            if (showPastRaces) {
                items(pastRaces) { race ->
                    PastRaceCard(
                        race = race,
                        onDelete = { viewModel.deleteRaceSimulation(race.id) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun TimerDisplayCard(
    state: TimerUiState,
    intervalNames: List<String>,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val totalSec = state.totalTimeMillis / 1000
    val totalHours = totalSec / 3600
    val totalMins = (totalSec % 3600) / 60
    val totalSeconds = totalSec % 60
    val totalTenths = (state.totalTimeMillis % 1000) / 100

    val totalFormatted = if (totalHours > 0) {
        String.format(Locale.US, "%02d:%02d:%02d.%d", totalHours, totalMins, totalSeconds, totalTenths)
    } else {
        String.format(Locale.US, "%02d:%02d.%d", totalMins, totalSeconds, totalTenths)
    }

    val intervalSec = state.intervalTimeMillis / 1000
    val intervalMins = intervalSec / 60
    val intervalSeconds = intervalSec % 60
    val intervalFormatted = String.format(Locale.US, "%02d:%02d", intervalMins, intervalSeconds)

    val currentIntervalName = if (state.currentIntervalIndex < intervalNames.size) {
        HyroxStrings.translateIntervalName(intervalNames[state.currentIntervalIndex], lang)
    } else {
        HyroxStrings.completedStatus(lang)
    }

    val nextIntervalName = if (state.currentIntervalIndex + 1 < intervalNames.size) {
        "${HyroxStrings.nextIntervalLabel(lang)}: ${HyroxStrings.translateIntervalName(intervalNames[state.currentIntervalIndex + 1], lang)}"
    } else if (state.currentIntervalIndex == intervalNames.size - 1) {
        "${HyroxStrings.nextIntervalLabel(lang)}: ${HyroxStrings.finishLineLabel(lang)}"
    } else {
        HyroxStrings.intervalCountCompleted(intervalNames.size, lang)
    }

    val isCurrentRun = state.currentIntervalIndex % 2 == 0

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("timer_display_card"),
        colors = CardDefaults.cardColors(containerColor = Charcoal800),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, if (state.status == TimerStatus.RUNNING) NeonYellow else Charcoal600)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Interval indicator tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isCurrentRun) RunBlue.copy(alpha = 0.15f) else NeonYellowContainer)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = if (isCurrentRun) Icons.AutoMirrored.Filled.DirectionsRun else Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = if (isCurrentRun) RunBlue else NeonYellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = HyroxStrings.intervalBadge(state.currentIntervalIndex + 1, intervalNames.size, lang),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp,
                            color = if (isCurrentRun) RunBlue else NeonYellow
                        )
                    )
                }

                val statusLabel = when (state.status) {
                    TimerStatus.RUNNING -> if (lang == AppLanguage.FR) "EN COURS" else "RUNNING"
                    TimerStatus.PAUSED -> if (lang == AppLanguage.FR) "EN PAUSE" else "PAUSED"
                    TimerStatus.FINISHED -> if (lang == AppLanguage.FR) "TERMINÉ" else "FINISHED"
                    TimerStatus.IDLE -> if (lang == AppLanguage.FR) "EN ATTENTE" else "IDLE"
                }

                Text(
                    text = statusLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = when (state.status) {
                            TimerStatus.RUNNING -> NeonYellow
                            TimerStatus.PAUSED -> Color(0xFFFFA726)
                            TimerStatus.FINISHED -> SuccessGreen
                            TimerStatus.IDLE -> TextTertiary
                        }
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Interval Name
            Text(
                text = currentIntervalName.uppercase(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp,
                    color = TextPrimary
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = nextIntervalName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )

            // Massive Main Elapsed Timer
            Text(
                text = totalFormatted,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = TextPrimary
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("total_time_text")
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Split Lap Timer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Charcoal700)
                    .border(BorderStroke(1.dp, Charcoal500), RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${HyroxStrings.splitTimeLabel(lang)}:",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextSecondary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                )
                Text(
                    text = intervalFormatted,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = NeonYellow,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            val totalIntervals = intervalNames.size.coerceAtLeast(1)
            val progress = (state.currentIntervalIndex.toFloat() / totalIntervals).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = NeonYellow,
                trackColor = Charcoal600
            )
        }
    }
}

@Composable
fun BigActionControl(
    state: TimerUiState,
    intervalNames: List<String>,
    onStart: () -> Unit,
    onSplit: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current

    when (state.status) {
        TimerStatus.IDLE -> {
            val firstInterval = if (intervalNames.isNotEmpty()) HyroxStrings.translateIntervalName(intervalNames[0], lang) else "Run 1 (1 km)"
            HyroxBigButton(
                text = HyroxStrings.startRaceButton(lang),
                subText = "${HyroxStrings.intervalBadge(1, intervalNames.size, lang)}: $firstInterval",
                icon = Icons.Default.PlayArrow,
                isPrimary = true,
                onClick = onStart,
                testTag = "start_race_big_button",
                modifier = modifier
            )
        }
        TimerStatus.RUNNING, TimerStatus.PAUSED -> {
            val currentIndex = state.currentIntervalIndex
            val currentName = intervalNames.getOrElse(currentIndex) { "Interval ${currentIndex + 1}" }
            val translatedCurrent = HyroxStrings.translateIntervalName(currentName, lang)
            val isFinal = currentIndex == intervalNames.size - 1

            val buttonTitle = if (isFinal) {
                if (lang == AppLanguage.FR) "TERMINER COURSE (VALIDER $translatedCurrent)" else "FINISH RACE (LOG $currentName)"
            } else {
                if (lang == AppLanguage.FR) "TEMPS : VALIDER $translatedCurrent" else "SPLIT: COMPLETE $currentName"
            }

            val nextIndex = currentIndex + 1
            val nextDesc = if (nextIndex < intervalNames.size) {
                val nextTrans = HyroxStrings.translateIntervalName(intervalNames[nextIndex], lang)
                "${HyroxStrings.nextIntervalLabel(lang)}: $nextTrans"
            } else {
                HyroxStrings.finishLineLabel(lang)
            }

            HyroxBigButton(
                text = buttonTitle,
                subText = nextDesc,
                icon = Icons.Default.Timer,
                isPrimary = true,
                onClick = onSplit,
                testTag = "split_big_button",
                modifier = modifier
            )
        }
        TimerStatus.FINISHED -> {
            if (state.savedSuccessfully) {
                HyroxBigButton(
                    text = HyroxStrings.raceLoggedSuccess(lang),
                    subText = if (lang == AppLanguage.FR) "Enregistré dans votre historique" else "Saved to your performance history",
                    icon = Icons.Default.CheckCircle,
                    isPrimary = false,
                    enabled = false,
                    onClick = {},
                    testTag = "saved_big_button",
                    modifier = modifier
                )
            } else {
                HyroxBigButton(
                    text = HyroxStrings.logThisRaceButton(lang),
                    subText = if (lang == AppLanguage.FR) "Enregistrer les ${intervalNames.size} temps dans l'historique" else "Store ${intervalNames.size} splits in your workout history",
                    icon = Icons.Default.CheckCircle,
                    isPrimary = true,
                    onClick = onSave,
                    testTag = "save_race_big_button",
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun SecondaryTimerControls(
    state: TimerUiState,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onReset: () -> Unit,
    onUndo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    if (state.status == TimerStatus.IDLE) return

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Pause / Resume Button
        if (state.status == TimerStatus.RUNNING) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Charcoal800)
                    .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(14.dp))
                    .clickable { onPause() }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Pause, contentDescription = "Pause", tint = TextPrimary, modifier = Modifier.size(18.dp))
                    Text(if (lang == AppLanguage.FR) "PAUSE" else "PAUSE", style = MaterialTheme.typography.labelLarge.copy(color = TextPrimary, fontWeight = FontWeight.Bold))
                }
            }
        } else if (state.status == TimerStatus.PAUSED) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(NeonYellow)
                    .clickable { onResume() }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Resume", tint = Charcoal900, modifier = Modifier.size(18.dp))
                    Text(if (lang == AppLanguage.FR) "REPRENDRE" else "RESUME", style = MaterialTheme.typography.labelLarge.copy(color = Charcoal900, fontWeight = FontWeight.Black))
                }
            }
        }

        // Undo Split Button
        if (state.splits.isNotEmpty() && state.status != TimerStatus.IDLE) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Charcoal800)
                    .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(14.dp))
                    .clickable { onUndo() }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Replay, contentDescription = "Undo", tint = TextSecondary, modifier = Modifier.size(16.dp))
                    Text(if (lang == AppLanguage.FR) "ANNULER TEMPS" else "UNDO SPLIT", style = MaterialTheme.typography.labelMedium.copy(color = TextSecondary, fontWeight = FontWeight.Bold))
                }
            }
        }

        // Reset Button
        Box(
            modifier = Modifier
                .width(56.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Charcoal800)
                .border(BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f)), RoundedCornerShape(14.dp))
                .clickable { onReset() },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset", tint = ErrorRed, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun SplitRowItem(
    split: RaceSplit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val splitMin = split.splitTimeSeconds / 60
    val splitSec = split.splitTimeSeconds % 60
    val splitFormatted = String.format(Locale.US, "%02d:%02d", splitMin, splitSec)

    val cumMin = split.cumulativeTimeSeconds / 60
    val cumSec = split.cumulativeTimeSeconds % 60
    val cumFormatted = String.format(Locale.US, "%02d:%02d", cumMin, cumSec)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("split_row_${split.intervalIndex}"),
        colors = CardDefaults.cardColors(containerColor = Charcoal800),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Charcoal600)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (split.isRun) RunBlue.copy(alpha = 0.18f) else NeonYellowContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${split.intervalIndex + 1}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = if (split.isRun) RunBlue else NeonYellow,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }

                Text(
                    text = HyroxStrings.translateIntervalName(split.name, lang).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        letterSpacing = 0.3.sp
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Split Duration
                Text(
                    text = "+$splitFormatted",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = NeonYellow,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Black
                    )
                )

                // Cumulative
                Text(
                    text = cumFormatted,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}

@Composable
fun RaceFinishedSummaryCard(
    state: TimerUiState,
    gender: Gender,
    category: Category,
    ageGroup: String,
    customDivisionName: String = "Custom Division",
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val totalSec = state.totalTimeMillis / 1000
    val totalMins = totalSec / 60
    val totalSeconds = totalSec % 60

    val runSplitsCount = state.splits.count { it.isRun }.coerceAtLeast(1)
    val stationSplitsCount = state.splits.count { !it.isRun }.coerceAtLeast(1)

    val runSec = state.totalRunTimeMillis / 1000
    val runMins = runSec / 60
    val runSeconds = runSec % 60
    val avgRunPaceSec = if (runSec > 0) runSec / runSplitsCount else 0

    val stationSec = state.totalStationTimeMillis / 1000
    val stationMins = stationSec / 60
    val stationSeconds = stationSec % 60

    val roxzoneSec = (totalSec - (runSec + stationSec)).coerceAtLeast(0L)
    val roxMins = roxzoneSec / 60
    val roxSeconds = roxzoneSec % 60

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("race_summary_card"),
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
                    text = if (lang == AppLanguage.FR) "SIMULATION DE COURSE TERMINÉE" else "RACE SIMULATION COMPLETE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = NeonYellow,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.6.sp,
                        fontSize = 11.sp
                    )
                )

                val context = LocalContext.current
                IconButton(
                    onClick = { shareRaceState(context, state, gender, category, ageGroup, customDivisionName, lang) },
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("share_race_finish_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Race Results",
                        tint = NeonYellow,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(HyroxStrings.totalTimeLabel(lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, letterSpacing = 1.sp))
                    Text(
                        text = String.format(Locale.US, "%02d:%02d", totalMins, totalSeconds),
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = TextPrimary
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(HyroxStrings.avgRunPaceLabel(lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, letterSpacing = 1.sp))
                    Text(
                        text = String.format(Locale.US, "%02d:%02d /km", avgRunPaceSec / 60, avgRunPaceSec % 60),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = RunBlue
                        )
                    )
                }
            }

            // Stat breakdown row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Charcoal700)
                    .border(BorderStroke(1.dp, Charcoal500), RoundedCornerShape(12.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(HyroxStrings.runsStatLabel(runSplitsCount, lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, fontSize = 10.sp))
                    Text(
                        text = String.format(Locale.US, "%02d:%02d", runMins, runSeconds),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(HyroxStrings.stationsStatLabel(stationSplitsCount, lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, fontSize = 10.sp))
                    Text(
                        text = String.format(Locale.US, "%02d:%02d", stationMins, stationSeconds),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(HyroxStrings.roxzoneTimeLabel(lang), style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, fontSize = 10.sp))
                    Text(
                        text = String.format(Locale.US, "%02d:%02d", roxMins, roxSeconds),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = NeonYellow)
                    )
                }
            }
        }
    }
}

@Composable
fun PastRaceCard(
    race: RaceSimulation,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    val totalMin = race.totalTimeSeconds / 60
    val totalSec = race.totalTimeSeconds % 60
    val dateFormat = remember(lang) {
        SimpleDateFormat("MMM dd, yyyy • HH:mm", if (lang == AppLanguage.FR) Locale.FRANCE else Locale.US)
    }
    val dateString = dateFormat.format(Date(race.timestamp))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("past_race_${race.id}"),
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = race.divisionTitle.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = NeonYellow,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = String.format(Locale.US, "%02d:%02d", totalMin, totalSec),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = TextPrimary
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val context = LocalContext.current
                IconButton(
                    onClick = { shareRaceSimulation(context, race, lang) },
                    modifier = Modifier.testTag("share_past_race_${race.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Race Results",
                        tint = NeonYellow,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_past_race_${race.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete race",
                        tint = TextTertiary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

fun shareRaceState(
    context: Context,
    state: TimerUiState,
    gender: Gender,
    category: Category,
    ageGroup: String,
    customDivisionName: String = "Custom Division",
    lang: AppLanguage = AppLanguage.EN
) {
    val totalSec = state.totalTimeMillis / 1000
    val totalMins = totalSec / 60
    val totalSeconds = totalSec % 60

    val runSplitsCount = state.splits.count { it.isRun }.coerceAtLeast(1)
    val stationSplitsCount = state.splits.count { !it.isRun }.coerceAtLeast(1)

    val runSec = state.totalRunTimeMillis / 1000
    val runMins = runSec / 60
    val runSeconds = runSec % 60
    val avgRunPaceSec = if (runSec > 0) runSec / runSplitsCount else 0

    val stationSec = state.totalStationTimeMillis / 1000
    val stationMins = stationSec / 60
    val stationSeconds = stationSec % 60

    val roxzoneSec = (totalSec - (runSec + stationSec)).coerceAtLeast(0L)
    val roxMins = roxzoneSec / 60
    val roxSeconds = roxzoneSec % 60

    val divisionTitle = if (category == Category.CUSTOM) {
        val name = if (customDivisionName.isNotBlank()) customDivisionName else "Custom Division"
        "$name (${HyroxStrings.genderName(gender, lang)} • $ageGroup)"
    } else {
        "${HyroxStrings.genderName(gender, lang)} ${HyroxStrings.categoryName(category, lang)} • $ageGroup"
    }

    val totalFormatted = String.format(Locale.US, "%02d:%02d", totalMins, totalSeconds)
    val shareText = HyroxStrings.buildShareText(
        divisionTitle = divisionTitle,
        totalTimeFormatted = totalFormatted,
        runMins = runMins,
        runSecs = runSeconds,
        runCount = runSplitsCount,
        avgRunPaceSec = avgRunPaceSec,
        stationMins = stationMins,
        stationSecs = stationSeconds,
        stationCount = stationSplitsCount,
        roxMins = roxMins,
        roxSecs = roxSeconds,
        lang = lang
    )

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, if (lang == AppLanguage.FR) "Partager les résultats" else "Share Race Results")
    context.startActivity(shareIntent)
}

fun shareRaceSimulation(context: Context, race: RaceSimulation, lang: AppLanguage = AppLanguage.EN) {
    val totalMin = race.totalTimeSeconds / 60
    val totalSec = race.totalTimeSeconds % 60
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", if (lang == AppLanguage.FR) Locale.FRANCE else Locale.US)
    val dateString = dateFormat.format(Date(race.timestamp))

    val shareText = buildString {
        if (lang == AppLanguage.FR) {
            appendLine("⚡ Résultat de Simulation de Course HYROXGO")
            appendLine("🏆 Division : ${race.divisionTitle}")
            appendLine("⏱️ Temps Officiel : ${String.format(Locale.FRANCE, "%02d:%02d", totalMin, totalSec)}")
            appendLine("📅 Date : $dateString")
            appendLine()
            append("Entraîné avec HYROXGO • #HYROX #HYROXGO #ROXGO #HyroxFrance #RaceReady")
        } else {
            appendLine("⚡ HYROXGO Race Simulation Result")
            appendLine("🏆 Division: ${race.divisionTitle}")
            appendLine("⏱️ Official Simulation Time: ${String.format(Locale.US, "%02d:%02d", totalMin, totalSec)}")
            appendLine("📅 Date: $dateString")
            appendLine()
            append("Trained with HYROXGO • #HYROX #HYROXGO #ROXGO #HyroxWorld #RaceReady")
        }
    }

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, if (lang == AppLanguage.FR) "Partager les résultats" else "Share Race Results")
    context.startActivity(shareIntent)
}
