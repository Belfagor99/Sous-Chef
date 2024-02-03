package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.google.common.truth.Truth
import eu.mobcomputing.dima.registration.uiEvents.RegistrationUIEvent
import eu.mobcomputing.dima.registration.uiStates.RegistrationUIState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegistrationViewModelTest {

    @Mock
    lateinit var navController: NavController

    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun validateFirstName_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        viewModel.registrationUIState.value = RegistrationUIState(firstName = "John")

        viewModel.validateDataWithRules()

        Truth.assertThat(viewModel.registrationInProgress.value).isFalse()
    }

    @Test
    fun onEvent_FirstNameChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.FirstNameChanged("John"), navController, context)

        Truth.assertThat("John").isEqualTo(viewModel.registrationUIState.value.firstName)
    }

    @Test
    fun validateFirstName_And_LastName_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith"
        )
        viewModel.validateDataWithRules()
        Truth.assertThat(viewModel.registrationInProgress.value).isFalse()
    }

    @Test
    fun onEvent_LastNameChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.LastNameChanged("Smith"), navController, context)

        Truth.assertThat("Smith").isEqualTo(viewModel.registrationUIState.value.lastName)
    }
    @Test
    fun validateFirstName_And_LastName_And_Email_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith",
            email = "john.smith@email.com"
        )
        viewModel.validateDataWithRules()
        Truth.assertThat(viewModel.registrationInProgress.value).isFalse()

    }

    @Test
    fun onEvent_EmailChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.EmailChanged("john.smith@email.com"), navController, context)

        Truth.assertThat("john.smith@email.com").isEqualTo(viewModel.registrationUIState.value.email)
    }

    @Test
    fun onEvent_PasswordChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.PasswordChanged("Password123"), navController, context)

        Truth.assertThat("Password123").isEqualTo(viewModel.registrationUIState.value.password)
    }

    @Test
    fun testRegistrationButtonClicked() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))

        // Mock the required data
        viewModel.registrationUIState = mutableStateOf(RegistrationUIState())
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith",
            email = "john.smith@example.com",
            password = "Password123",
            firstNameError = false,
            lastNameError = false,
            emailError = false,
            passwordError = false
        )

        viewModel.onEvent(RegistrationUIEvent.RegistrationButtonClicked, navController, context)

        // Check if the registration process has started
        Truth.assertThat(viewModel.registrationInProgress.value).isTrue()
    }


    @Test
    fun testValidateDataWithRules() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))

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
        Truth.assertThat(viewModel.registrationUIState.value.passwordError).isFalse()
        Truth.assertThat(viewModel.registrationUIState.value.firstNameError).isTrue()
        Truth.assertThat(viewModel.registrationUIState.value.lastNameError).isTrue()
        Truth.assertThat(viewModel.registrationUIState.value.emailError).isTrue()
        Truth.assertThat(viewModel.allValidationPassed.value)
            .isFalse() // checking also that the button to register is not clickable
    }



}