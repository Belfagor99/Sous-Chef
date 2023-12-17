package eu.mobcomputing.dima.registration.navigation

sealed class Screen(val route: String) {
    data object Welcome: Screen(route = "welcome_screen")
    data object Register: Screen(route = "register_screen")
    data object LogIn: Screen(route = "login_screen")
    data object Home: Screen(route = "home_screen")
    data object UserAllergies: Screen(route = "user_allergies_screen")
    data object UserDietScreen: Screen(route = "user_diet_screen")
    data object SignInSuccessfulScreen: Screen(route = "sign_in_successful_screen")
}