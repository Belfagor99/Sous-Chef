package eu.mobcomputing.dima.registration.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataModelViewFactory
import eu.mobcomputing.dima.registration.viewmodels.HomeViewModel
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataViewModel
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.screens.AddIngredientToPantry
import eu.mobcomputing.dima.registration.screens.HomeScreen
import eu.mobcomputing.dima.registration.screens.LogInScreen
import eu.mobcomputing.dima.registration.screens.PantryScreen
import eu.mobcomputing.dima.registration.screens.ProfileScreen
import eu.mobcomputing.dima.registration.screens.SearchIngredientScreen
import eu.mobcomputing.dima.registration.screens.SearchScreen
import eu.mobcomputing.dima.registration.screens.SignUnSuccessfulScreen
import eu.mobcomputing.dima.registration.screens.SignUpScreen
import eu.mobcomputing.dima.registration.screens.UserAllergiesScreen
import eu.mobcomputing.dima.registration.screens.UserDietScreen
import eu.mobcomputing.dima.registration.screens.WelcomeScreen
import kotlinx.coroutines.delay
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SetUpNavGraph(navController: NavHostController, homeViewModel: HomeViewModel) {

    // Create a shared instance of SharedUserDataModel using the SharedUserDataModelViewFactory
    val sharedViewModelFactory = SharedUserDataModelViewFactory()
    val sharedViewModel: SharedUserDataViewModel = viewModel(factory = sharedViewModelFactory)

    // Use LaunchedEffect to navigate when userLoggedIn changes
    LaunchedEffect(homeViewModel.userLoggedIn) {
        delay(100)
        if (homeViewModel.userLoggedIn.value) {
            // If user is logged in, navigate to the Home screen
            navController.navigate(Screen.Home.route) {
                // Clear back stack to prevent going back to the WelcomeScreen
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            // If user is not logged in, navigate to the Welcome screen
            navController.navigate(Screen.Welcome.route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    // NavHost composable defines the navigation structure
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
        )
    {
        // composable functions for different screens

        // Welcome Screen
        composable(
            route = Screen.Welcome.route
        ) {
            WelcomeScreen(navController)
        }

        // Log in Screen
        composable(
            route = Screen.LogIn.route
        ) {
            LogInScreen(navController)
        }

        // Sign up Screen
        composable(
            route = Screen.Register.route
        ) {
            SignUpScreen(navController)
        }

        // Home Screen
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        // User Allergies Screen
        composable(route = Screen.UserAllergies.route) {
            UserAllergiesScreen(navController = navController, sharedViewModel)
        }

        // User Diet Screen
        composable(route = Screen.UserDiet.route) {
            UserDietScreen(navController = navController, sharedViewModel)
        }

        // Successful signing in Screen
        composable(route = Screen.SignUnSuccessful.route) {
            SignUnSuccessfulScreen(navController = navController)
        }

        // Pantry Screen
        composable(route = Screen.Pantry.route) {
            PantryScreen(navController = navController)
        }

        // Profile Screen
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Search Screen
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)
        }


        composable(route = Screen.SearchIngredientScreen.route){
            SearchIngredientScreen(navController = navController)
        }

        composable(route = Screen.AddIngredientToFridgeScreen.route) { backStackEntry ->
            // Extract the ingredientJSON from the route
            val ingredientJSON = backStackEntry.arguments?.getString("ingredient") ?: ""

            //convert it back into object
            val ingredient: Ingredient? = try {
                val decodedString = URLDecoder.decode(ingredientJSON, "utf-8")
                Gson().fromJson(decodedString, Ingredient::class.java)
            } catch (e: Exception) {
                null
            }

            // Display the details screen using the extracted obj
            ingredient?.let { AddIngredientToPantry(navController= navController, ingredient = it) }
        }


    }

}