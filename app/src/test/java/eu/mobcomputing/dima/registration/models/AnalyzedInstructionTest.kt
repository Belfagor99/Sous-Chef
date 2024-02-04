package eu.mobcomputing.dima.registration.models

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class AnalyzedInstructionTest {

    @Test
    fun testAnalyzedInstructionSerialization() {
        val instruction1 = Instruction(step_num = 1, content = "Step 1")
        val instruction2 = Instruction(step_num = 2, content = "Step 2")

        val analyzedInstruction = AnalyzedInstruction(
            name = "Cooking",
            steps = listOf(instruction1, instruction2)
        )

        val gson = Gson()
        val json = gson.toJson(analyzedInstruction)
        val deserializedAnalyzedInstruction = gson.fromJson(json, AnalyzedInstruction::class.java)

        assertEquals(analyzedInstruction, deserializedAnalyzedInstruction)
    }

    @Test
    fun testAnalyzedInstructionDefaultValues() {
        val analyzedInstruction = AnalyzedInstruction(name = "Cooking", steps = emptyList())

        assertEquals("Cooking", analyzedInstruction.name)
        assertEquals(emptyList<Instruction>(), analyzedInstruction.steps)
    }
}