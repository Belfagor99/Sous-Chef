package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
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

    private var _name = MutableLiveData<String>()
    var name : LiveData<String> = _name


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

            val filteredByMissingIngredient : List<Recipe>? = response.body()?.filter {it.missedIngredientCount == 0}

            if(!filteredByMissingIngredient.isNullOrEmpty()){
                val ids = filteredByMissingIngredient.map { recipe -> recipe.id  }.joinToString(separator = ",")


                var recipesBulkList : List<Recipe> = emptyList()


                val res = APIService().api.getRecipeInfoById(ids)
                Log.e("RES", res.body().toString())

                if (res.isSuccessful){
                    recipesBulkList = res.body()!!
                }

                _recipes.value = recipesBulkList
            }


        }



            //TODO: filter the recipes based on user's diet and allergies


            val gson = Gson()


            Log.e("HOME @ getAvailableRecipe",gson.toJson(_recipes.value))
            Log.e("COUNTER RECIPE", _recipes.value?.size.toString())
        }




    /**
     * Helper function that retrieves the DocumentReference for the current user from Firestore.
     *
     * @return The DocumentReference for the current user, or null if the user is not logged in.
     */
    private fun getUserDocumentRef(): DocumentReference? {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            val db = FirebaseFirestore.getInstance()
            val doc = db.collection("users").document(userID)

            doc.get().addOnSuccessListener {
                if (it.exists()) {
                    val user = (it.toObject(User::class.java))!!
                    _name.value = user.firstName

                }
            }

            return doc

        } ?: run {
            Log.d("HOME @ getUserDocRef", "USER is not logged in")
            return null
        }
    }







}