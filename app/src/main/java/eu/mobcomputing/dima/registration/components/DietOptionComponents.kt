package eu.mobcomputing.dima.registration.components

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.DietOption


/**
 * DietOptionItem: A composable function for displaying an individual diet option item.
 *
 * This composable creates a Column containing a Box with a background color based on the diet option's selected state.
 * The diet option icon is displayed in the center, and an optional check icon is shown if the option is selected.
 * Clicking on the item triggers the specified [onDietOptionClick] action.
 *
 * @param dietOption A diet option object representing the item to be displayed.
 * @param onDietOptionClick A lambda function to be executed when the diet option item is clicked.
 *
 */
@Composable
fun DietOptionItem(
    dietOption: DietOption,
    onDietOptionClick: (DietOption) -> Unit
) {
    val isSelected = dietOption.selected.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onDietOptionClick(dietOption) })
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = if (isSelected) colorResource(id = R.color.pink_900) else colorResource(
                        id = R.color.pink_200
                    )
                )
        ) {
            Image(
                painter = painterResource(id = dietOption.icon),
                contentDescription = dietOption.type.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = dietOption.type.diet,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = dietOption.type.diet },
        )
    }
}

/**
 * DietOptionList: A composable function for displaying a list of diet option items.
 *
 * This composable uses LazyVerticalGrid to create a grid layout with adaptive columns based on the provided
 * diet options. Each diet option is represented by a DietOptionItem, and clicking on an item triggers the
 * specified [onDietClick] action.
 *
 * @param dietOptions A list of diet options to be displayed in the list.
 * @param onDietClick A lambda function to be executed when a diet option item is clicked.
 *
 */
@Composable
fun DietOptionList(
    dietOptions: List<DietOption>,
    onDietClick: (DietOption) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        content = {
            items(count = dietOptions.size) { i ->
                DietOptionItem(
                    dietOptions[i],
                    onDietClick
                )
            }
        }
    )

}
