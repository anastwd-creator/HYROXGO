package com.example.ui.i18n

import com.example.data.model.Category
import com.example.data.model.Gender

object HyroxStrings {

    fun appHeaderSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "ATHLETE MODE"
        AppLanguage.FR -> "MODE ATHLÈTE"
    }

    fun tabDivisions(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "DIVISIONS"
        AppLanguage.FR -> "DIVISIONS"
    }

    fun tabRaceTimer(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RACE TIMER"
        AppLanguage.FR -> "CHRONO COURSE"
    }

    fun tabWorkoutLog(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "WORKOUT LOG"
        AppLanguage.FR -> "JOURNAL"
    }

    // --- DIVISIONS SCREEN ---
    fun divisionCalculatorTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "OFFICIAL HYROX DIVISION CALCULATOR"
        AppLanguage.FR -> "CALCULATEUR OFFICIEL DE DIVISION HYROX"
    }

    fun genderLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "GENDER"
        AppLanguage.FR -> "GENRE"
    }

    fun genderName(gender: Gender, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> gender.label
        AppLanguage.FR -> when (gender) {
            Gender.MEN -> "Hommes"
            Gender.WOMEN -> "Femmes"
        }
    }

    fun categoryLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "CATEGORY"
        AppLanguage.FR -> "CATÉGORIE"
    }

    fun categoryName(category: Category, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> category.label
        AppLanguage.FR -> when (category) {
            Category.OPEN -> "Open"
            Category.PRO -> "Pro"
            Category.DOUBLES -> "Doubles"
            Category.CUSTOM -> "Personnalisé"
        }
    }

    fun ageGroupLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "AGE GROUP"
        AppLanguage.FR -> "GROUPE D'ÂGE"
    }

    fun customDivisionNameLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "CUSTOM DIVISION NAME"
        AppLanguage.FR -> "NOM DE LA DIVISION PERSONNALISÉE"
    }

    fun unitsLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "UNITS"
        AppLanguage.FR -> "UNITÉS"
    }

    fun kgUnit(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "KILOGRAMS (KG)"
        AppLanguage.FR -> "KILOGRAMMES (KG)"
    }

    fun lbsUnit(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "POUNDS (LBS)"
        AppLanguage.FR -> "LIVRES (LBS)"
    }

    fun quickPresetsLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "CUSTOM WORKOUT PRESETS"
        AppLanguage.FR -> "MODÈLES D'ENTRAÎNEMENT"
    }

    fun selectPresetButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "QUICK PRESETS"
        AppLanguage.FR -> "MODÈLES RAPIDES"
    }

    fun stationsHeader(count: Int, isCustom: Boolean, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> if (isCustom) "CUSTOM $count STATIONS & WEIGHTS" else "$count RACE STATIONS & WEIGHTS"
        AppLanguage.FR -> if (isCustom) "$count STATIONS ET CHARGES PERSONNALISÉES" else "$count STATIONS ET CHARGES OFFICIELLES"
    }

    fun customizeWeightsButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "CUSTOMIZE WEIGHTS"
        AppLanguage.FR -> "PERSONNALISER CHARGES"
    }

    fun addStationButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "ADD STATION"
        AppLanguage.FR -> "AJOUTER STATION"
    }

    fun addStationCard(number: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "+ ADD STATION $number"
        AppLanguage.FR -> "+ AJOUTER STATION $number"
    }

    fun resetStandardsButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RESET"
        AppLanguage.FR -> "RÉINITIALISER"
    }

    fun editButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Edit"
        AppLanguage.FR -> "Modifier"
    }

    fun saveStationButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Save Station"
        AppLanguage.FR -> "Enregistrer Station"
    }

    fun removeStationButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Remove"
        AppLanguage.FR -> "Supprimer"
    }

    fun cancelButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Cancel"
        AppLanguage.FR -> "Annuler"
    }

    fun stationNameLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "STATION NAME / MOVEMENT"
        AppLanguage.FR -> "NOM DU MOUVEMENT / STATION"
    }

    fun distanceRepsLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "DISTANCE / REPETITIONS"
        AppLanguage.FR -> "DISTANCE / RÉPÉTITIONS"
    }

    fun weightLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "WEIGHT"
        AppLanguage.FR -> "CHARGE"
    }

    fun equipmentDetailsLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "EQUIPMENT / TARGET NOTES"
        AppLanguage.FR -> "ÉQUIPEMENT / REMARQUES"
    }

    fun customizeStationTitle(number: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "CUSTOMIZE STATION #$number"
        AppLanguage.FR -> "PERSONNALISER STATION #$number"
    }

    fun selectPresetDialogTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "SELECT WORKOUT PRESET"
        AppLanguage.FR -> "SÉLECTIONNER UN MODÈLE"
    }

    // Preset names and descriptions
    fun presetTitle(name: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> name
        AppLanguage.FR -> when (name) {
            "Standard Open" -> "Open Standard"
            "Pro Standards" -> "Standards Pro"
            "Half Sim (4 Stations)" -> "Demi-Simulation (4 Stations)"
            "10-Station Ultra" -> "Ultra 10 Stations"
            "Heavy Rx (+20%)" -> "Charge Lourde (+20%)"
            "Scaled / Light (-20%)" -> "Adapté / Léger (-20%)"
            "Gym / Dumbbell Setup" -> "Équipement Salle / Haltères"
            else -> name
        }
    }

    fun presetDescription(name: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> when (name) {
            "Standard Open" -> "Official HYROX Open division race standards (8 stations)"
            "Pro Standards" -> "Official HYROX Pro division race standards (+heavy weights)"
            "Half Sim (4 Stations)" -> "Shortened 4-station race simulation (SkiErg, Sled Push, Rower, Wall Balls)"
            "10-Station Ultra" -> "Extended 10-station workout (+Echo Bike & Devil Press)"
            "Heavy Rx (+20%)" -> "20% heavier sleds, carries, and sandbags for peak strength overload"
            "Scaled / Light (-20%)" -> "20% lighter load for speed, recovery, and beginners"
            "Gym / Dumbbell Setup" -> "Replaced with gym-friendly dumbbells and rowers/bikes"
            else -> ""
        }
        AppLanguage.FR -> when (name) {
            "Standard Open" -> "Standards officiels de course division Open HYROX (8 stations)"
            "Pro Standards" -> "Standards officiels Pro HYROX (+charges lourdes)"
            "Half Sim (4 Stations)" -> "Simulation courte à 4 stations (SkiErg, Poussée Traîneau, Rameur, Wall Balls)"
            "10-Station Ultra" -> "Entraînement étendu à 10 stations (+Echo Bike & Devil Press)"
            "Heavy Rx (+20%)" -> "Traîneaux, charges et sacs de sable 20% plus lourds pour surcharge de force"
            "Scaled / Light (-20%)" -> "Charges allégées de 20% pour vitesse, récupération et débutants"
            "Gym / Dumbbell Setup" -> "Adapté avec haltères et vélos/rameurs accessibles en salle classique"
            else -> ""
        }
    }

    // --- RACE TIMER SCREEN ---
    fun timerHeaderTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "OFFICIAL HYROX RACE SIMULATOR"
        AppLanguage.FR -> "SIMULATEUR DE COURSE HYROX"
    }

    fun readyToRaceStatus(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "READY TO RACE"
        AppLanguage.FR -> "PRÊT POUR LA COURSE"
    }

    fun activeRaceStatus(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "ACTIVE RACE SIMULATION"
        AppLanguage.FR -> "SIMULATION EN COURS"
    }

    fun pausedStatus(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RACE PAUSED"
        AppLanguage.FR -> "COURSE EN PAUSE"
    }

    fun completedStatus(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RACE COMPLETED"
        AppLanguage.FR -> "COURSE TERMINÉE"
    }

    fun startRaceButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "START RACE SIMULATION"
        AppLanguage.FR -> "DÉMARRER LA COURSE"
    }

    fun resumeRaceButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RESUME RACE"
        AppLanguage.FR -> "REPRENDRE LA COURSE"
    }

    fun pauseRaceButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "PAUSE RACE"
        AppLanguage.FR -> "METTRE EN PAUSE"
    }

    fun finishIntervalButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "COMPLETE INTERVAL / SPLIT"
        AppLanguage.FR -> "VALIDER INTERVALLE / TEMPS"
    }

    fun nextIntervalLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "NEXT"
        AppLanguage.FR -> "SUIVANT"
    }

    fun finishLineLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Finish Line!"
        AppLanguage.FR -> "Ligne d'Arrivée !"
    }

    fun intervalCountCompleted(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "All $count Intervals Completed"
        AppLanguage.FR -> "Les $count intervalles sont terminés"
    }

    fun intervalBadge(current: Int, total: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> if (current <= total) "INTERVAL $current/$total" else "COMPLETE"
        AppLanguage.FR -> if (current <= total) "INTERVALLE $current/$total" else "TERMINÉ"
    }

    fun roxzoneActiveBadge(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "ROXZONE TRANSITION ACTIVE"
        AppLanguage.FR -> "TRANSITION ROXZONE EN COURS"
    }

    fun enterRoxzoneButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "ENTER ROXZONE"
        AppLanguage.FR -> "ENTRER DANS LA ROXZONE"
    }

    fun exitRoxzoneButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "EXIT ROXZONE & START"
        AppLanguage.FR -> "SORTIR ROXZONE & COMMENCER"
    }

    fun recordedSplitsHeader(recorded: Int, total: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RECORDED SPLITS ($recorded/$total)"
        AppLanguage.FR -> "TEMPS INTERMÉDIAIRES ($recorded/$total)"
    }

    fun splitTimeLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "SPLIT"
        AppLanguage.FR -> "TEMPS"
    }

    fun totalTimeLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "TOTAL TIME"
        AppLanguage.FR -> "TEMPS TOTAL"
    }

    fun roxzoneTimeLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "ROXZONE"
        AppLanguage.FR -> "ROXZONE"
    }

    fun runsStatLabel(km: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RUNS (${km}km)"
        AppLanguage.FR -> "COURSES (${km}km)"
    }

    fun stationsStatLabel(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "STATIONS ($count)"
        AppLanguage.FR -> "STATIONS ($count)"
    }

    fun avgRunPaceLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "AVG RUN PACE"
        AppLanguage.FR -> "ALLURE MOYENNE"
    }

    fun logThisRaceButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "LOG RACE TO HISTORY"
        AppLanguage.FR -> "ENREGISTRER AU JOURNAL"
    }

    fun shareRaceCardButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "SHARE SPLITS"
        AppLanguage.FR -> "PARTAGER LES TEMPS"
    }

    fun resetTimerButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RESET TIMER"
        AppLanguage.FR -> "RÉINITIALISER LE CHRONO"
    }

    fun raceLoggedSuccess(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "✓ RACE LOGGED TO HISTORY"
        AppLanguage.FR -> "✓ COURSE ENREGISTRÉE DANS L'HISTORIQUE"
    }

    // --- WORKOUT LOG SCREEN ---
    fun logWorkoutSessionButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "+ LOG WORKOUT SESSION"
        AppLanguage.FR -> "+ NOTER UN ENTRAÎNEMENT"
    }

    fun logWorkoutSubText(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Quick log run distance or 8 HYROX stations"
        AppLanguage.FR -> "Enregistrer une course ou les 8 stations HYROX"
    }

    fun movementsProgressHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "8 MOVEMENTS PROGRESS"
        AppLanguage.FR -> "PROGRESSION DES 8 MOUVEMENTS"
    }

    fun recentActivityHeader(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RECENT ACTIVITY ($count)"
        AppLanguage.FR -> "ACTIVITÉS RÉCENTES ($count)"
    }

    fun noWorkoutsLoggedTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "NO WORKOUTS LOGGED YET"
        AppLanguage.FR -> "AUCUN ENTRAÎNEMENT ENREGISTRÉ"
    }

    fun noWorkoutsLoggedSub(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Tap above to log your weekly runs or station training."
        AppLanguage.FR -> "Touchez ci-dessus pour noter vos courses ou entraînements aux stations."
    }

    fun weeklyRunningDistance(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "WEEKLY RUNNING DISTANCE"
        AppLanguage.FR -> "DISTANCE DE COURSE HEBDOMADAIRE"
    }

    fun runsCountLabel(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> if (count == 1) "1 RUN" else "$count RUNS"
        AppLanguage.FR -> if (count == 1) "1 COURSE" else "$count COURSES"
    }

    fun avgPaceLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "AVG PACE"
        AppLanguage.FR -> "ALLURE MOY"
    }

    fun sessionsCount(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> if (count == 1) "1 session" else "$count sessions"
        AppLanguage.FR -> if (count == 1) "1 séance" else "$count séances"
    }

    fun noLogsYet(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "No logs yet"
        AppLanguage.FR -> "Aucun log"
    }

    fun logRunningSessionTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "LOG RUNNING SESSION"
        AppLanguage.FR -> "NOTER UNE SÉANCE DE COURSE"
    }

    fun logHyroxStationTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "LOG HYROX STATION"
        AppLanguage.FR -> "NOTER UNE STATION HYROX"
    }

    fun runningTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "1. RUNNING"
        AppLanguage.FR -> "1. COURSE"
    }

    fun stationsTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "2. 8 STATIONS"
        AppLanguage.FR -> "2. 8 STATIONS"
    }

    fun distanceLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "DISTANCE"
        AppLanguage.FR -> "DISTANCE"
    }

    fun saveWorkoutEntryButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "SAVE WORKOUT ENTRY"
        AppLanguage.FR -> "ENREGISTRER L'ENTRÉE"
    }

    fun quickWorkoutLogTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "HYROX WORKOUT LOG & STATS"
        AppLanguage.FR -> "JOURNAL D'ENTRAÎNEMENT & STATS HYROX"
    }

    fun logNewWorkoutButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "LOG NEW WORKOUT"
        AppLanguage.FR -> "NOUVEL ENTRAÎNEMENT"
    }

    fun workoutMovementLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "MOVEMENT / EXERCISE"
        AppLanguage.FR -> "MOUVEMENT / EXERCICE"
    }

    fun workoutTimeLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "TIME (MM:SS)"
        AppLanguage.FR -> "TEMPS (MM:SS)"
    }

    fun minutesLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "MIN"
        AppLanguage.FR -> "MIN"
    }

    fun secondsLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "SEC"
        AppLanguage.FR -> "SEC"
    }

    fun workoutDistanceLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "DISTANCE / REPS"
        AppLanguage.FR -> "DISTANCE / RÉPS"
    }

    fun workoutWeightLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "WEIGHT (KG)"
        AppLanguage.FR -> "CHARGE (KG)"
    }

    fun workoutNotesLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "NOTES / PACING / STRATEGY"
        AppLanguage.FR -> "NOTES / ALLURE / STRATÉGIE"
    }

    fun saveWorkoutButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "SAVE WORKOUT"
        AppLanguage.FR -> "ENREGISTRER L'ENTRAÎNEMENT"
    }

    fun workoutHistoryHeader(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "WORKOUT HISTORY ($count)"
        AppLanguage.FR -> "HISTORIQUE D'ENTRAÎNEMENT ($count)"
    }

    fun noWorkoutsRecorded(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "No workouts recorded yet. Complete a race simulation or log a station workout above!"
        AppLanguage.FR -> "Aucun entraînement enregistré. Réalisez une simulation de course ou notez un exercice ci-dessus !"
    }

    fun personalBestBadge(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "PR"
        AppLanguage.FR -> "PR"
    }

    fun weeklyRunningSummaryTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "RUNNING VOLUME"
        AppLanguage.FR -> "VOLUME DE COURSE"
    }

    fun totalDistanceLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Total Run Distance"
        AppLanguage.FR -> "Distance Totale de Course"
    }

    fun shareWorkoutButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Share"
        AppLanguage.FR -> "Partager"
    }

    fun deleteWorkoutButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> "Delete"
        AppLanguage.FR -> "Supprimer"
    }

    // Translated station name helper
    fun translateStationName(original: String, lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> original
        AppLanguage.FR -> when {
            original.equals("SkiErg", ignoreCase = true) -> "SkiErg"
            original.equals("Sled Push", ignoreCase = true) -> "Poussée de Traîneau"
            original.equals("Sled Pull", ignoreCase = true) -> "Tirage de Traîneau"
            original.equals("Burpee Broad Jumps", ignoreCase = true) -> "Burpees Sautés en Longueur"
            original.equals("Rowing", ignoreCase = true) -> "Rameur"
            original.equals("Farmers Carry", ignoreCase = true) -> "Marche du Fermier"
            original.equals("Sandbag Lunges", ignoreCase = true) -> "Fentes avec Sac de Sable"
            original.equals("Wall Balls", ignoreCase = true) -> "Wall Balls"
            original.contains("Echo Bike", ignoreCase = true) || original.contains("Assault", ignoreCase = true) -> "Echo Bike / Assault Bike"
            original.contains("Devil Press", ignoreCase = true) -> "Devil Press"
            original.contains("Dumbbell Step-Over", ignoreCase = true) -> "Step-Overs avec Haltères"
            original.contains("Sandbag Clean", ignoreCase = true) -> "Épaulés Sac de Sable"
            original.contains("Kettlebell Swing", ignoreCase = true) -> "Swings Kettlebell"
            original.contains("Dumbbell Thruster", ignoreCase = true) -> "Thrusters avec Haltères"
            original.contains("Weighted Push-Up", ignoreCase = true) -> "Pompes Lestées"
            else -> original
        }
    }

    // Interval name helper (e.g., "Run 1 (1 km)" -> "Course 1 (1 km)", "Station 1: SkiErg" -> "Station 1 : SkiErg")
    fun translateIntervalName(original: String, lang: AppLanguage): String {
        if (lang == AppLanguage.EN) return original
        return when {
            original.startsWith("Run ") -> {
                original.replace("Run ", "Course ")
            }
            original.startsWith("Station ") -> {
                val parts = original.split(":", limit = 2)
                if (parts.size == 2) {
                    val stationPart = parts[0].trim()
                    val namePart = parts[1].trim()
                    "$stationPart : ${translateStationName(namePart, lang)}"
                } else {
                    original
                }
            }
            else -> translateStationName(original, lang)
        }
    }

    // Share text generator
    fun buildShareText(
        divisionTitle: String,
        totalTimeFormatted: String,
        runMins: Long,
        runSecs: Long,
        runCount: Int,
        avgRunPaceSec: Long,
        stationMins: Long,
        stationSecs: Long,
        stationCount: Int,
        roxMins: Long,
        roxSecs: Long,
        lang: AppLanguage
    ): String {
        val sb = StringBuilder()
        when (lang) {
            AppLanguage.EN -> {
                sb.appendLine("⚡ HYROXGO Race Simulation Finished!")
                sb.appendLine("🏆 Division: $divisionTitle")
                sb.appendLine("⏱️ Total Time: $totalTimeFormatted")
                sb.appendLine("🏃 ${runCount}km Run Time: ${String.format(java.util.Locale.US, "%02d:%02d", runMins, runSecs)} (Avg ${String.format(java.util.Locale.US, "%02d:%02d", avgRunPaceSec / 60, avgRunPaceSec % 60)}/km)")
                sb.appendLine("🏋️ $stationCount Stations: ${String.format(java.util.Locale.US, "%02d:%02d", stationMins, stationSecs)}")
                sb.appendLine("⚡ Roxzone: ${String.format(java.util.Locale.US, "%02d:%02d", roxMins, roxSecs)}")
                sb.appendLine()
                sb.append("Trained with HYROXGO • #HYROX #HYROXGO #ROXGO #HyroxDaily #Fitness")
            }
            AppLanguage.FR -> {
                sb.appendLine("⚡ Simulation de Course HYROXGO Terminée !")
                sb.appendLine("🏆 Division : $divisionTitle")
                sb.appendLine("⏱️ Temps Total : $totalTimeFormatted")
                sb.appendLine("🏃 Course (${runCount}km) : ${String.format(java.util.Locale.FRANCE, "%02d:%02d", runMins, runSecs)} (Moy ${String.format(java.util.Locale.FRANCE, "%02d:%02d", avgRunPaceSec / 60, avgRunPaceSec % 60)}/km)")
                sb.appendLine("🏋️ $stationCount Stations : ${String.format(java.util.Locale.FRANCE, "%02d:%02d", stationMins, stationSecs)}")
                sb.appendLine("⚡ Roxzone : ${String.format(java.util.Locale.FRANCE, "%02d:%02d", roxMins, roxSecs)}")
                sb.appendLine()
                sb.append("Entraîné avec HYROXGO • #HYROX #HYROXGO #ROXGO #HyroxFrance #Fitness")
            }
        }
        return sb.toString()
    }
}
