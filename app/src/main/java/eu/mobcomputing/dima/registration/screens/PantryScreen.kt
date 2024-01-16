package eu.mobcomputing.dima.registration.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.SearchBar
import eu.mobcomputing.dima.registration.components.pantry.PantryIngredientGrid
import eu.mobcomputing.dima.registration.viewmodels.PantryViewModel

/**
 * Composable function representing the Pantry screen of the application.
 *
 * @param navController NavController for navigating between screens.
 */
@Composable
fun PantryScreen(
    navController: NavController,
    viewModel: PantryViewModel = hiltViewModel() ) {

    // Observe the LiveData containing the list of Ingredients
    val ingredientsList = viewModel.ingredients.observeAsState()


    // State for holding the search text
    var searchText by remember { mutableStateOf("") }




    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.SpaceBetween
        ) {

            SearchBar(
                onSearch = { newSearchText ->
                    searchText = newSearchText
                    // Call the filter function in the ViewModel
                    viewModel.filterIngredients(newSearchText)
                },

                onSearchTextChange = { newSearchText ->
                    searchText = newSearchText
                    // Call the filter function in the ViewModel
                    viewModel.filterIngredients(newSearchText)
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                ), thickness = 1.dp, color = Color.LightGray
            )

            Box(modifier = Modifier.weight(weight = 1f, fill = true)) {
                ingredientsList.value?.let {
                    if (it.isNotEmpty()) {
                        //populate grid if ingredients found
                        PantryIngredientGrid(ingredients = it)
                    } else {

                        // Ingredients not found
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            HeaderTextComponent(
                                value = "No ingredients found!\n\n Let's add some ingredients to the pantry via the button below"
                            )
                        }

                    }

                }
            }


            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 1
            )

        }

    }
}

/**
 * Preview annotation for previewing the PantryScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewPantryScreen() {
    PantryScreen(
        navController = rememberNavController(),
        viewModel = PantryViewModel(Application()),

    )
}