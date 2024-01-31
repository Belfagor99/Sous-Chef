package eu.mobcomputing.dima.registration.data.rules

import com.google.common.truth.Truth
import org.junit.Test

class ValidatorTest {

    // First name tests
    @Test
    fun `validate first name - valid input`() {
        val result = Validator.validateFirstName("John")
        Truth.assertThat(result.status).isTrue()
    }

    @Test
    fun `validate first name - empty input`() {
        val result = Validator.validateFirstName("")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate first name - short input`() {
        val result = Validator.validateFirstName("Jo")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate first name - lowercase first letter`() {
        val result = Validator.validateFirstName("john")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate first name - contains empty space`() {
        val result = Validator.validateFirstName("J ohn")
        Truth.assertThat(result.status).isFalse()
    }


    // Last name tests
    @Test
    fun `validate last name - valid input`() {
        val result = Validator.validateLastName("Smith")
        Truth.assertThat(result.status).isTrue()
    }

    @Test
    fun `validate last name - empty input`() {
        val result = Validator.validateLastName("")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate last name - short input`() {
        val result = Validator.validateLastName("Sm")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate last name - lowercase first letter`() {
        val result = Validator.validateLastName("smith")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate last name - contains empty space`() {
        val result = Validator.validateLastName("S mith")
        Truth.assertThat(result.status).isFalse()
    }

    // E-mail tests
    @Test
    fun `validate email - valid input`() {
        val result = Validator.validateEmail("te.st@example.com")
        Truth.assertThat(result.status).isTrue()
    }

    @Test
    fun `validate email - invalid input dot in the domain part`() {
        val result = Validator.validateEmail("user@.example.com")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate email - invalid input consecutive dots in the domain part`() {
        val result = Validator.validateEmail("user@example..com")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate email - invalid input missing domain part`() {
        val result = Validator.validateEmail(" user@com")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate email - invalid input missing top-level domain`() {
        val result = Validator.validateEmail("user@domain")
        Truth.assertThat(result.status).isFalse()
    }


    @Test
    fun `validate email - contains empty space`() {
        val result = Validator.validateEmail("test @example.com")
        Truth.assertThat(result.status).isFalse()
    }

    // Password tests
    @Test
    fun `validate password - valid input`() {
        val result = Validator.validatePassword("Secure123")
        Truth.assertThat(result.status).isTrue()
    }

    @Test
    fun `validate password - short input`() {
        val result = Validator.validatePassword("short")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate password - contains space`() {
        val result = Validator.validatePassword("secure 123")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate password - does not contain upper letter`() {
        val result = Validator.validatePassword("secure123")
        Truth.assertThat(result.status).isFalse()
    }

    @Test
    fun `validate password - does not contain enough numbers`() {
        val result = Validator.validatePassword("Secure23")
        Truth.assertThat(result.status).isFalse()
    }

}