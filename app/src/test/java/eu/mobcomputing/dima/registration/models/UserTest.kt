package eu.mobcomputing.dima.registration.models

import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {

    @Test
    fun testUserDefaultValues() {
        val user = User()

        assertEquals("", user.firstName)
        assertEquals("", user.lastName)
        assertEquals("", user.email)
        assertEquals(DietType.OMNIVORE, user.dietType)
        assertEquals(emptyList<String>(), user.allergies)
        assertEquals(emptyList<Ingredient>(), user.ingredientsInPantry)
    }

    @Test
    fun testUserCustomValues() {
        val ingredients = listOf(
            Ingredient(1,"Ingredient1", "image", "original"),
            Ingredient(2,"Ingredient2", "image", "original")
        )

        val user = User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dietType = DietType.VEGAN,
            allergies = listOf("Peanuts", "Shellfish"),
            ingredientsInPantry = ingredients
        )

        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("john.doe@example.com", user.email)
        assertEquals(DietType.VEGAN, user.dietType)
        assertEquals(listOf("Peanuts", "Shellfish"), user.allergies)
        assertEquals(ingredients, user.ingredientsInPantry)
    }
}