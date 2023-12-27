package eu.mobcomputing.dima.registration.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.mobcomputing.dima.registration.data.registration.SharedUserDataModelViewFactory
import eu.mobcomputing.dima.registration.data.home.HomeViewModel
import eu.mobcomputing.dima.registration.data.registration.SharedUserDataModel
import eu.mobcomputing.dima.registration.screens.HomeScreen
import eu.mobcomputing.dima.registration.screens.LogInScreen
import eu.mobcomputing.dima.registration.screens.PantryScreen
import eu.mobcomputing.dima.registration.screens.ProfileScreen
import eu.mobcomputing.dima.registration.screens.SearchScreen
import eu.mobcomputing.dima.registration.screens.SignInSuccessfulScreen
import eu.mobcomputing.dima.registration.screens.SignUpScreen
import eu.mobcomputing.dima.registration.screens.UserAllergiesScreen
import eu.mobcomputing.dima.registration.screens.UserDietScreen
import eu.mobcomputing.dima.registration.screens.WelcomeScreen
import kotlinx.coroutines.delay

@Composable
fun SetUpNavGraph(navController: NavHostController, homeViewModel: HomeViewModel) {
    val sharedViewModelFactory = SharedUserDataModelViewFactory()

    // Create a shared instance of SharedUserDataModel
    val sharedViewModel: SharedUserDataModel = viewModel(factory = sharedViewModelFactory)

    // Use LaunchedEffect to navigate when userLoggedIn changes
    LaunchedEffect(homeViewModel.userLoggedIn) {
        delay(100)
        if (homeViewModel.userLoggedIn.value) {
            navController.navigate(Screen.Home.route) {
                // Clear back stack to prevent going back to the WelcomeScreen
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            navController.navigate(Screen.Welcome.route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    )
    {
        composable(
            route = Screen.Welcome.route
        ) {
            WelcomeScreen(navController)
        }

        composable(
            route = Screen.LogIn.route
        ) {
            LogInScreen(navController)
        }

        composable(
            route = Screen.Register.route
        ) {
            SignUpScreen(navController)
        }

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(route = Screen.UserAllergies.route) {
            UserAllergiesScreen(navController = navController, sharedViewModel)
        }

        composable(route = Screen.UserDietScreen.route) {
            UserDietScreen(navController = navController, sharedViewModel)
        }

        composable(route = Screen.SignInSuccessfulScreen.route) {
            SignInSuccessfulScreen(navController = navController)
        }

        composable(route = Screen.PantryScreen.route) {
            PantryScreen(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }

    }
}