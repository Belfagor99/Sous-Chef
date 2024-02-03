package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import eu.mobcomputing.dima.registration.navigation.Screen
import eu.mobcomputing.dima.registration.uiEvents.RegistrationUIEvent
import eu.mobcomputing.dima.registration.uiStates.RegistrationUIState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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

        assertThat(viewModel.registrationInProgress.value).isFalse()
    }

    @Test
    fun onEvent_FirstNameChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.FirstNameChanged("John"), navController, context)

        assertThat("John").isEqualTo(viewModel.registrationUIState.value.firstName)
    }

    @Test
    fun validateFirstName_And_LastName_ValidInput_ReturnsFalse() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith"
        )
        viewModel.validateDataWithRules()
        assertThat(viewModel.registrationInProgress.value).isFalse()
    }

    @Test
    fun onEvent_LastNameChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(RegistrationUIEvent.LastNameChanged("Smith"), navController, context)

        assertThat("Smith").isEqualTo(viewModel.registrationUIState.value.lastName)
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
        assertThat(viewModel.registrationInProgress.value).isFalse()

    }

    @Test
    fun onEvent_EmailChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(
            RegistrationUIEvent.EmailChanged("john.smith@email.com"),
            navController,
            context
        )

        assertThat("john.smith@email.com")
            .isEqualTo(viewModel.registrationUIState.value.email)
    }

    @Test
    fun onEvent_PasswordChanged_UpdateStateCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

        viewModel.onEvent(
            RegistrationUIEvent.PasswordChanged("Password123"),
            navController,
            context
        )

        assertThat("Password123").isEqualTo(viewModel.registrationUIState.value.password)
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
        assertThat(viewModel.registrationInProgress.value).isTrue()
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
        assertThat(viewModel.registrationUIState.value.passwordError).isFalse()
        assertThat(viewModel.registrationUIState.value.firstNameError).isTrue()
        assertThat(viewModel.registrationUIState.value.lastNameError).isTrue()
        assertThat(viewModel.registrationUIState.value.emailError).isTrue()
        assertThat(viewModel.allValidationPassed.value)
            .isFalse() // checking also that the button to register is not clickable
    }

/*

Not good
    @Test
    fun createFirebaseUser_SuccessfulRegistration_NavigatesCorrectly() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))
        val navController = mock(NavController::class.java)

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
        // Mock FirebaseAuth and other dependencies

        viewModel.createFirebaseUser("john.smith@example.com", "Password123", navController, context)

        // Verify that navigation occurs as expected
        val uriCaptor = ArgumentCaptor.forClass(Uri::class.java) // Adjust Uri to the correct type
        verify(navController).navigate(uriCaptor.capture(), any(), any())

        val capturedUri = uriCaptor.value
        assertThat(capturedUri).isEqualTo(Screen.SignUnSuccessful.route)
    }*/
}