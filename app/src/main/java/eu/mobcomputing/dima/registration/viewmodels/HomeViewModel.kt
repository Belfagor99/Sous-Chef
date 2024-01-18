package eu.mobcomputing.dima.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.navigation.Screen


/**
 * ViewModel for the home screen responsible for handling home screen.
 */

class HomeViewModel : ViewModel() {

    /**
     * Logs out the user and navigates to the welcome screen.
     *
     * @param navController The NavController used for navigation.
     */
    fun logOut(navController: NavController) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                navController.navigate(route = Screen.Welcome.route)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }
}