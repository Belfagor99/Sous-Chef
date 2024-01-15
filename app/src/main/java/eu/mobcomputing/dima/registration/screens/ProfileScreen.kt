package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent

/**
 * Composable function representing the Profile screen of the application.
 *
 * @param navController NavController for navigating between screens.
 */
@Composable
fun ProfileScreen(navController: NavController,){
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderTextComponent("This is profile screen")
            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 2
            )

        }

    }
}


/**
 * Preview annotation for previewing the ProfileScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewProfileScreen(){
    ProfileScreen(navController = rememberNavController())
}