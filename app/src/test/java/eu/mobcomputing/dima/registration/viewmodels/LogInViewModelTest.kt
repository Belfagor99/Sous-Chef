package eu.mobcomputing.dima.registration.viewmodels

import androidx.navigation.NavController
import com.google.common.truth.Truth
import eu.mobcomputing.dima.registration.uiEvents.LogInUIEvent
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LogInViewModelTest{

    @Mock
    lateinit var navController: NavController

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun onEvent_EmailChanged_UpdateStateCorrectly() {
        val viewModel = LogInViewModel()
        val email = "john.smith@email.com"

        viewModel.onEvent(LogInUIEvent.EmailChanged(email), navController)

        Truth.assertThat(email).isEqualTo(viewModel.logInUIState.value.email)
    }

    @Test
    fun onEvent_PasswordChanged_UpdateStateCorrectly() {
        val viewModel = LogInViewModel()
        val password = "Password123"

        viewModel.onEvent(LogInUIEvent.PasswordChanged(password), navController)

        Truth.assertThat(password).isEqualTo(viewModel.logInUIState.value.password)
    }

}