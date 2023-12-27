package eu.mobcomputing.dima.registration.data.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.navigation.Screen


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val selected: Boolean = false,
    val screen: Screen
)


class HomeViewModel : ViewModel() {
    val userLoggedIn: MutableState<Boolean> = mutableStateOf(false)

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        val firebaseAuth = FirebaseAuth.getInstance()
        userLoggedIn.value = firebaseAuth.currentUser != null
    }

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