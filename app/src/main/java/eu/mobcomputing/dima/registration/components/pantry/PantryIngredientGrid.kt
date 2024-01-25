package eu.mobcomputing.dima.registration.components.pantry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.models.Ingredient

@Composable
fun PantryIngredientGrid(ingredients: List<Ingredient>) {

        Column {
            // LazyVerticalGrid with filtered ingredients
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 350.dp),
                contentPadding = PaddingValues(10.dp),
            ) {
                items(ingredients) { ingredient ->
                    // Grid item Composable
                    PantryIngredientItem(
                        ingredient = ingredient,
                    )
                }
            }
        }

}

@Preview
@Composable
fun LazyVerticalGridComponentPreview() {
    // Preview your LazyVerticalGridComponent with sample data
    val sampleIngredients = List(30) { index -> Ingredient(id = index, name = "Ingr $index", userQuantity = index.toDouble()) }
    PantryIngredientGrid(
        ingredients = sampleIngredients,
    )
}