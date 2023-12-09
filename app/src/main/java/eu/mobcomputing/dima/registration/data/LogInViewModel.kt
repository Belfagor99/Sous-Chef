package eu.mobcomputing.dima.registration.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import eu.mobcomputing.dima.registration.data.rules.Validator

class LogInViewModel : ViewModel() {

    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValidationPassed = mutableStateOf(false)

    private val TAG = LogInViewModel::class.simpleName
    fun onEvent(event: UIEvent) {
        validateDataWithRules()
        when (event) {
            is UIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
            }

            is UIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )

            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is UIEvent.RegisterButtonClicked -> {
                register()
            }
        }
    }


    private fun register() {
        createFirebaseUser(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password )
    }

    private fun validateDataWithRules() {
        val firstNameValidation = Validator.validateFirstName(
            registrationUIState.value.firstName
        )
        val lastNameValidation = Validator.validateLastName(
            registrationUIState.value.lastName
        )
        val emailValidation = Validator.validateEmail(
            registrationUIState.value.email
        )
        val passwordValidation = Validator.validatePassword(
            registrationUIState.value.password
        )

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = firstNameValidation.status,
            lastNameError = lastNameValidation.status,
            emailError = emailValidation.status,
            passwordError = passwordValidation.status
        )

        allValidationPassed.value = firstNameValidation.status &&
                lastNameValidation.status &&
                emailValidation.status &&
                passwordValidation.status

    }

    private fun createFirebaseUser(email: String, password: String){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                Log.d(TAG, "Inside on Complete Lister")
                Log.d(TAG, "is Successful = ${it.isSuccessful}")
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside on Failure Lister")
                Log.d(TAG, "Exception = ${it.message}")

            }

    }

}
