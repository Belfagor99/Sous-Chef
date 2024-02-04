package eu.mobcomputing.dima.registration.models

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test


class ResultTest {

    @Test
    fun testResultSerialization() {
        val ingredients = listOf(
            Ingredient(1,"Ingredient1",),
            Ingredient(2,"Ingredient2",)
        )

        val result = Result(results = ingredients)

        val gson = Gson()
        val json = gson.toJson(result)
        val deserializedResult = gson.fromJson(json, Result::class.java)

        assertEquals(result, deserializedResult)
    }

    @Test
    fun testResultDefaultValues() {
        val result = Result(results = emptyList())

        assertEquals(emptyList<Ingredient>(), result.results)
    }
}