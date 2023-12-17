package eu.mobcomputing.dima.registration.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class DietType ( val diet:String) {
    VEGAN ("Vegan"),
    VEGETARIAN ("Vegetarian"),
    GLUTEN_FREE("Gluten free"),
    LACTOSE_FREE ("Lactose free"),
    PESCETARIAN("Pescetarian"),
    NORMAL("Normal")
}
data class DietOption(val type: DietType, val icon: Int, val selected: MutableState<Boolean> = mutableStateOf(false))