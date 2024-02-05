package eu.mobcomputing.dima.registration.models

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class InstructionTest {

    @Test
    fun testInstructionSerialization() {
        val instruction = Instruction(step_num = 1, content = "Do something")

        val gson = Gson()
        val json = gson.toJson(instruction)
        val deserializedInstruction = gson.fromJson(json, Instruction::class.java)

        assertEquals(instruction, deserializedInstruction)
    }

    @Test
    fun testInstructionDefaultValues() {
        val instruction = Instruction(step_num = 1, content = "")

        assertEquals(1, instruction.step_num)
        assertEquals("", instruction.content)
    }

    @Test
    fun testInstructionCustomValues() {
        val instruction = Instruction(step_num = 2, content = "Follow these steps")

        assertEquals(2, instruction.step_num)
        assertEquals("Follow these steps", instruction.content)
    }
}