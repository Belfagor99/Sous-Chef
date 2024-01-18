package eu.mobcomputing.dima.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import eu.mobcomputing.dima.registration.navigation.Screen

class WelcomeViewModel : ViewModel() {
    fun redirection(navController: NavController) {
        navController.navigate(route = Screen.Register.route) {
            popUpTo(Screen.Welcome.route) { inclusive = true }
        }
    }
}