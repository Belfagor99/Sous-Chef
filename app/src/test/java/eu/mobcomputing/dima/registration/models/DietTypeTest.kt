package eu.mobcomputing.dima.registration.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DietTypeTest {

    @Test
    fun testDietTypeValues() {
        assertEquals("vegan", DietType.VEGAN.diet)
        assertEquals("vegetarian", DietType.VEGETARIAN.diet)
        assertEquals("gluten free", DietType.GLUTEN_FREE.diet)
        assertEquals("lacto vegetarian", DietType.LACTOSE_VEGETARIAN.diet)
        assertEquals("pescetarian", DietType.PESCETARIAN.diet)
        assertEquals("omnivore", DietType.OMNIVORE.diet)
    }

    @Test
    fun testDietOptionDefaultValues() {
        val dietOption = DietOption(type = DietType.VEGAN, icon = 1)

        assertEquals(DietType.VEGAN, dietOption.type)
        assertEquals(1, dietOption.icon)
        assertTrue(!dietOption.selected.value)
    }

}