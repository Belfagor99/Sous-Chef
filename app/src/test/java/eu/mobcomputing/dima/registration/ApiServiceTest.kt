package eu.mobcomputing.dima.registration


import eu.mobcomputing.dima.registration.api.APIService
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.Recipe
import org.junit.Assert.*

/**
 * The [ApiServiceTest] class contains test functions for the [APIService] and [RecipesAPICalls] implementation.
 *
 * <p>
 * The test functions include verifying the correctness of the API response for retrieving a recipe by ID
 * and filtering recipes by ingredients. The expected values in these tests are based on actual API calls
 * made in Postman.
 * </p>
 *
 * @see APIService The class providing utilities for handling API calls using Retrofit.
 * @see RecipesAPICalls The interface representing the API endpoints for recipe-related calls.
 * @see Recipe The data class representing information about a cooking recipe.
 * @see Ingredient The data class representing information about a food ingredient.
 */
class ApiServiceTest {

    /**
     * Test function that checks if the recipe returned by the API call is the right one.
     */
   /* @Test
    fun getRecipeByID() = runTest {

        val response = APIService().api.getRecipeInfoById(
            id = 1,
            includeNutrition = true,
        )

        val ingredientsChecker = ArrayList<Ingredient>()
        ingredientsChecker.add(Ingredient(id=15001, name="anchovies", image="fresh-anchovies.jpg", original="1lb of anchovies cleaned, spine removed", amount=1.0, unit="lb", consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=15001, name="anchovies", image="anchovies.jpg", original="1lb of anchovies cleaned, spine removed", amount=1.0, unit="lb", consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=18369, name="baking powder", image="white-powder.jpg", original="1 teaspoon of baking powder", amount=1.0, unit="teaspoon", consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=1123, name="egg", image="egg.png", original="1 egg", amount=1.0,  consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=20081, name="flour", image="flour.png", original="1 cup of flour", amount=1.0, unit="cup", consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=99226, name="sage", image="fresh-sage.png", original="sage leaves (optional - if you are not a fan of sage just omit)", amount=1.0, unit="leaves", consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=2047, name="salt", image="salt.jpg", original="1 teaspoon of salt", amount=1.0, unit="teaspoon", consistency="SOLID"))
        ingredientsChecker.add(Ingredient(id=14121, name="seltzer water", image="sparkling-water.png", original="seltzer water", amount=3.0, unit="servings", consistency="LIQUID"))
        ingredientsChecker.add(Ingredient(id=4669, name="vegetable oil", image="vegetable-oil.jpg", original="vegetable oil for frying", amount=3.0, unit="servings", consistency="SOLID"))


        val recipeChecker = Recipe(
            id = 1,
            title =  "Fried Anchovies with Sage",
            readyInMinutes = 45,
            //summary = "Fried Anchovies with Sage might be a good recipe to expand your main course collection. Watching your figure? This dairy free and pescatarian recipe has <b>396 calories</b>, <b>37g of protein</b>, and <b>12g of fat</b> per serving. This recipe serves 3. For <b>$5.61 per serving</b>, this recipe <b>covers 26%</b> of your daily requirements of vitamins and minerals. From preparation to the plate, this recipe takes around <b>45 minutes</b>. This recipe from latavolamarcherecipebox.blogspot.com requires anchovies, baking powder, salt, and vegetable oil. This recipe is liked by 3 foodies and cooks. Taking all factors into account, this recipe <b>earns a spoonacular score of 75%</b>, which is solid. <a href=\"https://spoonacular.com/recipes/fried-anchovies-with-sage-1189555\">Fried Anchovies with Sage</a>, <a href=\"https://spoonacular.com/recipes/fried-anchovies-with-sage-1355669\">Fried Anchovies with Sage</a>, and <a href=\"https://spoonacular.com/recipes/fried-anchovies-with-sage-1201577\">Fried Anchovies with Sage</a> are very similar to this recipe.",
            image = "https://spoonacular.com/recipeImages/1-556x370.jpg",
            servings = 3,
            instructions = "<p>If you have not tried anchovies before - you must try them now! Get over any weird apprehensions or that its just bait or a punchline for a joke about pizza (\"extra anchovies\")! These little suckers are delicious &amp; actually good for you! Baked, fried &amp; grilled - they are ohh so good and worth a try. If your not up to it, then pass me your plate because I love'em!Here is my favorite - Fried Anchovies - the recipe below adds a sage leave to each piece of fish as well for an extra burst of flavor &amp; color.Fried Anchovies with SageAcciughe fritte con Salvia1lb of anchovies cleaned, spine removedsage leaves (optional - if you are not a fan of sage just omit)batter1 cup of flour1 egg1 teaspoon of salt1 teaspoon of baking powderseltzer watervegetable oil for fryingIn a bowl combine flour, eggs, salt &amp; baking powder. Slowly add in seltzer water &amp; mix until forms a thin batter. Cover with plastic &amp; set in the fridge for at least an hour.Heat oil in a pot to 350 degree.Remove batter from fridge and mix once or twice (batter will have separated).Take a sage leaf &amp; anchovy put them together &amp; dip into the batter - allowing access batter to drip off.Fry 20 seconds a side until golden brown.Remove from oil &amp; drain on a paper towel.Sprinkle with salt &amp; serve immediately.Pairs great with prosecco or white wine.</p>",
            cuisines = ArrayList<String>(),
            diets = arrayListOf("dairy free", "pescatarian"),
            ingredients = ingredientsChecker,
            missedIngredientCount = 0
        )

        assertEquals(response.isSuccessful,true)

        if (response.isSuccessful) {
            val recipe = response.body()
            println(recipe)
            assertEquals(recipe,recipeChecker)
        }
    }

    /**
     * Test function that checks if the recipes returned by the API call , after being filtered are the right ones.
     */
    @Test
    fun getRecipesByIngredients() = runTest {

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

            val ingredientsChecker = ArrayList<Ingredient>()
            ingredientsChecker.add(Ingredient(id=93832, name="g pre-made agnolotti/ravioli", image="https://spoonacular.com/cdn/ingredients_100x100/ravioli.png", original="1/3 packet of 375g (13 oz) pre-made fresh agnolotti/ravioli", amount=13.0, unit="oz"))
            ingredientsChecker.add(Ingredient(id=1123, name="egg", image="https://spoonacular.com/cdn/ingredients_100x100/egg.png", original="1 egg", amount=1.0))
            ingredientsChecker.add(Ingredient(id=18079, name="breadcrumbs", image="https://spoonacular.com/cdn/ingredients_100x100/breadcrumbs.jpg", original="1.5 cup breadcrumbs", amount=1.5, unit="cup", consistency=""))


            val recipeChecker = Recipe(
                id=631807,
                image="https://spoonacular.com/recipeImages/631807-312x231.jpg",
                instructions=null,
                readyInMinutes=0,
                servings=0,
                //summary=null,
                title="Toasted\" Agnolotti (or Ravioli)",
                cuisines=null,
                diets=null,
                ingredients = ingredientsChecker,
                missedIngredientCount = 0,
            )
            assertEquals(recipes?.size , 1 )
            assertEquals(recipes?.get(0), recipeChecker )
        }
    }*/

}