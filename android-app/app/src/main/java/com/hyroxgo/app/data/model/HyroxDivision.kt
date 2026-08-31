package com.hyroxgo.app.data.model

enum class Gender(val label: String) {
    MEN("Men"),
    WOMEN("Women")
}

enum class Category(val label: String) {
    OPEN("Open"),
    PRO("Pro"),
    DOUBLES("Doubles"),
    CUSTOM("Custom")
}

data class StationSpec(
    val stationNumber: Int,
    val name: String,
    val distanceOrReps: String,
    val weightKg: Double,
    val weightLbs: Double = ((weightKg * 2.20462 * 10).toInt() / 10.0),
    val details: String,
    val iconType: String = "general"
)

object HyroxDivisionData {
    val AGE_GROUPS = listOf(
        "16–24",
        "25–29",
        "30–34",
        "35–39",
        "40–44",
        "45–49",
        "50–54",
        "55–59",
        "60–64",
        "65–69",
        "70+"
    )

    val MOVEMENTS = listOf(
        "SkiErg",
        "Sled Push",
        "Sled Pull",
        "Burpee Broad Jumps",
        "Rowing",
        "Farmers Carry",
        "Sandbag Lunges",
        "Wall Balls"
    )

    val CUSTOM_MOVEMENT_OPTIONS = listOf(
        "SkiErg",
        "Sled Push",
        "Sled Pull",
        "Burpee Broad Jumps",
        "Rowing",
        "Farmers Carry",
        "Sandbag Lunges",
        "Wall Balls",
        "Echo Bike / Assault Bike",
        "BikeErg",
        "Dumbbell Step-Overs",
        "Sandbag Cleans",
        "Devil Presses",
        "Kettlebell Swings",
        "Dumbbell Thrusters",
        "Weighted Push-Ups"
    )

