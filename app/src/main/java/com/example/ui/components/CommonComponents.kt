package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Charcoal500
import com.example.ui.theme.Charcoal600
import com.example.ui.theme.Charcoal700
import com.example.ui.theme.Charcoal800
import com.example.ui.theme.Charcoal900
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.NeonYellowContainer
import com.example.ui.theme.NeonYellowDim
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Oversized, high-visibility button in Elegant Dark theme (rounded-2rem / 28dp pill)
 */
@Composable
fun HyroxBigButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subText: String? = null,
    icon: ImageVector? = null,
    isPrimary: Boolean = true,
    enabled: Boolean = true,
    testTag: String = "hyrox_big_button"
) {
    val bgColor = if (isPrimary) {
        if (enabled) NeonYellow else Charcoal700
    } else {
        Charcoal700
    }

    val contentColor = if (isPrimary) {
        if (enabled) Charcoal900 else TextSecondary
    } else {
        TextPrimary
    }

    val borderColor = if (isPrimary) {
        if (enabled) NeonYellow else Charcoal600
    } else {
        Charcoal600
    }

    Box(
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 72.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(24.dp))
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = if (isPrimary) Charcoal900 else NeonYellow),
                onClick = onClick
            )
            .padding(horizontal = 20.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (subText != null) {
                    Text(
                        text = subText.uppercase(),
                        color = if (isPrimary) Charcoal900.copy(alpha = 0.65f) else TextSecondary,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.6.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Text(
                    text = text.uppercase(),
                    color = contentColor,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.8.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Elegant Dark Selector for Divisions (Gender, Category, Age)
 */
@Composable
fun HyroxDropdownField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "hyrox_dropdown"
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp,
                color = TextSecondary
            ),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Box(
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Charcoal800)
                .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(14.dp))
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedValue,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand $label",
                    tint = NeonYellow
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Charcoal800)
                    .border(BorderStroke(1.dp, Charcoal500), RoundedCornerShape(14.dp))
            ) {
                options.forEach { option ->
                    val isSelected = option == selectedValue
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                color = if (isSelected) NeonYellow else TextPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = TextPrimary
                        )
                    )
                }
            }
        }
    }
}

/**
 * Top Segmented Navigation Tab Controller in Elegant Dark style
 */
@Composable
fun HyroxTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Charcoal800)
            .border(BorderStroke(1.dp, Charcoal600), RoundedCornerShape(16.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) NeonYellow else Color.Transparent,
                label = "tabBg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Charcoal900 else TextSecondary,
                label = "tabText"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = Charcoal900),
                        onClick = { onTabSelected(index) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.uppercase(),
                    color = textColor,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

