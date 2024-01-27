package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.User
import eu.mobcomputing.dima.registration.utils.checkNetworkConnectivity
import javax.inject.Inject

/**
 * ViewModel class for handling the addition of ingredients to the user's pantry.
 *
 */
@HiltViewModel
class AddIngredientToPantryViewModel  @Inject constructor(
    application: Application,
) : AndroidViewModel(application)  {



    private var _connectionStatus = MutableLiveData<Boolean>(checkNetworkConnectivity(application.applicationContext))
    var connectionStatus : LiveData<Boolean> = _connectionStatus




    /**
     * Reference to the user's document in Firestore.
     */
    var userDoc : DocumentReference? = getUserDocumentRef()


    /**
     * Adds the specified ingredient to the user's pantry. If ingredient already present it just update
     * the counter.
     *
     * @param ingredientToAdd The ingredient to be added to the pantry.
     */
    fun addIngredientToPantry( ingredientToAdd: Ingredient) : Boolean {

        var exit : Boolean = false

        try {
            if (userDoc == null){
                Log.e("ADD TO PANTRY", "Error fetching user doc")
            }else{

                /*
                * If the ingredient is already present modify the userQuantity variable
                */
                userDoc!!.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {

                        // Check if the array contains the specified element
                        val array = documentSnapshot.toObject(User::class.java)?.ingredientsInPantry

                        if (array != null) {
                            val existingIngredientsByName = array.filter { it.name  == ingredientToAdd.name }

                            if (existingIngredientsByName.isNotEmpty()) {
                                // Object with the specified name already exist, not add
                                exit = false
                            } else {
                                // Object with the specified name doesn't exist, add it to the array
                                userDoc!!.update("ingredientsInPantry", FieldValue.arrayUnion(ingredientToAdd))
                                exit = true
                            }
                        }else{
                            Log.e("CHECK","array seems to be null!")
                        }
                    } else {
                        // Document does not exist
                        Log.e("CHECK","Document does not exist.")
                    }
                }.addOnFailureListener { e ->
                    // Handle errors
                    Log.e("CHECK","Error checking array: $e")
                }

            }
        } catch (e: Exception) {
            Log.e("ADD TO PANTRY", "Error adding ingredient to user's pantry", e)

        }

        return exit
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
            Log.d("ADD TO PANTRY", "USER is not logged in")
            return null
        }
    }

}