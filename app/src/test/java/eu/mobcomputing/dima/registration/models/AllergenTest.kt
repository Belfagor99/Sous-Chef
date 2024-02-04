package eu.mobcomputing.dima.registration.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AllergenTest {

    @Test
    fun testAllergenDefaultValues() {
        val allergen = Allergen(name = "Peanuts")

        assertEquals("Peanuts", allergen.name)
        assertFalse(allergen.selectedState.value)
    }

    @Test
    fun testAllergenStateChange() {
        val allergen = Allergen(name = "Milk")

        assertFalse(allergen.selectedState.value)

        allergen.selectedState.value = true

        assertTrue(allergen.selectedState.value)
    }
}