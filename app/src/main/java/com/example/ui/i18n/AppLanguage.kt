package com.example.ui.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import java.util.Locale

enum class AppLanguage(val code: String, val displayName: String, val shortCode: String, val flag: String) {
    EN("en", "English", "EN", "🇬🇧"),
    FR("fr", "Français", "FR", "🇫🇷");

    companion object {
        fun getDefault(): AppLanguage {
            val systemLang = Locale.getDefault().language.lowercase()
            return if (systemLang.startsWith("fr")) FR else EN
        }
    }
}

val LocalAppLanguage = compositionLocalOf { AppLanguage.EN }
