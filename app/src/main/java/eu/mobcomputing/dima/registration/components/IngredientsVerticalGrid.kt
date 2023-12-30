package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Ingredient

/**
 * Composable function representing a vertical grid of ingredient cards.
 *
 * This grid is populated with information about each ingredient provided in the [ingredients] list.
 * Each card includes the ingredient's image, name, and allows navigation to a detailed screen
 * for adding the ingredient to the fridge.
 *
 * @param ingredients The list of [Ingredient] objects to display in the grid.
 * @param navController The NavController for navigation within the application.
 */
@Composable
fun IngredientVerticalGrid(ingredients: List<Ingredient>, navController: NavController) {
    Surface (
        color = colorResource(id = R.color.pink_50),
        ){
        Column {
            // LazyVerticalGrid with filtered ingredients
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp),
                contentPadding = PaddingValues(10.dp),
            ) {
                items(ingredients) { ingredient ->
                    // Grid item Composable
                    IngredientCard(ingredient = ingredient, navController= navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun LazyVerticalGridComponentPreview() {
        // Preview your LazyVerticalGridComponent with sample data
        val sampleIngredients = List(30) { index -> Ingredient(id = index, name = "Ingr $index") }
        IngredientVerticalGrid(ingredients = sampleIngredients, navController = rememberNavController())
}