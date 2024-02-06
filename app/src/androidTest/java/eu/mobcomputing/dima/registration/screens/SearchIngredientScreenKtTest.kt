package eu.mobcomputing.dima.registration.screens

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.mobcomputing.dima.registration.viewmodels.SearchIngredientViewModel
import io.mockk.mockkClass
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// To perform tests use device of such type that the function is testing
@RunWith(AndroidJUnit4::class)
class SearchIngredientScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSearchIngredientComponents() {
        // Mock the application object
        val viewModel = mockkClass(SearchIngredientViewModel::class)
        // Instantiate your view model with the mocked application object
        composeTestRule.setContent {
            SearchIngredientScreen(
                navController = TestNavHostController(LocalContext.current),
                viewModel = viewModel
            )
        }

        composeTestRule
            .onNodeWithContentDescription("search bar")
            .assertIsDisplayed()
    }

}