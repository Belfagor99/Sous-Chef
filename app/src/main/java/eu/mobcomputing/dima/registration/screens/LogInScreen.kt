package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
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
import eu.mobcomputing.dima.registration.components.ClickableForgottenPasswordTextComponent
import eu.mobcomputing.dima.registration.components.ClickableLoginTextComponent
import eu.mobcomputing.dima.registration.components.MyPasswordFieldComponent
import eu.mobcomputing.dima.registration.components.MyRedHeadingComponent
import eu.mobcomputing.dima.registration.components.MyTextFieldComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.data.LogInViewModel
import eu.mobcomputing.dima.registration.data.UIEvent

@Composable
fun LogInScreen(navController: NavController, logInViewModel: LogInViewModel = viewModel()) {
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                    logInViewModel.onEvent(UIEvent.EmailChanged(it))
                }
            )
            MyPasswordFieldComponent(
                labelValue = stringResource(id = R.string.password),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_lock_24
                ),
                onTextSelected = {
                    logInViewModel.onEvent(UIEvent.PasswordChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = stringResource(id = R.string.log_in_text), {})
            Spacer(modifier = Modifier.height(20.dp))
            ClickableForgottenPasswordTextComponent()
            Spacer(modifier = Modifier.height(240.dp))
            ClickableLoginTextComponent()

        }
    }
}

@Preview
@Composable
fun LogInScreenPreview(){
    LogInScreen(navController = rememberNavController())
}