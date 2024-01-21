package eu.mobcomputing.dima.registration.components.recipe_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import eu.mobcomputing.dima.registration.models.Ingredient

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientRowItem(
    ingredient : Ingredient
){

    val BASE_IMG_URL="https://spoonacular.com/cdn/ingredients_100x100/"

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
        onClick = {},
    ) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = BASE_IMG_URL+ingredient.image,
                contentDescription = "recipe image",
                modifier = Modifier
                    .size(100.dp),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = ingredient.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,

            )

            Text(
                text = " ${ingredient.amount} ${ingredient.unit}",
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,

            )

        }
    }



}

@Composable
@Preview(showBackground = true)
fun IngredientRowItemPreview(){
    val ingr = Ingredient(id=0, name = "egg","", amount = 10.0, unit = "g")

    IngredientRowItem(ingr)

}