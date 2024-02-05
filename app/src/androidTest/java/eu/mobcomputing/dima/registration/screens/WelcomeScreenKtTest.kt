package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.testing.TestNavHostController
import eu.mobcomputing.dima.registration.viewmodels.WelcomeViewModel
import org.junit.Rule
import org.junit.Test

class WelcomeScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun welcomeScreenContentDisplayed() {
        val viewModel = WelcomeViewModel()
        composeTestRule.setContent {
            WelcomeScreen(
                navController = TestNavHostController(LocalContext.current),
                welcomeViewModel = viewModel
            )
        }

        // Assertions is UI components are displayed
        composeTestRule
            .onNodeWithContentDescription( viewModel.imageDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(viewModel.buttonDescription)
            .assertIsDisplayed()

    }
}