package eu.mobcomputing.dima.registration.uiStates

/**
 * Data class representing the state of the login user interface.
 *
 * @property email The current value of the email input field.
 * @property password The current value of the password input field.
 * @property emailError Indicates whether there is an error in the email input.
 * @property passwordError Indicates whether there is an error in the password input.
 * @property numberOfRemainingSubmissions The number of remaining login attempts before account lockout.
 * @property passwordResetSent Indicates whether a password reset email has been sent.
 */
data class LogInUIState (
    var email: String = "",
    var password: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var numberOfRemainingSubmissions: Int = 2,
    var passwordResetSent: Boolean = false
)