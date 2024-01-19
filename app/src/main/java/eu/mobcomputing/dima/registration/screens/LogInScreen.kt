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
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                HeaderTextComponent(value = stringResource(id = R.string.welcome_back_text), shouldBeCentered = true, shouldBeRed = true)
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = stringResource(id = R.string.log_in_small_text))
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


                    )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.log_in_text), onClickAction = {
                        logInViewModel.onEvent(LogInUIEvent.LogInButtonClicked, navController)
                    }, isEnabled = logInViewModel.allValidationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                ClickableForgottenPasswordTextComponent(onClickAction = {
                    logInViewModel.resetPassword(
                        email = logInViewModel.logInUIState.value.email
                    )
                })

                if (logInViewModel.passwordIsIncorrect.value) {
                    WrongPasswordSubmitterComponent(logInViewModel.logInUIState.value.numberOfRemainingSubmissions)
                }

                if (logInViewModel.passwordResetSent.value) {
                    NormalTextComponent(value = stringResource(id = R.string.password_reset))
                }

                Spacer(modifier = Modifier.weight(1f))
                NormalTextComponent(value = stringResource(id = R.string.or))
                ClickableLoginTextComponent(onClickAction = {
                    logInViewModel.redirectToSignUp(
                        navController
                    )
                })
            }


            if (logInViewModel.logInInProgress.value) {
                CircularProgressIndicator()
            }
        }
        // If the screen is wider, if it is a tablet
        else {
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
                        R.drawable.sous_chef, modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    HeaderTextComponent(value = stringResource(id = R.string.welcome_back_text), shouldBeCentered = true, shouldBeRed = true)
                    Spacer(modifier = Modifier.height(20.dp))
                    NormalTextComponent(value = stringResource(id = R.string.log_in_small_text))
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


                        )
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonComponent(
                        value = stringResource(id = R.string.log_in_text), onClickAction = {
                            logInViewModel.onEvent(LogInUIEvent.LogInButtonClicked, navController)
                        }, isEnabled = logInViewModel.allValidationPassed.value
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ClickableForgottenPasswordTextComponent(onClickAction = {
                        logInViewModel.resetPassword(
                            email = logInViewModel.logInUIState.value.email
                        )
                    })

                    if (logInViewModel.passwordIsIncorrect.value) {
                        WrongPasswordSubmitterComponent(logInViewModel.logInUIState.value.numberOfRemainingSubmissions)
                    }

                    if (logInViewModel.passwordResetSent.value) {
                        NormalTextComponent(value = stringResource(id = R.string.password_reset))
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    NormalTextComponent(value = stringResource(id = R.string.or))
                    ClickableLoginTextComponent(onClickAction = {
                        logInViewModel.redirectToSignUp(
                            navController
                        )
                    })
                }


                if (logInViewModel.logInInProgress.value) {
                    CircularProgressIndicator()
                }
            }
        }
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