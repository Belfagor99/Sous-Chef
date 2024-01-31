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

}

