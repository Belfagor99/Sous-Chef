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
import eu.mobcomputing.dima.registration.components.ClickableRegisterTextComponent
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyPasswordFieldComponent
import eu.mobcomputing.dima.registration.components.MyRedHeadingComponent
import eu.mobcomputing.dima.registration.components.MyTextFieldComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.data.registration.RegistrationUIEvent
import eu.mobcomputing.dima.registration.data.registration.RegistrationViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    //sharedRegistrationViewModel: SharedRegistrationViewModel = viewModel()
    registrationViewModel: RegistrationViewModel = viewModel()
    ) {
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
                .padding(18.dp)
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                MyRedHeadingComponent(value = stringResource(id = R.string.create_account_text), shouldBeCentered = true)
                Spacer(modifier = Modifier.height(10.dp))
                /*StepperBar(
                    steps = sharedRegistrationViewModel.steps,
                    currentStep = sharedRegistrationViewModel.userInfoStep.value
                )*/
                HeaderTextComponent(value = stringResource(id = R.string.personal_information))
                Spacer(modifier = Modifier.height(10.dp))

                // First name Component
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    iconPainterResource = painterResource(
                        id = R.drawable.baseline_person_24
                    ),
                    onTextSelected = {
                        registrationViewModel.onEvent(
                            RegistrationUIEvent.FirstNameChanged(it),
                            navController
                        )
                    },
                    errorStatus = registrationViewModel.registrationUIState.value.firstNameError,

                    //preFilledText = shaiewModel.getSharedName(),
                    shouldPreFill = false
                )

                // Last name component
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    iconPainterResource = painterResource(
                        id = R.drawable.baseline_person_24
                    ),
                    onTextSelected = {
                        registrationViewModel.onEvent(
                            RegistrationUIEvent.LastNameChanged(it),
                            navController
                        )
                    },
                    errorStatus = registrationViewModel.registrationUIState.value.lastNameError,
                    //preFilledText = shiewModel.getSharedLastName(),
                    //shouldPreFill = sharedRegistrationViewModel.readOnlyPassword.value
                    shouldPreFill = false
                    )

                //R-mail component
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    iconPainterResource = painterResource(
                        id = R.drawable.baseline_alternate_email_24
                    ),
                    onTextSelected = {
                        registrationViewModel.onEvent(
                            RegistrationUIEvent.EmailChanged(it),
                            navController
                        )
                    },
                    errorStatus = registrationViewModel.registrationUIState.value.emailError,
                    //preFilledText = sharedRegistrationViewModel.getSharedEmail(),
                    //shouldPreFill = sharedRegistrationViewModel.readOnlyPassword.value
                    shouldPreFill = false
                    )

                // Password component
                MyPasswordFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    iconPainterResource = painterResource(
                        id = R.drawable.baseline_lock_24
                    ),
                    onTextSelected = {
                        registrationViewModel.onEvent(
                            RegistrationUIEvent.PasswordChanged(it),
                            navController
                        )
                    },
                    errorStatus = registrationViewModel.registrationUIState.value.passwordError,
                    //readOnly = sharedRegistrationViewModel.readOnlyPassword.value,
                    readOnly = false,
                    labelValueFiled = "Password cannot be modified once filled",
                )

                Spacer(modifier = Modifier.weight(1f))
                ButtonComponent(
                    value = stringResource(id = R.string.sign_up),
                    isEnabled = registrationViewModel.allValidationPassed.value,
                    onClickAction = {
                        registrationViewModel.onEvent(RegistrationUIEvent.RegistrationButtonClicked,
                            navController)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp) )
                NormalTextComponent(value = stringResource(id = R.string.or))
                ClickableRegisterTextComponent(onClickAction = {
                    registrationViewModel.redirectToLogInScreen(navController)
                }
                )
            }
        }

        if (registrationViewModel.registrationInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}
