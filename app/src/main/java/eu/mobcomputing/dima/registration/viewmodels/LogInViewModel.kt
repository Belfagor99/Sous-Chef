package eu.mobcomputing.dima.registration.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.uiEvents.LogInUIEvent
import eu.mobcomputing.dima.registration.uiStates.LogInUIState
import eu.mobcomputing.dima.registration.data.rules.Validator
import eu.mobcomputing.dima.registration.navigation.Screen

/**
 * ViewModel responsible for handling the logic related to the login screen.
 */
class LogInViewModel : ViewModel() {
    private val TAG = LogInViewModel::class.simpleName

    // Represents the current state of the login UI.
    var logInUIState = mutableStateOf(LogInUIState())

    // Indicates whether all input validations have passed.
    var allValidationPassed = mutableStateOf(false)

    // Indicates whether a login process is in progress.
    var logInInProgress = mutableStateOf(false)

    // Indicates whether the entered password is incorrect.
    var passwordIsIncorrect = mutableStateOf(false)

    // Indicates whether a password reset email has been sent.
    var passwordResetSent = mutableStateOf(false)

    /**
     * Handles UI events triggered by user interactions.
     *
     * @param event The UI event to be handled.
     * @param navController The NavController used for navigation.
     */
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

    /**
     * Validates user input based on predefined rules and updates UI state accordingly.
     */
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

    /**
     * Performs logging in the user using Firebase authentication.
     *
     * @param navController The NavController used for navigation.
     */
    private fun logIn(navController: NavController) {
        logInInProgress.value = true
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth
            .signInWithEmailAndPassword(logInUIState.value.email, logInUIState.value.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Login successful, navigate to the home screen.
                    passwordIsIncorrect.value = false
                    logInInProgress.value = false
                    navController.navigate(route = Screen.Home.route,
                        builder = {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    )

                } else if (logInUIState.value.numberOfRemainingSubmissions > 1) {
                    // Incorrect password, update state and remaining submission count.
                    passwordIsIncorrect.value = true
                    logInUIState.value.numberOfRemainingSubmissions -= 1
                    allValidationPassed.value = false
                } else {
                    // User has reached maximum number of login attempts, initiate the password reset.
                    resetPassword(email = logInUIState.value.email)
                }
            }
            .addOnFailureListener {
                // Login failed.
                logInInProgress.value = false
                Log.d(TAG, "Inside on Failure Lister")
                Log.d(TAG, "Exception = ${it.message}")
            }
    }

    /**
     * Initiates a password reset process using Firebase authentication.
     *
     * @param email The email address for which the password reset is requested.
     */
    fun resetPassword(email: String) {
        if (email.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    passwordResetSent.value = true
                    passwordIsIncorrect.value = false
                    logInUIState.value.numberOfRemainingSubmissions = 2
                }
                .addOnFailureListener {
                    Log.d(TAG, "Inside on Failure Lister Password Reset")
                    Log.d(TAG, "Exception = ${it.message}")
                }
        }
    }
    /**
     * Navigates to the sign-up screen.
     *
     * @param navController The NavController used for navigation.
     */
    fun redirectToSignUp(navController: NavController) {
        navController.navigate(route = Screen.Register.route)
    }
}

