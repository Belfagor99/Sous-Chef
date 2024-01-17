package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
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


class SuccessfulRegistrationViewModel : ViewModel() {
    private val TAG = SuccessfulRegistrationViewModel::class.simpleName

    // MutableLiveData to hold the user's name
    private val _userName = MutableLiveData<String>()

    // Expose an immutable LiveData to observe changes to userName
    val userName: MutableLiveData<String> get() = _userName

    // Initialization block, called when the ViewModel is created
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
                        _userName.postValue("${it.firstName} ${it.lastName}")
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

    fun redirect(navController: NavController){
        navController.navigate(route = Screen.UserAllergies.route) {
            popUpTo(Screen.SignUnSuccessful.route) { inclusive = true }
        }
    }
}