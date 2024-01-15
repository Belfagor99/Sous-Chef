package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.models.Ingredient

/**
 * ViewModel class for handling the addition of ingredients to the user's pantry.
 *
 */
class AddIngredientToPantryViewModel : ViewModel() {

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
    fun addIngredientToPantry( ingredientToAdd: Ingredient) {

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
                        val array = documentSnapshot.get("ingredientsInPantry") as? ArrayList<MutableMap<String,Any>>

                        if (array != null) {
                            val existingIngredient = array.find { it["name"]  == ingredientToAdd.name }

                            if (existingIngredient != null) {
                                // Object with the specified name already exists, increment the userQuantity
                                val index = array.indexOf(existingIngredient)

                                val updatedQuantity = existingIngredient["userQuantity"] as Long +  ingredientToAdd.userQuantity

                                array[index]["userQuantity"] = updatedQuantity

                                Log.e("CHECK","Already there. New amount: ${array[index]["userQuantity"]}")

                                //update all the array in firestore (seems that Firestore can't update just a value from the array)
                                userDoc!!.update("ingredientsInPantry", array)

                            } else {
                                // Object with the specified name doesn't exist, add it to the array
                                userDoc!!.update("ingredientsInPantry", FieldValue.arrayUnion(ingredientToAdd))
                            }
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