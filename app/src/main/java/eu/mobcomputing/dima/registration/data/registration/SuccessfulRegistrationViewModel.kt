package eu.mobcomputing.dima.registration.data.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import eu.mobcomputing.dima.registration.data.User

class SuccessfulRegistrationViewModel : ViewModel() {
    private val TAG = SuccessfulRegistrationViewModel::class.simpleName
    var userName: String = ""
    init {
        welcomeUser()
    }

    private fun fetchUserName(userID: String) {
        val db = Firebase.firestore
        val userDocumentRef: DocumentReference = db.collection("users").document(userID)

        userDocumentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    user?.let {
                        // Update the userName property
                        userName = "${it.firstName} ${it.lastName}"
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error fetching user document", e)
            }
    }

    private fun welcomeUser() {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            fetchUserName(userID)
        } ?: run {
            Log.d(TAG, "Home screen USER is not logged in")
        }
    }

}