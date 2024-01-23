package eu.mobcomputing.dima.registration.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("results") val results: List<Ingredient>,
)