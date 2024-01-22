package eu.mobcomputing.dima.registration.api

import eu.mobcomputing.dima.registration.models.ConvertedIngredient
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The [RecipesAPICalls] interface defines methods for making API calls related to recipes.
 *
 * <p>
 * It includes methods for retrieving a list of recipes based on provided ingredients and getting
 * detailed information about a specific recipe by its ID. The interface utilizes the Spoonacular API.
 * </p>
 *
 * @return The functions return Response objects containing the requested recipe information.
 *
 * @see Recipe The data class representing information about a cooking recipe.
 * @see GET The HTTP GET method annotation for Retrofit.
 * @see Query The annotation used to specify query parameters in a Retrofit request.
 * @see Path The annotation used to specify a URL path parameter in a Retrofit request.
 */
interface RecipesAPICalls {

    /**
     * The [getRecipesByIngredients] method retrieves a list of recipes based on provided ingredients.
     * For our purpose we want only the recipe with missingIngredients = 0 but the API does not directly
     * filter recipes based on missing ingredients, so additional filtering is required
     * after receiving the response.
     *
     * @param ingredients -> A comma-separated list of ingredients that the recipes should contain.
     * @param ranking -> =1 to maximize used ingredients; =2 to minimize missing ingredients (in this
     * case the returned list of recipes is sorted by the missedIngredientsCount property in ascending order).
     * @param number -> max number of recipes to return.
     * @param ignorePantry -> whether to ignore typical pantry items, such as water, salt, flour, etc.
     *
     * @return The function return a list of recipes unfiltered wrt the missingIngredient recipe's parameter.
     */
    @GET("recipes/findByIngredients")
    suspend fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("ranking") ranking: Int = 2,
        @Query("number") number: Int = 10,
        @Query("ignorePantry ") ignorePantry : Boolean = true,
    ): Response<ArrayList<Recipe>>



    /**
     * The [getRecipeInfoById] method retrieves detailed information about a specific recipe identified
     * by its ID. The function includes an option to include nutrition information in the response.
     *
     * @param ids -> List of ids coma separates
     *
     * @return The function return a Response objects containing the requested recipe information
     * */
    @GET("/recipes/informationBulk")
    suspend fun getRecipeInfoById(
        @Query("ids") ids: String,
        @Query("includeNutrition") includeNutrition : Boolean = false,
    ): Response<List<Recipe>>



    @GET("/food/ingredients/{id}/information")
    suspend fun getIngredientInfoById(
        @Path("id") id: Int,
    ): Response<Ingredient>


    @GET("/recipes/convert")
    suspend fun convertIngredientAmount(
        @Query("ingredientName") ingredientName : String,
        @Query("sourceAmount") sourceAmount : Double,
        @Query("sourceUnit") sourceUnit : String,
        @Query("targetUnit") targetUnit : String,
    ): Response<ConvertedIngredient>




}