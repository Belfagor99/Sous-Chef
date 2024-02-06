package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.mobcomputing.dima.registration.viewmodels.SuccessfulRegistrationViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// To perform this tests, go to view model and comment out init function of the view model as it
// triggers a call to Firebase.

// To perform tests use device of such type that the function is testing
@RunWith(AndroidJUnit4::class)
class SignUnSuccessfulScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSmallSuccessfulRegistrationScreen() {
        val viewModel = SuccessfulRegistrationViewModel()

        composeTestRule.setContent {
            SmallSingUpSuccessfulScreen(
                navController = TestNavHostController(LocalContext.current),
                successfulRegistrationViewModel = viewModel
            )
        }
        testSameComponents(viewModel)

    }

    @Test
    fun testWideSuccessfulRegistrationScreen() {
        val viewModel = SuccessfulRegistrationViewModel()

        composeTestRule.setContent {
            WideSingUpSuccessfulScreen(
                navController = TestNavHostController(LocalContext.current),
                successfulRegistrationViewModel = viewModel
            )
        }
        testSameComponents(viewModel)
    }

    private fun testSameComponents(viewModel: SuccessfulRegistrationViewModel) {
        // Assertions is UI components are displayed
        composeTestRule
            .onNodeWithContentDescription(viewModel.imgDesc)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(viewModel.butDesc)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.headerComp1)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.headerComp2)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.normComp1)
            .assertIsDisplayed()
    }
}

