package eu.mobcomputing.dima.registration.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.google.common.truth.Truth.assertThat
import eu.mobcomputing.dima.registration.uiEvents.RegistrationUIEvent
import eu.mobcomputing.dima.registration.uiStates.RegistrationUIState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegistrationViewModelTest {

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun validateFirstName_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel()
        viewModel.registrationUIState.value = RegistrationUIState(firstName = "John")

        viewModel.validateDataWithRules()

        assertThat(viewModel.registrationInProgress.value).isFalse()
    }

    @Test
    fun onEvent_FirstNameChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel()
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.FirstNameChanged("John"), navController)

        assertThat("John").isEqualTo(viewModel.registrationUIState.value.firstName)
    }

    @Test
    fun validateFirstName_And_LastName_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel()
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith"
        )
        viewModel.validateDataWithRules()
        assertThat(viewModel.registrationInProgress.value).isFalse()
    }

    @Test
    fun onEvent_LastNameChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel()
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.LastNameChanged("Smith"), navController)

        assertThat("Smith").isEqualTo(viewModel.registrationUIState.value.lastName)
    }

    @Test
    fun validateFirstName_And_LastName_And_Email_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel()
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith",
            email = "john.smith@email.com"
        )
        viewModel.validateDataWithRules()
        assertThat(viewModel.registrationInProgress.value).isFalse()

    }

    @Test
    fun onEvent_EmailChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel()
        val navController = mock(NavController::class.java)

        viewModel.onEvent(
            RegistrationUIEvent.EmailChanged("john.smith@email.com"),
            navController,

        )

        assertThat("john.smith@email.com")
            .isEqualTo(viewModel.registrationUIState.value.email)
    }

    @Test
    fun onEvent_PasswordChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel()
        val navController = mock(NavController::class.java)

        viewModel.onEvent(
            RegistrationUIEvent.PasswordChanged("Password123"),
            navController,

        )

        assertThat("Password123").isEqualTo(viewModel.registrationUIState.value.password)
    }



    @Test
    fun testValidateDataWithRules() {
        val viewModel = RegistrationViewModel()

        // Mock the required data
        viewModel.registrationUIState = mutableStateOf(RegistrationUIState())
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith",
            email = "john.smith@example.com",
            password = "password123",
            firstNameError = false,
            lastNameError = false,
            emailError = false,
            passwordError = false
        )

        viewModel.validateDataWithRules()
        assertThat(viewModel.registrationUIState.value.passwordError).isFalse()
        assertThat(viewModel.registrationUIState.value.firstNameError).isTrue()
        assertThat(viewModel.registrationUIState.value.lastNameError).isTrue()
        assertThat(viewModel.registrationUIState.value.emailError).isTrue()
        assertThat(viewModel.allValidationPassed.value)
            .isFalse() // checking also that the button to register is not clickable
    }

}