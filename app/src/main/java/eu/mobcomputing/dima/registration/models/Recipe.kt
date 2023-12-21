package eu.mobcomputing.dima.registration.models

import com.google.gson.annotations.SerializedName

/**
 * The [Recipe] data class represents information about a cooking recipe.
 *
 * <p>
 * This data class includes properties such as the unique identifier, image URL, cooking instructions,
 * preparation time, servings, summary, title, cuisines, diets, list of ingredients, and the count of
 * missed ingredients for the recipe.
 * </p>
 *
 * @param id The unique identifier of the recipe.
 * @param image The URL to the image of the recipe.
 * @param instructions The cooking instructions for the recipe (nullable).
 * @param readyInMinutes The total time required to prepare the recipe in minutes.
 * @param servings The number of servings the recipe yields.
 * @param summary A summary or description of the recipe (nullable).
 * @param title The title or name of the recipe.
 * @param cuisines The list of cuisines associated with the recipe (nullable).
 * @param diets The list of diets associated with the recipe (nullable).
 * @param ingredients The list of ingredients required for the recipe (nullable).
 * @param missedIngredientCount The count of missed ingredients for the recipe.
 */

data class Recipe(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("image") val image: String = "",
        @SerializedName("instructions") val instructions: String ?= "",
        @SerializedName("readyInMinutes") val readyInMinutes: Int = 0,
        @SerializedName("servings") val servings: Int = 0,
        @SerializedName("summary") val summary: String ?= "",
        @SerializedName("title") val title: String = "",
        @SerializedName("cuisines") val cuisines: List<String> ?= null,
        @SerializedName("diets") val diets : List<String> ?= null,
        @SerializedName(value = "ingredients", alternate=["extendedIngredients","usedIngredients"]) val ingredients: List<Ingredient> ?= null,
        @SerializedName("missedIngredientCount") val missedIngredientCount: Int,
)