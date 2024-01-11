package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Allergen


@Composable
fun AllergenGrid(
    allergens: List<Allergen>,
    onAllergenClick: (Allergen) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        content = {
            items(count = allergens.size) { i ->
                AllergenItem(
                    allergens[i],
                    onAllergenClick
                )
            }
        }
    )
}

/**
 * AllergenItem: A composable function for displaying an individual allergen item within the grid.
 *
 * This composable creates a Button containing a Box with background color based on the allergen's selected
 * state. The allergen name is displayed in the center, and clicking on the item triggers the specified
 * [onAllergenClick] action.
 *
 * @param allergen An allergen object representing the item to be displayed.
 * @param onAllergenClick A lambda function to be executed when the allergen item is clicked.
 *
 */
@Composable
fun AllergenItem(
    allergen: Allergen,
    onAllergenClick: (Allergen) -> Unit
) {
    Button(
        onClick = { onAllergenClick(allergen) },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (allergen.selectedState.value) colorResource(id = R.color.pink_900) else colorResource(
                        id = R.color.pink_200
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = allergen.name,
                color = if (allergen.selectedState.value) colorResource(id = R.color.pink_50) else colorResource(
                    id = R.color.pink_900
                ),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}
