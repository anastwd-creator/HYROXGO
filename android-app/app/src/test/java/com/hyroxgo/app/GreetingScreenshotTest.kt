package com.hyroxgo.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.hyroxgo.app.data.model.Category
import com.hyroxgo.app.data.model.Gender
import com.hyroxgo.app.data.model.HyroxDivisionData
import com.hyroxgo.app.ui.division.DivisionScreen
import com.hyroxgo.app.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun greeting_screenshot() {
    composeTestRule.setContent {
      MyApplicationTheme {
        DivisionScreen(
          gender = Gender.MEN,
          category = Category.OPEN,
          ageGroup = HyroxDivisionData.AGE_GROUPS[2],
          onGenderChange = {},
          onCategoryChange = {},
          onAgeGroupChange = {}
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}