    fun getStationSpecs(gender: Gender, category: Category): List<StationSpec> {
        val isMale = gender == Gender.MEN
        val isPro = category == Category.PRO
        val isDoubles = category == Category.DOUBLES

        return listOf(
            StationSpec(
                stationNumber = 1,
                name = "SkiErg",
                distanceOrReps = "1,000 m",
                weightKg = 0.0,
                weightLbs = 0.0,
                details = "Damper setting 6 (Concept2 PM5)",
                iconType = "skierg"
            ),
            StationSpec(
                stationNumber = 2,
                name = "Sled Push",
                distanceOrReps = "4 × 12.5 m (50 m)",
                weightKg = when {
                    isPro && isMale -> 202.0
                    isPro && !isMale -> 152.0
                    !isPro && isMale -> 152.0
                    else -> 102.0
                },
                weightLbs = when {
                    isPro && isMale -> 445.0
                    isPro && !isMale -> 335.0
                    !isPro && isMale -> 335.0
                    else -> 225.0
                },
                details = when {
                    isPro && isMale -> "Sled (30kg) + 172kg plates"
                    isPro && !isMale -> "Sled (30kg) + 122kg plates"
                    !isPro && isMale -> "Sled (30kg) + 122kg plates"
                    else -> "Sled (30kg) + 72kg plates"
                },
                iconType = "sled_push"
            ),
            StationSpec(
                stationNumber = 3,
                name = "Sled Pull",
                distanceOrReps = "4 × 12.5 m (50 m)",
                weightKg = when {
                    isPro && isMale -> 153.0
                    isPro && !isMale -> 103.0
                    !isPro && isMale -> 103.0
                    else -> 78.0
                },
                weightLbs = when {
                    isPro && isMale -> 337.0
                    isPro && !isMale -> 227.0
                    !isPro && isMale -> 227.0
                    else -> 172.0
                },
                details = when {
                    isPro && isMale -> "Sled (30kg) + 123kg plates"
                    isPro && !isMale -> "Sled (30kg) + 73kg plates"
                    !isPro && isMale -> "Sled (30kg) + 73kg plates"
                    else -> "Sled (30kg) + 48kg plates"
                },
                iconType = "sled_pull"
            ),
            StationSpec(
                stationNumber = 4,
                name = "Burpee Broad Jumps",
                distanceOrReps = "80 m",
                weightKg = 0.0,
                weightLbs = 0.0,
                details = "Bodyweight (Chest to floor, feet behind hands)",
                iconType = "burpee"
            ),
            StationSpec(
                stationNumber = 5,
                name = "Rowing",
                distanceOrReps = "1,000 m",
                weightKg = 0.0,
                weightLbs = 0.0,
                details = "Damper setting 6 (Concept2 PM5)",
                iconType = "row"
            ),
            StationSpec(
                stationNumber = 6,
                name = "Farmers Carry",
                distanceOrReps = "200 m",
                weightKg = when {
                    isPro && isMale -> 32.0
                    isPro && !isMale -> 24.0
                    !isPro && isMale -> 24.0
                    else -> 16.0
                },
                weightLbs = when {
                    isPro && isMale -> 70.0
                    isPro && !isMale -> 53.0
                    !isPro && isMale -> 53.0
                    else -> 35.0
                },
                details = when {
                    isPro && isMale -> "2 × 32 kg Kettlebells"
                    isPro && !isMale -> "2 × 24 kg Kettlebells"
                    !isPro && isMale -> "2 × 24 kg Kettlebells"
                    else -> "2 × 16 kg Kettlebells"
                },
                iconType = "kettlebell"
            ),
            StationSpec(
                stationNumber = 7,
                name = "Sandbag Lunges",
                distanceOrReps = "100 m",
                weightKg = when {
                    isPro && isMale -> 30.0
                    isPro && !isMale -> 20.0
                    !isPro && isMale -> 20.0
                    else -> 10.0
                },
                weightLbs = when {
                    isPro && isMale -> 66.0
                    isPro && !isMale -> 44.0
                    !isPro && isMale -> 44.0
                    else -> 22.0
                },
                details = when {
                    isPro && isMale -> "30 kg Sandbag on shoulders"
                    isPro && !isMale -> "20 kg Sandbag on shoulders"
                    !isPro && isMale -> "20 kg Sandbag on shoulders"
                    else -> "10 kg Sandbag on shoulders"
                },
                iconType = "sandbag"
            ),
            StationSpec(
                stationNumber = 8,
                name = "Wall Balls",
                distanceOrReps = when {
                    isPro -> "100 Reps"
                    isMale -> "100 Reps"
                    isDoubles -> "100 Reps"
                    else -> "75 Reps"
                },
                weightKg = when {
                    isPro && isMale -> 9.0
                    isPro && !isMale -> 6.0
                    !isPro && isMale -> 6.0
                    else -> 4.0
                },
                weightLbs = when {
                    isPro && isMale -> 20.0
                    isPro && !isMale -> 14.0
                    !isPro && isMale -> 14.0
                    else -> 9.0
                },
                details = when {
                    isMale -> "Target height: 3.05 m (10 ft)"
                    else -> "Target height: 2.75 m (9 ft)"
                },
                iconType = "wallball"
            )
        )
    }

