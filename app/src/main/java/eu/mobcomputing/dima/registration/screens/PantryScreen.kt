package eu.mobcomputing.dima.registration.screens

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.IngredientEditDialog
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
    viewModel: PantryViewModel = hiltViewModel()
) {

    // Observe the LiveData containing the list of Ingredients
    val ingredientsList = viewModel.ingredients.observeAsState()
    val isNetworkAvailable = viewModel.connectionStatus.observeAsState()
    // State for holding the search text
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        bottomBar = {
            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 1
            )

        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.pink_50)),
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
                            PantryIngredientGrid(ingredients = it, pantryViewModel = viewModel)
                        } else {

                            // Ingredients not found
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                HeaderTextComponent(
                                    value = stringResource(R.string.no_ingredient_message)
                                )
                            }
                        }
                    }
                }
            }
        }

        // if ingredient has been clicked to open a dialog
        if (viewModel.openIngredientDialog.value) {
            val context = LocalContext.current
            IngredientEditDialog(
                ingredientName = viewModel.ingredientClicked.name,
                onClose = { viewModel.openIngredientDialog.value = false },
                onTrash = { if (viewModel.ingredientClicked.name != "") {
                    viewModel.removeFromPantry(listOf(viewModel.ingredientClicked))
                    Toast.makeText(context, "Ingredient deleted", Toast.LENGTH_SHORT).show()
                    viewModel.openIngredientDialog.value = false
                }
                    else{
                        Toast.makeText(context, "Ingredient not found", Toast.LENGTH_SHORT).show()
                }

                }
            )

        }

        if (isNetworkAvailable.value == false) {
            val context = LocalContext.current
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Connection Lost")
            builder.setMessage("We lost connection to the server. Please make sure your connection works and restart the app")

            builder.setPositiveButton("Ok") { _, _ ->
                (context as Activity).finishAffinity()
            }

            val dialog = builder.create()
            dialog.show()
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