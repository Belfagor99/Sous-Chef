package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.mobcomputing.dima.registration.viewmodels.SplashScreenViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class SplashScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun splashScreenContentDisplayed() {
        val viewModel = SplashScreenViewModel()
        composeTestRule.setContent {
            SplashScreen(
                navController = TestNavHostController(LocalContext.current),
                splashScreenViewModel = viewModel
            )
        }

        // Assertions is UI components are displayed
        composeTestRule
            .onNodeWithContentDescription( viewModel.imageDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(viewModel.cicrcularProgresDescription)
            .assertIsDisplayed()

    }
}