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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.viewmodels.SuccessfulRegistrationViewModel

/**
 * Composable function representing the SignUp screen of the application.
 *
 * @param navController NavController for navigating between screens.
 * @param successfulRegistrationViewModel View model to handle the actions of the screen
 */
@Composable
fun SignUnSuccessfulScreen(
    navController: NavController,
    successfulRegistrationViewModel: SuccessfulRegistrationViewModel = viewModel()
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
            SmallSingUpSuccessfulScreen(
                navController = navController,
                successfulRegistrationViewModel = successfulRegistrationViewModel
            )
        }
        // If the screen is wider, if it is a tablet
        else {
            WideSingUpSuccessfulScreen(
                navController = navController,
                successfulRegistrationViewModel = successfulRegistrationViewModel
            )
        }
    }
}

/**
 * Composable function representing the SignUp screen of the application for small screen (mobile).
 *
 * @param navController NavController for navigating between screens.
 * @param successfulRegistrationViewModel View model to handle the actions of the screen
 */
@Composable
fun SmallSingUpSuccessfulScreen(
    navController: NavController,
    successfulRegistrationViewModel: SuccessfulRegistrationViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTextComponent(
            value = successfulRegistrationViewModel.userName.value.toString(),
            shouldBeCentered = false, shouldBeRed = true
        )
        HeaderTextComponent(
            value = stringResource(id = R.string.registration_successful),
            shouldBeCentered = true,
            shouldBeRed = true
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            MyImageComponent(R.drawable.souschef_logo, modifier = Modifier.fillMaxSize())
        }


        Spacer(modifier = Modifier.height(5.dp))
        NormalTextComponent(value = stringResource(id = R.string.after_registration))

        Spacer(
            modifier = Modifier.weight(1f)
        )
        ButtonComponent(
            value = stringResource(
                id = R.string.next_step
            ),
            onClickAction = { successfulRegistrationViewModel.redirect(navController) },
            isEnabled = true
        )
    }
}

/**
 * Composable function representing the SignUp screen of the application for wide screen (tablet).
 *
 * @param navController NavController for navigating between screens.
 * @param successfulRegistrationViewModel View model to handle the actions of the screen
 */
@Composable
fun WideSingUpSuccessfulScreen(
    navController: NavController,
    successfulRegistrationViewModel: SuccessfulRegistrationViewModel
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            HeaderTextComponent(
                value = successfulRegistrationViewModel.userName.value.toString(),
                shouldBeCentered = false,
                shouldBeRed = true
            )
            HeaderTextComponent(
                value = stringResource(id = R.string.registration_successful),
                shouldBeCentered = true,
                shouldBeRed = true

            )


            MyImageComponent(
                R.drawable.souschef_logo,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column {
            NormalTextComponent(value = stringResource(id = R.string.after_registration))
            ButtonComponent(
                value = stringResource(
                    id = R.string.next_step
                ),
                onClickAction = { successfulRegistrationViewModel.redirect(navController) },
                isEnabled = true
            )

        }
    }
}


/**
 * Preview annotation for previewing the SignUpSuccessfulScreen in Android Studio.
 */
@Preview(device = Devices.PIXEL_6)
@Preview(device = Devices.PIXEL_TABLET)
@Composable
fun PreviewSignInSuccessfulScreen() {
    SignUnSuccessfulScreen(navController = rememberNavController())
}