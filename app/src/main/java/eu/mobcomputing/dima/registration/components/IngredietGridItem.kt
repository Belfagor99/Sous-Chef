package eu.mobcomputing.dima.registration.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Ingredient
import java.net.URLEncoder

/**
 * Composable function representing a card displaying information about a specific ingredient.
 *
 * This card includes the ingredient's image, name, and allows navigation to a detailed screen
 * for adding the ingredient to the fridge.
 *
 * @param ingredient The [Ingredient] object to display information about.
 * @param navController The NavController for navigation within the application.
 */
@Composable
fun IngredientCard(ingredient: Ingredient, navController: NavController) {
    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
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
            val ingredientJSON = Gson().toJson(ingredient)
            Log.v("INGREDIENT CLICKED",ingredientJSON)
            // Navigate to the new screen when the card is clicked
            navController.navigate("addToFridge/${URLEncoder.encode(ingredientJSON, "utf-8")}")
        },
    ) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = ingredient.name,
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
fun AppPreview() {
    val ingredient = Ingredient(name = "Eggs")
    MaterialTheme {
        IngredientCard(ingredient= ingredient, navController = rememberNavController())
    }
}