package eu.mobcomputing.dima.registration.data.login

data class LogInUIState (
    var email: String = "",
    var password: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var numberOfRemainingSubmissions: Int = 2,
    var passwordResetSent: Boolean = false
)