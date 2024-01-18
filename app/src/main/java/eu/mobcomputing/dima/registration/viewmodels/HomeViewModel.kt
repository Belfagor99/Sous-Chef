package eu.mobcomputing.dima.registration.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.models.User
import eu.mobcomputing.dima.registration.navigation.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


/**
 * ViewModel for the home screen responsible for handling home screen.
 */

class HomeViewModel : ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username


    init {
        welcomeUser()
    }

    /**
     *  Fetching user's name from Firestore based on the provided userID.
     *
     * @param userID userID that serves as document identifier.
     *
     */
    private fun fetchUserName(userID: String) {
        val db = FirebaseFirestore.getInstance()
        val userDocumentRef: DocumentReference = db.collection("users").document(userID)

        // Use viewModelScope for coroutine management
        viewModelScope.launch {
            try {
                val documentSnapshot = userDocumentRef.get().await()

                if (documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    user?.let {
                        // Update the userName property
                        _username.postValue(it.firstName)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching user document", e)
            }
        }
    }

    /**
     *  Welcomes user when the ViewModel is initialized.
     *
     */
    private fun welcomeUser() {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            fetchUserName(userID)
        } ?: run {
            Log.d(TAG, "Home screen USER is not logged in")
        }
    }
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