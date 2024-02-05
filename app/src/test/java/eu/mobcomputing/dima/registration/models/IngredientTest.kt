package eu.mobcomputing.dima.registration.models

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class IngredientTest {

    @Test
    fun testIngredientSerialization() {
        val ingredient = Ingredient(
            id = 1,
            name = "Tomato",
        )

        val gson = Gson()
        val json = gson.toJson(ingredient)
        val deserializedIngredient = gson.fromJson(json, Ingredient::class.java)

        assertEquals(ingredient, deserializedIngredient)
    }

    @Test
    fun testIngredientDefaultValues() {
        val ingredient = Ingredient()

        assertEquals(0, ingredient.id)
        assertEquals("", ingredient.name)
        assertEquals("", ingredient.image)
        assertEquals("", ingredient.original)
        assertEquals(0.0, ingredient.amount, 0.001)
        assertEquals("", ingredient.unit)
        assertEquals("", ingredient.consistency)
        assertEquals(null, ingredient.expiringDate)
        assertEquals(0.0, ingredient.userQuantity, 0.001)
        assertEquals(emptyList<String>(), ingredient.possibleUnits)
    }

    @Test
    fun testConvertedIngredientSerialization() {
        val convertedIngredient = ConvertedIngredient(
            sourceAmount = 100.0,
            sourceUnit = "grams",
            targetAmount = 0.22,
            targetUnit = "pounds"
        )

        val gson = Gson()
        val json = gson.toJson(convertedIngredient)
        val deserializedConvertedIngredient = gson.fromJson(json, ConvertedIngredient::class.java)

        assertEquals(convertedIngredient, deserializedConvertedIngredient)
    }

    @Test
    fun testConvertedIngredientDefaultValues() {
        val convertedIngredient = ConvertedIngredient()

        assertEquals(0.0, convertedIngredient.sourceAmount, 0.001)
        assertEquals("", convertedIngredient.sourceUnit)
        assertEquals(0.0, convertedIngredient.targetAmount, 0.001)
        assertEquals("", convertedIngredient.targetUnit)
    }
}