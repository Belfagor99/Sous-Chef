package eu.mobcomputing.dima.registration.uiStates

import com.google.common.truth.Truth
import org.junit.Test

class LogInUIStateTest {
    @Test
    fun `error flags are initially false`() {
        val initialState = LogInUIState()

        val result =
            initialState.emailError || initialState.passwordError
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `update email`() {
        val initialState = LogInUIState()
        val newState = initialState.copy(email = "john.smith@email.com")

        Truth.assertThat(newState.email).isEqualTo("john.smith@email.com")
        Truth.assertThat(newState.password).isEqualTo(initialState.password)
        Truth.assertThat(newState.emailError).isEqualTo(initialState.emailError)
        Truth.assertThat(newState.passwordError).isEqualTo(initialState.passwordError)
    }

    @Test
    fun `update password`() {
        val initialState = LogInUIState()
        val newState = initialState.copy(password = "Password123")

        Truth.assertThat(newState.password).isEqualTo("Password123")
        Truth.assertThat(newState.email).isEqualTo(initialState.email)
        Truth.assertThat(newState.emailError).isEqualTo(initialState.emailError)
        Truth.assertThat(newState.passwordError).isEqualTo(initialState.passwordError)
    }
}