    fun getCustomPresetSpecs(preset: String, gender: Gender): List<StationSpec> {
        val base = getStationSpecs(gender, Category.OPEN)
        return when (preset) {
            "Half Sim (4 Stations)" -> listOf(
                base[0], // SkiErg
                base[1], // Sled Push
                base[4], // Rowing
                base[7]  // Wall Balls
            ).mapIndexed { idx, spec -> spec.copy(stationNumber = idx + 1) }
            "10-Station Ultra" -> (base + listOf(
                StationSpec(9, "Echo Bike / Assault", "50 Cal", 0.0, 0.0, "All-out cardio interval", "bike"),
                StationSpec(10, "Devil Press", "30 Reps", if (gender == Gender.MEN) 15.0 else 10.0, if (gender == Gender.MEN) 35.0 else 22.5, "2 × Dumbbell burpee ground to overhead", "kettlebell")
            )).mapIndexed { idx, spec -> spec.copy(stationNumber = idx + 1) }
            "Heavy Rx (+20%)" -> base.map { spec ->
                if (spec.weightKg > 0) {
                    val newKg = (spec.weightKg * 1.2).toInt().toDouble()
                    spec.copy(
                        weightKg = newKg,
                        weightLbs = ((newKg * 2.20462 * 10).toInt() / 10.0),
                        details = "Heavy Rx: ${spec.details} (+20% load)"
                    )
                } else spec
            }
            "Scaled / Light (-20%)" -> base.map { spec ->
                if (spec.weightKg > 0) {
                    val newKg = (spec.weightKg * 0.8).toInt().toDouble()
                    spec.copy(
                        weightKg = newKg,
                        weightLbs = ((newKg * 2.20462 * 10).toInt() / 10.0),
                        details = "Scaled: ${spec.details} (-20% load)"
                    )
                } else spec
            }
            "Gym / Dumbbell Setup" -> listOf(
                StationSpec(1, "SkiErg", "1,000 m", 0.0, 0.0, "Or 2,000m BikeErg / 100 Cal Echo", "skierg"),
                StationSpec(2, "Sled Push", "50 m", if (gender == Gender.MEN) 120.0 else 90.0, if (gender == Gender.MEN) 265.0 else 200.0, "Gym Sled + plates or DB walking lunges", "sled_push"),
                StationSpec(3, "Sled Pull", "50 m", if (gender == Gender.MEN) 80.0 else 60.0, if (gender == Gender.MEN) 175.0 else 135.0, "Rope pull or DB Romanian deadlifts", "sled_pull"),
                StationSpec(4, "Burpee Broad Jumps", "80 m", 0.0, 0.0, "80m or 40 reps chest-to-floor", "burpee"),
                StationSpec(5, "Rowing", "1,000 m", 0.0, 0.0, "Concept2 Rower damper 6", "row"),
                StationSpec(6, "Farmers Carry", "200 m", if (gender == Gender.MEN) 24.0 else 16.0, if (gender == Gender.MEN) 53.0 else 35.0, "2 × Dumbbells / Kettlebells", "kettlebell"),
                StationSpec(7, "Sandbag Lunges", "100 m", if (gender == Gender.MEN) 20.0 else 12.0, if (gender == Gender.MEN) 44.0 else 26.0, "Sandbag or 2x DB front rack lunges", "sandbag"),
                StationSpec(8, "Wall Balls", "100 Reps", if (gender == Gender.MEN) 6.0 else 4.0, if (gender == Gender.MEN) 14.0 else 9.0, "Medicine ball to 3m/2.75m target", "wallball")
            )
            "Pro Standards" -> getStationSpecs(gender, Category.PRO)
            else -> base
        }
    }

    val RACE_INTERVAL_NAMES = listOf(
        "Run 1 (1 km)",
        "Station 1: SkiErg",
        "Run 2 (1 km)",
        "Station 2: Sled Push",
        "Run 3 (1 km)",
        "Station 3: Sled Pull",
        "Run 4 (1 km)",
        "Station 4: Burpee Broad Jumps",
        "Run 5 (1 km)",
        "Station 5: Rowing",
        "Run 6 (1 km)",
        "Station 6: Farmers Carry",
        "Run 7 (1 km)",
        "Station 7: Sandbag Lunges",
        "Run 8 (1 km)",
        "Station 8: Wall Balls"
    )

    fun getRaceIntervalNames(stationSpecs: List<StationSpec>): List<String> {
        if (stationSpecs.isEmpty()) {
            return RACE_INTERVAL_NAMES
        }
        val list = mutableListOf<String>()
        stationSpecs.forEachIndexed { index, spec ->
            list.add("Run ${index + 1} (1 km)")
            list.add("Station ${index + 1}: ${spec.name}")
        }
        return list
    }
}
