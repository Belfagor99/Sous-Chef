package eu.mobcomputing.dima.registration.components.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import eu.mobcomputing.dima.registration.models.Recipe

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCardItem (
    recipe : Recipe,
    navController : NavController
) {

    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentWidth()
            .wrapContentHeight(),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        ),
        onClick = {
            /*
             * convert object to json string
             * in order to pass the entire object to the next screen via navController
             */
            val recipeJSON = Gson().toJson(recipe)
            Log.v("HOME @ RECIPE CLICKED",recipeJSON)
            // Navigate to the new screen when the card is clicked
            //navController.navigate("addToFridge/${URLEncoder.encode(ingredientJSON, "utf-8")}")
        },
    ) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(13f/9f),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = recipe.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding (8.dp)

            )
        }
    }
}




@Preview
@Composable
fun RecipeCardItemPreview () {

    val myRecipe = Recipe(
        id= 631807,
        title= "Toasted Agnolotti (or Ravioli)",
        image = "https://spoonacular.com/recipeImages/631807-312x231.jpg",
        missedIngredientCount = 0,
    )
    RecipeCardItem(navController = rememberNavController(), recipe =myRecipe)
}