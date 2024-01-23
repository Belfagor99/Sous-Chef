package eu.mobcomputing.dima.registration.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Enum class representing different diet types.
enum class DietType(val diet: String) {
    VEGAN("vegan"),
    VEGETARIAN("vegetarian"),
    GLUTEN_FREE("gluten free"),
    LACTOSE_VEGETARIAN("lacto vegetarian"),
    PESCETARIAN("pescetarian"),
    OMNIVORE("omnivore"),
}

// Data class representing a diet option.
data class DietOption(
    val type: DietType,
    val icon: Int,
    val selected: MutableState<Boolean> = mutableStateOf(false)
)