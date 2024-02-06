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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.ClickableForgottenPasswordTextComponent
import eu.mobcomputing.dima.registration.components.ClickableLoginTextComponent
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.MyPasswordFieldComponent
import eu.mobcomputing.dima.registration.components.MyTextFieldComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.WrongPasswordSubmitterComponent
import eu.mobcomputing.dima.registration.uiEvents.LogInUIEvent
import eu.mobcomputing.dima.registration.viewmodels.LogInViewModel

/**
 * Composable function representing the LogIn screen of the application.
 *
 * @param navController NavController for navigating between screens.
 * @param logInViewModel ViewModel managing the logic for the LogIn screen.
 */
@Composable
fun LogInScreen(navController: NavController, logInViewModel: LogInViewModel = viewModel()) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ), contentAlignment = Alignment.Center
    ) {
        val isSmallScreen = maxWidth < 600.dp
        if (isSmallScreen) {
            SmallLogInScreen(navController = navController, logInViewModel = logInViewModel)
        } else {
            WideLogInScreen(navController = navController, logInViewModel = logInViewModel)
        }
    }
}

/**
 * Composable function representing the LogIn screen of the application for small screen (mobile).
 *
 * @param navController NavController for navigating between screens.
 * @param logInViewModel ViewModel managing the logic for the LogIn screen.
 */
@Composable
fun SmallLogInScreen(navController: NavController, logInViewModel: LogInViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        HeaderTextComponent(
            value = stringResource(id = R.string.welcome_back_text),
            shouldBeCentered = true,
            shouldBeRed = true,
            testTag = logInViewModel.headTxt
        )
        Spacer(modifier = Modifier.height(20.dp))
        NormalTextComponent(
            value = stringResource(id = R.string.log_in_small_text),
            testTag = logInViewModel.normTxt3)
        Spacer(modifier = Modifier.height(20.dp))
        MyTextFieldComponent(
            labelValue = stringResource(id = R.string.email),
            leadingIcon = painterResource(
                id = R.drawable.baseline_alternate_email_24
            ),
            onTextSelected = {
                logInViewModel.onEvent(LogInUIEvent.EmailChanged(it), navController)
            },
            errorStatus = logInViewModel.logInUIState.value.emailError,
            testTag = logInViewModel.emailField

            )
        MyPasswordFieldComponent(
            labelValue = stringResource(id = R.string.password),
            leadingIcon = painterResource(
                id = R.drawable.baseline_lock_24
            ),
            onTextSelected = {
                logInViewModel.onEvent(LogInUIEvent.PasswordChanged(it), navController)
            },
            errorStatus = logInViewModel.logInUIState.value.passwordError,
            testTag = logInViewModel.passwdField


            )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent(
            value = stringResource(id = R.string.log_in_text),
            onClickAction = {
                logInViewModel.onEvent(LogInUIEvent.LogInButtonClicked, navController)
            },
            isEnabled = logInViewModel.allValidationPassed.value,
            buttonDescription = logInViewModel.btnDesc
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClickableForgottenPasswordTextComponent(testTag = logInViewModel.clickableTxt2,
            onClickAction = {
                logInViewModel.resetPassword(
                    email = logInViewModel.logInUIState.value.email
                )
            })

        if (logInViewModel.passwordIsIncorrect.value) {
            WrongPasswordSubmitterComponent(
                numberOfTries = logInViewModel.logInUIState.value.numberOfRemainingSubmissions,
                compDesc = logInViewModel.compDesc
            )
        }

        if (logInViewModel.passwordResetSent.value) {
            NormalTextComponent(
                testTag = logInViewModel.passwordResentDesc,
                value = stringResource(id = R.string.password_reset)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        NormalTextComponent(
            testTag = logInViewModel.normTxt1, value = stringResource(id = R.string.or)
        )
        ClickableLoginTextComponent(testTag = logInViewModel.clickableTxt, onClickAction = {
            logInViewModel.redirectToSignUp(
                navController
            )
        })
    }


    if (logInViewModel.logInInProgress.value) {
        CircularProgressIndicator(modifier = Modifier.semantics {
            contentDescription = logInViewModel.circular
        })
    }
}

/**
 * Composable function representing the LogIn screen of the application for wide screen (tablet).
 *
 * @param navController NavController for navigating between screens.
 * @param logInViewModel ViewModel managing the logic for the LogIn screen.
 */
@Composable
fun WideLogInScreen(navController: NavController, logInViewModel: LogInViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            MyImageComponent(
                R.drawable.sous_chef,
                modifier = Modifier.fillMaxSize(),
                imageDesc = logInViewModel.imageDesc
            )
        }
        SmallLogInScreen(navController = navController, logInViewModel = logInViewModel)
    }
}

/**
 * Preview annotation for previewing the LoginScreen in Android Studio.
 */
@Preview(device = Devices.PIXEL_TABLET)
@Preview(device = Devices.PIXEL_6)
@Composable
fun LogInScreenPreview() {
    LogInScreen(navController = rememberNavController())
}