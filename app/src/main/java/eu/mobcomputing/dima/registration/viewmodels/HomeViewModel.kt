package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.mobcomputing.dima.registration.api.APIService
import eu.mobcomputing.dima.registration.models.DietType
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.Recipe
import eu.mobcomputing.dima.registration.models.User
import eu.mobcomputing.dima.registration.utils.checkNetworkConnectivity
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for the home screen responsible for handling home screen.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private var _connectionStatus =
        MutableLiveData(checkNetworkConnectivity(application.applicationContext))
    var connectionStatus: LiveData<Boolean> = _connectionStatus

    // Reference to the user's document in Firestore.
    private var userDoc: DocumentReference? = getUserDocumentRef()

    private var _name = MutableLiveData<String>()
    var name: LiveData<String> = _name

    private var userDiet: DietType? = null
    private var userPantry: List<Ingredient> = emptyList()
    private var userIntolerances: String = ""

    // LiveData containing the list of recipes based on the ingredients contained in the user's pantry.
    private var _recipes = MutableLiveData<List<Recipe>>()
    var recipes: LiveData<List<Recipe>> = _recipes

    private var _ingredientsAsString = MutableLiveData<String>()
    private var ingredientsAsString: LiveData<String> = _ingredientsAsString


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
     */
    private suspend fun getIngredientsListAsString() {
        val isNetworkAvailable = _connectionStatus.value ?: false

        if (isNetworkAvailable) {

            try {
                if (userDoc == null) {
                    Log.e("GET PANTRY", "Error fetching user doc")
                } else {
                    userDoc!!.get().addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {

                            // get ingredient's name List from firestore user's document
                            val ingredientsList =
                                (documentSnapshot.toObject(User::class.java))?.ingredientsInPantry?.map { ingredient ->
                                    ingredient.name
                                }

                            //prepare the string's format to be suitable for the api call
                            if (ingredientsList != null) {
                                _ingredientsAsString.value =
                                    ingredientsList.joinToString(separator = ",")
                            }

                            Log.e("CHECK", _ingredientsAsString.value.toString())

                            this.viewModelScope.launch {
                                getAvailableRecipe()
                            }

                        } else {
                            // Document does not exist
                            Log.e("CHECK", "Document does not exist.")
                        }
                    }.addOnFailureListener { e ->
                        // Handle errors
                        Log.e("CHECK", "Error checking array: $e")
                    }

                }
            } catch (e: Exception) {
                Log.e("ADD TO PANTRY", "Error getting ingredient in user's pantry", e)

            }
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
     */
    private suspend fun getAvailableRecipe() {
        val isNetworkAvailable = _connectionStatus.value ?: false

        if (isNetworkAvailable) {
            //update userDiet if it has been modified
            updateUserDietType(userDoc!!)
            getUserPantry(userDoc!!)
            getUserIntolerances(userDoc!!)

            Log.e("CHECK USER DIET", userDiet!!.diet)
            Log.e("CHECK USER PANTRY", userPantry.toString())
            Log.e("CHECK USER INTOLERANCES", userIntolerances)
            Log.e("CHECK RECIPE INGREDIENT LIST", ingredientsAsString.value.toString())

            val response = APIService().api.getRecipesByIngredients(
                ingredients = ingredientsAsString.value.toString(),
                ranking = 2,
                number = 20,
            )

            Log.e("RECIPE COUNTER BEF MISSING INGREDIENT", response.body()?.size.toString())
            Log.e("RESPONSE", response.body().toString())


            if (response.isSuccessful) {
                //get only the recipes with missedIngredients == 0
                val filteredByMissingIngredient: List<Recipe>? =
                    response.body()?.filter { it.missedIngredientCount == 0 }


                //of this get all recipe info (bulk version)
                var recipesBulkList: List<Recipe> = emptyList()
                if (!filteredByMissingIngredient.isNullOrEmpty()) {
                    val ids = filteredByMissingIngredient.map { recipe -> recipe.id }
                        .joinToString(separator = ",")

                    val res = APIService().api.getRecipeInfoById(ids)
                    Log.e("RES", res.body().toString())

                    if (res.isSuccessful) {
                        recipesBulkList = res.body()!!
                    }

                }

                Log.e("RECIPES W/ 0 INGREDIENT ", recipesBulkList.size.toString())

                // filter the recipe based on user's diet type
                //if is not omnivore filter the recipe, otherwise all recipe are good
                if (userDiet != null && userDiet != DietType.OMNIVORE) {

                    recipesBulkList = if (userDiet == DietType.LACTOSE_VEGETARIAN) {
                        recipesBulkList.filter { recipe ->
                                (recipe.diets!!.contains("lacto vegetarian") or recipe.diets.contains(
                                    "lacto ovo vegetarian"
                                ))
                            }
                    } else {
                        recipesBulkList.filter { recipe ->
                            recipe.diets?.contains(userDiet!!.diet) ?: false
                        }
                    }


                }
                Log.e("RECIPES BY DIET ", recipesBulkList.size.toString())


                _recipes.value = recipesBulkList
            }

        }

        val gson = Gson()


        Log.e("HOME @ getAvailableRecipe", gson.toJson(_recipes.value))
        Log.e("COUNTER RECIPE", _recipes.value?.size.toString())
    }


    /**
     * Helper function that retrieves the DocumentReference for the current user from Firestore.
     *
     * @return The DocumentReference for the current user, or null if the user is not logged in.
     */
    private fun getUserDocumentRef(): DocumentReference? {
        val isNetworkAvailable = _connectionStatus.value ?: false

        if (isNetworkAvailable) {
            FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
                val db = FirebaseFirestore.getInstance()
                val doc = db.collection("users").document(userID)

                updateUserDietType(doc)

                return doc

            } ?: run {
                Log.d("HOME @ getUserDocRef", "USER is not logged in")
                return null
            }
        }
        return null
    }


    private fun updateUserDietType(doc: DocumentReference) {
        val isNetworkAvailable = _connectionStatus.value ?: false

        if (isNetworkAvailable) {
            doc.get().addOnSuccessListener {
                if (it.exists()) {
                    val user = (it.toObject(User::class.java))!!
                    _name.value = user.firstName

                    userDiet = user.dietType
                }
            }
        }
    }

    private fun getUserPantry(doc: DocumentReference) {
        val isNetworkAvailable = _connectionStatus.value ?: false

        if (isNetworkAvailable) {
            doc.get().addOnSuccessListener {
                if (it.exists()) {
                    val user = (it.toObject(User::class.java))!!
                    userPantry = user.ingredientsInPantry
                }
            }
        }
    }


    private fun getUserIntolerances(doc: DocumentReference) {

        val isNetworkAvailable = _connectionStatus.value ?: false

        if (isNetworkAvailable) {
            doc.get().addOnSuccessListener {
                if (it.exists()) {
                    val user = (it.toObject(User::class.java))!!
                    userIntolerances = user.allergies.joinToString(separator = ",")
                }
            }
        }

    }


    private fun checkNetworkStatus(context: Context) {
        _connectionStatus.value = checkNetworkConnectivity(context)
    }
}