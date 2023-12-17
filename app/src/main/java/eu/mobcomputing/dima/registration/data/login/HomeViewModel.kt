package eu.mobcomputing.dima.registration.data.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.navigation.Screen

class HomeViewModel: ViewModel() {
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