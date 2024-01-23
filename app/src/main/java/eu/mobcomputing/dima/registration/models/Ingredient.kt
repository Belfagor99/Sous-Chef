package eu.mobcomputing.dima.registration.models


import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * The [Ingredient] data class represents information about a food ingredient.
 *
 * <p>
 * This data class includes properties such as the unique identifier, name, image URL, original name,
 * amount, unit of measurement, and consistency of the ingredient.
 * </p>
 *
 * @param id The unique identifier of the ingredient.
 * @param name The name of the ingredient.
 * @param image The URL to the image of the ingredient.
 * @param original The original name of the ingredient.
 * @param amount The amount of the ingredient.
 * @param unit The unit of measurement for the ingredient amount.
 * @param consistency The consistency of the ingredient.
 *
 */
data class Ingredient (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("original") val original: String = "",
    @SerializedName("amount") var amount: Double = 0.0,
    @SerializedName("unit") var unit: String = "",
    @SerializedName("consistency") val consistency: String = "",
    @SerializedName("expiring_date") var expiringDate: Date ?= null,
    @SerializedName("user_quantity") var userQuantity: Double = 0.00,

    @SerializedName("possibleUnits") var possibleUnits: List<String> = emptyList(),

    )

data class ConvertedIngredient (
    @SerializedName("sourceAmount") val sourceAmount: Double = 0.0,
    @SerializedName("sourceUnit") val sourceUnit: String = "",
    @SerializedName("targetAmount") val targetAmount: Double = 0.0,
    @SerializedName("targetUnit") val targetUnit: String = "",
)
