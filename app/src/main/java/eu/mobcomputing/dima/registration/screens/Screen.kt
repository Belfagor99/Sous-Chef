package eu.mobcomputing.dima.registration.screens

sealed class Screen(val route: String) {
    data object Welcome: Screen(route = "welcome_screen")
    data object Register: Screen(route = "register_screen")
    data object LogIn: Screen(route = "login_screen")
}