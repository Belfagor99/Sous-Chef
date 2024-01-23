package eu.mobcomputing.dima.registration.components.home

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
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.models.Recipe


@Composable
fun RecipeGrid(recipes: List<Recipe>, navController: NavController) {
    Surface (
        color = colorResource(id = R.color.pink_50),
    ){
        Column {
            // LazyVerticalGrid with filtered ingredients
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 250.dp),
                contentPadding = PaddingValues(10.dp),
            ) {
                items(recipes) { it ->
                    // Grid item Composable
                    RecipeCardItem(it,navController= navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun LazyVerticalGridComponentPreview() {
    // Preview your LazyVerticalGridComponent with sample data

    val sampleRecipe : List<Recipe> = listOf(
        Recipe(id=0,title="1", image = "", missedIngredientCount = 0),
        Recipe(id=0,title="2", image = "", missedIngredientCount = 0),
        Recipe(id=0,title="3", image = "", missedIngredientCount = 0),
        Recipe(id=0,title="4", image = "", missedIngredientCount = 0),
        Recipe(id=0,title="5", image = "", missedIngredientCount = 0),
        Recipe(id=0,title="6", image = "", missedIngredientCount = 0)
    )

    RecipeGrid(recipes = sampleRecipe, navController = rememberNavController())
}