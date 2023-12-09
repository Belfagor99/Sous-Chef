package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyPasswordFieldComponent
import eu.mobcomputing.dima.registration.components.MyTextFieldComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.data.LogInViewModel
import eu.mobcomputing.dima.registration.data.UIEvent

@Composable
fun SignUpScreen(navController: NavController, logInViewModel: LogInViewModel = viewModel()) {
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
            .padding(28.dp)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NormalTextComponent(value = stringResource(id = R.string.no_account_text))
            HeaderTextComponent(value = stringResource(id = R.string.create_account_text))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.first_name),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_person_24
                ),
                onTextSelected = {
                    logInViewModel.onEvent(UIEvent.FirstNameChanged(it), navController)
                },
                errorStatus = logInViewModel.registrationUIState.value.firstNameError
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.last_name),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_person_24
                ),
                onTextSelected = {
                    logInViewModel.onEvent(UIEvent.LastNameChanged(it), navController)
                },
                errorStatus = logInViewModel.registrationUIState.value.lastNameError
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_alternate_email_24
                ),
                onTextSelected = {
                    logInViewModel.onEvent(UIEvent.EmailChanged(it), navController)
                },
                errorStatus = logInViewModel.registrationUIState.value.emailError
            )

            MyPasswordFieldComponent(
                labelValue = stringResource(id = R.string.password),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_lock_24
                ),
                onTextSelected = {
                    logInViewModel.onEvent(UIEvent.PasswordChanged(it), navController)
                },
                errorStatus = logInViewModel.registrationUIState.value.passwordError
            )

            Spacer(modifier = Modifier.height(80.dp))
            ButtonComponent(
                value = stringResource(id = R.string.create_account_text),
                isEnabled = logInViewModel.allValidationPassed.value,
                onClickAction = {
                    logInViewModel.onEvent(UIEvent.RegisterButtonClicked, navController) }
            )
            Spacer(modifier = Modifier.height(30.dp))
            // NormalTextComponent(value = stringResource(id = R.string.or))
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}
