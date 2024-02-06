package eu.mobcomputing.dima.registration.components.pantry

import android.annotation.SuppressLint
import android.app.Application
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
import eu.mobcomputing.dima.registration.viewmodels.PantryViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun PantryIngredientItem(ingredient: Ingredient, pantryViewModel: PantryViewModel) {
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
        onClick = {
            pantryViewModel.ingredientClicked = ingredient
            pantryViewModel.openIngredientDialog.value = true
        },
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
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .fillMaxWidth()

            )

            if (ingredient.expiringDate != null) {
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterVertically)
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(colorResource(R.color.pink_900))
                        .wrapContentHeight()
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 20.dp,
                                bottom = 5.dp,
                                top = 5.dp,
                                end = 20.dp
                            )
                            .align(Alignment.Center),
                        text = SimpleDateFormat("dd/mm/yyyy").format(ingredient.expiringDate!!),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
            }


        }
    }

}


@SuppressLint("SimpleDateFormat")
@Preview
@Composable
fun AppPreview() {

    val stringDate = "12/02/2021"
    val formatter: DateFormat = SimpleDateFormat("dd/mm/yyyy")
    val myDate: Date = formatter.parse(stringDate)!!

    val ingredient = Ingredient(
        name = "coconut cooking oil",
        userQuantity = 2.0,
        unit = "units",
        expiringDate = myDate
    )
    MaterialTheme {
        PantryIngredientItem(ingredient = ingredient, PantryViewModel(application = Application()))
    }
}