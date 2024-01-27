package eu.mobcomputing.dima.registration.activities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.navigation.SetUpNavGraph
import eu.mobcomputing.dima.registration.ui.theme.RegistrationTheme


/**
 * Main entry point for the application.
 * Configures the navigation and sets up the initial screen.
 */

class MainActivity : NetworkAwareActivity() {
    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun setContent() {
        RegistrationTheme {
            navController = rememberNavController()
            SetUpNavGraph(navController = navController)
        }
    }
}



