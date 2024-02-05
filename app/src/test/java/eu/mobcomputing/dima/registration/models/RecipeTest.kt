package eu.mobcomputing.dima.registration.models

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeTest {

    @Test
    fun testRecipeSerialization() {
        val recipe = Recipe(
            id = 1,
            image = "recipe_image_url",
            instructions = listOf(AnalyzedInstruction("Step 1", listOf(Instruction(1,"Detail 1")))),
            readyInMinutes = 30,
            servings = 4,
            title = "Delicious Recipe",
            cuisines = listOf("Italian"),
            diets = listOf("Vegetarian"),
            ingredients = listOf(Ingredient(1,"Ingredient 1", "image", "original")),
            missedIngredientCount = 1
        )

        val gson = Gson()
        val json = gson.toJson(recipe)
        val deserializedRecipe = gson.fromJson(json, Recipe::class.java)

        assertEquals(recipe, deserializedRecipe)
    }

    @Test
    fun testRecipeDefaultValues() {
        val recipe = Recipe(missedIngredientCount = 2)

        assertEquals(0, recipe.id)
        assertEquals("", recipe.image)
        assertEquals(null, recipe.instructions)
        assertEquals(0, recipe.readyInMinutes)
        assertEquals(0, recipe.servings)
        assertEquals("", recipe.title)
        assertEquals(null, recipe.cuisines)
        assertEquals(null, recipe.diets)
        assertEquals(null, recipe.ingredients)
        assertEquals(2, recipe.missedIngredientCount)
    }
}