package eu.mobcomputing.dima.registration.uiEvents

/**
 * Sealed class representing UI events related to the registration screen.
 */
sealed class RegistrationUIEvent {
    /**
     * Event indicating a change in the first name input field.
     *
     * @param firstName The updated first name entered by the user.
     */
    data class FirstNameChanged(val firstName: String): RegistrationUIEvent()

    /**
     * Event indicating a change in the last name input field.
     *
     * @param lastName The updated last name entered by the user.
     */
    data class LastNameChanged(val lastName: String): RegistrationUIEvent()

    /**
     * Event indicating a change in the email input field.
     *
     * @param email The updated email entered by the user.
     */
    data class EmailChanged(val email: String): RegistrationUIEvent()

    /**
     * Event indicating a change in the password input field.
     *
     * @param password The updated password entered by the user.
     */
    data class PasswordChanged(val password: String): RegistrationUIEvent()

    /**
     * Event indicating that the registration button has been clicked.
     */
    data object RegistrationButtonClicked: RegistrationUIEvent()
}