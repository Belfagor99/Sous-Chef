package eu.mobcomputing.dima.registration.data.rules

import android.util.Patterns

object Validator {

    fun validateFirstName(firstName: String): ValidationResult {
        return ValidationResult((
                !firstName.isNullOrEmpty()) &&
                firstName.length >= 3 &&
                firstName[0].isUpperCase() &&
                firstName.all { it.isLetter() })
    }

    fun validateLastName(lastName: String): ValidationResult {
        return ValidationResult((
                !lastName.isNullOrEmpty()) &&
                lastName.length >= 3 &&
                lastName[0].isUpperCase() &&
                lastName.all { it.isLetter() })
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty()) &&
                    Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty()) &&
            password.length >= 8
        )
    }
}

data class ValidationResult(
    val status: Boolean = false
)