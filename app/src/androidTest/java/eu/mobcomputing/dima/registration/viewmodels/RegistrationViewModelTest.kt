package eu.mobcomputing.dima.registration.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.google.common.truth.Truth
import eu.mobcomputing.dima.registration.uiEvents.RegistrationUIEvent
import eu.mobcomputing.dima.registration.uiStates.RegistrationUIState
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegistrationViewModelTest {
    @Mock
    lateinit var navController: NavController

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun testRegistrationButtonClicked() {
        val viewModel = RegistrationViewModel()

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

        viewModel.onEvent(RegistrationUIEvent.RegistrationButtonClicked, navController)

        // Check if the registration process has started
        Truth.assertThat(viewModel.registrationInProgress.value).isTrue()
    }

}