package eu.mobcomputing.dima.registration.uiStates


/**
 * Data class representing the state of the registration user interface.
 *
 * @property firstName The current value of the first name input field.
 * @property lastName The current value of the last name input field.
 * @property email The current value of the email input field.
 * @property password The current value of the password input field.
 * @property firstNameError Indicates whether there is an error in the first name input.
 * @property lastNameError Indicates whether there is an error in the last name input.
 * @property emailError Indicates whether there is an error in the email input.
 * @property passwordError Indicates whether there is an error in the password input.
 */
data class RegistrationUIState(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",

    var firstNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false
)