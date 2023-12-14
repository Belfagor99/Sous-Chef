package eu.mobcomputing.dima.registration.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Allergen (
    val name: String,
    var selectedState: MutableState<Boolean> = mutableStateOf(false)

)
