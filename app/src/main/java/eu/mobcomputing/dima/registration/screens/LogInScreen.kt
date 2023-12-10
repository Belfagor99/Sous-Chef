package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.ClickableForgottenPasswordTextComponent
import eu.mobcomputing.dima.registration.components.ClickableLoginTextComponent
import eu.mobcomputing.dima.registration.components.MyPasswordFieldComponent
import eu.mobcomputing.dima.registration.components.MyRedHeadingComponent
import eu.mobcomputing.dima.registration.components.MyTextFieldComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.WrongPasswordSubmitterComponent
import eu.mobcomputing.dima.registration.data.LogInUIEvent
import eu.mobcomputing.dima.registration.data.LogInViewModel

@Composable
fun LogInScreen(navController: NavController, logInViewModel: LogInViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ),
        contentAlignment = Alignment.Center
    )
    {
        Surface(
            color = colorResource(id = R.color.pink_50),
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.pink_50))
                .padding(28.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                MyRedHeadingComponent(value = stringResource(id = R.string.welcome_back_text))
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(value = stringResource(id = R.string.log_in_small_text))
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    iconPainterResource = painterResource(
                        id = R.drawable.baseline_alternate_email_24
                    ),
                    onTextSelected = {
                        logInViewModel.onEvent(LogInUIEvent.EmailChanged(it), navController)
                    },
                    errorStatus = logInViewModel.logInUIState.value.emailError

                )
                MyPasswordFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    iconPainterResource = painterResource(
                        id = R.drawable.baseline_lock_24
                    ),
                    onTextSelected = {
                        logInViewModel.onEvent(LogInUIEvent.PasswordChanged(it), navController)
                    },
                    errorStatus = logInViewModel.logInUIState.value.passwordError

                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.log_in_text),
                    onClickAction = {
                        logInViewModel.onEvent(LogInUIEvent.LogInButtonClicked, navController)
                    },
                    isEnabled = logInViewModel.allValidationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                ClickableForgottenPasswordTextComponent(
                    onClickAction = {
                        logInViewModel.resetPassword(
                            email = logInViewModel.logInUIState.value.email)
                    }
                )

                if (logInViewModel.passwordIsIncorrect.value) {
                    WrongPasswordSubmitterComponent(logInViewModel.logInUIState.value.numberOfRemainingSubmissions)
                }

                if (logInViewModel.passwordResetSent.value) {
                    NormalTextComponent(value = stringResource(id = R.string.password_reset))
                }

                Spacer(modifier = Modifier.height(240.dp))
                ClickableLoginTextComponent(
                    onClickAction = { navController.navigate(route = Screen.LogIn.route) })

            }
        }

        if (logInViewModel.logInInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun LogInScreenPreview() {
    LogInScreen(navController = rememberNavController())
}