package eu.mobcomputing.dima.registration.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataViewModel
import org.junit.Rule
import org.junit.Test

class UserAllergiesScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSmallUserDietScreenComponentsVisibility() {
        val viewModel = SharedUserDataViewModel()

        composeTestRule.setContent {
            SmallUserAllergiesScreen(
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
            WiderUserAllergiesScreen(
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

        viewModel.allergens.forEach { allergen ->
            val description = allergen.name
            assertNodeIsDisplayedWithContentDescription(description)
        }

        assertNodeIsDisplayedWithContentDescription("stepper bar")
    }
}