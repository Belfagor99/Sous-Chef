package eu.mobcomputing.dima.registration.uiEvents

/**
 * Sealed class representing UI events related to the login screen.
 */
sealed class LogInUIEvent {
    /**
     * Event indicating a change in the email input field.
     *
     * @param email The updated email entered by the user.
     */
    data class EmailChanged(val email: String): LogInUIEvent()
    /**
     * Event indicating a change in the password input field.
     *
     * @param password The updated email entered by the user.
     */
    data class PasswordChanged(val password: String): LogInUIEvent()
    /**
     * Event indicating the log in button has been clicked.
     *
     */
    data object LogInButtonClicked: LogInUIEvent()

}