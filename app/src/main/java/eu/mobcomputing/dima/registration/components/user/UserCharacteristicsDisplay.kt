package eu.mobcomputing.dima.registration.components.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.ui.theme.PurpleGrey40

/**
 * Compose component that displays user characteristics along with an option to edit.
 *
 * @param headerString The header text describing the type of user characteristics.
 * @param userCharacteristics List of user characteristics to be displayed.
 * @param clickOnEdit Callback to be invoked when the user clicks on the "Edit" button.
 */
@Composable
fun UserCharacteristicsDisplay(
    headerString: String,
    userCharacteristics: List<String>?,
    clickOnEdit: () -> Unit
) {

    Surface(
        color = colorResource(id = R.color.pink_50),
    ) {

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                ) {
                    Row {
                        Text(
                            text = headerString,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                                ),
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )


                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                }

                Button(
                    onClick = clickOnEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.pink_900)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "edit"
                    )
                    Text(
                        text = "Edit",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 4.dp),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.pink_100),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                    )
                }
            }


            if (userCharacteristics.isNullOrEmpty()) {
                Text(
                    text = "Seems I don't have this info obout you yet!",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                    ),
                    modifier = Modifier
                        .padding(40.dp)
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally)
                )

            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    items(userCharacteristics) { it ->
                        // Grid item
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        ) {
                            ElevatedSuggestionChip(
                                onClick = {},
                                label = {
                                    Text(
                                        text = it,
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = Color.White,
                                        ),
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .wrapContentSize()
                                    )
                                },
                                border = BorderStroke(1.dp, colorResource(id = R.color.pink_900)),
                                colors = ChipColors(
                                    labelColor = Color.White,
                                    disabledContainerColor = PurpleGrey40,
                                    containerColor = colorResource(id = R.color.pink_900),
                                    disabledLabelColor = PurpleGrey40,
                                    disabledLeadingIconContentColor = PurpleGrey40,
                                    leadingIconContentColor = PurpleGrey40,
                                    disabledTrailingIconContentColor = PurpleGrey40,
                                    trailingIconContentColor = PurpleGrey40
                                )
                            )
                        }
                    }
                }
            }

        }
    }
}


@Composable
@Preview
fun UserAllergiesDisplayPreview() {

    val userCharacteristics: List<String> = listOf(
        "Dairy",
        "Egg",
        "Gluten",
        "Grain",
        "Peanut",
        "Seafood",
        "Sesame",
        "Shellfish",
        "Peanuts",
        "Soy",
        "Sulfite",
        "Tree Nut",
        "Wheat",
    )

    UserCharacteristicsDisplay("Alergies", userCharacteristics, {})
}

@Composable
@Preview
fun UserDietDisplayPreview() {

    val userCharacteristics: List<String> = listOf(
        "Vegan",
        "Vegetarian",
        "Gluten free",
        "Lacto-\nVegetarian",
        "Pescetarian",
        "Omnivore",
    )

    UserCharacteristicsDisplay("Diet", userCharacteristics, {})
}