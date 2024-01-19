package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.api.APIService
import eu.mobcomputing.dima.registration.models.Recipe
import eu.mobcomputing.dima.registration.models.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


/**
 * ViewModel for the home screen responsible for handling home screen.
 */

class HomeViewModel : ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    /**
     * Reference to the user's document in Firestore.
     */
    var userDoc : DocumentReference? = getUserDocumentRef()

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username


    /**
     * LiveData containing the list of recipes based on the ingredients contained in the user's pantry.
     */
    private var _recipes = MutableLiveData<List<Recipe>>()
    var recipes: LiveData<List<Recipe>> = _recipes

    private var _ingredientsAsString = MutableLiveData<String>()
    var ingredientsAsString: LiveData<String> = _ingredientsAsString





    init {
        viewModelScope.launch {
            getIngredientsListAsString()
        }
    }




    /**
     * Retrieves the list of ingredients from the user's pantry stored in Firestore,
     * formats it as a comma-separated string, and sets it in the [_ingredientsAsString] LiveData.
     * Subsequently, triggers the [getAvailableRecipe] function to fetch recipes based on the ingredients.
     *
     * If the user document is not available or an error occurs during the process, appropriate logs are generated.
     *
     * @throws Exception if there is an error while retrieving or processing the ingredients from the user's pantry.
     */
    private suspend fun getIngredientsListAsString (){

        try {
            if (userDoc == null){
                Log.e("GET PANTRY", "Error fetching user doc")
            }else{
                userDoc!!.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {

                        // get ingredient's name List from firestore user's document
                        val ingredientsList = (documentSnapshot.toObject(User::class.java))?.ingredientsInPantry?.map {
                            ingredient ->  ingredient.name
                        }

                        //prepare the string's format to be suitable for the api call
                        if (ingredientsList != null) {
                            _ingredientsAsString.value = ingredientsList.joinToString(separator = ",")
                        }

                        Log.e("CHECK", _ingredientsAsString.value.toString())

                        this.viewModelScope.launch{
                            getAvailableRecipe()
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
            Log.e("ADD TO PANTRY", "Error getting ingredient in user's pantry", e)

        }

    }



    /**
     * Retrieves available recipes based on the provided list of ingredients.
     *
     * This function makes an API call to get recipes using the specified ingredients.
     *
     * After a successful API response, it filters and sets the recipes with no missed ingredients.
     * Additional filtering based on user's diet and allergies is a performed.
     *
     * @throws IOException if there is a network-related issue during the API call.
     */
    private suspend fun getAvailableRecipe(){


        Log.e("INGR LIST", ingredientsAsString.value.toString())

        val response = APIService().api.getRecipesByIngredients(
            ingredients = ingredientsAsString.value.toString(),
            ranking = 2,
            number = 20,
            ignorePantry = true,
        )

        Log.e("INGR LIST", response.body()?.size.toString())

        Log.e("RESPONSE",response.body().toString())


        if(response.isSuccessful){
            //get only the recipes with missedIngredients == 0
            _recipes.value = response.body()?.filter {it.missedIngredientCount == 0}


            //TODO: filter the recipes based on user's diet and allergies


            Log.e("HOME @ getAvailableRecipe",_recipes.value.toString())
            Log.e("COUNTER RECIPE", _recipes.value?.size.toString())
        }
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
     * Helper function that retrieves the DocumentReference for the current user from Firestore.
     *
     * @return The DocumentReference for the current user, or null if the user is not logged in.
     */
    private fun getUserDocumentRef(): DocumentReference? {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            val db = FirebaseFirestore.getInstance()
            return db.collection("users").document(userID)

        } ?: run {
            Log.d("HOME @ getUserDocRef", "USER is not logged in")
            return null
        }
    }







}