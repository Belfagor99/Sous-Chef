package eu.mobcomputing.dima.registration.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.mobcomputing.dima.registration.screens.HomeScreen
import eu.mobcomputing.dima.registration.screens.LogInScreen
import eu.mobcomputing.dima.registration.screens.SignInSuccessfulScreen
import eu.mobcomputing.dima.registration.screens.SignUpScreen
import eu.mobcomputing.dima.registration.screens.UserAllergiesScreen
import eu.mobcomputing.dima.registration.screens.UserDietScreen
import eu.mobcomputing.dima.registration.screens.WelcomeScreen

@Composable
fun SetUpNavGraph( navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = Screen.Welcome.route)
    {
        composable(
            route = Screen.Welcome.route
        ){
            WelcomeScreen(navController)
        }

        composable(
            route = Screen.LogIn.route
        ){
            LogInScreen(navController)
        }

        composable(
            route = Screen.Register.route
        ){
            SignUpScreen(navController)
        }

        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController)
        }
        composable(route = Screen.UserAllergies.route){
            UserAllergiesScreen(navController = navController)
        }

        composable(route = Screen.UserDietScreen.route){
            UserDietScreen(navController = navController)
        }

        composable(route = Screen.SignInSuccessfulScreen.route){
            SignInSuccessfulScreen(navController = navController)
        }

    }
}