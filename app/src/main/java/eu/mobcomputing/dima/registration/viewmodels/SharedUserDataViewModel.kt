package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Allergen
import eu.mobcomputing.dima.registration.models.DietOption
import eu.mobcomputing.dima.registration.models.DietType
import eu.mobcomputing.dima.registration.models.User
import eu.mobcomputing.dima.registration.navigation.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * ViewModel responsible for managing shared user data during the registration process.
 */
class SharedUserDataViewModel() : ViewModel() {

    private val TAG = SharedUserDataViewModel::class.simpleName

    // Step indices for the allergies and diet type screens
    val allergiesStep: MutableState<Int> = mutableIntStateOf(1)
    val dietTypeStep: MutableState<Int> = mutableIntStateOf(2)

    // List of steps for navigation
    val steps: List<String> = listOf("1", "2", "3")

    // List of allergens available for selection
    val allergens = listOf(
        Allergen("Dairy"),
        Allergen("Egg"),
        Allergen("Gluten"),
        Allergen("Grain"),
        Allergen("Peanut"),
        Allergen("Seafood"),
        Allergen("Sesame"),
        Allergen("Shellfish"),
        Allergen("Peanuts"),
        Allergen("Soy"),
        Allergen("Sulfite"),
        Allergen("Tree Nut"),
        Allergen("Wheat"),
        )

    // List of diet options available for selection
    val dietOptions = listOf(
        DietOption(DietType.VEGAN, R.drawable.vegan_removebg_preview),
        DietOption(DietType.VEGETARIAN, R.drawable.vegetarian_removebg_preview),
        DietOption(DietType.GLUTEN_FREE, R.drawable.glutenfree_removebg_preview),
        DietOption(DietType.LACTOSE_VEGETARIAN, R.drawable.lactofree_removebg_preview),
        DietOption(DietType.OMNIVORE, R.drawable.normal_removebg_preview, mutableStateOf(true)),
        DietOption(DietType.PESCETARIAN, R.drawable.pescetarian_preview),
    )

    // Currently selected diet option
    private var selectedDietOption: MutableState<DietOption> = mutableStateOf(this.dietOptions[4])

    // User information state
    private var user = mutableStateOf(User())

    /**
     * Sets the selected diet type for the user.
     */
    private fun setDietType(dietType: DietType) {
        user.value = user.value.copy(dietType = dietType)
    }

    /**
     * Adds an allergen to the user's list of allergies.
     */
    private fun addAllergenToUser(
        allergen: Allergen
    ) {
        user.value = user.value.copy(allergies = user.value.allergies + allergen.name)

    }

    /**
     * Removes an allergen from the user's list of allergies.
     */
    private fun removeAllergenFromUser(
        allergen: Allergen
    ) {
        user.value = user.value.copy(allergies = user.value.allergies - allergen.name)

    }

    /**
     * Handles the onClick event for allergens.
     */
    fun allergenOnClick(
        allergen: Allergen
    ) {
        if (allergen.selectedState.value) {
            removeAllergenFromUser(allergen)
        } else {
            addAllergenToUser(allergen)
        }
        allergen.selectedState.value = !allergen.selectedState.value
        Log.d(TAG, "allergen clicked")
        Log.d(TAG, allergens.toString())
        Log.d(TAG, user.toString())
    }

    /**
     * Navigates to the next screen in the registration process (User Diet).
     */
    fun allergiesScreenNext(navController: NavController) {
        navController.navigate(Screen.UserDiet.route)
    }

    /**
     * Navigates back to the previous screen in the registration process.
     */
    fun backStepOnClick(navController: NavController) {
        navController.popBackStack()
    }

    /**
     * Handles the onClick event for diet options.
     */
    fun dietOptionOnClick(dietOption: DietOption) {
        selectedDietOption.value.selected.value = false

        dietOption.selected.value = !dietOption.selected.value


        selectedDietOption.value = dietOption


        if (dietOption.selected.value) {
            setDietType(dietOption.type)
        }

        Log.d(TAG, "dietOption clicked")
        Log.d(TAG, dietOption.toString())
        Log.d(TAG, user.toString())

    }

    /**
     * Asynchronously updates the user data in the Firebase Firestore database.
     */
    private suspend fun updateUserInFirebase() {
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        if (userID != null) {
            val db = Firebase.firestore
            val userDocumentRef: DocumentReference = db.collection("users").document(userID)

            try {
                userDocumentRef.update(
                    "allergies", user.value.allergies,
                    "dietType", user.value.dietType.toString()
                ).await()

                Log.d(TAG, "User data updated successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error updating user data", e)
            }
        } else {
            Log.d(TAG, "USER NOT LOGGED IN")
        }
    }

    /**
     * Initiates the asynchronous user data update and navigates to the Home screen.
     */
    private fun updateUserAsync(navController: NavController) {
        viewModelScope.launch {
            updateUserInFirebase()

            // Navigate to the Home screen after updating data
            navController.navigate(Screen.Home.route)
        }
    }

    /**
     * Finishes the registration process by updating user data and navigating to the Home screen.
     */
    fun finishRegistration(navController: NavController) {
        updateUserAsync(navController)
    }
}