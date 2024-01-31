package eu.mobcomputing.dima.registration.uiStates

import com.google.common.truth.Truth
import org.junit.Test

class RegistrationUIStateTest {

    @Test
    fun `error flags are initially false`() {
        val initialState = RegistrationUIState()

        val result =
            initialState.emailError || initialState.passwordError || initialState.firstNameError || initialState.lastNameError
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `update first name`() {
        val initialState = RegistrationUIState()
        val newState = initialState.copy(firstName = "John")

        Truth.assertThat(newState.firstName).isEqualTo("John")
        Truth.assertThat(newState.lastName).isEqualTo(initialState.lastName)
        Truth.assertThat(newState.email).isEqualTo(initialState.email)
        Truth.assertThat(newState.password).isEqualTo(initialState.password)
        Truth.assertThat(newState.firstNameError).isEqualTo(initialState.firstNameError)
        Truth.assertThat(newState.lastNameError).isEqualTo(initialState.lastNameError)
        Truth.assertThat(newState.emailError).isEqualTo(initialState.emailError)
        Truth.assertThat(newState.passwordError).isEqualTo(initialState.passwordError)
    }

    @Test
    fun `update last name`() {
        val initialState = RegistrationUIState()
        val newState = initialState.copy(lastName = "Smith")

        Truth.assertThat(newState.lastName).isEqualTo("Smith")
        Truth.assertThat(newState.firstName).isEqualTo(initialState.firstName)
        Truth.assertThat(newState.email).isEqualTo(initialState.email)
        Truth.assertThat(newState.password).isEqualTo(initialState.password)
        Truth.assertThat(newState.firstNameError).isEqualTo(initialState.firstNameError)
        Truth.assertThat(newState.lastNameError).isEqualTo(initialState.lastNameError)
        Truth.assertThat(newState.emailError).isEqualTo(initialState.emailError)
        Truth.assertThat(newState.passwordError).isEqualTo(initialState.passwordError)
    }

    @Test
    fun `update email`() {
        val initialState = RegistrationUIState()
        val newState = initialState.copy(email = "john.smith@email.com")

        Truth.assertThat(newState.email).isEqualTo("john.smith@email.com")
        Truth.assertThat(newState.firstName).isEqualTo(initialState.firstName)
        Truth.assertThat(newState.lastName).isEqualTo(initialState.lastName)
        Truth.assertThat(newState.password).isEqualTo(initialState.password)
        Truth.assertThat(newState.firstNameError).isEqualTo(initialState.firstNameError)
        Truth.assertThat(newState.lastNameError).isEqualTo(initialState.lastNameError)
        Truth.assertThat(newState.emailError).isEqualTo(initialState.emailError)
        Truth.assertThat(newState.passwordError).isEqualTo(initialState.passwordError)
    }

    @Test
    fun `update password`() {
        val initialState = RegistrationUIState()
        val newState = initialState.copy(password = "Password123")

        Truth.assertThat(newState.password).isEqualTo("Password123")
        Truth.assertThat(newState.firstName).isEqualTo(initialState.firstName)
        Truth.assertThat(newState.lastName).isEqualTo(initialState.lastName)
        Truth.assertThat(newState.email).isEqualTo(initialState.email)
        Truth.assertThat(newState.firstNameError).isEqualTo(initialState.firstNameError)
        Truth.assertThat(newState.lastNameError).isEqualTo(initialState.lastNameError)
        Truth.assertThat(newState.emailError).isEqualTo(initialState.emailError)
        Truth.assertThat(newState.passwordError).isEqualTo(initialState.passwordError)
    }


}

