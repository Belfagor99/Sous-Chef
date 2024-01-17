package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.models.User
import kotlinx.coroutines.launch


/**
 * ViewModel responsible for managing user profile data, including allergies and diet.
 *
 * @property _userAllergies LiveData containing the list of allergies for the logged user.
 * @property userAllergies Exposed LiveData for observing the list of user allergies.
 * @property _userDiet LiveData containing the list of diet type for the logged user.
 * @property userDiet Exposed LiveData for observing the list of user diet.
 * @property userDoc Reference to the user's document in Firestore.
 */
class ProfileViewModel : ViewModel() {

    /**
     * LiveData containing the list of allergies and diets for the logged user.
     */
    private var _userAllergies = MutableLiveData<List<String>>(emptyList())
    var userAllergies: LiveData<List<String>> = _userAllergies

    private var _userDiet = MutableLiveData<List<String>>(emptyList())
    var userDiet: LiveData<List<String>> = _userDiet


    private var _name = MutableLiveData<String>()
    var name : LiveData<String> = _name


    private var _surname = MutableLiveData<String>()
    var surname : LiveData<String> = _surname

    private var _email = MutableLiveData<String>()
    var email : LiveData<String> = _email



    /**
     * Reference to the user's document in Firestore.
     */
    var userDoc : DocumentReference? = getUserDocumentRef()


    /**
     * Initializes the ViewModel by launching a coroutine to load ingredients from the Firestore document.
     */
    init {
        viewModelScope.launch {
            getUserCharacteristics()
        }
    }


    /**
     * Retrieve user characteristics (allergies and diet) from the Firestore database.
     * If the user document is not available or an error occurs during retrieval, appropriate logs are generated.
     */
    private fun getUserCharacteristics(){

        try {
            if (userDoc == null){
                Log.e("PROFILE->GET USER", "Error fetching user doc")
            }else{
                userDoc!!.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {

                        val user = (documentSnapshot.toObject(User::class.java))!!


                        val usrName = user.firstName
                        _name.value = usrName


                        val usrSurname = user.lastName
                        _surname.value = usrSurname

                        val usrEmail = user.email
                        _email.value = usrEmail

                        // get ingredient List from firestore user's document
                        val allergies = user.allergies.map { allergen -> allergen.name }.toList()
                        _userAllergies.value = allergies


                        val diet =  listOf(user.dietType.toString())
                        _userDiet.value = diet


                        Log.e("PROFILE->CHECK:allergies",allergies.toString())
                        Log.e("PROFILE->CHECK:diet",_userDiet.value.toString())

                    } else {
                        // Document does not exist
                        Log.e("PROFILE->CHECK","Document does not exist.")
                    }
                }.addOnFailureListener { e ->
                    // Handle errors
                    Log.e("PROFILE->CHECK","Error Failire Listener: $e")
                }

            }
        } catch (e: Exception) {
            Log.e("PROFILE-> GET USR", "Error getting user document", e)

        }

    }





    /**
     * Helper function that retrieves the DocumentReference for the current user from Firestore.
     *
     * @return The DocumentReference for the current user, or null if the user is not logged in.
     */
    private fun getUserDocumentRef(): DocumentReference? {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            val db = FirebaseFirestore.getInstance()
            return db.collection("users").document(userID)

        } ?: run {
            Log.d("PROFILE->GET USR", "USER is not logged in")
            return null
        }
    }


}