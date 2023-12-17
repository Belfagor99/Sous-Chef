package eu.mobcomputing.dima.registration.data.registration

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.data.Allergen
import eu.mobcomputing.dima.registration.data.DietOption
import eu.mobcomputing.dima.registration.data.DietType
import eu.mobcomputing.dima.registration.data.User
import eu.mobcomputing.dima.registration.navigation.Screen

class SharedUserDataModel() : ViewModel() {
    private val TAG = SharedUserDataModel::class.simpleName

    val allergiesStep: MutableState<Int> = mutableIntStateOf(1)
    val dietTypeStep: MutableState<Int> = mutableIntStateOf(2)
    val steps: List<String> = listOf("1", "2", "3")

    val allergens = listOf(
        Allergen("Eggs"),
        Allergen("Fish"),
        Allergen("Almond"),
        Allergen("Gluten"),
        Allergen("Chocolate"),
        Allergen("Avocado"),
        Allergen("Mustard"),
        Allergen("Peach"),
        Allergen("Peanuts"),
        Allergen("Soy"),
        Allergen("Milk"),
        Allergen("Soybeans"),
        Allergen("Walnuts"),
        Allergen("Berries"),
        Allergen("Dairy"),

        )

    val dietOptions = listOf(
        DietOption(DietType.VEGAN, R.drawable.vegan_removebg_preview),
        DietOption(DietType.VEGETARIAN, R.drawable.vegetarian_removebg_preview),
        DietOption(DietType.GLUTEN_FREE, R.drawable.glutenfree_removebg_preview),
        DietOption(DietType.LACTOSE_FREE, R.drawable.lactofree_removebg_preview),
        DietOption(DietType.NORMAL, R.drawable.normal_removebg_preview, mutableStateOf(true)),
        DietOption(DietType.PESCETARIAN, R.drawable.pescetarian_preview),
    )

    private var selectedDietOption: MutableState<DietOption> = mutableStateOf(this.dietOptions[4])

    private var user = mutableStateOf(User())

    private fun setDietType(dietType: DietType) {
        user.value = user.value.copy(dietType = dietType)
    }

    fun backStepOnClick(navController: NavController) {
        navController.popBackStack()
    }

    private fun addAllergenToUser(
        allergen: Allergen
    ) {
        user.value = user.value.copy(allergies = user.value.allergies + allergen)

    }

    private fun removeAllergenFromUser(
        allergen: Allergen
    ) {
        user.value = user.value.copy(allergies = user.value.allergies - allergen)

    }

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

    fun allergiesScreenNext(navController: NavController) {
        navController.navigate(Screen.UserDietScreen.route)
    }

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


    fun finishRegistration(navController: NavController) {
        val userID = FirebaseAuth.getInstance().currentUser?.uid

        if (userID != null) {
            val db = Firebase.firestore
            val userDocumentRef: DocumentReference = db.collection("users").document(userID)

            userDocumentRef.update(
                "allergies",
                user.value.allergies.map { it.name }, // Assuming Allergen has a "name" property
                "dietType",
                user.value.dietType.toString()
            )
                .addOnSuccessListener {
                    Log.d(TAG, "User data updated successfully")
                    navController.navigate(Screen.Home.route)

                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error updating user data", e)
                }
        } else {
            Log.d(TAG, "USER NOT LOGGED IN")
        }
    }
}