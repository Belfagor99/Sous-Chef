package eu.mobcomputing.dima.registration.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.data.rules.Validator
import eu.mobcomputing.dima.registration.screens.Screen

class LogInViewModel : ViewModel() {
    private val TAG = LogInViewModel::class.simpleName

    var logInUIState = mutableStateOf(LogInUIState())
    var allValidationPassed = mutableStateOf(false)
    var logInInProgress = mutableStateOf(false)

    fun onEvent(event: LogInUIEvent, navController: NavController) {

        when (event) {
            is LogInUIEvent.EmailChanged -> {
                logInUIState.value = logInUIState.value.copy(
                    email = event.email
                )

            }

            is LogInUIEvent.PasswordChanged -> {
                logInUIState.value = logInUIState.value.copy(
                    password = event.password
                )
            }

            is LogInUIEvent.LogInButtonClicked -> {
                logIn(navController)
            }


        }
        validateDataWithRules()
    }

    private fun validateDataWithRules() {
        val emailValidation = Validator.validateEmail(
            logInUIState.value.email
        )
        val passwordValidation = Validator.validatePassword(
            logInUIState.value.password
        )

        logInUIState.value = logInUIState.value.copy(
            emailError = emailValidation.status,
            passwordError = passwordValidation.status
        )

        allValidationPassed.value = emailValidation.status && passwordValidation.status
    }


    private fun logIn(navController: NavController) {
        logInInProgress.value = true
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth
            .signInWithEmailAndPassword(logInUIState.value.email, logInUIState.value.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    logInInProgress.value = false
                    navController.navigate(route = Screen.Home.route)
                }
            }
            .addOnFailureListener {
                logInInProgress.value = false
                Log.d(TAG, "Inside on Failure Lister")
                Log.d(TAG, "Exception = ${it.message}")
            }
    }

}

