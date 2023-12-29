package eu.mobcomputing.dima.registration.data.rules

import android.util.Patterns

// Validator object contains functions for validating different user input
object Validator {

    /**
     *  Function to validate the format of a first name.
     *
     * @param firstName name to be validated.
     */
    fun validateFirstName(firstName: String): ValidationResult {
        return ValidationResult((
                firstName.isNotEmpty()) &&
                firstName.length >= 3 &&
                firstName[0].isUpperCase() &&
                firstName.all { it.isLetter() })
    }

    /**
     *  Function to validate the format of a last name.
     *
     * @param lastName surname to be validated.
     */
    fun validateLastName(lastName: String): ValidationResult {
        return ValidationResult((
                lastName.isNotEmpty()) &&
                lastName.length >= 3 &&
                lastName[0].isUpperCase() &&
                lastName.all { it.isLetter() })
    }

    /**
     *  Function to validate the format of an e-mail.
     *
     * @param email e-mail to be validated.
     */
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (email.isNotEmpty()) &&
                    Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    /**
     *  Function to validate the format of a password.
     *
     * @param password password to be validated.
     */
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (password.isNotEmpty()) &&
            password.length >= 8
        )
    }
}

// Data class to represent the result of a validation
data class ValidationResult(
    val status: Boolean = false
)