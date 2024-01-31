package eu.mobcomputing.dima.registration.api

import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.Recipe
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * The [RecipesAPICallsTest] class contains test functions for the [RecipesAPICalls] implementation.
 *
 * The test functions include verifying the correctness of the API response.
 * The expected values in these tests are based on actual API calls made in Postman.
 *
 * @see APIService The class providing utilities for handling API calls using Retrofit.
 * @see RecipesAPICalls The interface representing the API endpoints for recipe-related calls.
 * @see Recipe The data class representing information about a cooking recipe.
 * @see Ingredient The data class representing information about a food ingredient.
 */
class RecipesAPICallsTest {


    /**
     * Test function that checks getRecipeByID api call .
     */
     @Test
     fun getRecipeByIDTest() = runTest {

         val response = APIService().api.getRecipeInfoById(
             ids = "1,631807",
             includeNutrition = false,
         )

         assertEquals(response.isSuccessful,true)

         if (response.isSuccessful) {
             val recipes = response.body()

             recipes!!.map { recipe -> recipe.title }

             assertEquals(recipes[0].title,"Fried Anchovies with Sage")
             assertEquals(recipes[1].title,"Toasted\" Agnolotti (or Ravioli)")
         }
     }

     /**
      * Test function that checks getRecipeByIngredients api call .
      */
     @Test
     fun getRecipesByIngredientsTest() = runTest {

         val ingredientsList = "carrots,tomatoes,pasta,eggs,spinach,breadcrumbs"
         val response = APIService().api.getRecipesByIngredients(
             ingredients = ingredientsList,
             ranking = 2,
             number = 10,
             ignorePantry = true,
         )

         assertEquals(response.isSuccessful,true)

         if(response.isSuccessful){
             val recipes = response.body()?.filter {it.missedIngredientCount == 0}

             assertEquals(recipes?.size , 1 )
             assertEquals(recipes?.get(0)?.title, "Toasted\" Agnolotti (or Ravioli)" )
         }
     }


    /**
     * Test function that checks getIngredientInfoById api call .
     */
    @Test
    fun getIngredientInfoByIdTest () = runTest {
        val response = APIService().api.getIngredientInfoById(
            id = 1017,
        )

        assertEquals(response.isSuccessful,true)

        if (response.isSuccessful) {
            val ingredient = response.body()
            assertEquals(ingredient?.name,"cream cheese")
        }
    }


    /**
     * Test function that checks searchIngredient api call .
     */
    @Test
    fun searchIngredientTest() = runTest {
        val response = APIService().api.searchIngredient(
            query = "burger",
            number = 1,
        )
        assertEquals(response.isSuccessful,true)

        if (response.isSuccessful) {
            val ingredients = response.body()?.results

            assertEquals(ingredients?.get(0)?.name,"hamburger bun")
        }
    }

}