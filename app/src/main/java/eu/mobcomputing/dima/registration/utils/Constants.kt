package eu.mobcomputing.dima.registration.utils



/**
 * The [Constants] class provides static constant values used throughout the application.
 */
class Constants {

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/"
        const val BASE_IMG_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val BASE_IMG_URL_250 = "https://spoonacular.com/cdn/ingredients_250x250/"
        const val BASE_IMG_URL_500 = "https://spoonacular.com/cdn/ingredients_500x500/"

        const val CHANNEL_ID = "SOUS-CHEF"
        const val CHANNEL_NAME = "Sous chef channel"
        const val TOPIC = "RECIPE"


        val allergies = listOf(
            "Dairy",
            "Egg",
            "Gluten",
            "Grain",
            "Peanut",
            "Seafood",
            "Sesame",
            "Shellfish",
            "Peanuts",
            "Soy",
            "Sulfite",
            "Tree Nut",
            "Wheat",
        )

    }
}