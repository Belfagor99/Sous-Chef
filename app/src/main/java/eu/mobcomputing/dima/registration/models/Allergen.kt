package eu.mobcomputing.dima.registration.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Data class representing an allergen
data class Allergen (
    val name: String,
    var selectedState: MutableState<Boolean> = mutableStateOf(false)

)
