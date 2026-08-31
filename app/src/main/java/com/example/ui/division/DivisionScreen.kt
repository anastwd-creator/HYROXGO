package com.example.ui.division

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.Gender
import com.example.data.model.HyroxDivisionData
import com.example.data.model.StationSpec
import com.example.ui.components.HyroxDropdownField
import com.example.ui.theme.Charcoal500
import com.example.ui.theme.Charcoal600
import com.example.ui.theme.Charcoal700
import com.example.ui.theme.Charcoal800
import com.example.ui.theme.Charcoal900
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.NeonYellowContainer
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import java.util.Locale

import com.example.ui.i18n.HyroxStrings
import com.example.ui.i18n.LocalAppLanguage

@Composable
fun DivisionScreen(
    gender: Gender,
    category: Category,
    ageGroup: String,
    customDivisionName: String = "Custom Division",
    customStationSpecs: List<StationSpec> = HyroxDivisionData.getStationSpecs(gender, category),
    onGenderChange: (Gender) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onAgeGroupChange: (String) -> Unit,
    onCustomDivisionNameChange: (String) -> Unit = {},
    onCustomStationSpecsChange: (List<StationSpec>) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current
    var useLbs by remember { mutableStateOf(false) }
    var editingStationIndex by remember { mutableStateOf<Int?>(null) }
    var showPresetDialog by remember { mutableStateOf(false) }

    val activeStationSpecs = remember(gender, category, customStationSpecs) {
        if (category == Category.CUSTOM) {
            customStationSpecs
        } else {
            HyroxDivisionData.getStationSpecs(gender, category)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Division Selector Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("division_selector_card"),
                colors = CardDefaults.cardColors(containerColor = Charcoal800),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, if (category == Category.CUSTOM) ElectricBlue else Charcoal600)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (category == Category.CUSTOM) HyroxStrings.customDivisionNameLabel(lang) else HyroxStrings.divisionCalculatorTitle(lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    letterSpacing = 1.8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (category == Category.CUSTOM) ElectricBlue else TextSecondary
                                )
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (category == Category.CUSTOM) ElectricBlueContainer else NeonYellowContainer)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "${HyroxStrings.categoryName(category, lang).uppercase()} / ${HyroxStrings.genderName(gender, lang).uppercase()}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Black,
                                            color = if (category == Category.CUSTOM) ElectricBlue else NeonYellow,
                                            letterSpacing = 0.5.sp
                                        )
                                    )
                                }

                                Text(
                                    text = if (category == Category.CUSTOM && customDivisionName.isNotBlank()) customDivisionName else ageGroup,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                )
                            }
                        }

                        // Unit Toggle (KG / LBS)
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Charcoal700)
                                .border(BorderStroke(1.dp, Charcoal500), RoundedCornerShape(10.dp))
                                .padding(3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (!useLbs) ElectricBlue else Color.Transparent)
                                    .clickable { useLbs = false }
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "KG",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (!useLbs) Charcoal900 else TextSecondary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (useLbs) ElectricBlue else Color.Transparent)
                                    .clickable { useLbs = true }
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "LBS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (useLbs) Charcoal900 else TextSecondary
                                )
                            }
                        }
                    }

                    // Quick Selectors for Gender, Category, Age
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Gender Selector
                        val genderMenLabel = HyroxStrings.genderName(Gender.MEN, lang)
                        val genderWomenLabel = HyroxStrings.genderName(Gender.WOMEN, lang)
                        HyroxDropdownField(
                            label = HyroxStrings.genderLabel(lang),
                            selectedValue = HyroxStrings.genderName(gender, lang),
                            options = listOf(genderMenLabel, genderWomenLabel),
                            onSelect = { selected ->
                                onGenderChange(if (selected == genderMenLabel) Gender.MEN else Gender.WOMEN)
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "gender_dropdown"
                        )

                        // Category Selector with CUSTOM option
                        val openLabel = HyroxStrings.categoryName(Category.OPEN, lang)
                        val proLabel = HyroxStrings.categoryName(Category.PRO, lang)
                        val doublesLabel = HyroxStrings.categoryName(Category.DOUBLES, lang)
                        val customLabel = HyroxStrings.categoryName(Category.CUSTOM, lang)
                        HyroxDropdownField(
                            label = HyroxStrings.categoryLabel(lang),
                            selectedValue = HyroxStrings.categoryName(category, lang),
                            options = listOf(openLabel, proLabel, doublesLabel, customLabel),
                            onSelect = { selected ->
                                val cat = when (selected) {
                                    proLabel -> Category.PRO
                                    doublesLabel -> Category.DOUBLES
                                    customLabel -> Category.CUSTOM
                                    else -> Category.OPEN
                                }
                                onCategoryChange(cat)
                            },
                            modifier = Modifier.weight(1.1f),
                            testTag = "category_dropdown"
                        )

                        // Age Group Selector
                        HyroxDropdownField(
                            label = HyroxStrings.ageGroupLabel(lang),
                            selectedValue = ageGroup,
                            options = HyroxDivisionData.AGE_GROUPS,
                            onSelect = onAgeGroupChange,
                            modifier = Modifier.weight(1f),
                            testTag = "age_dropdown"
                        )
                    }

                    // Custom Division Settings Panel
                    if (category == Category.CUSTOM) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Charcoal700)
                                .border(BorderStroke(1.dp, Charcoal500), RoundedCornerShape(16.dp))
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = HyroxStrings.customDivisionNameLabel(lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ElectricBlue,
                                    letterSpacing = 1.2.sp
                                )
                            )

                            OutlinedTextField(
                                value = customDivisionName,
                                onValueChange = onCustomDivisionNameChange,
                                label = { Text(HyroxStrings.customDivisionNameLabel(lang), color = TextSecondary, fontSize = 12.sp) },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("custom_division_name_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ElectricBlue,
                                    unfocusedBorderColor = Charcoal500,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary,
                                    focusedContainerColor = Charcoal800,
                                    unfocusedContainerColor = Charcoal800
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )

                            // Quick Presets Selector
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = HyroxStrings.quickPresetsLabel(lang),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TextSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                TextButton(
                                    onClick = { showPresetDialog = true },
                                    modifier = Modifier.testTag("open_preset_dialog_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Tune,
                                        contentDescription = "Presets",
                                        tint = ElectricBlue,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = HyroxStrings.selectPresetButton(lang),
                                        color = ElectricBlue,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val quickPresets = listOf(
                                    "Half Sim (4 Stations)",
                                    "10-Station Ultra",
                                    "Heavy Rx (+20%)",
                                    "Scaled / Light (-20%)",
                                    "Gym / Dumbbell Setup",
                                    "Pro Standards",
                                    "Standard Open"
                                )
                                items(quickPresets) { preset ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Charcoal800)
                                            .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(8.dp))
                                            .clickable {
                                                val newSpecs = HyroxDivisionData.getCustomPresetSpecs(preset, gender)
                                                onCustomStationSpecsChange(newSpecs)
                                                if (customDivisionName.isBlank() || customDivisionName == "Custom Division") {
                                                    onCustomDivisionNameChange(HyroxStrings.presetTitle(preset, lang))
                                                }
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = HyroxStrings.presetTitle(preset, lang),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = TextPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section Title & Add / Reset Actions
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = HyroxStrings.stationsHeader(activeStationSpecs.size, category == Category.CUSTOM, lang),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.6.sp,
                        fontWeight = FontWeight.Black,
                        color = if (category == Category.CUSTOM) ElectricBlue else TextSecondary,
                        fontSize = 10.sp
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (category != Category.CUSTOM) {
                        TextButton(
                            onClick = {
                                onCategoryChange(Category.CUSTOM)
                                onCustomStationSpecsChange(HyroxDivisionData.getStationSpecs(gender, category))
                            },
                            modifier = Modifier.testTag("enable_custom_weights_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Customize",
                                tint = ElectricBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = HyroxStrings.customizeWeightsButton(lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ElectricBlue,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    } else {
                        TextButton(
                            onClick = {
                                val nextNum = activeStationSpecs.size + 1
                                val newStation = StationSpec(
                                    stationNumber = nextNum,
                                    name = "Custom Station $nextNum",
                                    distanceOrReps = "1,000 m",
                                    weightKg = 0.0,
                                    weightLbs = 0.0,
                                    details = "Custom workout station",
                                    iconType = "generic"
                                )
                                val updated = activeStationSpecs + newStation
                                onCustomStationSpecsChange(updated)
                                editingStationIndex = updated.lastIndex
                            },
                            modifier = Modifier.testTag("header_add_station_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Station",
                                tint = ElectricBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = HyroxStrings.addStationButton(lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ElectricBlue,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 10.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        TextButton(
                            onClick = {
                                val reset = HyroxDivisionData.getStationSpecs(gender, Category.OPEN)
                                onCustomStationSpecsChange(reset)
                            },
                            modifier = Modifier.testTag("reset_custom_weights_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset",
                                tint = TextTertiary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = HyroxStrings.resetStandardsButton(lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TextTertiary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Station Spec Cards
        itemsIndexed(activeStationSpecs) { index, spec ->
            val isEditing = editingStationIndex == index
            StationSpecCard(
                spec = spec,
                useLbs = useLbs,
                isCustom = category == Category.CUSTOM,
                isEditing = isEditing,
                canDelete = activeStationSpecs.size > 1,
                onToggleEdit = {
                    editingStationIndex = if (isEditing) null else index
                    if (category != Category.CUSTOM) {
                        onCategoryChange(Category.CUSTOM)
                        onCustomStationSpecsChange(activeStationSpecs)
                    }
                },
                onDelete = if (activeStationSpecs.size > 1) {
                    {
                        if (category != Category.CUSTOM) {
                            onCategoryChange(Category.CUSTOM)
                        }
                        val updated = activeStationSpecs.filterIndexed { i, _ -> i != index }
                            .mapIndexed { i, s -> s.copy(stationNumber = i + 1) }
                        onCustomStationSpecsChange(updated)
                        if (editingStationIndex == index) {
                            editingStationIndex = null
                        } else if (editingStationIndex != null && editingStationIndex!! > index) {
                            editingStationIndex = editingStationIndex!! - 1
                        }
                    }
                } else null,
                onSaveSpec = { updatedSpec ->
                    val updatedList = activeStationSpecs.toMutableList()
                    updatedList[index] = updatedSpec
                    onCustomStationSpecsChange(updatedList)
                    editingStationIndex = null
                }
            )
        }

        // Add Station Card at bottom of station list
        if (category == Category.CUSTOM || true) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            if (category != Category.CUSTOM) {
                                onCategoryChange(Category.CUSTOM)
                            }
                            val nextNum = activeStationSpecs.size + 1
                            val newStation = StationSpec(
                                stationNumber = nextNum,
                                name = "Echo Bike / Cardio",
                                distanceOrReps = "50 Cal",
                                weightKg = 0.0,
                                weightLbs = 0.0,
                                details = "Assault / Echo bike or custom station",
                                iconType = "bike"
                            )
                            val updated = activeStationSpecs + newStation
                            onCustomStationSpecsChange(updated)
                            editingStationIndex = updated.lastIndex
                        }
                        .testTag("add_station_button"),
                    colors = CardDefaults.cardColors(containerColor = Charcoal800.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(ElectricBlue, NeonYellow)))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Station",
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = HyroxStrings.addStationCard(activeStationSpecs.size + 1, lang),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = ElectricBlue,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    // Preset Options Modal Dialog
    if (showPresetDialog) {
        CustomPresetDialog(
            gender = gender,
            onDismiss = { showPresetDialog = false },
            onSelectPreset = { presetName, specs ->
                onCustomStationSpecsChange(specs)
                onCustomDivisionNameChange(HyroxStrings.presetTitle(presetName, lang))
                showPresetDialog = false
            }
        )
    }
}

@Composable
fun StationSpecCard(
    spec: StationSpec,
    useLbs: Boolean,
    isCustom: Boolean,
    isEditing: Boolean,
    canDelete: Boolean = true,
    onToggleEdit: () -> Unit,
    onDelete: (() -> Unit)? = null,
    onSaveSpec: (StationSpec) -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = LocalAppLanguage.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("station_card_${spec.stationNumber}"),
        colors = CardDefaults.cardColors(containerColor = Charcoal800),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, if (isEditing) ElectricBlue else if (isCustom) Charcoal500 else Charcoal600)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleEdit() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Station Number Badge
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Charcoal700)
                        .border(BorderStroke(1.dp, if (isCustom) ElectricBlue else Charcoal500), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (spec.stationNumber < 10) "0${spec.stationNumber}" else "${spec.stationNumber}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = if (isCustom) ElectricBlue else NeonYellow,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Station Details
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = HyroxStrings.translateStationName(spec.name, lang).uppercase(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = TextPrimary,
                                fontSize = 15.sp,
                                letterSpacing = 0.3.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = spec.details,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Weight & Distance Display
                Column(horizontalAlignment = Alignment.End) {
                    if (spec.weightKg > 0.0) {
                        val weightText = if (useLbs) {
                            val lbsVal = if (spec.weightLbs > 0) spec.weightLbs else (spec.weightKg * 2.20462)
                            "${String.format(Locale.US, "%.1f", lbsVal)} lbs"
                        } else {
                            if (spec.weightKg % 1.0 == 0.0) "${spec.weightKg.toInt()} kg" else "${spec.weightKg} kg"
                        }
                        Text(
                            text = weightText,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = if (isCustom) ElectricBlue else NeonYellow,
                                fontSize = 17.sp
                            )
                        )
                    }
                    Text(
                        text = spec.distanceOrReps,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = if (spec.weightKg == 0.0) (if (isCustom) ElectricBlue else NeonYellow) else TextPrimary,
                            fontFamily = FontFamily.Monospace,
                            fontSize = if (spec.weightKg == 0.0) 15.sp else 12.sp
                        )
                    )
                }

                IconButton(
                    onClick = onToggleEdit,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 6.dp)
                        .testTag("edit_station_${spec.stationNumber}_btn")
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Default.Close else Icons.Default.Edit,
                        contentDescription = "Edit Station",
                        tint = if (isEditing) ElectricBlue else TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Inline Station Editor Expandable Section
            AnimatedVisibility(
                visible = isEditing,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                InlineStationEditor(
                    spec = spec,
                    useLbs = useLbs,
                    canDelete = canDelete,
                    onDelete = onDelete,
                    onSave = onSaveSpec,
                    onCancel = onToggleEdit
                )
            }
        }
    }
}

@Composable
fun InlineStationEditor(
    spec: StationSpec,
    useLbs: Boolean,
    canDelete: Boolean = true,
    onDelete: (() -> Unit)? = null,
    onSave: (StationSpec) -> Unit,
    onCancel: () -> Unit
) {
    val lang = LocalAppLanguage.current
    var editedName by remember(spec) { mutableStateOf(spec.name) }
    var editedDistanceReps by remember(spec) { mutableStateOf(spec.distanceOrReps) }
    var editedWeightKg by remember(spec) { mutableDoubleStateOf(spec.weightKg) }
    var editedDetails by remember(spec) { mutableStateOf(spec.details) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Charcoal900)
            .border(BorderStroke(1.dp, Charcoal700))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = HyroxStrings.customizeStationTitle(spec.stationNumber, lang),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = ElectricBlue,
                    letterSpacing = 1.2.sp
                )
            )

            // Quick Movement Selector dropdown
            HyroxDropdownField(
                label = HyroxStrings.stationNameLabel(lang),
                selectedValue = HyroxStrings.translateStationName(editedName, lang),
                options = HyroxDivisionData.CUSTOM_MOVEMENT_OPTIONS.map { HyroxStrings.translateStationName(it, lang) },
                onSelect = { selectedMovement ->
                    val originalIdx = HyroxDivisionData.CUSTOM_MOVEMENT_OPTIONS.indexOfFirst {
                        HyroxStrings.translateStationName(it, lang) == selectedMovement
                    }
                    editedName = if (originalIdx != -1) HyroxDivisionData.CUSTOM_MOVEMENT_OPTIONS[originalIdx] else selectedMovement
                },
                modifier = Modifier.width(170.dp),
                testTag = "movement_dropdown_${spec.stationNumber}"
            )
        }

        // Custom Distance/Reps and Custom Weight
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Distance or Reps
            OutlinedTextField(
                value = editedDistanceReps,
                onValueChange = { editedDistanceReps = it },
                label = { Text(HyroxStrings.distanceRepsLabel(lang), fontSize = 11.sp, color = TextSecondary) },
                singleLine = true,
                modifier = Modifier
                    .weight(1.2f)
                    .testTag("edit_dist_reps_${spec.stationNumber}"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = Charcoal600,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = Charcoal800,
                    unfocusedContainerColor = Charcoal800
                ),
                shape = RoundedCornerShape(10.dp)
            )

            // Weight Input
            OutlinedTextField(
                value = if (editedWeightKg == 0.0) "0" else if (editedWeightKg % 1.0 == 0.0) editedWeightKg.toInt().toString() else editedWeightKg.toString(),
                onValueChange = { input ->
                    val parsed = input.toDoubleOrNull() ?: 0.0
                    editedWeightKg = parsed.coerceAtLeast(0.0)
                },
                label = { Text(if (useLbs) HyroxStrings.lbsUnit(lang) else HyroxStrings.kgUnit(lang), fontSize = 11.sp, color = TextSecondary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("edit_weight_${spec.stationNumber}"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = Charcoal600,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = Charcoal800,
                    unfocusedContainerColor = Charcoal800
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        // Weight Quick Stepper Buttons (-10kg, -2.5kg, +2.5kg, +10kg)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${HyroxStrings.weightLabel(lang)}:",
                style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary, fontSize = 10.sp)
            )

            listOf(-10.0, -2.5, 2.5, 10.0).forEach { step ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Charcoal800)
                        .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(6.dp))
                        .clickable {
                            val newW = (editedWeightKg + step).coerceAtLeast(0.0)
                            editedWeightKg = (newW * 10).toInt() / 10.0
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (step > 0) "+${step}kg" else "${step}kg",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (step > 0) ElectricBlue else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val lbsCalc = (editedWeightKg * 2.20462 * 10).toInt() / 10.0
            Text(
                text = "≈ $lbsCalc lbs",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextTertiary,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp
                )
            )
        }

        // Equipment details text
        OutlinedTextField(
            value = editedDetails,
            onValueChange = { editedDetails = it },
            label = { Text(HyroxStrings.equipmentDetailsLabel(lang), fontSize = 11.sp, color = TextSecondary) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("edit_details_${spec.stationNumber}"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricBlue,
                unfocusedBorderColor = Charcoal600,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = Charcoal800,
                unfocusedContainerColor = Charcoal800
            ),
            shape = RoundedCornerShape(10.dp)
        )

        // Delete, Cancel, & Save Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (canDelete && onDelete != null) {
                TextButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_station_${spec.stationNumber}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove Station",
                        tint = Color(0xFFFF5252),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = HyroxStrings.removeStationButton(lang),
                        color = Color(0xFFFF5252),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(
                    onClick = onCancel,
                    modifier = Modifier.testTag("cancel_station_edit_${spec.stationNumber}")
                ) {
                    Text(HyroxStrings.cancelButton(lang), color = TextSecondary, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        val lbs = (editedWeightKg * 2.20462 * 10).toInt() / 10.0
                        val updated = spec.copy(
                            name = editedName.ifBlank { spec.name },
                            distanceOrReps = editedDistanceReps.ifBlank { spec.distanceOrReps },
                            weightKg = editedWeightKg,
                            weightLbs = lbs,
                            details = editedDetails.ifBlank { spec.details }
                        )
                        onSave(updated)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Charcoal900),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("save_station_edit_${spec.stationNumber}")
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(HyroxStrings.saveStationButton(lang), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun CustomPresetDialog(
    gender: Gender,
    onDismiss: () -> Unit,
    onSelectPreset: (String, List<StationSpec>) -> Unit
) {
    val lang = LocalAppLanguage.current
    val presetNames = listOf(
        "Standard Open",
        "Pro Standards",
        "Half Sim (4 Stations)",
        "10-Station Ultra",
        "Heavy Rx (+20%)",
        "Scaled / Light (-20%)",
        "Gym / Dumbbell Setup"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Charcoal800,
        title = {
            Text(
                text = HyroxStrings.selectPresetDialogTitle(lang),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = ElectricBlue,
                    fontSize = 16.sp
                )
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                presetNames.forEach { name ->
                    val translatedTitle = HyroxStrings.presetTitle(name, lang)
                    val translatedDesc = HyroxStrings.presetDescription(name, lang)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val specs = HyroxDivisionData.getCustomPresetSpecs(name, gender)
                                onSelectPreset(name, specs)
                            },
                        colors = CardDefaults.cardColors(containerColor = Charcoal700),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Charcoal500)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = translatedTitle,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary,
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = translatedDesc,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(HyroxStrings.cancelButton(lang), color = TextSecondary)
            }
        }
    )
}
