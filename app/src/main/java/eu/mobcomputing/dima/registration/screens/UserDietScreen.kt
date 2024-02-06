package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.DietOptionList
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.SmallButtonComponent
import eu.mobcomputing.dima.registration.components.StepperBar
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataViewModel

/**
 * Composable function representing the UserDiet screen of the application.
 *
 * @param navController NavController for navigating between screens.
 * @param sharedUserDataViewModel ViewModel managing the logic for the UserDiet screen.
 */
@Composable
fun UserDietScreen(
    navController: NavController,
    sharedUserDataViewModel: SharedUserDataViewModel
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ),
        contentAlignment = Alignment.Center
    )
    {
        val isSmallScreen = maxWidth < 600.dp
        if (isSmallScreen) {
            SmallUserDietScreenView(
                navController = navController,
                sharedUserDataViewModel = sharedUserDataViewModel
            )
        }
        // If the screen is wider, if it is a tablet
        else {
            WiderUserDietScreenView(
                navController = navController,
                sharedUserDataViewModel = sharedUserDataViewModel
            )
        }
    }
}

/**
 * Composable function representing the UserDiet screen of the application for small screen (mobile).
 *
 * @param navController NavController for navigating between screens.
 * @param sharedUserDataViewModel ViewModel managing the logic for the UserDiet screen.
 */
@Composable
fun SmallUserDietScreenView(
    navController: NavController,
    sharedUserDataViewModel: SharedUserDataViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(
            modifier = Modifier
                .height(5.dp)
                .testTag("spacer1")
        )
        HeaderTextComponent(
            value = stringResource(id = R.string.create_account_text),
            shouldBeCentered = true,
            shouldBeRed = true,
            testTag = sharedUserDataViewModel.headerComp1
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .semantics { contentDescription = "spacer2" },
        )
        StepperBar(
            steps = sharedUserDataViewModel.steps,
            currentStep = sharedUserDataViewModel.dietTypeStep.value
        )
        NormalTextComponent(
            value = stringResource(id = R.string.diet_type),
            testTag = sharedUserDataViewModel.normComp2
        )

        DietOptionList(
            dietOptions = sharedUserDataViewModel.dietOptions,
            onDietClick = {
                sharedUserDataViewModel.dietOptionOnClick(it)
            },

            )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            SmallButtonComponent(
                value = stringResource(id = R.string.previous_step),
                onClickAction = {
                    sharedUserDataViewModel.backStepOnClick(navController)
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(0.5f)
                    .testTag(sharedUserDataViewModel.butt1)

            )

            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .testTag("spacer3")
            )
            SmallButtonComponent(
                value = stringResource(id = R.string.finish),
                onClickAction = {
                    sharedUserDataViewModel.finishRegistration(navController)
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(0.5f)
                    .testTag(sharedUserDataViewModel.butt2)
            )

        }
    }

}

/**
 * Composable function representing the UserDiet screen of the application for wide screen (tablet).
 *
 * @param navController NavController for navigating between screens.
 * @param sharedUserDataViewModel ViewModel managing the logic for the UserDiet screen.
 */
@Composable
fun WiderUserDietScreenView(
    navController: NavController,
    sharedUserDataViewModel: SharedUserDataViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.weight(1f).testTag("wide spacer1"))
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            MyImageComponent(
                R.drawable.sous_chef, modifier = Modifier.fillMaxSize(),
                imageDesc = sharedUserDataViewModel.imgDesc
            )
        }
        SmallUserDietScreenView(
            navController = navController,
            sharedUserDataViewModel = sharedUserDataViewModel
        )
    }
}


/**
 * Preview annotation for previewing the UserDietScreen in Android Studio.
 */
@Preview(device = Devices.PIXEL_TABLET)
@Preview(device = Devices.PIXEL_6)
@Composable
fun PreviewUserDietScreen() {
    UserDietScreen(
        navController = rememberNavController(),
        sharedUserDataViewModel = SharedUserDataViewModel()
    )
}