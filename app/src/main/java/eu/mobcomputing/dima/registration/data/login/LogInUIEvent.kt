package eu.mobcomputing.dima.registration.data.login

sealed class LogInUIEvent {
    data class EmailChanged(val email: String): LogInUIEvent()
    data class PasswordChanged(val password: String): LogInUIEvent()
    data object LogInButtonClicked: LogInUIEvent()

}