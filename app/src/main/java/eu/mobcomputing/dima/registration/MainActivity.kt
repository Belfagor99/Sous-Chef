package eu.mobcomputing.dima.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.mobcomputing.dima.registration.data.home.HomeViewModel
import eu.mobcomputing.dima.registration.navigation.SetUpNavGraph
import eu.mobcomputing.dima.registration.ui.theme.RegistrationTheme

/**
 * Main entry point for the application.
 * Configures the navigation and sets up the initial screen.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val homeViewModel: HomeViewModel by viewModels()

    /**
     * Called when the activity is first created.
     * Initializes the content of the activity and sets up the navigation graph.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *     being shut down, this Bundle contains the data it most recently supplied in
     *     [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationTheme {
                navController = rememberNavController()
                SetUpNavGraph(navController = navController, homeViewModel)
            }
        }
    }
}


