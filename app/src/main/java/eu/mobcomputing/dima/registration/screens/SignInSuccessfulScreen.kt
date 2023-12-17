package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.MyRedHeadingComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.data.registration.SuccessfulRegistrationViewModel
import eu.mobcomputing.dima.registration.navigation.Screen

@Composable
fun SignInSuccessfulScreen(
    navController: NavController,
    successfulRegistrationViewModel: SuccessfulRegistrationViewModel = viewModel()
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
                .padding(18.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyRedHeadingComponent(value = successfulRegistrationViewModel.userName)
                MyRedHeadingComponent(
                    value = stringResource(id = R.string.registration_successful),
                    shouldBeCentered = true
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
                    onClickAction = { navController.navigate(route = Screen.UserAllergies.route) },
                    isEnabled = true
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignInSuccessfulScreen() {
    SignInSuccessfulScreen(navController = rememberNavController())
}