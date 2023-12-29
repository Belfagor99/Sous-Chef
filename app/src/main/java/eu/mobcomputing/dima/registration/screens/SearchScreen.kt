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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent

/**
 * Composable function representing the Search screen of the application.
 *
 * @param navController NavController for navigating between screens.
 */
@Composable
fun SearchScreen(navController: NavController) {

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
        ) {
            HeaderTextComponent("This is search screen")
            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 3
            )

        }

    }
}

/**
 * Preview annotation for previewing the SearchScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen(navController = rememberNavController())
}