package eu.mobcomputing.dima.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import eu.mobcomputing.dima.registration.navigation.Screen

class WelcomeViewModel : ViewModel() {

    val imageDescription = "logo of the application"
    val buttonDescription = "sign up or log in button"
    fun redirection(navController: NavController) {
        navController.navigate(route = Screen.Register.route) {
            popUpTo(Screen.Welcome.route) { inclusive = true }
        }
    }
}