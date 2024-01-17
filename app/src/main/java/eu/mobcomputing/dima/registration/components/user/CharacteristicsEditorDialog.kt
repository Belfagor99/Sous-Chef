package eu.mobcomputing.dima.registration.components.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import eu.mobcomputing.dima.registration.R


/**
 * Compose component that displays a dialog for editing user characteristics.
 *
 * @param onDismissRequest Callback to be invoked when the user dismisses the dialog.
 * @param onConfirmation Callback to be invoked when the user confirms the changes made in the dialog.
 * @param dialogTitle The title of the dialog, describing the characteristics being edited.
 * @param userCharacteristics List of user characteristics to be displayed and edited.
 */
@Composable
fun CharacteristicsEditorDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    userCharacteristics: List<String>
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = dialogTitle,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally)
                )


                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 125.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    items(userCharacteristics) { it ->
                        // Grid item Composable
                        ElevatedFilterChip(
                            selected = false,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            onClick = {},
                            label = {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal,
                                    ),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .wrapContentSize()
                                )
                            },
                            border = BorderStroke(1.dp,colorResource(id = R.color.pink_900)),

                            colors = SelectableChipColors(
                                disabledLabelColor = Color.Black ,
                                disabledContainerColor = colorResource(id = R.color.pink_100),
                                disabledLeadingIconColor = Color.White,
                                disabledSelectedContainerColor = Color.White,
                                disabledTrailingIconColor = Color.White,

                                containerColor = colorResource(id = R.color.pink_200),
                                labelColor = Color.Black,
                                leadingIconColor = Color.White,
                                trailingIconColor = Color.White,

                                selectedContainerColor = colorResource(id = R.color.pink_900),
                                selectedLabelColor = Color.White,
                                selectedLeadingIconColor = Color.White,
                                selectedTrailingIconColor = Color.White,
                            )

                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.pink_900)),
                    ) {
                        Text(
                            text="Dismiss",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.pink_900)),
                    ) {
                        Text(
                            text="Confirm",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun CharacteristicsEditorDialogPreview() {
    CharacteristicsEditorDialog(
        {},
        {},
        "select your allergies: ",
        listOf(
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
    )
}