package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import eu.mobcomputing.dima.registration.R

@Composable
fun IngredientEditDialog(ingredientName: String, onClose: () -> Unit, onTrash: () -> Unit) {

    Dialog(
        onDismissRequest = { onClose() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(colorResource(id = R.color.pink_900)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    Text(
                        text = ingredientName,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ),
                        color = Color.White
                    )
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Icon",
                        tint = Color.White
                    )
                }

                NormalTextComponent(
                    value = stringResource(R.string.edit_ingredient_txt),
                )

                Row(Modifier.padding(top = 10.dp)) {

                    Button(
                        onClick = { onClose() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close Icon")
                    }


                    Button(
                        onClick = { onTrash() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Icon")

                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomEditDialog() {
    IngredientEditDialog(ingredientName = "Eggs", onClose = {}, onTrash = {})
}