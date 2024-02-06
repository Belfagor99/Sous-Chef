package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.mobcomputing.dima.registration.viewmodels.LogInViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// To perform tests use device of such type that the function is testing
@RunWith(AndroidJUnit4::class)
class LogInScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSmallLoginScreen() {
        val viewModel = LogInViewModel()

        composeTestRule.setContent {
            SmallLogInScreen(
                navController = TestNavHostController(LocalContext.current),
                logInViewModel = viewModel
            )
        }
        testSameComponents(viewModel)
    }

    @Test
    fun testWideLoginScreen() {
        val viewModel = LogInViewModel()

        composeTestRule.setContent {
            WideLogInScreen(
                navController = TestNavHostController(LocalContext.current),
                logInViewModel = viewModel
            )
        }
        testSameComponents(viewModel)
        composeTestRule
            .onNodeWithContentDescription(viewModel.imageDesc)
            .assertIsDisplayed()
    }

    @Test
    fun testCircularWhenLogInInProgress() {
        val viewModel = LogInViewModel()
        viewModel.logInInProgress.value = true
        composeTestRule.setContent {
            SmallLogInScreen(
                navController = TestNavHostController(LocalContext.current),
                logInViewModel = viewModel
            )
        }
        composeTestRule
            .onNodeWithContentDescription(viewModel.circular)
            .assertIsDisplayed()
    }

    @Test
    fun testScreenWhenPasswordForgotten() {
        val viewModel = LogInViewModel()
        viewModel.passwordIsIncorrect.value = true
        composeTestRule.setContent {
            SmallLogInScreen(
                navController = TestNavHostController(LocalContext.current),
                logInViewModel = viewModel
            )
        }
        composeTestRule
            .onNodeWithContentDescription(viewModel.compDesc)
            .assertIsDisplayed()
    }

    @Test
    fun testScreenWhenEmailSent() {
        val viewModel = LogInViewModel()
        viewModel.passwordResetSent.value = true
        composeTestRule.setContent {
            SmallLogInScreen(
                navController = TestNavHostController(LocalContext.current),
                logInViewModel = viewModel
            )
        }
        composeTestRule
            .onNodeWithTag(viewModel.passwordResentDesc)
            .assertIsDisplayed()
    }

    private fun testSameComponents(viewModel: LogInViewModel) {
        composeTestRule
            .onNodeWithContentDescription(viewModel.btnDesc)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.normTxt3)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.normTxt1)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.clickableTxt)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.clickableTxt2)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.emailField)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.passwdField)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(viewModel.headTxt)
            .assertIsDisplayed()
    }
}