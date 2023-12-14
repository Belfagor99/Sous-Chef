package eu.mobcomputing.dima.registration.data.userInformation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import eu.mobcomputing.dima.registration.data.Allergen
import eu.mobcomputing.dima.registration.screens.Screen

class UserAllergiesViewModel() :
    ViewModel() {
    val currentStep: MutableState<Int> = mutableIntStateOf(1)

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
    private val TAG = UserAllergiesViewModel::class.simpleName

    fun nextStepOnClick(navController: NavController) {
        navController.navigate(Screen.UserAllergies.route)
    }

    fun backStepOnClick(navController: NavController) {
        navController.popBackStack()
    }

//    fun addAllergenToUser(
//        registrationSharedViewModel: RegistrationSharedViewModel,
//        allergen: Allergen
//    ) {
//        registrationSharedViewModel.addAllergen(allergen)
//    }

//    fun removeAllergenFromUser(
//        registrationSharedViewModel: RegistrationSharedViewModel,
//        allergen: Allergen
//    ) {
//        registrationSharedViewModel.removeAllergen(allergen)
//    }

//    fun allergenOnClick(
//        allergen: Allergen,
//        registrationSharedViewModel: RegistrationSharedViewModel
//    )
//    {
//        if (allergen.selectedState.value) {
//            removeAllergenFromUser(registrationSharedViewModel, allergen)
//        } else {
//            addAllergenToUser(registrationSharedViewModel, allergen)
//        }
//        allergen.selectedState.value = !allergen.selectedState.value
//        Log.d(TAG, "ON allergies screen")
//        Log.d(TAG, registrationSharedViewModel.user.toString())
//    }
}