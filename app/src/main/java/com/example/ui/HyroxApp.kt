package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.Gender
import com.example.data.model.HyroxDivisionData
import com.example.ui.components.HyroxTabRow
import com.example.ui.division.DivisionScreen
import com.example.ui.theme.Charcoal500
import com.example.ui.theme.Charcoal600
import com.example.ui.theme.Charcoal700
import com.example.ui.theme.Charcoal800
import com.example.ui.theme.Charcoal900
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.NeonYellowContainer
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.timer.RaceTimerScreen
import com.example.ui.timer.RaceTimerViewModel
import com.example.ui.workout.WorkoutLogScreen
import com.example.ui.workout.WorkoutViewModel

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDim
import com.example.ui.theme.GoldPrimary

import androidx.compose.runtime.CompositionLocalProvider
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.HyroxStrings
import com.example.ui.i18n.LocalAppLanguage

@Composable
fun HyroxApp(
    raceTimerViewModel: RaceTimerViewModel,
    workoutViewModel: WorkoutViewModel,
    modifier: Modifier = Modifier
) {
    var appLanguage by remember { mutableStateOf(AppLanguage.getDefault()) }
    var selectedTab by remember { mutableIntStateOf(1) } // Default to Race Timer
    val tabs = listOf(
        HyroxStrings.tabDivisions(appLanguage),
        HyroxStrings.tabRaceTimer(appLanguage),
        HyroxStrings.tabWorkoutLog(appLanguage)
    )

    // Global athlete preferences
    var gender by remember { mutableStateOf(Gender.MEN) }
    var category by remember { mutableStateOf(Category.OPEN) }
    var ageGroup by remember { mutableStateOf(HyroxDivisionData.AGE_GROUPS[2]) } // 30-34 default
    var customDivisionName by remember { mutableStateOf("Custom Division") }
    var customStationSpecs by remember {
        mutableStateOf(HyroxDivisionData.getStationSpecs(Gender.MEN, Category.OPEN))
    }

    CompositionLocalProvider(LocalAppLanguage provides appLanguage) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(Charcoal900),
            containerColor = Charcoal900,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                // Elegant Dark App Header with HYROXGO & ROXGO Branding & Language Switcher
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = HyroxStrings.appHeaderSubtitle(appLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 2.sp,
                                color = ElectricBlue
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "HYROX",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = (-0.5).sp,
                                    color = TextPrimary
                                )
                            )
                            Text(
                                text = "GO",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = (-0.5).sp,
                                    color = ElectricBlue
                                )
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Language Selector (EN / FR)
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Charcoal800)
                                .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(12.dp))
                                .padding(2.dp)
                                .testTag("language_selector"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (appLanguage == AppLanguage.EN) ElectricBlue else Color.Transparent)
                                    .clickable { appLanguage = AppLanguage.EN }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .testTag("lang_en_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "EN",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp,
                                        color = if (appLanguage == AppLanguage.EN) Charcoal900 else TextSecondary
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (appLanguage == AppLanguage.FR) ElectricBlue else Color.Transparent)
                                    .clickable { appLanguage = AppLanguage.FR }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .testTag("lang_fr_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "FR",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp,
                                        color = if (appLanguage == AppLanguage.FR) Charcoal900 else TextSecondary
                                    )
                                )
                            }
                        }

                        // ROXGO Black and Gold Logo Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF111111))
                                .border(BorderStroke(1.2.dp, GoldPrimary), RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "ROX",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        letterSpacing = 1.sp,
                                        fontSize = 11.sp
                                    )
                                )
                                Text(
                                    text = "GO",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = GoldPrimary,
                                        letterSpacing = 1.sp,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }

                // Segmented Tab Navigation
                HyroxTabRow(
                    tabs = tabs,
                    selectedIndex = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .testTag("hyrox_tab_row")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Screen Content
                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        0 -> DivisionScreen(
                            gender = gender,
                            category = category,
                            ageGroup = ageGroup,
                            customDivisionName = customDivisionName,
                            customStationSpecs = customStationSpecs,
                            onGenderChange = {
                                gender = it
                                if (category != Category.CUSTOM) {
                                    customStationSpecs = HyroxDivisionData.getStationSpecs(it, category)
                                }
                            },
                            onCategoryChange = {
                                category = it
                                if (it != Category.CUSTOM) {
                                    customStationSpecs = HyroxDivisionData.getStationSpecs(gender, it)
                                }
                            },
                            onAgeGroupChange = { ageGroup = it },
                            onCustomDivisionNameChange = { customDivisionName = it },
                            onCustomStationSpecsChange = { customStationSpecs = it }
                        )
                        1 -> RaceTimerScreen(
                            viewModel = raceTimerViewModel,
                            gender = gender,
                            category = category,
                            ageGroup = ageGroup,
                            customDivisionName = customDivisionName,
                            customStationSpecs = if (category == Category.CUSTOM) customStationSpecs else null
                        )
                        2 -> WorkoutLogScreen(
                            viewModel = workoutViewModel,
                            gender = gender,
                            category = category,
                            customStationSpecs = if (category == Category.CUSTOM) customStationSpecs else null
                        )
                    }
                }
            }
        }
    }
}

