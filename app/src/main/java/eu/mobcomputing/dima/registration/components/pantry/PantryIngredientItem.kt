package eu.mobcomputing.dima.registration.components.pantry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Ingredient
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PantryIngredientItem(ingredient: Ingredient) {
    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
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


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = ingredient.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically)

            )

            if(ingredient.expiringDate!=null){
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterVertically)
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(colorResource(R.color.pink_900))
                        .wrapContentHeight()
                ){
                    Text(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            bottom = 5.dp,
                            top = 5.dp,
                            end = 20.dp
                        ),
                        text = SimpleDateFormat("dd/MM/YYYY").format(ingredient.expiringDate!!),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            }


        }
    }

}



@Preview
@Composable
fun AppPreview() {
    val ingredient = Ingredient(name = "Eggs", userQuantity = 2.0, unit = "units", expiringDate = Date("12/02/2021"))
    MaterialTheme {
        PantryIngredientItem(ingredient= ingredient,)
    }
}