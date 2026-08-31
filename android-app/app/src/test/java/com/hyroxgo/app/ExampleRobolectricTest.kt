package com.hyroxgo.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.hyroxgo.app.data.model.Category
import com.hyroxgo.app.data.model.Gender
import com.hyroxgo.app.data.model.HyroxDivisionData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("HYROXGO", appName)
  }

  @Test
  fun `custom division returns valid 8 station specs`() {
    val customSpecs = HyroxDivisionData.getStationSpecs(Gender.MEN, Category.CUSTOM)
    assertEquals(8, customSpecs.size)
    assertEquals("SkiErg", customSpecs[0].name)
    assertEquals("Wall Balls", customSpecs[7].name)
  }

  @Test
  fun `heavy rx preset increases sled and carry weights`() {
    val openSpecs = HyroxDivisionData.getStationSpecs(Gender.MEN, Category.OPEN)
    val heavySpecs = HyroxDivisionData.getCustomPresetSpecs("Heavy Rx (+20%)", Gender.MEN)
    assertEquals(8, heavySpecs.size)

    val openSledPush = openSpecs.first { it.stationNumber == 2 }
    val heavySledPush = heavySpecs.first { it.stationNumber == 2 }
    assertTrue(heavySledPush.weightKg > openSledPush.weightKg)
  }

  @Test
  fun `scaled preset decreases weights`() {
    val openSpecs = HyroxDivisionData.getStationSpecs(Gender.MEN, Category.OPEN)
    val scaledSpecs = HyroxDivisionData.getCustomPresetSpecs("Scaled / Light (-20%)", Gender.MEN)
    assertEquals(8, scaledSpecs.size)

    val openSledPush = openSpecs.first { it.stationNumber == 2 }
    val scaledSledPush = scaledSpecs.first { it.stationNumber == 2 }
    assertTrue(scaledSledPush.weightKg < openSledPush.weightKg)
  }

  @Test
  fun `race interval names match custom station specs`() {
    val gymSpecs = HyroxDivisionData.getCustomPresetSpecs("Gym / Dumbbell Setup", Gender.MEN)
    val intervals = HyroxDivisionData.getRaceIntervalNames(gymSpecs)
    assertEquals(16, intervals.size)
    assertEquals("Run 1 (1 km)", intervals[0])
    assertTrue(intervals[1].contains("SkiErg"))
    assertEquals("Run 8 (1 km)", intervals[14])
    assertTrue(intervals[15].contains("Station 8"))
  }

  @Test
  fun `half sim 4 stations generates 8 intervals`() {
    val halfSpecs = HyroxDivisionData.getCustomPresetSpecs("Half Sim (4 Stations)", Gender.MEN)
    assertEquals(4, halfSpecs.size)
    val intervals = HyroxDivisionData.getRaceIntervalNames(halfSpecs)
    assertEquals(8, intervals.size)
    assertEquals("Run 1 (1 km)", intervals[0])
    assertEquals("Station 1: SkiErg", intervals[1])
    assertEquals("Run 4 (1 km)", intervals[6])
    assertEquals("Station 4: Wall Balls", intervals[7])
  }

  @Test
  fun `10 station ultra generates 20 intervals`() {
    val ultraSpecs = HyroxDivisionData.getCustomPresetSpecs("10-Station Ultra", Gender.MEN)
    assertEquals(10, ultraSpecs.size)
    val intervals = HyroxDivisionData.getRaceIntervalNames(ultraSpecs)
    assertEquals(20, intervals.size)
    assertEquals("Run 10 (1 km)", intervals[18])
    assertTrue(intervals[19].contains("Devil Press"))
  }
}
