package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.User
import eu.mobcomputing.dima.registration.utils.checkNetworkConnectivity
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing and providing data related to the user's pantry.
 *
 * This class extends AndroidViewModel and is responsible for handling interactions between the UI
 * and the data model. It retrieves and filters the list of ingredients in the user's database document.
 *
 * @param application An instance of the Application class, for accessing
 *                    application-level resources.
 */
@HiltViewModel
class PantryViewModel @Inject constructor(application: Application,) : AndroidViewModel(application) {



    private var _connectionStatus = MutableLiveData<Boolean>(checkNetworkConnectivity(application.applicationContext))
    var connectionStatus : LiveData<Boolean> = _connectionStatus


    /**
     * LiveData containing the list of ingredients in the user's pantry.
     */
    private var _ingredients = MutableLiveData<List<Ingredient>>(emptyList())
    var ingredients: LiveData<List<Ingredient>> = _ingredients

    /**
     * Reference to the user's document in Firestore.
     */
    var userDoc : DocumentReference? = getUserDocumentRef()


    /**
     * Initializes the ViewModel by launching a coroutine to load ingredients from the Firestore document.
     */
    init {
        viewModelScope.launch {
            getPantryIngredients()
        }
    }


    /**
     * Get List of ingredients in the user's pantry from Firestore
     */
    private fun getPantryIngredients() {
        try {
            if (userDoc == null){
                Log.e("GET PANTRY", "Error fetching user doc")
            }else{
                userDoc!!.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {

                        // get ingredient List from firestore user's document
                        val ingredientsList = (documentSnapshot.toObject(User::class.java))!!.ingredientsInPantry.sortedBy {
                            it.name.toLowerCase()
                        }

                        _ingredients.value= ingredientsList
                        Log.e("CHECK",_ingredients.toString())

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
            Log.e("ADD TO PANTRY", "Error getting ingredient in user's pantry", e)

        }

    }

    /**
     * Filters the list of ingredients based on the provided search text.
     *
     * If the search text is blank, the original list is reloaded from the database document.
     * Otherwise, the list is filtered based on whether the ingredient's name
     * contains the search text (case-insensitive).
     *
     * @param searchText The text to use for filtering ingredients.
     */
    fun filterIngredients(searchText: String) {

        if (searchText.isBlank()){
            getPantryIngredients()
        }else{
            val filteredList = ingredients.value?.filter { ingredient ->
                ingredient.name.contains(searchText, ignoreCase = true)
            }
            _ingredients.value = filteredList!!
            Log.v("PANTRY SEARCH", _ingredients.value.toString())

        }
    }




    fun removeFromPantry(ingredientsRecipe: List<Ingredient>){

        userDoc?.get()!!.addOnSuccessListener {
            if(it.exists()){
                var pantry = it.toObject(User::class.java)?.ingredientsInPantry

                ingredientsRecipe.forEach { ingrRecipe ->
                    pantry = pantry?.filter { ingredient -> ingredient.id != ingrRecipe.id   }
                }

                Log.e("NEW PANTRY ",pantry.toString())

                //update the doc
                userDoc!!.update("ingredientsInPantry", pantry)
            }
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