package eu.mobcomputing.dima.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.data.home.HomeViewModel
import eu.mobcomputing.dima.registration.navigation.SetUpNavGraph
import eu.mobcomputing.dima.registration.ui.theme.RegistrationTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val homeViewModel: HomeViewModel by viewModels()

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


