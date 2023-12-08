package eu.mobcomputing.dima.registration

sealed class Screen(val route: String) {
    object Welcome: Screen(route = "welcome_screen")
    object Register: Screen(route = "register_screen")
    object LogIn: Screen(route = "login_screen")
}