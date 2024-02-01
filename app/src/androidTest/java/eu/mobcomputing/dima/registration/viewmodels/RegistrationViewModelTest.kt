package eu.mobcomputing.dima.registration.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.google.common.truth.Truth
import eu.mobcomputing.dima.registration.uiEvents.RegistrationUIEvent
import eu.mobcomputing.dima.registration.uiStates.RegistrationUIState
import junit.framework.TestCase.assertEquals
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
        assertEquals(true, viewModel.registrationInProgress.value)
    }


    @SuppressLint("CheckResult")
    @Test
    fun testEmailAlreadyRegisteredDialog() {
        val viewModel = RegistrationViewModel(mock(Application::class.java))

        // Mock the required data
        viewModel.registrationUIState = mutableStateOf(RegistrationUIState())
        viewModel.registrationUIState.value = RegistrationUIState(
            firstName = "John",
            lastName = "Smith",
            email = "sisilienka@gmail.com",
            password = "Password123",
            firstNameError = false,
            lastNameError = false,
            emailError = false,
            passwordError = false
        )

        viewModel.onEvent(RegistrationUIEvent.RegistrationButtonClicked, navController, context)
        Truth.assertThat(viewModel.registrationSuccessful.intValue == 1)

    }


}