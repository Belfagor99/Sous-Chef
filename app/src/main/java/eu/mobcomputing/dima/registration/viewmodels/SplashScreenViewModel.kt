package eu.mobcomputing.dima.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.navigation.Screen

class SplashScreenViewModel : ViewModel() {

    val imageDescription  = "app logo and small description"
    val cicrcularProgresDescription = "circular progress indicator"

    /**
     * Checks the user's login status using Firebase authentication.
     */
    private fun checkUserLoginStatus(): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            return firebaseAuth.currentUser!!.email != null
        }
        return false
    }

    /**
     * Change the screen according to the log in status.
     *
     * @param navController NavController for navigating between screens.
     */
    fun redirection(navController: NavController) {
        if (checkUserLoginStatus()) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.SplashScreen.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Welcome.route) {
                popUpTo(Screen.SplashScreen.route) { inclusive = true }
            }
        }
    }
}