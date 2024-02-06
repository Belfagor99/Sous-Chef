package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// To perform tests use device of such type that the function is testing
@RunWith(AndroidJUnit4::class)
class UserDietScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSmallUserDietScreenComponentsVisibility() {
        val viewModel = SharedUserDataViewModel()

        composeTestRule.setContent {
            SmallUserDietScreenView(
                navController = TestNavHostController(LocalContext.current),
                sharedUserDataViewModel = viewModel
            )
        }


        testSameComponents(viewModel)
    }

    @Test
    fun testWideUserDietScreenComponentsVisibility() {
        val viewModel = SharedUserDataViewModel()

        composeTestRule.setContent {
            WiderUserDietScreenView(
                navController = TestNavHostController(LocalContext.current),
                sharedUserDataViewModel = viewModel
            )
        }

        testSameComponents(viewModel)
        assertNodeIsDisplayedWithContentDescription(viewModel.imgDesc)
    }


    private fun assertNodeIsDisplayedWithTag(tag: String) {
        composeTestRule
            .onNodeWithTag(tag)
            .assertIsDisplayed()
            .also { println("Node with tag $tag is displayed") }
    }

    private fun assertNodeIsDisplayedWithContentDescription(contentDescription: String) {
        composeTestRule
            .onNodeWithContentDescription(contentDescription)
            .assertIsDisplayed()
            .also { println("Node with content description '$contentDescription' is displayed") }
    }

    private fun testSameComponents(viewModel: SharedUserDataViewModel){

        assertNodeIsDisplayedWithTag(viewModel.headerComp1)
        assertNodeIsDisplayedWithTag(viewModel.normComp2)
        assertNodeIsDisplayedWithTag(viewModel.butt1)
        assertNodeIsDisplayedWithTag(viewModel.butt2)

        viewModel.dietOptions.forEach { dietOption ->
            val description = dietOption.type.diet
            assertNodeIsDisplayedWithContentDescription(description)
        }

        assertNodeIsDisplayedWithContentDescription("stepper bar")
    }
}