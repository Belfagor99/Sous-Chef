package eu.mobcomputing.dima.registration.data.userInformation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Enum class representing different diet types.
enum class DietType(val diet: String) {
    VEGAN("Vegan"),
    VEGETARIAN("Vegetarian"),
    GLUTEN_FREE("Gluten free"),
    LACTOSE_VEGETARIAN("Lacto-\nVegetarian"),
    PESCETARIAN("Pescetarian"),
    NORMAL("Normal"),
}

// Data class representing a diet option.
data class DietOption(
    val type: DietType,
    val icon: Int,
    val selected: MutableState<Boolean> = mutableStateOf(false)
)