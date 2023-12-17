package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.data.login.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()) {
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            HeaderTextComponent(value = stringResource(id = R.string.information_text_logged_in))
            ButtonComponent(
                value = stringResource(id = R.string.log_out),
                onClickAction = { homeViewModel.logOut(navController) },
                isEnabled = true)

        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}