package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.api.APIService
import eu.mobcomputing.dima.registration.models.ConvertedIngredient
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
                        val array = documentSnapshot.toObject(User::class.java)?.ingredientsInPantry

                        if (array != null) {
                            val existingIngredientsByName = array.filter { it.name  == ingredientToAdd.name }

                            Log.e("PD",existingIngredientsByName.toString())

                            if (existingIngredientsByName.isNotEmpty()) {

                                val existingIngredientsByUnit = existingIngredientsByName.filter { it.unit == ingredientToAdd.unit }

                                // if i cant find an ingredeint with same unit , try to convert
                                if(existingIngredientsByUnit.isEmpty()) {
                                    // Object with the specified name already exists, increment the userQuantity
                                    val index = array.indexOf(existingIngredientsByName[0])


                                    /**
                                     * Try to convert amount ingrToAdd to the unit of the already present ingr
                                     * if not successful just add it to the db
                                     * */
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val response = APIService().api.convertIngredientAmount(
                                            ingredientName = ingredientToAdd.name,
                                            sourceAmount = ingredientToAdd.userQuantity,
                                            sourceUnit = ingredientToAdd.unit,
                                            targetUnit = existingIngredientsByName[0].unit
                                        )
                                        if (response.isSuccessful){
                                            val convertedIngredient = response.body()!!
                                            Log.e("CONVERTING", "Already there. Converting ... $convertedIngredient")

                                            //TODO: update the userquantity with the converted value

                                            val updatedQuantity = existingIngredientsByName[0].userQuantity +  convertedIngredient.targetAmount

                                            array[index].userQuantity = updatedQuantity

                                            Log.e("CONVERTED","Already there. New amount: ${array[index].userQuantity}")

                                            //update all the array in firestore (seems that Firestore can't update just a value from the array)
                                            userDoc!!.update("ingredientsInPantry", array)

                                        }else{
                                            userDoc!!.update("ingredientsInPantry", FieldValue.arrayUnion(ingredientToAdd))
                                            Log.e("NOT CONVERTED -> ADDED","Added to db")
                                        }
                                    }
                                }else{
                                    //just update its user quantity
                                    val index = array.indexOf(existingIngredientsByUnit[0])
                                    val updatedQuantity = existingIngredientsByUnit[0].userQuantity +  ingredientToAdd.userQuantity

                                    array[index].userQuantity = updatedQuantity

                                    Log.e("UPDATING QNT","Already there. New amount: ${array[index].userQuantity}")

                                    //update all the array in firestore (seems that Firestore can't update just a value from the array)
                                    userDoc!!.update("ingredientsInPantry", array)


                                }

                            } else {
                                // Object with the specified name doesn't exist, add it to the array
                                userDoc!!.update("ingredientsInPantry", FieldValue.arrayUnion(ingredientToAdd))
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