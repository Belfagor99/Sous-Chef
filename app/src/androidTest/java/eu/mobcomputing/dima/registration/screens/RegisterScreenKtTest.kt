package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.mobcomputing.dima.registration.viewmodels.RegistrationViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// To perform tests use device of such type that the function is testing
@RunWith(AndroidJUnit4::class)
class RegisterScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSmallRegistrationScreen() {
        val viewModel = RegistrationViewModel()

        composeTestRule.setContent {
            SmallRegistrationScreen(
                navController = TestNavHostController(LocalContext.current),
                registrationViewModel = viewModel
            )
        }
        testSameComponents(viewModel)
    }
    @Test
    fun testCircularProgressIsDisplayedRegistration(){
        val viewModel = RegistrationViewModel()
        viewModel.registrationInProgress.value = true

        composeTestRule.setContent {
            SmallRegistrationScreen(
                navController = TestNavHostController(LocalContext.current),
                registrationViewModel = viewModel
            )
        }

        composeTestRule
            .onNodeWithContentDescription(viewModel.circular)
            .assertIsDisplayed()

    }
    @Test
    fun testAlertRegistrationDialogIsDisplayed(){
        val viewModel = RegistrationViewModel()

        viewModel.openAlertDialog.value = true
        composeTestRule.setContent {
            SmallRegistrationScreen(
                navController = TestNavHostController(LocalContext.current),
                registrationViewModel = viewModel
            )
        }

        composeTestRule
            .onNodeWithContentDescription(viewModel.alertDesc)
            .assertIsDisplayed()

    }

    @Test
    fun testWideRegistrationScreen() {
        val viewModel = RegistrationViewModel()

        composeTestRule.setContent {
            WideRegistrationScreen(
                navController = TestNavHostController(LocalContext.current),
                registrationViewModel = viewModel
            )
        }
        testSameComponents(viewModel)
        composeTestRule
            .onNodeWithContentDescription(viewModel.appLogoImg)
            .assertIsDisplayed()
    }


    private fun testSameComponents(viewModel: RegistrationViewModel) {
        // Assertions is UI components are displayed
        composeTestRule
            .onNodeWithContentDescription(viewModel.buttonDesc)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.clickableTxt)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.field4)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.field3)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.field2)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.field1)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.normTxt1)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.headerTxt1)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(viewModel.headerTxt2)
            .assertIsDisplayed()
    }

}
