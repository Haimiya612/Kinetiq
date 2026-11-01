package com.kinetiq.player

import org.junit.Assert.assertEquals
import org.junit.Test

class ThemeModeTest {
    @Test
    fun knownValuesRestoreCorrectTheme() {
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromStorageValue("system"))
        assertEquals(ThemeMode.LIGHT, ThemeMode.fromStorageValue("light"))
        assertEquals(ThemeMode.DARK, ThemeMode.fromStorageValue("dark"))
    }

    @Test
    fun unknownOrMissingValueFallsBackToSystem() {
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromStorageValue(null))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromStorageValue("unexpected"))
    }
}
